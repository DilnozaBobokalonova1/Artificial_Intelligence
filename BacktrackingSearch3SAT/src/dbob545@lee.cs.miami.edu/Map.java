package backtrack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 * A class representing a map of states and borders. 
 */
public class Map {
	Vector<Border> borders;
	Vector<String> states;
	
	public Map() {
		borders = new Vector<Border>();
		states = new Vector<String>();
	}
	
	/**
	 * Translates this map into a csp.
	 * @return The csp modeling this map.
	 */
	public CSP<Integer> createCSP() {
		CSP<Integer> csp = new CSP<Integer>();
		
		csp.variables = states; //assigns states to the variables
		Collection<Integer> domain = new ArrayList<Integer>(4);
		for(int i = 0; i < 4; ++i) {
			domain.add(i);
		}
		
		for(String i : states) {
			csp.domains.put(i, new ArrayList<Integer>(domain));
		}
	
		
//		
//		for(String i : states) {
//			List<Integer> domain = new ArrayList<Integer>(4);
//			
//			//int size = (1 + (int)(Math.random()*4));
//			int size = 4;
//
//			
//			while(domain.size() < size) {
//				int x = (int)(Math.random()*4);
//				if(!domain.contains(x))
//					domain.add(x);
//			}
//			
//			csp.domains.put(i, new ArrayList<Integer>(domain));
//			/** fills it up in this manner: [[North Carolina=[0,1,2,3], South Carolina=[0,1,2,3]...] **/
//			 
//		}
		
		for(Border i : borders) {
			csp.constraints.add(new InequalityConstraint(states.get(i.index1), states.get(i.index2)));
		}
//		for(int i = 0; i < borders.size(); i++) {
//			InequalityConstraint vals = new InequalityConstraint(states.get(borders.get(i).index1), states.get(borders.get(i).index2));
//			csp.constraints.add(vals);
//		}
		
		return csp;
	}
}
