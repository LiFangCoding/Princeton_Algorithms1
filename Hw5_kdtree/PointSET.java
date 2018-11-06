/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (!set.contains(p)) {
            set.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return set.contains(p);
    }

    public void draw() {


    }           // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Queue<Point2D> range = new Queue<>();

        for (Point2D p : set) {
            if (rect.contains(p)) {
                range.enqueue(p);
            }
        }

        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Point2D nearest = null;
        if (set.isEmpty()) {
            return null;
        }

        for (Point2D pointInSet : set) {
            if (nearest == null) {
                nearest = pointInSet;
            }

            if (nearest.distanceSquaredTo(p) > pointInSet.distanceSquaredTo(p)) {
                nearest = pointInSet;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}
