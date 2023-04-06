import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    public static ArrayList<Candidate> candList;
    public static ArrayList<Candidate> activeCandidateList;
    public static ArrayList<Voter> voterList;
    private static VOTINGMODES activeVotingMode;
    private static DISTRIBUTION activeDistribution;

    enum DISTRIBUTION {
        NORMAL,
        BIMODAL
    }
    enum VOTINGMODES {
        PLURALITY,
        RANKEDCHOICE,
    }

    public Simulation() {
        candList = new ArrayList<Candidate>();
        voterList = new ArrayList<Voter>();
        activeCandidateList = candList;
        System.out.println("here");
        activeVotingMode = VOTINGMODES.PLURALITY;
        activeDistribution = DISTRIBUTION.BIMODAL;
    }

    public void setActiveCandList() {
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
        System.out.println("Jack: RED");
        candList.add( paul );
        System.out.println("Paul: BLUE");
        candList.add( gillian );
        System.out.println("Gillian: GREEN");
        candList.add( natalie );
        System.out.println("Natalie: ORANGE");
        candList.add( gabriel );
        System.out.println("Gabriel: CYAN");

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
            System.out.println(c.getName() + " (" + c.getID() + "): " + c.getVotes());
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
            double issue;
            switch (activeDistribution) {
                case NORMAL:
                    issue = genIssueValNormal(62.5);
                    break;
                case BIMODAL:
                    issue = genIssueValBiModal(75);
                    break;
                default:
                    issue = genIssueValNormal(62.5);
                    break;
            }
            v.addIssue( issue );
        }
        return v;
    }

    public double genIssueValNormal(double multiplier) {
        Random generator = new Random();
        double val = generator.nextGaussian();
        val*=multiplier;
        return val;
    }
    
    // Generate an issue value on the Bimodal distribution
    // Passes in a double, disMode, that defines the mode of the distribution
    // Gets a normal-distribution issue value with multiplier mode/divisor to limit the size of the graph
    // HIGHER DIVISOR MEANS MORE CLUMPED. should be between 1 and 4
    public double genIssueValBiModal(double disMode) {
        double issue = genIssueValNormal((disMode/2)) + disMode;

        Random generator = new Random();
        int posneg = generator.nextInt(2);
        // System.out.println("+-: " + posneg);
        if (posneg % 2 == 0) {
            issue*=-1;
        }
        return issue;
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

    // Getter for the active voting mode
    public VOTINGMODES getActiveMode() {
        return activeVotingMode;
    }

    // Setter for the active voting mode.
    // Passes in an integer. Integer corresponds to a different voting mode
    public void setActiveMode(int mode) {
        switch (mode) {
            case 1:
                activeVotingMode = VOTINGMODES.PLURALITY;
                break;
            case 2:
                activeVotingMode = VOTINGMODES.RANKEDCHOICE;
                break;
            default:
                activeVotingMode = VOTINGMODES.PLURALITY;
                break;
        }
    }

    // Getter for the active values distribution
    public DISTRIBUTION getActiveDistribution() {
        return activeDistribution;
    }

    // Setter for the active distribution
    // Passes in an integer. Integer corresponds to a different distribution
    public void setActiveDistribution(int dist) {
        switch (dist) {
            case 1:
                activeDistribution = DISTRIBUTION.NORMAL;
                break;
            case 2:
                activeDistribution = DISTRIBUTION.BIMODAL;
                break;
            default:
                activeDistribution = DISTRIBUTION.BIMODAL;
                break;
        }
    }
}