package backtrack;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the inequality constraint of two variables. 
 */
public class InequalityConstraint implements Constraint {
	private String variable1, variable2;

	public InequalityConstraint(String value1, String value2) {
		this.variable1 = value1;
		this.variable2 = value2;
	}

	@Override
	public List<String> getScope() {
		/* TODO
		 * return a list containing value1 and value2
		 */
		List<String> theListOfVals = new ArrayList<String>();
		theListOfVals.add(variable1);
		theListOfVals.add(variable2);
		return theListOfVals;
	}

	@Override
	public <E> boolean isConsistent(Assignment<E> assignment) {
		
		if(!assignment.containsKey(variable1) || !assignment.containsKey(variable2))
			return true;
		
		return !assignment.get(variable1).equals(assignment.get(variable2));
		
		
		/* TODO
		 * Check if the assignment is consistent with the constraint,
		 * or if it violates it. It is consistent if the assigned values
		 * of the two variables differ. Keep in mind, that if the assignment
		 * does not contain an assigned value for both variables, the
		 * constraint is not violated, and therefore consistent!
		 */
		
		
	}
	
	@Override
	public String toString() {
		return variable1 + " != " + variable2;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof InequalityConstraint)) {
			return false;
		}
		InequalityConstraint other = (InequalityConstraint)o;
		return variable1.equals(other.variable1) && variable2.equals(other.variable2);
	}
}
