import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;

public class Simulation {
    public static ArrayList<Candidate> candList = new ArrayList<Candidate>();
    public static ArrayList<Candidate> activeCandidateList = candList;
    public static ArrayList<Voter> voterList = new ArrayList<Voter>();
    private static VOTINGMODES activeVotingMode = VOTINGMODES.RANKEDCHOICE;
    private static DISTRIBUTION activeDistribution = DISTRIBUTION.BIMODAL;
    private static int numIssues = 3;

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
    }

    public static void setActiveCandList() {
        activeCandidateList = candList;
    }

    public static void generateAllVoters() {
        voterList.clear();
        createVoters(251, numIssues);
    }

    public static void createManualCandidate(){
        generateCandidates();
        
        Candidate jack = new Candidate(0, "Jack Jackathy");
        jack.addIssue(50.0);
        jack.addIssue(25.0);
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

    public static void generateCandidates() {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("simulation1.csv"));
            while((line = br.readLine()) != null) {
                String[] csv = line.split(splitBy);
                System.out.println(csv[0]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void removeLastPlaceCandidate() {
        if (activeCandidateList.size() <= 1) {
            return;
        }
        // L + ratio
        Candidate candLoser = activeCandidateList.get(0);


        // finding the candidate with the least amount of votes
        for(int i=0; i < activeCandidateList.size(); ++i) {
            if (activeCandidateList.get(i).getVotes() < candLoser.getVotes()) {
                candLoser = activeCandidateList.get(i);
            }
        }
        
        activeCandidateList.remove(candLoser);
    }

    public static void getCandidateVoteCounts() {
        Candidate c;
        for(int i=0; i < activeCandidateList.size(); ++i) {
            c = activeCandidateList.get(i);
            System.out.println(c.getName() + " (" + c.getID() + "): " + c.getVotes());
        }
    }

    public static void resetActiveCandidateVoteCounts() {
        Candidate c;
        for(int i=0; i < activeCandidateList.size(); ++i) {
            c = activeCandidateList.get(i);
            c.resetVotes();
        }
    }

    public static void createVoters(int numVoters, int numIssues) {
        // System.out.println("creating voters");
        int vIndex = 0;
        for(int i=0; i<numVoters; ++i) {
            voterList.add( createVoter(vIndex++, numIssues) );
        }
    }

    public static Voter createVoter(int vIndex, int numIssues) {
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

    public static double genIssueValNormal(double multiplier) {
        Random generator = new Random();
        double val = generator.nextGaussian();
        val*=multiplier;
        return val;
    }
    
    // Generate an issue value on the Bimodal distribution
    // Passes in a double, disMode, that defines the mode of the distribution
    // Gets a normal-distribution issue value with multiplier mode/divisor to limit the size of the graph
    // HIGHER DIVISOR MEANS MORE CLUMPED. should be between 1 and 4
    public static double genIssueValBiModal(double disMode) {
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

    // run ranked choice voting UNUSED
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
    public static Candidate getWinner() {
        Candidate candLeader = activeCandidateList.get(0);
        for (int i=0; i<activeCandidateList.size(); i++) {
            if (activeCandidateList.get(i).getVotes() > candLeader.getVotes() ) {
                candLeader = activeCandidateList.get(i);
            }
        }

        return candLeader;
    }

    // Getter for the active voting mode
    public static VOTINGMODES getActiveMode() {
        return activeVotingMode;
    }

    // Setter for the active voting mode.
    // Passes in an integer. Integer corresponds to a different voting mode
    public static void setActiveMode(int mode) {
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
    public static DISTRIBUTION getActiveDistribution() {
        return activeDistribution;
    }

    // Setter for the active distribution
    // Passes in an integer. Integer corresponds to a different distribution
    public static void setActiveDistribution(int dist) {
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