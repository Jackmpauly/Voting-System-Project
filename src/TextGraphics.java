import java.util.ArrayList;

public class TextGraphics {
    private Simulation mySim;
    private String name;

    public TextGraphics(Simulation mySim, String name) { 
        this.mySim = mySim;
        this.name = name;
    }

    public void start() {
        
        // accelerate rounds if ranked choice
        if (mySim.getActiveMode() == 2) {
            while (mySim.activeCandidateList.size() > 2) {
                mySim.nextRound();
            }
        }
        
        // display the graph if plurality
        if (mySim.getActiveMode() == 1) {
            draw();
        }
        
        System.out.println("-----------STARTING SIMULATION-----------");
        System.out.println("Voting System: " + name);
        mySim.sortCandidates();
        mySim.getCandidateVoteCounts();
        new Approval(mySim).getSatisfactionRatings();
    }

    public void draw() {
        ArrayList<String> graph = new ArrayList<String>();
        for (int i=0; i<51; ++i) {
            graph.add(            "                                                  |                                                  ");
        }
        graph.set(0 , "                                                  ^                                                  ");
        graph.set(50, "                                                  v                                                  ");
        graph.set(25, "<-------------------------------------------------|------------------------------------------------->");
        for (int i=0; i<50; ++i) {
            graph.set(i, graph.get(i));
        }
        // •

        for(int i = 0; i < mySim.voterList.size(); ++i) {
            Voter v = mySim.voterList.get(i);
            int xCoord = (v.getIssuesList().get(0)).intValue();
            int yCoord = (v.getIssuesList().get(1)).intValue();

            xCoord = (xCoord / 10) * 2;
            yCoord/=10;
            String newLine = graph.get(25-yCoord).substring(0, 50+xCoord) 
                + "•"
                + graph.get(25-yCoord).substring(50+xCoord+1);
            
            graph.set(25-yCoord, newLine);
        }

        // Draw each candidate
        for(int i = 0; i < mySim.activeCandidateList.size(); ++i) {
            Candidate c = mySim.activeCandidateList.get(i);
            int xCoord = (c.getIssuesList().get(0)).intValue();
            int yCoord = (c.getIssuesList().get(1)).intValue();
            // take example. xCoord = -100, yCoord = -100 
            xCoord = (xCoord / 10) * 2;
            yCoord/= 10;
            // System.out.println(xCoord);
            // System.out.println(yCoord);
            
            String newLine = graph.get(25-yCoord).substring(0, 50+xCoord) 
                + c.getName().substring(0, 2) 
                + graph.get(25-yCoord).substring(50+xCoord+2);
            
            graph.set(25-yCoord, newLine);
        }

        for (int i=0; i<graph.size(); ++i) {
            System.out.println(graph.get(i));
        }
    }
}
