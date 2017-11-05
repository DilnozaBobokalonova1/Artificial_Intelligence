package backtrack;

import java.util.HashMap;

/**
 * An assignment for the variables (or a subset of them) of a csp.
 *
 * @param <E> The type of the values of the variables.
 */
@SuppressWarnings("serial")
public class Assignment<E> extends HashMap<String, E> { //each String contains the value of type E which is essentially the color from 0-3, only one color!
	
	/**
	 * Returns whether all variables of the csp have a value assigned.
	 * @param csp The underlying csp
	 * @return Whether the assignment is complete.
	 */
	public boolean isComplete(CSP<E> csp) {
		
		return keySet().containsAll(csp.variables);
		
//		for(int i = 0; i < csp.variables.size(); i++) {
//			if(!this.containsKey(csp.variables.get(i))) {
//				return false;
//			}
//		}
//		return true;
		
	}

	public void replace(String var, int choice) {
		// TODO Auto-generated method stub
		this.replace(var, choice);
	}
}
