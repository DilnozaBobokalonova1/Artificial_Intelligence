package FiniteStateMachine;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BidirectionalSearch {
	static State start = new State(3,3,0,0,0,1,0);
	static State goal = new State(0,0,0,3,3,1,1);
	static int numberOfSteps;
	public static void solution() {
		Set<State> theStart = new HashSet<State>();
		Set<State> theGoal = new HashSet<State>();
		ArrayList<Transitions> operatorsWhenLeft = new ArrayList<Transitions>(); //the list of operators for when the boat is on the left
		ArrayList<Transitions> operatorsWhenRight = new ArrayList<Transitions>(); //the list of operators for when the boat is on the right
		theStart.add(start);
		theGoal.add(goal);
		operatorsWhenRight.add(new Transitions(1,1,0));
		operatorsWhenLeft.add(new Transitions(1,1,1));
		operatorsWhenRight.add(new Transitions(2,0,0));
		operatorsWhenLeft.add(new Transitions(2,0,1));
		operatorsWhenRight.add(new Transitions(0,2,0));
		operatorsWhenLeft.add(new Transitions(0,2,1));
		operatorsWhenRight.add(new Transitions(1,0,0));
		operatorsWhenLeft.add(new Transitions(1,0,1));
		operatorsWhenRight.add(new Transitions(0,1,0));
		operatorsWhenLeft.add(new Transitions(0,1,1));
		
		Queue<State> Q1 = new LinkedList<State>();
		Queue<State> Q2 = new LinkedList<State>();
		Q1.add(start);
		Q2.add(goal);
		while(!(Q1.isEmpty() && Q2.isEmpty())) {
			if(!Q1.isEmpty()) {
				State one = Q1.remove();
				System.out.println("Exploring from the state");
				System.out.println(one.toString());
				theStart.add(one);
				
				if(one.visited(goal) == true || (theGoal.contains(one))) {
					System.out.println("State in common found! The paths from the start and from the goal have finally met at " + one.toString() + " and it was done in " + numberOfSteps + " steps!"
							 + "\n" + "Thank you Bidirectional Search!");
					return;
				}
				if(one.currentLocation == 0){
					for (Transitions x: operatorsWhenLeft) {
						State next = new State(one.missionariesLeft - x.numOfMissionaries, one.cannibalsLeft - x.numOfCannibals, 0, one.missionariesRight + x.numOfMissionaries,
								one.cannibalsRight + x.numOfCannibals, 1, x.goalSide);
						if(next.allowed() && !theStart.contains(next) && !Q1.contains(next)){
							Q1.add(next); //if the state is allowed add it to the queue for it to be popped later
						}
					}
				}
				else if(one.currentLocation == 1) {
					for (Transitions x: operatorsWhenRight) {
						State next = new State(one.missionariesLeft + x.numOfMissionaries, one.cannibalsLeft + x.numOfCannibals, 0, one.missionariesRight - x.numOfMissionaries,
								one.cannibalsRight - x.numOfCannibals, 1, x.goalSide);
						if(next.allowed() && !theStart.contains(next) && !Q1.contains(next)){ //make sure the state is not already in the queue
							
							theStart.add(next);
							Q1.add(next);
						}
					}
				}
				numberOfSteps++;
			}
			if(!Q2.isEmpty()) {
				State two = Q2.remove();
				System.out.println("Exploring from the goal....");
				System.out.println(two.toString());
				theGoal.add(two);
				
				if(two.visited(start) == true || (theStart.contains(two))) {
					System.out.println("State in common found! The paths from the start and from the goal have finally met at " + two.toString() + " and it was done in " + numberOfSteps + " steps");
					return;
				}
				if(two.currentLocation == 0){
					for (Transitions x: operatorsWhenLeft) {
						State next = new State(two.missionariesLeft - x.numOfMissionaries, two.cannibalsLeft - x.numOfCannibals, 0, two.missionariesRight + x.numOfMissionaries,
								two.cannibalsRight + x.numOfCannibals, 1, x.goalSide);
						if(next.allowed() && !theGoal.contains(next) && !Q2.contains(next)){ 
							Q2.add(next);
						}
					}
				}
				else if(two.currentLocation == 1) {
					for (Transitions x: operatorsWhenRight) {
						State next = new State(two.missionariesLeft + x.numOfMissionaries, two.cannibalsLeft + x.numOfCannibals, 0, two.missionariesRight - x.numOfMissionaries,
								two.cannibalsRight - x.numOfCannibals, 1, x.goalSide);
						if(next.allowed() && !theGoal.contains(next) && !Q2.contains(next)){ //checks if the state just explored had not been visited already and is an allowed state
							Q2.add(next);
						}
					}
				}
				numberOfSteps++;
			}
			
		}	
	}
	
	public static void main(String[] args) {
		solution();
	}
}
