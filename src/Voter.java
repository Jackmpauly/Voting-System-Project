import java.util.ArrayList;

public class Voter extends Person{
    
    // private ArrayList<Candidate> candList;
    private ArrayList<Candidate> activeCandidateList;
    private Candidate myVote;
    private ArrayList<Candidate> candidatesApproved; 
    // FIX ALL INSTANCES OF CANDLIST // CHANGE TO activeCandidateList

    public Voter(int id, Simulation s) {
        super(id);
        // this.candList = s.candList;
        this.activeCandidateList = s.activeCandidateList;
        candidatesApproved = new ArrayList<Candidate>();
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

    public void voteApproval(int maxDistance) {
        candidatesApproved.clear();
        if (activeCandidateList.isEmpty()) {
            return;
        }

        Candidate c;
        for (int i=0; i<activeCandidateList.size(); i++) {
            c = activeCandidateList.get(i);
            
            if ( !c.getRunning() ) {
                continue;
            }

            if (this.getDistance(c) <= maxDistance) {
                candidatesApproved.add(c);
                c.incrementVotes();
            }
        }


        double minDist = Double.MAX_VALUE;
        for (int i=0; i<candidatesApproved.size(); ++i) {
            c = candidatesApproved.get(i);

            if(this.getDistance(c) < minDist) {
                minDist = this.getDistance(c);
                myVote = c;
            }
        }
    }

    // Getter for myVote
    public Candidate getMyVote() {
        return myVote;
    }
}
