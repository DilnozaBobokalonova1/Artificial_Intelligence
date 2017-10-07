package heuristics;

public class Pair {
    public int x; //first member of pair
    public int y; //second member of pair
    public Pair() {}
    public Pair(int first, int second) {
        this.x = first;
        this.y = second;
    }
    // create and initialize a point with given (x, y)
  
  
    // return Euclidean distance between this point and that point
    public double distanceTo(Pair that) {
        if (that == null) return Double.POSITIVE_INFINITY;
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.hypot(dx, dy);
    }



    // is a->b->c a counter-clockwise turn?
    // +1 if counter-clockwise, -1 if clockwise, 0 if collinear
    public static int ccw(Pair a, Pair b, Pair c) {
        // return a.x*b.y - a.y*b.x + a.y*c.x - a.x*c.y + b.x*c.y - b.y*c.x;
        double area2 = (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }

    // is a-b-c collinear?
    public static boolean collinear(Pair a, Pair b, Pair c) {
        return ccw(a, b, c) == 0;
    }

    // is c between a and b?
    // Reference: O' Rourke p. 32
    public static boolean between(Pair a, Pair b, Pair c) {
        if (ccw(a, b, c) != 0) return false;
        if (a.x == b.x && a.y == b.y) {
            return a.x == c.x && a.y == c.y;
        }
        else if (a.x != b.x) {
            // ab not vertical
            return (a.x <= c.x && c.x <= b.x) || (a.x >= c.x && c.x >= b.x);
        }
        else {
            // ab not horizontal
            return (a.y <= c.y && c.y <= b.y) || (a.y >= c.y && c.y >= b.y);
        }
    }


    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
