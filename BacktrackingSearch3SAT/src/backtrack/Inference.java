package backtrack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class implementing some inference on the domains of some variables
 * of a CSP.
 * @param <E> The type of values of variables in the underlying CSP.
 */
@SuppressWarnings("serial")
public class Inference<E> extends HashMap<String, Set<E>> {
	/**
	 * Applies the inference to the csp, effectively modifying the
	 * domains according to the inferences.
	 * @param csp The csp to be modified.
	 */
	public void reduceDomain(CSP<E> csp) {
		for(Map.Entry<String, Set<E>> i : this.entrySet()) {
			/**returns a collection-view of the map, whose elements are of this class. 
			 * The only way to obtain a reference to a map entry is from the iterator 
			 * of this collection-view. These Map.Entry objects are valid only for 
			 * the duration of the iteration
			 */
			csp.domains.get(i.getKey()).removeAll(i.getValue()); //removes the entry in the domain whose value equals the value k
		}
	}
	
	/**
	 * Undoes the changes of an inference to a csp, effectively
	 * restoring the domain values that were previously changed.
	 * @param csp
	 */
	public void restoreDomain(CSP<E> csp) {
		for(Map.Entry<String, Set<E>> i : this.entrySet()) {
			csp.domains.get(i.getKey()).addAll(i.getValue());
		}
	}
}
