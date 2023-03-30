import java.util.Scanner;


public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int votingMode;
        int voterDistrMode;
        // Simulation sim = new Simulation();
        // sim.generateAllVoters();
        // for(Voter each : Simulation.voterList) {
        //     System.out.println("(" + each.getIssuesList().get(0) + ", " + each.getIssuesList().get(1) + ")");
        // }

        System.out.println("          WELCOME TO VOTING SIMULATION");
        
        // get user input for the voting mode
        while(true){
            System.out.println("\nPlease Select A Voting Mode: ");
            System.out.println("   Enter '1' for Plurality Voting ");
            System.out.println("   Enter '2' for Ranked Choice Voting ");
            System.out.println("   Enter '3' for Approval Voting ");
            String userInput = in.nextLine();

            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3")){
                break;
            }
            else{
                System.out.println("INVALID INPUT");
            }
        }
        
        // get user input for the voter distribution mode
        while(true){
            System.out.println("\nPlease Select a Voter Distribution Mode: ");
            System.out.println("   Enter '1' for Normal Distribution ");
            System.out.println("   Enter '2' for BiModal Distribution ");
            String userInput = in.nextLine();

            if (userInput.equals("1") || userInput.equals("2")){
                break;
            }
            else{
                System.out.println("INVALID INPUT");
            }
        }
        

        Graph g = new Graph();
        g.start();
    }
}
