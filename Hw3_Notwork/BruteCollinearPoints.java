/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        Arrays.sort(points);
        segments = new ArrayList<>();
        int size = points.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    if (!isCollinear(points[i], points[j], points[k])) {
                        continue;
                    }
                    for (int m = k + 1; m < size; m++) {
                        if (isCollinear(points[i], points[j], points[k], points[m])) {
                            LineSegment segment = new LineSegment(points[i], points[m]);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
    }

    private boolean isCollinear(Point p1, Point p2, Point p3) {
        return p1.slopeTo(p2) == p2.slopeTo(p3);
    }

    private boolean isCollinear(Point p1, Point p2, Point p3, Point p4) {
        return p1.slopeTo(p2) == p2.slopeTo(p3) && p2.slopeTo(p3) == p3.slopeTo(p4);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return (LineSegment[]) segments.toArray();
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
