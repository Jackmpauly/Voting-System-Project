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