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
        voterList.clear();
        createVoters(251, 2);
    }

    public void createManualCandidate() {
        Candidate jack = new Candidate(0, "Jack Jackathy");
        jack.addIssue(50.0);
        jack.addIssue(150.0);
        Candidate paul = new Candidate(1, "Paul Paulerson");
        paul.addIssue(-87.0);
        paul.addIssue(-42.0);

        Candidate gillian = new Candidate(2, "Gillian Canicosa");
        gillian.addIssue(-50.0);
        gillian.addIssue(100.0);
        Candidate natalie = new Candidate(3, "Cousin Natalie");
        natalie.addIssue(75);
        natalie.addIssue(-100);
        Candidate gabriel = new Candidate(4, "Cousin Gabriel");
        gabriel.addIssue(50);
        gabriel.addIssue(-75);

        candList.add( jack );
        candList.add( paul );
        candList.add( gillian );
        candList.add( natalie );
        candList.add( gabriel );
    }

    public void generateCandidates(int numCandidates) {
        // int vIndex = 0;
        for(int i=0; i<numCandidates; ++i) {
            // candList.add()
        }
    }

    public void removeLastPlaceCandidate() {
        if (activeCandidateList.size() <= 1) {
            return;
        }
        // L + ratio
        Candidate candLoser = activeCandidateList.get(0);
        // finding the candidate with the least amount of votes
        for (Candidate c : activeCandidateList) {
            // System.out.println(c.getName() + ": " + c.getVotes());
            if (c.getVotes() < candLoser.getVotes()) {
                candLoser = c;
            }
        }
        
        activeCandidateList.remove(candLoser);
    }

    public void getCandidateVoteCounts() {
        for (Candidate c : activeCandidateList) {
            System.out.println(c.getName() + ": " + c.getVotes());
        }
    }

    public void resetActiveCandidateVoteCounts() {
        for (Candidate c : activeCandidateList) {
            c.resetVotes();
        }
    }

    public void createVoters(int numVoters, int numIssues) {
        int vIndex = 0;
        for(int i=0; i<numVoters; ++i) {
            voterList.add( createVoter(vIndex++, numIssues) );
        }
    }

    public Voter createVoter(int vIndex, int numIssues) {
        Voter v = new Voter(vIndex);
        for(int j=0; j<numIssues; ++j) {
            double issue = genIssueValNormal();
            v.addIssue( issue );
        }
        return v;
    }

    public double genIssueValNormal() {
        Random generator = new Random();

        double val = generator.nextGaussian();

        val*=62.5;

        return val;
    }
    
    public double genIssueVal() {
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
            v.vote().incrementVotes(); // NEED TO CHANGE. CANDIDATE VOTES ARE INCREMENTED IN THE VOTE METHOD
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

    // Method to fetch the Candidate with the most votes
    public Candidate getWinner() {
        Candidate candLeader = activeCandidateList.get(0);
        for (Candidate c : activeCandidateList) {
            if (c.getVotes() > candLeader.getVotes() ) {
                candLeader = c;
            }
        }

        return candLeader;
    }
}