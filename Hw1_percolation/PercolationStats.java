/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import java.lang.IllegalArgumentException;

public class PercolationStats {

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

    }

    private int estimateThresholdForGrids() {

    }

    // sample mean of percolation threshold
    public double mean() {

    }

    // sample standard deviation of percolation threshold
    public double stddev() {

    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

    }

    public static void main(String[] args) {

    }
}
