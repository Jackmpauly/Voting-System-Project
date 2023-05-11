// import java.util.ArrayList;

import java.text.DecimalFormat;

public class Approval {
    Simulation mySim;


    public Approval(Simulation currentSim) {
        mySim = currentSim;
    }

    // Get satisfaction ratings
    public void getSatisfactionRatings() {
        double avgSat = 0;
        double minSat = 1;
        double maxSat = 0;

        for (int i=0; i<mySim.voterList.size(); ++i) {
            Voter v = mySim.voterList.get(i);
            double vSat = voterSatisfactionRating(v);
            avgSat += vSat;

            minSat = Math.min(minSat, vSat);
            maxSat = Math.max(maxSat, vSat);
        }

        avgSat /= mySim.voterList.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        
        System.out.println();
        System.out.println("Average Satisfaction Rating: " + Double.parseDouble(decimalFormat.format(avgSat)));
        System.out.println("Minimum Satisfaction Rating: " + Double.parseDouble(decimalFormat.format(minSat)));
        System.out.println("Maximum Satisfaction Rating: " + Double.parseDouble(decimalFormat.format(maxSat)));
    }

    // Calculate satisfaction rating
    public double voterSatisfactionRating(Voter v) {
        Candidate winner = mySim.getWinner();

        double distance = v.getDistance(winner);

        return 1 - (distance / getMaxDistance());
    }

    // Use the winning candidate and calucate the winning distance
    public double getMaxDistance() {
        Candidate winner = mySim.getWinner();
        double radicand = 0;
        for (int i=0; i<winner.getIssuesList().size(); ++i) {
            double issuesDiff = 250 + Math.abs(winner.getIssuesList().get(i)); // aka -250 - coordinate
            radicand += Math.pow(issuesDiff, 2);
        }

        return Math.sqrt(radicand);
    }
}
