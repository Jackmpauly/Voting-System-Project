import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

public class Graph extends Canvas {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My Graph");
        Canvas canvas = new Graph();
        canvas.setSize(500, 500);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public static void drawDot(Graphics g, int x, int y, int radius, int colorIdx) {
        // increase x and y by 250 to give them the correct coords on the graph
        x+=250;
        y+=250;

        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.CYAN};
        // Set the color of the circle to the index of colors and draw a filled circle
        g.setColor(colors[colorIdx]);
        g.fillOval(x - radius, 500 - y - radius, radius * 2, radius * 2);

        // Draw the stroke of the circle in black
        g.setColor(Color.BLACK);
        g.drawOval(x - radius, 500 - y - radius, radius * 2, radius * 2);
    }

    public void paint(Graphics g) {
        // Drawing the grid
        g.drawLine(250, 500, 250, 0);
        g.drawLine(0, 250, 500, 250);

        Simulation sim = new Simulation();
        sim.generateAllVoters();

        for(Voter each : Simulation.voterList) {
            drawDot(g, 
                    (each.getIssuesList().get(0)).intValue(), 
                    (each.getIssuesList().get(1)).intValue(), 
                    4, 
                    3);
        }
    }
}
