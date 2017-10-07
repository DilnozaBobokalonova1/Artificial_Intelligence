package heuristics;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Main {
	public static int ROW;
	public static int COL;
	public static Pair start;
	public static Pair goal;
	public static int numOfPolygons;
	public static Polygon[] allThePolygons;
	public static double distanceToTheDestination;
	static class Cell {
		int parentOfi;
		int parentOfj;
		double f;
		double g;
		double h;
		Cell(int parentOfi, int parentOfj, double f, double g, double h) {
			this.parentOfi = parentOfi;
			this.parentOfj = parentOfj;
			this.f = f;
			this.g = g;
			this.h = h;
		}
		Cell() {
			this.f = f;
			this.g= g;
			this.h = h;
			this.parentOfi = parentOfi;
			this.parentOfj = parentOfj;
		}
	}


	

	public static boolean isValid(int row, int col) {
		return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL);
	}
	public static boolean isNotInsidePolygon (int row, int col) {
		Pair p = new Pair(row, col);
		for(int i = 0; i < numOfPolygons; i++) {
			if(allThePolygons[i].contains2(p) == true) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isGoal(int row, int col, Pair destination) {
		if(row == destination.x && col == destination.y){
			return true;
		}
		else {
			return false;
		}
	}
	
	public static double calculateH(int row, int col, Pair destination) {
		return ((double) Math.sqrt((Math.pow(row - destination.x, 2) + Math.pow(col - destination.y, 2))));
	}
	
	public static void AStarSearch(Pair source, Pair destination) {
		if(isValid(source.x, source.y) == false) {
			System.out.println("Start point is invalid.");
			return;
		}
		if(isValid(destination.x, destination.y) == false) {
			System.out.println("Goal is invalid.");
			return;
		}
		if(isNotInsidePolygon(source.x, source.y) == false || isNotInsidePolygon(destination.x, destination.y) == false) {
			System.out.println("The start point or the destination are inside the polygon!");
			return;
		}
		if(isGoal(source.x, source.y, destination) == true) {
			System.out.println("We are already at the destination");
			return;
		}
		boolean[][] closedList = new boolean[ROW][COL];
		Cell[][] insides = new Cell[ROW][COL];
		int i, j;
		int num1 = ROW * COL;
		for(i = 0; i < ROW; i++) {
			for(j = 0; j < COL; j++) {
				insides[i][j] = new Cell(num1, num1, num1, -1, -1);
				//insides[i][j].setF(0.0);
				//insides[i][j].setG(0.0);
				//insides[i][j].setH(0.0);
				//insides[i][j].setParentOfi(-1);
				//insides[i][j].setParentOfj(-1);
			}
		}
		
		//initialize the parameters of the beginning node
		i = source.x;
		j = source.y;
		insides[i][j].f = 0.0;
		insides[i][j].g = 0.0;
		insides[i][j].h = 0.0;
		insides[i][j].parentOfi = i;
		insides[i][j].parentOfj = j;
		
		Queue<doublePair> openList = new LinkedList<doublePair>(); //<f, <i,j>>
		openList.add(new doublePair(0.0, (new Pair(i, j)))); //putting the start cell on the open list and setting its 'f' as 0
		boolean destinationFound = false;
		
		while(!openList.isEmpty()) {
			doublePair one = openList.poll();
			//adding the vertex to the open list
			i = one.second.x;
			j = one.second.y;
			closedList[i][j] = true;
			distanceToTheDestination = one.first;
			double hNew, gNew, fNew;
			
			
			//successor is upwards
			
			if(isValid(i - 1, j)) {
				if(isGoal(i-1, j, destination)) {
					insides[i-1][j].parentOfi = i;
					insides[i-1][j].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i-1][j] == false && isNotInsidePolygon(i-1, j) == true){
					gNew = insides[i][j].g + 1.0;
					hNew = calculateH(i-1, j, destination);
					fNew = gNew + hNew;
					if(insides[i-1][j].f > fNew) {
						//if(!openList.isEmpty()){
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i-1, j))));
						insides[i-1][j].f = fNew;
						insides[i-1][j].g = gNew;
						insides[i-1][j].h = hNew;
						insides[i-1][j].parentOfi = i;
						insides[i-1][j].parentOfj = j;
					}
				}
			}
			
			//successor is downwards
			
			if(isValid(i + 1, j)) {
				if(isGoal(i-1, j, destination)) {
					insides[i+1][j].parentOfi = i;
					insides[i+1][j].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i+1][j] == false && isNotInsidePolygon(i+1, j) == true){
					gNew = insides[i][j].g + 1.0;
					hNew = calculateH(i+1, j, destination);
					fNew = gNew + hNew;
					if(insides[i+1][j].f > fNew) {
						//if(!openList.isEmpty()){
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i+1, j))));
						insides[i+1][j].f = fNew;
						insides[i+1][j].g = gNew;
						insides[i+1][j].h = hNew;
						insides[i+1][j].parentOfi = i;
						insides[i+1][j].parentOfj = j;
					
					}
				}
			}
			
			//3rd successor of the right
			
			if(isValid(i, j+1)) {
				if(isGoal(i, j+1, destination)) {
					insides[i][j+1].parentOfi = i;
					insides[i][j+1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i][j+1] == false && isNotInsidePolygon(i, j+1) == true){
					gNew = insides[i][j].g + 1.0;
					hNew = calculateH(i, j+1, destination);
					fNew = gNew + hNew;
					if(insides[i][j+1].f > fNew) {
						//if(!openList.isEmpty()) {
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i, j+1))));
						insides[i][j+1].f = fNew;
						insides[i][j+1].g = gNew;
						insides[i][j+1].h = hNew;
						insides[i][j+1].parentOfi = i;
						insides[i][j+1].parentOfj = j;
					}
				}
			}
			
			//4th successor to the left
			if(isValid(i, j - 1)) {
				if(isGoal(i, j-1, destination)) {
					insides[i][j-1].parentOfi = i;
					insides[i][j-1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i][j-1] == false && isNotInsidePolygon(i, j-1) == true){
					gNew = insides[i][j].g + 1.0;
					hNew = calculateH(i, j-1, destination);
					fNew = gNew + hNew;
					if(insides[i][j-1].f > fNew) {
						//if(!openList.isEmpty()) {
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i, j-1))));
						insides[i][j-1].f = fNew;
						insides[i][j-1].g = gNew;
						insides[i][j-1].h = hNew;
						insides[i][j-1].parentOfi = i;
						insides[i][j-1].parentOfj = j;
					}
				}
			}
			//5th successor into the middle between up and right
			
			if(isValid(i - 1, j + 1)) {
				if(isGoal(i-1, j+1, destination)) {
					insides[i-1][j+1].parentOfi = i;
					insides[i-1][j+1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i-1][j+1] == false && isNotInsidePolygon(i-1, j+1) == true){
					gNew = insides[i][j].g + 1.414;
					hNew = calculateH(i-1, j+1, destination);
					fNew = gNew + hNew;
					if(insides[i-1][j+1].f > fNew) {
						//if(!openList.isEmpty()) {
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i-1, j+1))));
						insides[i-1][j+1].f = fNew;
						insides[i-1][j+1].g = gNew;
						insides[i-1][j+1].h = hNew;
						insides[i-1][j+1].parentOfi = i;
						insides[i-1][j+1].parentOfj = j;
					}
				}
			}
			
			//6th is up and left
			
			if(isValid(i - 1, j - 1)) {
				if(isGoal(i-1, j-1, destination)) {
					insides[i-1][j-1].parentOfi = i;
					insides[i-1][j-1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i-1][j-1] == false && isNotInsidePolygon(i-1, j-1) == true){
					gNew = insides[i][j].g + 1.414;
					hNew = calculateH(i-1, j-1, destination);
					fNew = gNew + hNew;
					if(insides[i-1][j-1].f > fNew) {
						//if(!openList.isEmpty()) {
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i-1, j-1))));
						insides[i-1][j-1].f = fNew;
						insides[i-1][j-1].g = gNew;
						insides[i-1][j-1].h = hNew;
						insides[i-1][j-1].parentOfi = i;
						insides[i-1][j-1].parentOfj = j;
					}
				}
			}
			
			//7th is down and right
			
			if(isValid(i + 1, j + 1)) {
				if(isGoal(i-1, j, destination)) {
					insides[i+1][j+1].parentOfi = i;
					insides[i+1][j+1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i+1][j+1] == false && isNotInsidePolygon(i+1, j+1) == true){
					gNew = insides[i][j].g + 1.414;
					hNew = calculateH(i+1, j+1, destination);
					fNew = gNew + hNew;
					if(insides[i+1][j+1].f > fNew) {
						//if(!openList.isEmpty()) {
							//openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i+1, j+1))));
						insides[i+1][j+1].f = fNew;
						insides[i+1][j+1].g = gNew;
						insides[i+1][j+1].h = hNew;
						insides[i+1][j+1].parentOfi = i;
						insides[i+1][j+1].parentOfj = j;
					}
				}
			}
			
			//8th down and left
			
			if(isValid(i + 1, j - 1)) {
				if(isGoal(i-1, j-1, destination)) {
					insides[i+1][j-1].parentOfi = i;
					insides[i+1][j-1].parentOfj = j;
					System.out.println("The goal is found! YAY");
					
					//build a trace function here
					trace(insides, destination);
					destinationFound = true;
					return;
				}
				else if(closedList[i+1][j-1] == false && isNotInsidePolygon(i+1, j) == true){
					gNew = insides[i][j].g + 1.0;
					hNew = calculateH(i+1, j-1, destination);
					fNew = gNew + hNew;
					if(insides[i+1][j-1].f > fNew) {
					//	if(!openList.isEmpty()) {
						//	openList.remove();
						//}
						openList.add(new doublePair(fNew, (new Pair(i+1, j-1))));
						insides[i+1][j-1].f = fNew;
						insides[i+1][j-1].g = gNew;
						insides[i+1][j-1].h = hNew;
						insides[i+1][j-1].parentOfi = i;
						insides[i+1][j-1].parentOfj = j;
					}
				}
			}
		}
		if(destinationFound == false){
			System.out.println("Failed to find Destination Cell\n");
		}
		
	}
	public static void trace(Cell[][] theDetails, Pair destination) {
		
		int row = destination.x;
		int col = destination.y;
		Stack<Pair> thePath = new Stack<Pair>();
		System.out.println("The algorithm was tested on a " + ROW + "x" + COL + " grid with the 8 Polygons as obstacles using A* search!! :D");
		System.out.println("The destination is: ");
		while(!(theDetails[row][col].parentOfi == row && theDetails[row][col].parentOfj == col)) {
			thePath.push(new Pair(row, col));
			int tempRow = theDetails[row][col].parentOfi;
			int tempCol = theDetails[row][col].parentOfj;
			row = tempRow;
			col = tempCol;
		}
		thePath.push(new Pair(row, col));
		while(!thePath.empty()) {
			Pair p = thePath.pop();
			//thePath.pop();
			System.out.printf("--> [%d, %d]", p.x, p.y);
		}
		System.out.println("\nWith the distance to the destination being: " + distanceToTheDestination);
		return;
	}
	
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Would you like to enter your own coordinates and polygon parameters to test my algorithm or would like me to show the test on internally \ngenerated one? Please answer 1 if you want to generate your own and 2 if you want the internal parameters tested instead");
		int answer = keyboard.nextInt();
		if(answer == 1){
		System.out.println("What are the parameters of your state space? (max x and max y cooardinates, please put max x first and max y second with space in between)");
		ROW = keyboard.nextInt();
		COL = keyboard.nextInt();
		//int[][] grid = new int[ROW][COL];
		System.out.println("What is your start point? (enter the x and y coordinates with space in between)");
		boolean good = false;
		start = new Pair();
		while(good == false){
		start = new Pair(keyboard.nextInt(), keyboard.nextInt());
		
		if(start.x > COL || start.x < 0 || start.y > ROW || start.y < 0) { //check for appropriate coordinates being entered
			System.out.println("Wrong coordinates given, please try again.");
			
		}
		else {
			good = true;
		}
		}
		good = false;
		System.out.println("What is your goal point? (enter the x and y coordinates with space in between)");
		goal = new Pair();
		while(good == false){
			goal = new Pair(keyboard.nextInt(), keyboard.nextInt());
			if(goal.x > COL || goal.x < 0 || goal.y > ROW || goal.y < 0) {
				System.out.println("Wrong coordinates given, please try again.");
			}
			else {
				good = true;
			}
		}
		good = false;
		System.out.println("How many polygons does your state space have?");
		numOfPolygons = keyboard.nextInt();
		
		allThePolygons = new Polygon[numOfPolygons];
		for(int i = 1; i <= numOfPolygons; i++) {
			System.out.println("How many vertices does your polygon " + i + " have?");
			int num = keyboard.nextInt();
			allThePolygons[i-1] = new Polygon(); //must create the polygon to not get a null pointer exception
			//allThePolygons[i-1].N = num;
			
			for(int j = 1; j <= num; j++){
				System.out.println("What are the coordinates of the vertex " + j + " of your current polygon? (please enter the two numbers with space in between)");
				Pair xy = new Pair();
				while(good == false){
					xy = new Pair(keyboard.nextInt(), keyboard.nextInt());
					if(xy.x > COL || xy.x < 0 || xy.y > ROW || xy.y < 0) {
						System.out.println("Wrong coordinates given, please try again.");
					}
					else {
						good = true;
					}
				}
				good = false;
				//allThePolygons[i-1].add(xy);
				//if(j >= allThePolygons[i-1].N) {
				//	allThePolygons[i-1].
				//}
			//	allThePolygons[i-1].a[j-1] = xy;
				
				allThePolygons[i-1].add(xy);
			}
		}
		}
		else {
			ROW = 100;
			COL = 100;
			start = new Pair(10, 10);
			goal = new Pair(95, 95);
			numOfPolygons = 8;
			allThePolygons = new Polygon[numOfPolygons]; //the rectangle
			allThePolygons[0] = new Polygon();
			allThePolygons[0].add(new Pair(20,5));
			allThePolygons[0].add(new Pair(20,20));
			allThePolygons[0].add(new Pair(50,5));
			allThePolygons[0].add(new Pair(50,20));
			
			allThePolygons[1] = new Polygon(); //the pentagon
			allThePolygons[1].add(new Pair(20,40));
			allThePolygons[1].add(new Pair(15,70));
			allThePolygons[1].add(new Pair(25,95));
			allThePolygons[1].add(new Pair(27,35));
			allThePolygons[1].add(new Pair(30,70));
			
			allThePolygons[2] = new Polygon();//the triangle
			allThePolygons[2].add(new Pair(25,25));
			allThePolygons[2].add(new Pair(35,70));
			allThePolygons[2].add(new Pair(40,25));
			
			allThePolygons[3] = new Polygon(); //the trapezoid
			allThePolygons[3].add(new Pair(40,60));
			allThePolygons[3].add(new Pair(40,90));
			allThePolygons[3].add(new Pair(50,95));
			allThePolygons[3].add(new Pair(60,80));
			
			allThePolygons[4] = new Polygon(); //the triangle
			allThePolygons[4].add(new Pair(60,10));
			allThePolygons[4].add(new Pair(65,20));
			allThePolygons[4].add(new Pair(55,40));

			allThePolygons[5] = new Polygon();//the rectangle
			allThePolygons[5].add(new Pair(63,35));
			allThePolygons[5].add(new Pair(63,90));
			allThePolygons[5].add(new Pair(78,90));
			allThePolygons[5].add(new Pair(78,35));
			
			allThePolygons[6] = new Polygon();//the hexagon
			allThePolygons[6].add(new Pair(78,30));
			allThePolygons[6].add(new Pair(72,20));
			allThePolygons[6].add(new Pair(84,20));
			allThePolygons[6].add(new Pair(72,8));
			allThePolygons[6].add(new Pair(84,8));
			allThePolygons[6].add(new Pair(78,0));

			allThePolygons[7] = new Polygon(); //the weird looking trapezoid
			allThePolygons[7].add(new Pair(85,28));
			allThePolygons[7].add(new Pair(90,85));
			allThePolygons[7].add(new Pair(80,90));
			allThePolygons[7].add(new Pair(78,85));
		}
		AStarSearch(start, goal);
		
	}
}
