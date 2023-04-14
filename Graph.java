// import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
// import java.awt.Component;
import javax.swing.JFrame;

// import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;

public class Graph extends JPanel {

    private static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN};
    // private static int count = 0;
    private Simulation mySim;

    public Graph(Simulation s) {
        mySim = s;
    }

    public void start() {
        JFrame frame = new JFrame("My Graph");
        Graph canvas = this; // Might need to fix this...
        frame.add(canvas);
        frame.setSize(500, 500);
        // frame.pack();
        frame.setVisible(true);

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

    // START: GRAPHICS DRAW METHODS

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

    public static void drawSqr(Graphics g, int x, int y, int side, int colorIdx) {
        x+=250;
        y+=250;

        g.setColor(colors[colorIdx]);
        g.fillRect(x - side, 500 - y - side, side * 2, side * 2);

        g.setColor(Color.BLACK);
        g.drawRect(x - side, 500 - y - side, side * 2, side * 2);
    }

    public static void writeName(Graphics g, String name, int x, int y, int colorIdx) {
        x += 250;
        y += 250;

        g.setColor(colors[colorIdx]);
        g.drawString(name, x, 500 - y);
    }

    // TODO: Draw rectangle. make it bigger too

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

    // END: GRAPHICS DRAW METHODS

    // Method to accelerate the election/vote
    public void accelerateVote(Graph c) {
        System.out.println("-----------NEXT VOTING ROUND-----------");
        mySim.nextRound();
        c.repaint();
    }

    public void paint(Graphics g) {     
        // Drawing the grid
        g.drawLine(250, 500, 250, 0);
        g.drawLine(0, 250, 500, 250);
        
        // Draw each voter
        for(int i = 0; i < mySim.voterList.size(); ++i) {
            Voter v = mySim.voterList.get(i);
            Candidate c = v.getMyVote();
            
            drawDot(g, 
                (v.getIssuesList().get(0)).intValue(), 
                (v.getIssuesList().get(1)).intValue(), 
                4, 
                c.getID()
            );
        }

        // Draw each candidate
        for(int i = 0; i < mySim.activeCandidateList.size(); ++i) {
            Candidate c = mySim.activeCandidateList.get(i);
            drawSqr(g,
                (c.getIssuesList().get(0)).intValue(), 
                (c.getIssuesList().get(1)).intValue(), 
                6, 
                c.getID()
            );
            // Write their name too
            writeName(g,
                c.getName(),
                (c.getIssuesList().get(0)).intValue(),
                (c.getIssuesList().get(1)).intValue(),
                c.getID()
            );   
        }

        mySim.getCandidateVoteCounts();
        System.out.println("# of candidates: " + mySim.activeCandidateList.size());
        System.out.println("Winner of this round: " + mySim.getWinner().getName() );
        new Approval(mySim).getSatisfactionRatings();
        System.out.println();
    }
}
