/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int n;
    private double mean;
    private double stddev;
    private double sqrOfT;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        sqrOfT = Math.sqrt(trials);
        double[] thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            thresholds[i] = getThreshold(p);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    private double getThreshold(Percolation p) {
        double openNum = 0;

        while (!p.percolates()) {
            int[] xy = getRandomBlockedSite(p);
            p.open(xy[0], xy[1]);
            openNum++;
        }

        return openNum / (n * n);
    }

    private int[] getRandomBlockedSite(Percolation p) {
        int randomRow = StdRandom.uniform(n) + 1;
        int randomCol = StdRandom.uniform(n) + 1;

        while (p.isOpen(randomRow, randomCol)) {
            randomRow = StdRandom.uniform(n) + 1;
            randomCol = StdRandom.uniform(n) + 1;
        }

        return new int[] { randomRow, randomCol };
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev / sqrOfT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev / sqrOfT;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        System.out.println("mean = " + new PercolationStats(n, t).mean());
    }
}
