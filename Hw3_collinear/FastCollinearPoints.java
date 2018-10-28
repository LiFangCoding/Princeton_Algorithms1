/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] pointsPara) {
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
            Point origin = points[i];
            Point[] pointsSortedBySlope = Arrays.copyOfRange(points, 0, pointsPara.length);
            Arrays.sort(pointsSortedBySlope, origin.slopeOrder());
            addSegmentFromOrigin(origin, pointsSortedBySlope);
        }
    }

    private void addSegmentFromOrigin(Point origin, Point[] pointsSortedBySlope) {
        int len = pointsSortedBySlope.length;
        int j = 0;

        while (j < len) {
            List<Point> pointsOfSegment = new ArrayList<>();
            double slope = origin.slopeTo(pointsSortedBySlope[j]);
            pointsOfSegment.add(origin);

            int numSameWithOrigin = 0;
            while (j < len && slope == origin.slopeTo(pointsSortedBySlope[j])) {
                pointsOfSegment.add(pointsSortedBySlope[j]);
                j++;
                numSameWithOrigin++;
            }
            if (numSameWithOrigin >= 3) {
                if (isOriginFirst(origin, pointsOfSegment)) {
                    segments.add(new LineSegment(origin, pointsSortedBySlope[j - 1]));
                }
            }
        }
    }

    private boolean isOriginFirst(Point origin, List<Point> pointsOfSegment) {
        List<Point> pointsOfSegmentSorted = new ArrayList<>(pointsOfSegment);
        Collections.sort(pointsOfSegmentSorted);
        return origin.equals(pointsOfSegmentSorted.get(0));
    }

    private boolean isDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return segments.size();
    }      // the number of line segments

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
    }

}
