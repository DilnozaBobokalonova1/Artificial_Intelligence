package backtrack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class AC3 {
	/**
	 * This class represents a single arc for the AC-3 algorithm.
	 */
	public static class Arc {
		private String value1, value2;

		public Arc(String value1, String value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
	}
	
	/**
	 * Implements the AC-3 algorithm to make a csp arc consistent.
	 * @param csp The csp
	 * @return Whether an inconsistency was found (false) or not (true)
	 * @throws Exception
	 */
	public static <E> boolean ac3(CSP<E> csp) {
//		  TODO
//		  First, set up a queue of all arcs. For each constraint (you can assume that
//		  all constraints are binary constraints) add two arcs, one forward, and one
//		  backwards. 
		
		Queue<Arc> q = new LinkedList<Arc>();
		for(Constraint c : csp.constraints) {
			q.offer(new Arc(c.getScope().get(0), c.getScope().get(1)));
			q.offer(new Arc(c.getScope().get(1), c.getScope().get(0)));
		}
		
		int revised = 0;
		
//        Then implement the following (taken from text book):
//		  
//		  while queue is not empty do
		while(!q.isEmpty()) {
//		    (Xi, Xj) <- REMOVE-FIRST(queue)
			Arc a = q.poll();
//		    if REVISE(csp, Xi, Xj) then
			if(revise(csp, a.value1, a.value2)) {
				revised++;
//		      if size of Di = 0 then return false
				if(csp.domains.get(a.value1).size() == 0) return false;
//		      for each Xk in Xi.NEIGHBORS - {Xj} do
//		        add (Xk, Xi) to queue
				for(String s : neighbors(csp, a.value1)) {
					if(s.equals(a.value2)) continue;
					q.offer(new Arc(s, a.value1));
				}
			}
		}
		
		System.out.println("revised: " + revised);
		
		return true;
//		  
//		  Note that Xi and Xj correspond to Arc.value1 and Arc.value2
//		  after some arc has been polled from the queue.
		 
	}
	
	/**
	 * Implements the revise-routine of the AC-3 algorithm. Effectively iterates
	 * over all domain values of var1 and checks if there is at least 1 possible value
	 * for var2 remaining. If not, removes that value from the domain of var1.
	 * @param csp
	 * @param var1
	 * @param var2
	 * @return
	 */
	private static <E> boolean revise(CSP<E> csp, String a, String b) {
		/* TODO
		 * You may want to use a temporary Assignment to check whether a constraint
		 * is violated by any values for var1 and var2. Iterate over all domain values
		 * of var1. Then iterate over all domain values of var2 and prepare the
		 * temporary assignment accordingly. If all values for var2 produce an
		 * inconsistent assignment, remove the current value from the domain of
		 * var1. Hint: You cannot modify the domain as long as you are iterating over
		 * it, therefore I recommend to temporarily store the values to be deleted in
		 * a list or something, and then delete them all together after you iterated
		 * over all domain values. Also, don't forget to return whether you actually
		 * modified the domain of var1. 
		 */
		Set<E> remove = new HashSet<E>();
		
		for(E va : csp.domains.get(a)) {
			boolean bb = false;
			for(E vb : csp.domains.get(b)) {
				Assignment<E> ass = new Assignment<E>();
				ass.put(a, va);
				ass.put(b, vb);
				bb |= csp.isConsistent(ass);
			}
			if(!bb) remove.add(va);
		}
		
		if(remove.size() > 0) {
			csp.domains.get(a).removeAll(remove);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Computes the "neighbors" of a variable in a CSP. A variable is
	 * a neighbor if it is coupled to another variable by a constraint.
	 * @param csp The csp
	 * @param var The variable the neighbors of which are to be found.
	 * @return The neighbors of the given variable.
	 */
	private static Set<String> neighbors(CSP<?> csp, String var) {
		/* TODO
		 * Iterate over all constraints and check if var is contained
		 * in the constraint's scope. If so, all _other_ variables of
		 * the constraint's scope are neighbors.
		 */
		
		Set<String> s = new HashSet<String>();
		
		for(Constraint c : csp.constraints) {
			List<String> scope = c.getScope();
			if(scope.contains(var)) {
				for(String t : scope) {
					if(!t.equals(var)) s.add(t);
				}
			}
		}
		
		return s;
	}
}
