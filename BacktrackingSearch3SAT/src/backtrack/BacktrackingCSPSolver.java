package backtrack;

import java.util.List;
import java.util.HashSet;

/**
 * A class implementing the backtracking CSP algorithm.
 */
public class BacktrackingCSPSolver implements CSPSolver {
	private int iterationCount;
	
	/**
	 * Initiates the backtracking search for this CSP.
	 * @return A consistent assignment for this CSP
	 */
	@Override
	public <E> CSPResult<E> solve(CSP<E> csp) {
		iterationCount = 0;
		Assignment<E> finalAssignment = backtrack(csp, new Assignment<E>());
		return new CSPResult<E>(finalAssignment, iterationCount);		
	}
	
	/**
	 * Selects an unassigned variable. For this algorithm, it can be just the first, or a
	 * randomly chosen unassigned variable.
	 * @param assignment The assignment for which to determine an unassigned variable
	 * @return A variable that is not assigned yet.
	 */
	protected <E> String selectUnassignedVariable(CSP<E> csp, Assignment<E> assignment) {
	
		
		for(int i = 0; i < csp.variables.size(); i++) {
			if(!assignment.containsKey(csp.variables.get(i))) {
				return csp.variables.get(i);
			}
		}
		return null;
	}
	
	/**
	 * This method returns the values of the domain of a variable in
	 * a specific order. Can be used to implement e.g. least-constraining-value heuristic.
	 * For this algorithm, it can be in any order, e.g. the arbitrary order in which they are
	 * stored in the csp.
	 * @param variable The variable for which the domain values should be returned
	 * @param assignment The current assignment
	 * @return An ordering of all domain values of the given variable.
	 */
	protected <E> List<E> orderDomainValues(CSP<E> csp, String variable, Assignment<E> assignment) {
		return csp.domains.get(variable);
	}
	
	/**
	 * This method returns some inference, which is basically a set of domain values that can be safely
	 * deleted from the domains of the csp. To be used to implement e.g. forward-checking heuristic.
	 * If it returns null this means there is some failure and we should back-track.
	 * For this algorithm, it can always return an empty inference.
	 * @param assignment The current assignment
	 * @param var The selected variable
	 * @param value The value for the given variable
	 * @return An inference based on the current state of the csp, or null if there is a failure.
	 */
	protected <E> Inference<E> inference(CSP<E> csp, Assignment<E> assignment, String var, E value) {
		// return an empty inference. We cannot return null, because this has a different meaning.
		return new Inference<E>();
	}
	
	/**
	 * Actual recursive implementation of the backtracking search. The
	 * implementation can pretty much follow the pseudo-code in the text book.
	 * Basically, the algorithm can be implemented using almost only calls to the other
	 * methods of this and the other classes.
	 * @param assignment The current assignment
	 * @return The updated assignment, or null if there is no valid assignment
	 */
	private <E> Assignment<E> backtrack(CSP<E> csp, Assignment<E> assignment) {
		++iterationCount;
	
		if(assignment.isComplete(csp)){ //if the assignment is complete for the given csp then return the assignment
			return assignment;
		}
		String var = selectUnassignedVariable(csp, assignment);
		for(E value : orderDomainValues(csp, var, assignment)) { //for each color i.e domain value in the available domain values for this specific variable i.e for 0-3 in North Carolina
			
			assignment.put(var, value);
			
			if(csp.isConsistent(assignment)) { //checks if the two states/variables are in the constraints and if they share a border and now the same color
				Inference<E> inferences = inference(csp, assignment, var, value);
				
				if(inferences != null) {
					inferences.reduceDomain(csp);
					Assignment<E> result = backtrack(csp, assignment);
					inferences.restoreDomain(csp);
					if(result != null) {
						return result;
					}
				}
			}
			assignment.remove(var);
			
		}
		 
		return null;
	}

}
