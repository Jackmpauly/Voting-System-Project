import java.util.ArrayList;

public class Person {
    private int id;
    private ArrayList<Double> issuesList;

    // Constructor
    public Person(int id) {
        this.id = id;
        issuesList = new ArrayList<Double>();
    }

    // calculate the difference between two Person objects using their values
    public double getDistance(Person p) {
        double radicand = 0; // the stuff under the square root
        
        int issuesLen = Math.min( issuesList.size(), p.getIssuesList().size() );
        // Loop through issues and compare
        for (int i=0; i<issuesLen; ++i) {
            // issuesDiff = the numerical difference between two issues
            double issuesDiff = (issuesList.get(i) - p.getIssuesList().get(i));
            radicand += Math.pow(issuesDiff, 2);
        }

        // return the square root of the radicand
        return Math.sqrt(radicand);
    }

    // Set the value of an issue
    public void setIssue(int issueIdx, double issueVal) {
        issuesList.set(issueIdx, issueVal);
    }

    // Add an issue value to the issuesList
    public void addIssue(double issueVal) {
        issuesList.add(issueVal);
    }

    // Get the id
    public int getID() {
        return id;
    }

    // Get the issuesList
    public ArrayList<Double> getIssuesList() {
        return issuesList;
    }

}