import java.util.Scanner;


public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int votingMode;
        int voterDistrMode;
        int numIssues;
        int maxDistance = 100;


        System.out.println("          WELCOME TO VOTING SIMULATION");
        
        // get user input for the voting mode
        while(true){
            System.out.println("\nPlease Select A Voting Mode: ");
            System.out.println("   Enter '1' for Plurality Voting ");
            System.out.println("   Enter '2' for Ranked Choice Voting ");
            System.out.println("   Enter '3' for Approval Voting ");
            String userInput = in.nextLine();

            if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3")){
                votingMode = Integer.valueOf(userInput);
                break;
            }
            else{
                System.out.println("INVALID INPUT");
            }
        }
        // get user input for distance 
        if (votingMode == 3) {
            while(true){
                System.out.println("\nPlease enter Approval voting distance threshold (default: 100): ");
                String userInput = in.nextLine();
                
                try {
                    Integer.parseInt(userInput);
                    maxDistance = Integer.valueOf(userInput);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("INVALID INPUT");
                }
            }
        }
            
        // get user input for the voter distribution mode
        while(true){
            System.out.println("\nPlease Select a Voter Distribution Mode: ");
            System.out.println("   Enter '1' for Normal Distribution ");
            System.out.println("   Enter '2' for BiModal Distribution ");
            String userInput = in.nextLine();

            if (userInput.equals("1") || userInput.equals("2")){
                voterDistrMode = Integer.valueOf(userInput);
                break;
            }
            else{
                System.out.println("INVALID INPUT");
            }
        }
        // get user input for number of issues
        while(true){
            System.out.println("\nPlease enter number of issues per person: ");
            String userInput = in.nextLine();

            try {
                Integer.parseInt(userInput);
                numIssues = Integer.valueOf(userInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("INVALID INPUT");
            }
        }


        in.close();

        // Setting up the simulation object

        Simulation s = new Simulation(votingMode, voterDistrMode);

        // Create the candidates, generate the voters, set the active candidates
        s.createManualCandidate();
        s.generateAllVoters(251, numIssues);
        s.setMaxVotingDist(maxDistance);
        s.castVotes();
        s.setActiveCandList();
        s.setSimFile(args[0]);

        if (args.length >= 2 && args[1].toLowerCase().equals("graph")) {
            Graph g = new Graph(s);
            g.start();
        } else {
            TextGraphics tg = new TextGraphics(s);
            tg.start();
        }
        // TextGraphics tg = new TextGraphics(s);
        // tg.start();
    }
}
