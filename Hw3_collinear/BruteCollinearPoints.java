/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsPara) {
        if (pointsPara == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (Point point : pointsPara) {
            if (point == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        Point[] points = Arrays.copyOfRange(pointsPara, 0, pointsPara.length);
        Arrays.sort(points);
        if (isDuplicate(points)) {
            throw new java.lang.IllegalArgumentException();
        }

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

    private boolean isDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
    }
}
