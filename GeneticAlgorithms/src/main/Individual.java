package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Random;
public class Individual {
	private Map map; // the map
	private double fitness; // fitness is cached and only updated on request whenever necessary
	// TODO some representation of the genom of the individual
	private String[] chromosome = new String[10]; //represents a chromose each spot representing a color for the node, ranging from 0-9 inclusive
	private ArrayList<String> colors = new ArrayList<String>() {{
		add("G");
		add("B");
		add("Y");
		add("R");
	}};
	private int[][] matrix = new int[10][10];
	
	/**
	 * Updates the fitness value based on the genom and the map.
	 */
	public void updateFitness() {
		// TODO implement fitness function
		//this has to use info from the map, specifically num of vertices plus num of borders and build an adjacent matrix for it
		//first step is to build an adjacency matrix for it using a double for loop
		
		int fitnessTracker2 = 0;
		boolean adjColorTaken = false;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(matrix[i][j] == 1) {
					if (!chromosome[i].equals(chromosome[j])) { //if the color of this specific node does not equal the color of its neighboring node, increase fitness
						fitness++;
					}
					else{
						adjColorTaken = true;
					}
				}
			}
			if(fitness > 0 && adjColorTaken == false) {
				fitnessTracker2+=fitness;
				fitness = 0; //reinitialize for the next node in the chromosome
			}
			//else {
			//	adjColorTaken = false;
			//}
		}
		fitness = fitnessTracker2;
	}

	/**
	 * Default ctor. Creates a (valid) random individual.
	 * @param map The US states map.
	 */
	public Individual(Map map) {
		Random generator = new Random();
		this.map = map;
		adjacencyMatrix(map);
		for(int i = 0; i < map.states.size(); i++) {
				
				this.chromosome[i] = (String) colors.get(generator.nextInt(4));
		}
		// TODO implement random generation of an individual
	
		updateFitness();				
	}
	
	public void adjacencyMatrix(Map map1) {
		
		for(int i = 0; i < map1.states.size(); i++) {
			for(int j = 0; j < map1.states.size(); j++) {
				//System.out.println(map1.borders.elementAt(i).index1);
				//System.out.println(map1.borders.elementAt(i).index2);
				if(i == j) {
					this.matrix[i][j] = 0;
				}
				
				//else if(map1.borders.elementAt(i).index1 == i && 
					//	map1.borders.elementAt(i).index2 == j){
				if(map1.borders.contains(new Border(i, j))){
					this.matrix[i][j] = 1;
				}
				else {
					this.matrix[i][j] = 0;
				}
			}
		}
		
	}
	/*
	public void fillColors(){
		colors.add("G");
		colors.add("Y");
		colors.add("B");
		colors.add("R");
		
	}*/
	/**
	 * Reproduces a child randomly from two individuals (see textbook).
	 * @param x The first parent.
	 * @param y The second parent.
	 * @return The child created from the two individuals.
	 */
	public static Individual reproduce(Individual x, Individual y) {
	/**choose a random point along the chromosome array which you will divide into
		  before and after and fill up the child with before chromosomes from parent x and 
		  the after chromosomes from parent y **/
		Random generator = new Random();
		int crossPoint = generator.nextInt(x.map.states.size()); 
		
		Individual child = new Individual(x.map);
		
		// TODO reproduce child from individuals x and y
	/** do the previously stated instructions using two for loops **/
		for(int i = 0; i <= crossPoint; i++) {
			child.chromosome[i] = x.chromosome[i]; 
		}
		for (int j = crossPoint + 1; j < y.map.states.size(); j++) {
			child.chromosome[j] = y.chromosome[j];
		}
		child.updateFitness();
		return child;
	}

	/**
	 * Returns the current fitness value of the individual.
	 * @return The current fitness value.
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * Randomly mutates the individual.
	 */
	
	public void mutate() {
		// TODO implement random mutation of the individual
		int index = 0;
		Random generator = new Random();
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(matrix[i][j] == 1) {
					if (chromosome[i].equals(chromosome[j])) { //if the color of this specific node does not equal the color of its neighboring node, increase fitness
						ArrayList<String> validColors = new ArrayList(){{ add("G"); add("B"); add("Y"); add("R");}};
						ArrayList adjacentColors = new ArrayList();
						adjacentColors.add(chromosome[j]);
						String currentColor = chromosome[i];
						
						for(int p = 0; p < 10; p++) {
							if(matrix[i][p] == 1) {
								adjacentColors.add(chromosome[p]);
								index++;
							}
						}
						//String[] adj = (String[]) adjacentColors.toArray();
						int size1 = 4;
						for(int z = 0; z < index; z++) {
							
								
								if(validColors.contains(adjacentColors.get(z))) {
									validColors.remove(adjacentColors.get(z));
									size1--;
								}
							
						}
						if(!validColors.isEmpty()){
						chromosome[i] = (String) validColors.get(generator.nextInt(size1));
						}
					}
				}
				index = 0;
			}
			
		}
		
		updateFitness();
	}

	/**
	 * Checks whether the individual represents a valid goal state.
	 * @return Whether the individual represents a valid goal state.
	 */
	public boolean isGoal() {
		return fitness == map.borders.size();
	}
	
	/**
	 * Prints out the individual to the console.
	 */
	void print() {
		System.out.println("fitness: " + fitness);
		for(int i = 0; i < map.states.size(); i++) {
			System.out.println(map.states.get(i) + ": " + chromosome[i]);
		}
		// TODO implement printing the individual in the following format:
		// fitness: 15
		// North Carolina: 0
		// South Carolina: 2
		// ...
}
}
