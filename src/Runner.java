import java.util.Scanner;


public class Runner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int voterDistrMode;
        int numIssues;
        int maxDistance = 100;
        int numVoters = 251;


        System.out.println("          WELCOME TO VOTING SIMULATION");
        // String userInput = in.nextLine();
        
        // get user input for distance 
            while(true){
                System.out.println("\nPlease enter number of voters (default: 251): ");
                String userInput = in.nextLine();
                
                try {
                    Integer.parseInt(userInput);
                    numVoters = Integer.valueOf(userInput);
                    break;
                } catch (NumberFormatException e) {
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

        // Setting up the simulation objects

        // Create the plurality Simulation.
        Simulation plurality = new Simulation(1, voterDistrMode, args[0]);
        // Generate the voters
        plurality.generateAllVoters(numVoters, numIssues);

        // Create the rankedChoice Simulation. Use the plurality system's voters.
        Simulation rankedChoice = new Simulation(2, voterDistrMode, args[0]);
        rankedChoice.copyVoterList(plurality.getVoterList());
        
        // Create the approval Simulation. Use the plurality system's voters.
        Simulation approval = new Simulation(3, voterDistrMode, args[0]);
        approval.copyVoterList(plurality.getVoterList());
        
        plurality.castVotes();
        rankedChoice.castVotes();
        approval.castVotes();

        if (args.length >= 2 && args[1].toLowerCase().equals("graph")) {
            Graph pluralityGraph = new Graph(plurality, "Plurality");
            Graph rankedChoiceGraph = new Graph(rankedChoice, "Ranked Choice");
            Graph approvalGraph = new Graph(approval, "Approval");
            
            pluralityGraph.start();
            rankedChoiceGraph.start();
            approvalGraph.start();
        } else {
            TextGraphics pluralityGraph = new TextGraphics(plurality, "Plurality");
            TextGraphics rankedChoiceGraph = new TextGraphics(rankedChoice, "Ranked Choice");
            TextGraphics approvalGraph = new TextGraphics(approval, "Approval");
            
            pluralityGraph.start();
            rankedChoiceGraph.start();
            approvalGraph.start();
        }
        // TextGraphics tg = new TextGraphics(s);
        // tg.start();
    }
}
