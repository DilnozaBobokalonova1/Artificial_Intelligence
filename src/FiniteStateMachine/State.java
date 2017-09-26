package FiniteStateMachine;

public class State {
	int missionariesLeft;
	int cannibalsLeft;
	int side1;
	int missionariesRight;
	int cannibalsRight;
	int side2;
	int currentLocation;
	boolean visited;
	public State(int missionariesLeft, int cannibalsLeft, int side1, int missionariesRight, int cannibalsRight, int side2, int currentLocation) {
		this.missionariesLeft = missionariesLeft;
		this.cannibalsLeft = cannibalsLeft;
		this.side1 = side1;
		this.missionariesRight = missionariesRight;
		this.cannibalsRight = cannibalsRight;
		this.side2 = side2;
		this.currentLocation = currentLocation;
	}
	
	public boolean visited(State two) {
		boolean equal = false;
		if((this.missionariesLeft == two.missionariesLeft) && (this.cannibalsLeft == two.cannibalsLeft)
				&& (this.missionariesRight == two.missionariesRight) && (this.cannibalsRight == two.cannibalsRight)
				&& (this.side1 == two.side1) && (this.side2 == two.side2) && (this.currentLocation == two.currentLocation)) {
			equal = true;
		}
		return equal;
	}
	
	public boolean allowed() {
		boolean allowed = false;
		if(((this.missionariesLeft >= this.cannibalsLeft) && (this.missionariesRight >= this.cannibalsRight) && 
				(this.cannibalsLeft >= 0) && (this.cannibalsRight >= 0) && (this.missionariesLeft >= 0) && 
				(this.missionariesRight >= 0)) || (((this.missionariesLeft == 0) || (this.missionariesRight == 0)) 
				&& (this.cannibalsLeft >= 0) && (this.cannibalsRight >= 0) && (this.missionariesLeft >= 0) && 
				(this.missionariesRight >= 0))){
			allowed = true;
		}
		return allowed;
	}
	public String toString() {
		return  "(" + this.missionariesLeft + ", " + this.cannibalsLeft + ", " + this.side1 + ", " + this.missionariesRight + ", " + 
				this.cannibalsRight + ", " + this.side2 + ", " + this.currentLocation + ")";
	}
	
	  @Override
	    public boolean equals(Object o) {

	        if (o == this) return true;
	        if (!(o instanceof State)) {
	            return false;
	        }

	        State state = (State) o;

	        return state.missionariesLeft == missionariesLeft &&
	                state.cannibalsLeft == cannibalsLeft &&
	                state.side1 == side1 &&
	                state.cannibalsRight == cannibalsRight &&
	                state.missionariesRight == missionariesRight &&
	                state.side2 == side2 &&
	                state.currentLocation == currentLocation;
	    }

	    //Idea from effective Java : Item 9
	    @Override
	    public int hashCode() {
	        int result = 17;
	        result = 31 * result + missionariesLeft;
	        result = 31 * result + missionariesRight;
	        result = 31 * result + cannibalsRight;
	        result = 31 * result + cannibalsLeft;
	        result = 31 * result + currentLocation;
	        return result;
	    }
}
