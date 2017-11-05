package backtrack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Implements the min-conflict local search CSP solver 
 */
public class MinConflicts implements CSPSolver {

	@Override
	public <E> CSPResult<E> solve(CSP<E> csp) {
		/* TODO
		 * find reasonable value for max iterations.
		 */
		
		return minConflicts(csp, 100000);
	}
	
	/**
	 * Implements the min-conflicts algorithm.
	 * @param csp The csp to solve
	 * @param maxSteps The max number of steps
	 * @return A solution to the csp, or null if none was found.
	 */
	@SuppressWarnings("unchecked")
	private static <E> CSPResult<E> minConflicts(CSP<E> csp, int maxSteps) {
//		 TODO
//		  The implementation can pretty much follow the pseudo code
//		  in the text book:
//		  
//		  current <- an initial complete assignment for csp
		Assignment<E> current = createCompleteAssignment(csp);

		String temp = "";
		
		for(int i = 0; i < maxSteps; i++){
			if(csp.isConsistent(current)) {
				return new CSPResult(current, i);
			}
			//String var = getRandomConflictedVariable(current, csp, temp);
			String var = csp.variables.get((int) (Math.random() * csp.variables.size()));
			int min_con = Integer.MAX_VALUE;
			E val = null;
			for(E v : csp.domains.get(var)) {
			      	int x = conflicts(var, val, current, csp); 
			        if(x < min_con) {
			          min_con = x; // 
			          val = v; 
			        }
			}
			temp = var;
			current.put(var, val);		 
		
	}
		return null;
	}


	/**
	 * Randomly selects a conflicted variable.
	 * @param current The current assignment
	 * @param csp The csp
	 * @return A randomly chosen conflicted variable.
	 */
	private static <E> String getRandomConflictedVariable(Assignment<E> current, CSP<E> csp, String temp) {

		Set<String> conflict = new HashSet<String>();
		for(Constraint c: csp.constraints) {
			if(!c.isConsistent(current)) {
				for(String s : c.getScope()){
					conflict.add(s);
				}
//				conflict.add(c.getScope().get(0));
//				conflict.add(c.getScope().get(1));
			}
		}
		
		String[] conflicts = (String[]) conflict.toArray(new String[0]);
//		//String conflictrand =  conflicts[(int) Math.random() * conflicts.length];
//		String conflictrand = conflicts[0];
//		conflict.remove(conflictrand);
		String conflictrand =  conflicts[(int) (Math.random() * conflicts.length)];
		while(conflictrand == temp)
			conflictrand =  conflicts[(int) (Math.random() * conflicts.length)];
		
		return conflictrand;
		
	}
	
	/**
	 * Creates an assignment in which every varibale is assigned a value from its domain.
	 * @param csp The underlying csp that defines the domains and the variables
	 * @return A complete assignment
	 */
	private static <E> Assignment<E> createCompleteAssignment(CSP<E> csp) {
		/* TODO
		 * create a new assignment and randomly assign a value to every
		 * variable from its domain.
		 */
		
		Assignment<Integer> assignment = new Assignment<Integer>();
		//int size = 4;
		for(String variable: csp.variables ){
			//for(E val: csp.domains.get(variable)) {
				//System.out.print(variable);
				//int size = 4;
				//while(csp.domains.size() < size){
				int x = (int) (Math.random() * csp.domains.get(variable).size());
				
				assignment.put(variable, (Integer) csp.domains.get(variable).get(x));
		}
			//}
		
		return (Assignment<E>) assignment;
	}
	
	/**
	 * Computes the number of conflict based on an assignment, but with one variable
	 * set to a specific value.
	 * @param var The variable to be checked
	 * @param value The value to be checked
	 * @param current The current assignment used as basis
	 * @param csp The csp problem
	 * @return The number of conflict given the current assignment, but with var=value
	 */
	private static <E> int conflicts(String var, E value, Assignment<E> current, CSP<E> csp) {

		int incons = 0;
		E val = null;
	    if(current.containsKey(var)){
	      val = current.get(var);
	    }
	  
		current.put(var, val); //needed to check if value is conflicted
		for(Constraint c : csp.constraints) {
			if(!c.isConsistent(current)) {
				incons++;
			}
		}	
		if(val == null)
			current.remove(var);
		else
			current.put(var, val);
		return incons;
	}
}
