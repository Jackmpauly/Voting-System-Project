import java.util.ArrayList;

public class Voter extends Person{
    
    private ArrayList<Candidate> candList;
    private ArrayList<Candidate> activeCandidateList;
    // FIX ALL INSTANCES OF CANDLIST // CHANGE TO activeCandidateList

    public Voter(int id) {
        super(id);
        this.candList = Simulation.candList;
        this.activeCandidateList = Simulation.activeCandidateList;
    }

    // Vote method
    public Candidate vote() {
        double currentIssueDist = this.getDistance(activeCandidateList.get(0));
        Candidate votingFor = activeCandidateList.get(0);

        for(Candidate c : activeCandidateList) {
            if ( !c.getRunning() ) {
                continue;
            }
            double newIssueDist = this.getDistance(c);
            if(newIssueDist < currentIssueDist) {
                currentIssueDist = newIssueDist;
                votingFor = c;
            }
        }

        return votingFor;
    }
}
