import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;
import java.io.File;

public class Simulation {
    public ArrayList<Candidate> candList = new ArrayList<Candidate>();
    public ArrayList<Candidate> activeCandidateList = candList;
    public ArrayList<Voter> voterList = new ArrayList<Voter>();

    // Simulation Settings
    public VOTINGMODES activeVotingMode;
    private DISTRIBUTION activeDistribution;
    private int maxVotingDistance = 100;
    private int numberofRoundsDone = 0;
    private String filename;
    private int numIssues = 2;

    enum DISTRIBUTION {
        NORMAL,
        BIMODAL
    }
    enum VOTINGMODES {
        PLURALITY,
        RANKEDCHOICE,
        APPROVAL
    }

    // CONSTRUCTORS
    public Simulation() {
        // The file
        filename = "simulation1.csv";

        // The Candidates
        candList = new ArrayList<Candidate>();
        generateCandidates();
        
        // The voters
        voterList = new ArrayList<Voter>();
        
        
        // Default Sim Settings
        activeVotingMode = VOTINGMODES.RANKEDCHOICE;
        activeDistribution = DISTRIBUTION.BIMODAL;
    }
    public Simulation(int votingMode, int voterDistrMode, String filename) {
        // The file
        this.filename = filename;

        // The candidates
        candList = new ArrayList<Candidate>();
        generateCandidates();

        // The voters
        voterList = new ArrayList<Voter>();
        
        // Custom Sim Settings
        setActiveMode(votingMode);
        setActiveDistribution(voterDistrMode);
    }

    // METHOD TO (RE)SET THE ACTIVE CANDIDATES TO ALL CANDIDATES
    public void setActiveCandList() {
        activeCandidateList = candList;
    }

    // START: CANDIDATE GENERATION

