package main;

/**
 * A class representing a border between two states.
 */
public class Border {
	public int index1, index2;
	
	public Border(int index1, int index2) {
		this.index1 = index1;
		this.index2 = index2;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) return true;
		Border border = (Border) obj;
		return this.index1 == border.index1 && this.index2 == border.index2;
	}
	
}

