/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF uf;
    private boolean[] opens;
    private int openSitesNum;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        N = n;
        int gridSize = N * N;
        openSitesNum = 0;
        virtualTop = 0;
        virtualBottom = gridSize + 1;

        opens = new boolean[gridSize + 2];
        opens[virtualTop] = true;
        opens[virtualBottom] = true;

        uf = new WeightedQuickUnionUF(gridSize + 2);

        connectUfIdxToRow(virtualTop, 1);
        connectUfIdxToRow(virtualBottom, N);
    }

    public void open(int row, int col) {
        if (!isValid(row) || !isValid(col)) {
            throw new java.lang.IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            opens[xyTo1D(row, col)] = true;
            connectOpenNeighbors(row, col);
            openSitesNum += 1;
        }
    }

    private void connectOpenNeighbors(int row, int col) {
        int[] rowMove = { -1, 1, 0, 0 };
        int[] colMove = { 0, 0, -1, 1 };

        for (int i = 0; i < rowMove.length; i++) {
            int neighRow = row + rowMove[i];
            int neighCol = col + colMove[i];

            if (neighRow == 0) {
                uf.union(xyTo1D(row, col), virtualTop);
            }
            else if (neighRow == N + 1) {
                uf.union(xyTo1D(row, col), virtualBottom);
            }
            else if (isNeighValidToConnect(neighRow, neighCol)) {
                uf.union(xyTo1D(row, col), xyTo1D(neighRow, neighCol));
            }
        }
    }

    private boolean isNeighValidToConnect(int row, int col) {
        return isValid(row) && isValid(col) && isOpen(row, col);
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row) || !isValid(col)) {
            throw new java.lang.IllegalArgumentException();
        }
        return opens[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!isValid(row) || !isValid(col)) {
            throw new java.lang.IllegalArgumentException();
        }
        return uf.connected(xyTo1D(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        return openSitesNum;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    private int xyTo1D(int row, int col) {
        return (row - 1) * N + col;
    }

    private void connectUfIdxToRow(int num, int row) {
        for (int col = 1; col <= N; col++) {
            if (isOpen(row, col)) {
                uf.union(xyTo1D(row, col), num);
            }
        }
    }

    private boolean isValid(int num) {
        return num >= 1 && num <= N;
    }
}
