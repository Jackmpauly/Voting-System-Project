# CPSC 406 - VOTING SYSTEM PROJECT
Members: Jack, Gillian, Gabriel, Natalie

## Introduction
In this project, our goal is to simulate democracy by testing different voting systems and analyzing the effect it has on voter satisfaction. Through this simulation, we can observe the strengths and flaws of each voting system and quantify fairness through how satisfied the voters are with the outcome.
[Final presentation.](https://docs.google.com/presentation/d/1lnj-1Q8w0nWQG3Lx8U_SRHHrw7u-Zu0Y/edit?usp=sharing&ouid=112817791602145407975&rtpof=true&sd=true)

## Literature Review
### References
* [A Liquid Perspective on Democratic Choice](https://bford.info/pub/soc/liquid/), Bryan Ford

### Theoretical Background
A voting system should aim to create a program where voters can be honest about their positions and viewpoints. However, this is an ongoing issue since many voters use strategy in the attempt to conclude with a result that benefits them the most. For this reason, it is interesting to look at different systems and simulate them so we can observe the differences. One example of this in real-world democracy is the concept of "spoiler candidates". If every voter voted for the candidate that most closely aligned with their values, two candidates with similar values could split voters. In an winner-takes-all election, dividing up the votes between two similar candidates could mean that a third candidate with undivided votes wins. See below.

![A voting graph showing two candidates near each other and one candidate opposite the two candidates.](images/voting-simulation2.png?raw=true "Simulation 2")

In the simulation above, run with a plurality voting system, Gillian wins the election by only two votes. Had this distribution been run in a ranked-choice setting, votes counting for Natalie would be redirected to Gabriel, and he would win handily.

## Description
Our project consists of considerate and strategic object-oriented design. The brain of our simulation is a class `Simulation.java`. This class creates the ArrayLists of `Candidate` and `Voter` objects, both of which are children of a larger `Person` class. Simulation generates the voters and candidates, conducts voting, and establishes the rules of the simulation. That information gets passed onto the `Graph.java` class, which interfaces with the Java Graphics library and draws a visual representation of the simulation.

## Build Instructions
To build and run the Voting System Simulation, do the following steps:
1. ***Make sure you have the latest version of Java installed***
2. cd to `/src/`
3. run `javac *.java`
4. run with the following format: `java Runner <CSV FILENAME> <graph>`.
   1. `<CSV FILENAME>` This is the name of the .csv file located in the simulations folder. The .csv file defines the candidates to be used in the simulation.
   2. `<graph>` Type `graph` as a second argument in the command line to run the simulation using the Java graphics. If you leave the argument empty, then it will use text-based graphics.
5. Follow the instructions on screen, give inputs that it asks for.

## Testing
For testing, we created various .csv files to act as configuration files for our simulation. These .csv files contain the names and values (locations) of all of the candidates. By using these files, we could test various situations that may arise during an election, such as spoiler and outlier candidates.

## What works? What doesn't
Currently, the program can accurately simulate and depict an election using three different 

## Future Developments

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

## Week 8 Update: ##
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


TODO 4/14/23

- Make more voting systems: approval rating VOTING SYSTEM


TODO 4/26/23
- Display after election number of people who did not vote
- Read in file with parameters for candidates, for voter distribution (# modes, where the modes are, etc), voting system
- Get the 1-5 numerical satisfaction rating from the voter sat rating
- in a single simulation, run multiple elections using the different voter distributions. Compare results.
