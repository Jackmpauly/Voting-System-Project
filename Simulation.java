import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    public static ArrayList<Candidate> candList;
    public static ArrayList<Candidate> activeCandidateList;
    public static ArrayList<Voter> voterList;

    public Simulation() {
        candList = new ArrayList<Candidate>();
        voterList = new ArrayList<Voter>();
        activeCandidateList = candList;
    }

    public void generateAllVoters() {
        createVoters(50, 2, true);
        createVoters(50, 2, false);
    }

    public void createVoters(int numVoters, int numIssues, boolean negate) {
        int vIndex = 0;
        for(int i=0; i<numVoters; ++i) {
            voterList.add( createVoter(vIndex++, numIssues, negate) );
        }
    }

    public Voter createVoter(int vIndex, int numIssues, boolean negate) {
        Voter v = new Voter(vIndex);
        for(int j=0; j<numIssues; ++j) {
            double issue = genPosIssueVal();
            if (negate) {
                issue*=-1;
            }
            v.addIssue( issue );
        }
        return v;
    }
    
    public double genPosIssueVal() {
        Random generator = new Random();
        
        double val = 300;
        while(Math.abs(val) > 250) {
            val = generator.nextGaussian();
            // val = Math.abs(val);
            val*=62.5;
            val+=200;
            // System.out.println("val: " + val);
        }
            
        return val;
    }

    // run plurality election
    public Candidate runPlurality() {
        return this.runElection();
    }

    // run ranked choice voting
    public Candidate runRankedChoice(int numCandidates) {
        Candidate currWinner = runElection();

        // L + ratio
        Candidate candLoser = activeCandidateList.get(0);
        // finding the candidate with the least amount of votes
        for (Candidate c : activeCandidateList) {
            if (c.getVotes() < candLoser.getVotes()) {
                candLoser = c;
            }
        }

        activeCandidateList.remove(candLoser);
        
        if (numCandidates == 2) {
            return runElection();
        } else {
            return runRankedChoice(numCandidates);
        }
    }

    // run election (first written for plurality voting, will change later)
    public Candidate runElection() {
        for (Voter v : voterList) {
            v.vote().incrementVotes();
        }

        Candidate candLeader = activeCandidateList.get(0);
        for (Candidate c : activeCandidateList) {
            if (c.getVotes() > candLeader.getVotes() ) {
                candLeader = c;
            }
        }

        // RETURN THE WINNER!!
        System.out.println(candLeader.getName() + " wins!");
        return candLeader;
    }
}