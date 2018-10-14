/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // create n-by-n grid, with all sites blocked
    // corner case:  , isOpen(), or isFull() is outside its prescribed range.
    // corner case: The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.
    public Percolation(int n) {

    }

    // open site (row, col) if it is not open already
    // corner case: Throw a java.lang.IllegalArgumentException
    // if any argument to open() is outside its prescribed range
    public void open(int row, int col) {

    }

    // is site (row, col) open?
    // Open is a status given by open method
    // corner case: Throw a java.lang.IllegalArgumentException
    // if any argument to isOpen() is outside its prescribed range
    public boolean isOpen(int row, int col) {

    }

    // is site (row, col) full?
    // Full means an open site can be connected to an open site in top
    // corner case
    public boolean isFull(int row, int col) {

    }

    // number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    // If there is a full site in the bottom row
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
