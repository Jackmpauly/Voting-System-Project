public class Candidate extends Person implements Comparable<Candidate> {
    private int votes;
    private String name;
    private boolean isRunning; 
    
    public Candidate(int id, String name) {
        super(id);
        votes = 0;
        isRunning = true;
        this.name = name;
    }

    // increase candidate's votes
    public void incrementVotes() {
        ++votes;
    }

    // reset candidate's votes
    public void resetVotes() {
        votes = 0;
    }

    // Get the votes
    public int getVotes() {
        return votes;
    }

    // set the isRunning to false
    public void dropoutOfRace() {
        isRunning = false;
    }

    // set the isRunning to true
    public void reenterRace() {
        isRunning = true;
    }

    // get the running status
    public boolean getRunning() {
        return isRunning;
    }

    // get the name
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Candidate c) {
        int difference = c.getVotes() - this.getVotes();
        if (difference == 0) {
            return 0;
        } else {
            return (difference/Math.abs(difference));
        }
    }
}
