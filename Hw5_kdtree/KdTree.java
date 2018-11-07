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
    private Point2D nearest;

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
        if (p == null) {
            throw new IllegalArgumentException("calls contains() with a null key");
        }
        if (!contains(p)) {
            root = put(root, p, true, new RectHV(0, 0, 1, 1));
            size++;
        }
    }

    public boolean contains(Point2D key) {
        if (key == null) throw new IllegalArgumentException("calls contains() with a null key");
        return contains(root, key, true);
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty()) {
            return queue;
        }
        addNode(root, rect, queue);
        return queue;
    }

    public void draw() {
        draw(root, true);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        nearest = root.p;
        nearest(root, p, true);
        return nearest;
    }

    private Node put(Node x, Point2D key, boolean horizon, RectHV rect) {
        if (x == null) return new Node(key, rect);

        int cmp = getCmp(x, key, horizon);

        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            if (horizon) x.lb = put(x.lb, key, !horizon, x.leftRect());
            if (!horizon) x.lb = put(x.lb, key, !horizon, x.bottomRect());
        }
        else {
            if (horizon) x.rt = put(x.rt, key, !horizon, x.rightRect());
            if (!horizon) x.rt = put(x.rt, key, !horizon, x.topRect());

        }

        return x;
    }


    private boolean contains(Node x, Point2D key, boolean horizon) {
        if (x == null) return false;

        int cmp = getCmp(x, key, horizon);

        if (cmp == 0) return true;
        else if (cmp < 0) return contains(x.lb, key, !horizon);
        else return contains(x.rt, key, !horizon);
    }

    private int getCmp(Node x, Point2D key, boolean horizon) {
        if (key.equals(x.p)) {
            return 0;
        }

        if (horizon) {
            return key.x() < x.p.x() ? -1 : 1;
        }
        else {
            return key.y() < x.p.y() ? -1 : 1;
        }
    }


    private void addNode(Node x, RectHV rect, Queue<Point2D> queue) {
        if (x == null) {
            return;
        }

        if (rect.contains(x.p)) {
            queue.enqueue(x.p);
        }

        if (x.lb != null && x.lb.rect.intersects(rect)) {
            addNode(x.lb, rect, queue);

        }
        if (x.rt != null && x.rt.rect.intersects(rect)) {
            addNode(x.rt, rect, queue);
        }
    }


    private void nearest(Node x, Point2D query, boolean horizon) {
        if (x == null) {
            return;
        }

        // System.out.println("node route is " + x.p.toString());

        // Point2D nearest = x.p;

        int cmp = getCmp(x, query, horizon);

        if (x.p.distanceSquaredTo(query) < nearest
                .distanceSquaredTo(query)) {
            nearest = x.p;
        }

        if (cmp < 0) {
            if (x.lb != null && x.lb.rect.distanceSquaredTo(query) < nearest
                    .distanceSquaredTo(query)) {
                nearest(x.lb, query, !horizon);
            }
            if (x.rt != null && x.rt.rect.distanceSquaredTo(query) < nearest
                    .distanceSquaredTo(query)) {
                nearest(x.rt, query, !horizon);
            }
        }

        else {
            if (x.rt != null && x.rt.rect.distanceSquaredTo(query) < nearest
                    .distanceSquaredTo(query)) {
                nearest(x.rt, query, !horizon);
            }
            if (x.lb != null && x.lb.rect.distanceSquaredTo(query) < nearest
                    .distanceSquaredTo(query)) {
                nearest(x.lb, query, !horizon);
            }
        }
    }


    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private double x;
        private double y;

        public Node(Point2D p) {
            this.p = p;
        }

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.x = p.x();
            this.y = p.y();
        }

        public RectHV leftRect() {
            return new RectHV(this.rect.xmin(), this.rect.ymin(), x, this.rect.ymax());
        }

        public RectHV rightRect() {
            return new RectHV(x, this.rect.ymin(), this.rect.xmax(), this.rect.ymax());
        }

        public RectHV topRect() {
            return new RectHV(this.rect.xmin(), y, this.rect.xmax(), this.rect.ymax());
        }

        public RectHV bottomRect() {
            return new RectHV(this.rect.xmin(), this.rect.ymin(), this.rect.xmax(), y);
        }
    }

    private void draw(Node node, boolean horizon) {
        if (node == null) {
            return;
        }

        if (horizon) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.01);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        draw(node.lb, !horizon);
        draw(node.rt, !horizon);
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.0, 0.375));
        kd.insert(new Point2D(0.375, 0.625));
        kd.insert(new Point2D(0.625, 0.875));
        kd.insert(new Point2D(0.25, 1.0));
        kd.insert(new Point2D(0.75, 0.125));

        System.out.println("the sequence shoule be " + "(0, 0.375), (0.375, 0.625, 0.75,0.125)");

        Point2D query = new Point2D(0.125, 0.0);
        Point2D nearest = kd.nearest(query);

        System.out.println(nearest);
        System.out.println(nearest.distanceSquaredTo(query));
    }

}