    // Method to create candidates manually
    public void createManualCandidate(){
        // generateCandidates();
        
        Candidate jack = new Candidate(0, "Jack Jackathy");
        jack.addIssue(50.0);
        jack.addIssue(50.0);
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

    // Test method to generate candidates via a csv file
    public void generateCandidates() {
        String line = "";
        String splitBy = ",";
        int cID = 0;
        // System.out.println("../simulations/" + filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader("../simulations/" + filename));
            line = br.readLine();
            while((line = br.readLine()) != null) {
                String[] csv = line.split(splitBy);
                // System.out.println(csv[0] + csv[1] + csv[2] + csv[3]);
                Candidate c = new Candidate(cID++, csv[0]);
                c.addIssue(Integer.valueOf(csv[1]));
                c.addIssue(Integer.valueOf(csv[2]));
                candList.add(c);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the Active Candidate List
        activeCandidateList = candList;
    }

    // Candidate Management

    // Removing the last place candidate
    public void removeLastPlaceCandidate() {
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

    // Printing the number of votes of each candidate
    public void getCandidateVoteCounts() {
        Candidate c;
        for(int i=0; i < activeCandidateList.size(); ++i) {
            c = activeCandidateList.get(i);
            System.out.println(c.getName() + ": " + c.getVotes());
        }
    }

    // Reseting the number of votes for each active candidate
    public void resetActiveCandidateVoteCounts() {
        Candidate c;
        for(int i=0; i < activeCandidateList.size(); ++i) {
            c = activeCandidateList.get(i);
            c.resetVotes();
        }
    }

    // Sorting the candidates by votes
    public void sortCandidates() {
        Collections.sort(activeCandidateList);
    }

    // START: VOTER GENERATION

    // METHOD TO GENERATE VOTERS GIVEN DISTRIBUTION
    public void generateAllVoters(int numVoters, int numIssues) {
        this.numIssues = numIssues;
        voterList.clear(); // clear the voterList

        // Loop through the number of voters and create voters.
        int vIndex = 0;
        for(int i=0; i<numVoters; ++i) {
            voterList.add( createVoter(vIndex++, numIssues) );
        }
    }

    public Voter createVoter(int vIndex, int numIssues) {
        Voter v = new Voter(vIndex, this);
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

    // Copy the voters
    public void copyVoterList(ArrayList<Voter> voterList) {
        // Iterate through the voterlist and clone
        for (int i=0; i<voterList.size(); ++i) {
            Voter v = new Voter(i, voterList.get(i).getIssuesList(), this);
            this.voterList.add(v);
        }
    }

    // Voter Management

    // Loop through all the voters, make them cast their ballots / pick their candidates
    public void castVotes() {

        // Simulate voting for the Approval Voting System
        if (activeVotingMode == VOTINGMODES.APPROVAL) {
            // System.out.println("here");
            for (int i=0; i< voterList.size(); ++i) {
                voterList.get(i).voteApproval(maxVotingDistance);
            }
        }
        
        // Simulate voting for the two other systems
        else {
            for (int i=0; i< voterList.size(); ++i) {
                voterList.get(i).vote();
            }
        }
    }

    // run plurality election
    public Candidate runPlurality() {
        return this.runElection();
    }

    // run ranked choice voting UNUSED
    public Candidate runRankedChoice(int numCandidates) {

        // activeCandidateList.remove(candLoser);
        if (numCandidates >= 2) {
            Candidate currWinner = runElection();
            removeLastPlaceCandidate();
            numCandidates = candList.size();
            return currWinner;
        } else if (numCandidates == 1) {
            Candidate emptyCandidate = new Candidate(0000, "0000");
            System.out.println("Only 1 candidate left");
            return emptyCandidate;
        } else {
            Candidate emptyCandidate = new Candidate(0000, "0000");
            System.out.println("Something went wrong!");
            return emptyCandidate;
        }
    }

    // Accelerate the election round

    public void nextRound() {
        removeLastPlaceCandidate();
        resetActiveCandidateVoteCounts();
        castVotes();
        numberofRoundsDone++;
    }

    public boolean checkIfElectionDone() {
        if ((activeVotingMode == VOTINGMODES.PLURALITY) || (activeVotingMode == VOTINGMODES.APPROVAL)){
            if (numberofRoundsDone == 0) {
                return true;
            }
        } else {
            if (activeCandidateList.size() == 2) {
                return true;
            }
        }
        return false;
    }

    // run election (first written for plurality voting, will change later)
    public Candidate runElection() {
        // for (Voter v : voterList) {
        //     // v.vote().incrementVotes(); // NEED TO CHANGE. CANDIDATE VOTES ARE INCREMENTED IN THE VOTE METHOD
        // }

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
        for (int i=0; i<activeCandidateList.size(); i++) {
            if (activeCandidateList.get(i).getVotes() > candLeader.getVotes() ) {
                candLeader = activeCandidateList.get(i);
            }
        }

        return candLeader;
    }


    // START: Static methods used to generate normal values

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


    // START: Simulation settings (voting mode, distribution, number of issues)

    // Getter for the active voting mode
    public int getActiveMode() {
        switch (activeVotingMode) {
            case PLURALITY:
                return 1;
            case RANKEDCHOICE:
                return 2;
            case APPROVAL:
                return 3;
            default:
                return 1;
        }
    }
    // Getter for the active values distribution
    public DISTRIBUTION getActiveDistribution() {
        return activeDistribution;
    }
    // Getter for the maxVotingDistance
    public int getMaxVotingDistance() {
        return maxVotingDistance;
    }

    // Getter for number of issues
    public int getNumIssues() {
        return numIssues;
    }

    // Getter for voterList
    public ArrayList<Voter> getVoterList() {
        return voterList;
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
            case 3:
                activeVotingMode = VOTINGMODES.APPROVAL;
                break;
            default:
                activeVotingMode = VOTINGMODES.PLURALITY;
                break;
        }
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

    // Setter for the maxVotingDist
    public void setMaxVotingDist(int d) {
        maxVotingDistance = d;
    }

    // Setter for the filename
    public void setSimFile(String filename) {
        File f = new File("../simulations/" + filename);
        if (f.exists() && !f.isDirectory()) {
            this.filename = filename;
        } else {
            this.filename = "simulation2.csv";
        }
    }

}