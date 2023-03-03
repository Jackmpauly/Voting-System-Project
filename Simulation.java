import java.util.ArrayList;

public class Simulation {
    public static ArrayList<Candidate> candList;
    public static ArrayList<Candidate> activeCandidateList;
    public static ArrayList<Voter> voterList;

    public Simulation() {
        candList = new ArrayList<Candidate>();
        activeCandidateList = candList;
    }

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