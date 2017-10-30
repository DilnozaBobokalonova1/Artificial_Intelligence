package main;

import java.util.Random;
import java.util.Vector;

/**
 * Class representing a population of individuals
 */
public class Population extends Vector<Individual> {
	private Map map;
		
	/**
	 * Actual standard ctor.
	 * @param map The map.
	 * @param initialSize The initial size of the population.
	 */
	Population(Map map, int initialSize) {
		for(int i = 0; i < initialSize; ++i)
		{
			add(new Individual(map));
		}
	}
	
	/**
	 * Standard ctor.
	 * @param map The map.
	 */
	public Population(Map map) {
		this(map, 0);
	}
	
	/**
	 * Randomly selects an individual out of the population
	 * proportionally to its fitness.
	 * @return The selected individual.
	 */
	Individual randomSelection() {
		// TODO implement random selection
		// an individual should be selected with a probability
		// proportional to its fitness
		//Individual parent1 = this.elementAt((int) (Math.random() * 50));
		//Individual parent2 = this.elementAt((int) (Math.random() * 50));
		/*Individual parent = this.elementAt(0);
		for(int i = 0; i < this.size() - 1; i++) {
			if(this.elementAt(i).getFitness() > this.elementAt(i+1).getFitness()){
				parent = this.elementAt(i);
			}
		}
		*/
		Random generator = new Random();
		Individual parent1 = this.elementAt(generator.nextInt(50));
		Individual parent2 = this.elementAt(generator.nextInt(50));
		if(parent1.getFitness() > parent2.getFitness()) {
			return parent1;
		}
		return parent2;
	}
	
}

