package backtrack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements the backtracking search with forward checking. 
 */
public class ForwardCheckingCSPSolver extends BacktrackingCSPSolver {
	
	static int infer = 0;
	
	/**
	 * Implements the actual forward checking. Infers the values to be deleted
	 * from the domains of some variables based on the given variable and value.
	 */
	@Override
	protected <E> Inference<E> inference(CSP<E> csp, Assignment<E> assignment, String var, E value) {
	
		Inference<E> inference = new Inference<E>();
		
		for(Constraint c : csp.constraints) {
			List<String> scope = c.getScope();
			
			if(scope.contains(var)) {
				for(String v : scope) {
					if(!assignment.containsKey(v)) {
						
						Set<E> set = new HashSet<E>();
						
						for(E val : csp.domains.get(v)) {
							assignment.put(v, val);
							if(!csp.isConsistent(assignment)) 
								set.add(val);
							assignment.remove(v);
						}
						
						if(set.size() > 0)
							inference.put(v, set);
					}
				}
			}
		}
		
		if(inference.size() > 0) {
			infer++;
		}
		
		return inference;
	}
}
