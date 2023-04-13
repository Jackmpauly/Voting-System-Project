import java.util.ArrayList;

public class Voter extends Person{
    
    private ArrayList<Candidate> candList;
    private ArrayList<Candidate> activeCandidateList;
    private Candidate myVote;
    // FIX ALL INSTANCES OF CANDLIST // CHANGE TO activeCandidateList

    public Voter(int id, Simulation s) {
        super(id);
        this.candList = s.candList;
        this.activeCandidateList = s.activeCandidateList;
    }

    // Vote method
    public void vote() {
        if (activeCandidateList.isEmpty()) {
            return;
        }
        Candidate votingFor = activeCandidateList.get(0);
        double currentIssueDist = this.getDistance(activeCandidateList.get(0));

        // System.out.println("got here");

        // MIGHT NEED TO CHANGE HERE
        Candidate c;
        for (int i=0; i<activeCandidateList.size(); i++) {
            c = activeCandidateList.get(i);
            if ( !c.getRunning() ) {
                continue;
            }
            double newIssueDist = this.getDistance(c);
            if(newIssueDist < currentIssueDist) {
                currentIssueDist = newIssueDist;
                votingFor = c;
            }
        }
        
        votingFor.incrementVotes();
        myVote = votingFor;
    }

    // Getter for myVote
    public Candidate getMyVote() {
        return myVote;
    }
}
