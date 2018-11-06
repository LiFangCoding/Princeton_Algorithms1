/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (!contains(p)) {
            root = put(root, p, true);
        }
        size++;
    }

    private Node put(Node x, Point2D key, boolean horizon) {
        if (x == null) return new Node(key);

        int cmp = getCmp(x, key, horizon);

        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            x.lb = put(x.lb, key, !horizon);
        }
        else {
            x.rt = put(x.rt, key, !horizon);
        }

        return x;
    }

    public boolean contains(Point2D key) {
        if (key == null) throw new IllegalArgumentException("calls contains() with a null key");
        return contains(root, key, true);
    }

    private boolean contains(Node x, Point2D key, boolean horizon) {
        if (x == null) return false;

        int cmp = getCmp(x, key, horizon);

        if (cmp == 0) return true;
        else if (cmp < 0) return contains(x.lb, key, !horizon);
        else return contains(x.rt, key, !horizon);
    }

    private int getCmp(Node x, Point2D key, boolean horizon) {
        if (key.x() == x.p.x() && key.y() == x.p.y()) {
            return 0;
        }

        if (horizon) {
            return key.x() < x.p.x() ? -1 : 1;
        }
        else {
            return key.y() < x.p.y() ? -1 : 1;
        }
    }

    public void draw() {
        draw(root, true);
    }           // draw all points to standard draw

    private void draw(Node node, boolean horizon) {
        if (node == null) {
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        if (horizon) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(node.p.x(), 0, node.p.x(), 1);
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(0, node.p.y(), 1, node.p.y());
        }

        draw(node.lb, !horizon);
        draw(node.rt, !horizon);
    }

    //TODO
    public Iterable<Point2D> range(RectHV rect) {
        return new Queue<Point2D>();

    }       // all points that are inside the rectangle (or on the boundary)

    //TODO
    public Point2D nearest(Point2D p) {
        return new Point2D(0, 0);

    }  // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        KdTree kd = new KdTree();

        kd.insert(new Point2D(0.7, 0.2));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.2, 0.3));
        kd.insert(new Point2D(0.4, 0.7));
        kd.insert(new Point2D(0.9, 0.6));

        System.out.println("the size should be 5, size is " + kd.size());
        System.out
                .println("kd contain be false. contains is " + kd.contains(new Point2D(0.7, 0.3)));
        System.out.println("kd contain be true. contains is " + kd.contains(new Point2D(0.2, 0.3)));

        kd.draw();
    }


    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
        }
    }
}
