# CPSC 406 - VOTING SYSTEM PROJECT
Jack, Gillian, Gabriel, Natalie

Classes:

- Simulation
  - + boolean runoff
  - + 
    - runElection(LinkedList<Voter> voterList, LinkedList<Candidate> candList) // runs recursively in ranked choice
    - setPlurality(int votingType) // set the variables to. votingType is the voting system we need to use. 
    - setRankedChoice() 
    - calcApprovalRating()
    - findDistance()
    - setStage() // sets up the voters and candidates. reads from file
- Person
  - Candidate
  - Voter

Week 8 Update:
Execution:
Download files
Type javac *.java
Type java Runner
Enter 1 for plurality voting or 2 for ranked choice voting
If ranked choice was chosen, hit enter to move the simulation forward one runoff election at a time

Known Bugs:
It is currently only running on Mac computers due to our method of visualizing the data.

Progress Compared to Plan:
We have completed everything we set out to do from our original MVP planning.  In addition to those requirements, we have also included a graphing mechanism to depict an election with up to two axis of values.

Further Progress:
Fixing the problem of the graph display only working on Mac computers is something that we are actively trying to fix.  The next improvement to be done is implementing an approval/disapproval rating system.  This will allows us to add a statistical backing and analysis to our simulation.  We also plan to implement a 3rd type of voting where voters can select multiple candidates at the same time.  We plan to continue to meet weekly in order to further these goals.


TODO: 3/30/23

- Bimodal distribution (two normal distributions)
- Autogenerate candidates
- Voting system type (settings)
- (maybe) move some functionality to the simulation class?
