# CPSC 406 - VOTING SYSTEM PROJECT
Members: Jack, Gillian, Gabriel, Natalie

## Introduction
In this project, our goal is to simulate democracy by testing different voting systems and analyzing the effect it has on voter satisfaction. Through this simulation, we can observe the strengths and flaws of each voting system and quantify fairness through how satisfied the voters are with the outcome.
[Final presentation.](https://docs.google.com/presentation/d/1lnj-1Q8w0nWQG3Lx8U_SRHHrw7u-Zu0Y/edit?usp=sharing&ouid=112817791602145407975&rtpof=true&sd=true)

## Literature Review
### References
* [A Liquid Perspective on Democratic Choice](https://bford.info/pub/soc/liquid/), Bryan Ford
This lengthy article discusses a multitude of things related to voting in the modern age.  The main goal proposed is that democracy should be more liquid and continue to change in order to represent voters most accurately.  This ranges from mindsets around voting to different voting systems.
* [Variants of Ranked-Choice Voting from a Strategic Perspective](https://www.cogitatiopress.com/politicsandgovernance/article/view/3955)
This article examines different types of ranked choice voting and their effects.  We decided to use runoff ranked choice due to its easy of understanding and implementation.  While we only used one type of ranked choice, this paper served to spark ideas of voting and measuring success when it comes to democracy.


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
Currently, the program can accurately simulate and depict an election using three different voting systems.

## Future Developments

The object-oriented nature of our project makes it extremely malleable and modular. Future developments may include:
- Writing more simulation/candidate situations
- Creating different accurate voter distributions
- Drawing from historical data to create voter/candidate distributions
- Scaling the simulation up to fit for larger, more complex voters with varying distributions and weighted values.
