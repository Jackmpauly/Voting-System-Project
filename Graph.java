import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Graph extends JPanel {

    private static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN};
    private static int count = 0;
    private static Simulation sim = new Simulation();

    public void start() {
        JFrame frame = new JFrame("My Graph");
        Graph canvas = new Graph();
        frame.add(canvas);
        frame.setSize(500, 500);
        // frame.pack();
        frame.setVisible(true);

        // Setting up our simulation
        sim.createManualCandidate();
        sim.generateAllVoters();
        System.out.println("-----------STARTING SIMULATION-----------");

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    System.out.println("Spacebar is pressed!");
                    accelerateVote(canvas);
                }
            }
        });
    }

    public static void drawDot(Graphics g, int x, int y, int radius, int colorIdx) {
        // increase x and y by 250 to give them the correct coords on the graph
        x+=250;
        y+=250;

        // Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN};
        // Set the color of the circle to the index of colors and draw a filled circle
        g.setColor(colors[colorIdx]);
        g.fillOval(x - radius, 500 - y - radius, radius * 2, radius * 2);

        // Draw the stroke of the circle in black
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, 500 - y - radius, radius * 2, radius * 2);
    }

    public static void drawLine(Graphics g, int x1, int y1, int x2, int y2, int colorIdx) {
        // increase x and y by 250 to give them the correct coords on the graph
        x1+=250;
        x2+=250;
        y1+=250;
        y2+=250;

        // System.out.println(colorIdx);

        float re = colors[colorIdx].getRed() / 255.0f;
        float gr = colors[colorIdx].getGreen() / 255.0f;
        float bl = colors[colorIdx].getBlue() / 255.0f;
        float alpha = 0.4f;
        // System.out.println("RGB: " + re + ", " + gr + ", "  + bl);
        g.setColor( new Color(re, gr, bl, alpha) );
        g.drawLine(x1, 500 - y1, x2, 500 - y2);
        // g.setColor( colors[colorIdx] );
    }

    public void accelerateVote(Graph c) {
        System.out.println("-----------NEXT VOTING ROUND-----------");
        sim.removeLastPlaceCandidate();
        sim.resetActiveCandidateVoteCounts();
        c.repaint();
    }

    public void paint(Graphics g) {     
        // Drawing the grid
        g.drawLine(250, 500, 250, 0);
        g.drawLine(0, 250, 500, 250);


        // DRAW THE CANDIDATES
        for(Candidate each : Simulation.activeCandidateList) {
            drawDot(g, 
                    (each.getIssuesList().get(0)).intValue(), 
                    (each.getIssuesList().get(1)).intValue(), 
                    6, 
                    each.getID());
        }

        for(Voter each : Simulation.voterList) {
            drawDot(g, 
                    (each.getIssuesList().get(0)).intValue(), 
                    (each.getIssuesList().get(1)).intValue(), 
                    4, 
                    3);
            Candidate c = each.vote();
            if (c != null) {
                drawLine(g,
                        (each.getIssuesList().get(0)).intValue(), 
                        (each.getIssuesList().get(1)).intValue(), 
                        (c.getIssuesList().get(0)).intValue(),
                        (c.getIssuesList().get(1)).intValue(),
                        c.getID());
            }
        }
        sim.getCandidateVoteCounts();
        System.out.println("# of candidates: " + Simulation.activeCandidateList.size());
        System.out.println("Winner of this round: " + sim.getWinner().getName());
        System.out.println();
    }
}
