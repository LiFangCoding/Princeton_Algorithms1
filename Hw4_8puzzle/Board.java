/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private int[][] initial;
    private int manhattan;
    private int hamming;
    private int N;
    private int[] idxOfempty;

    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.initial = blocks;
        this.manhattan = calManhattan();
        this.hamming = calHamming();
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        return hamming;
    }

    private int calHamming() {
        int hamSum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    continue;
                }
                int valInGoal = i * N + j + 1;
                if (valInGoal != initial[i][j]) {
                    hamSum++;
                }
            }
        }
        return hamSum;
    }

    public int manhattan() {
        return manhattan;
    }

    private int calManhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += calManhattanForOne(i, j);
            }
        }
        return sum;
    }

    private int calManhattanForOne(int i, int j) {
        if (initial[i][j] == 0) {
            idxOfempty = new int[]{i, j};
            return 0;
        }
        int[] idxInGoal = new int[2];
        int valInitial = initial[i][j];
        idxInGoal[0] = (valInitial - 1) / N;
        idxInGoal[1] = (valInitial - 1) % N;

        return Math.abs(i - idxInGoal[0]) + Math.abs(j - idxInGoal[1]);
    }

    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    return initial[i][j] == 0;
                } else if (initial[i][j] != (i * N + j + 1)) {
                    return false;
                }
            }
        }
        return false;
    }

    public Board twin() {
        int[][] twinBlocks = cloneBlocks();
        int col1 = 0;
        int col2 = 0;

        for (int i = 0; i < N; i++) {
            if (twinBlocks[0][i] != 0) {
                col1 = i;
            }
        }
        for (int i = 0; i < N; i++) {
            if (twinBlocks[1][i] != 0) {
                col2 = i;
            }
        }
        swap(twinBlocks, 0, col1, 1, col2);
        return new Board(twinBlocks);
    }

    private void swap(int[][] twinBlocks, int i1, int col1, int i2, int col2) {
        int temp = twinBlocks[i1][col1];
        twinBlocks[i1][col1] = twinBlocks[i2][col2];
        twinBlocks[i2][col2] = temp;
    }

    private int[][] cloneBlocks() {
        int[][] cloneBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cloneBlocks[i][j] = initial[i][j];
            }
        }
        return cloneBlocks;
    }


    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        return (this.initial.equals(that.initial));
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new LinkedList<>();

        int[] x = {1, -1, 0, 0};
        int[] y = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int newrow = idxOfempty[0] + x[i];
            int newcol = idxOfempty[1] + y[i];
            if (isValid(newrow, newcol)) {
                int[][] neighborBlocks = cloneBlocks();
                swap(neighborBlocks, newrow, newcol, idxOfempty[0], idxOfempty[1]);
                neighbors.add(new Board(neighborBlocks));
            }
        }

        return neighbors;
    }

    private boolean isValid(int row, int col) {
        return (row >= 0) && (row < N) && (col >= 0) && (col < N);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", initial[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

        Board board = new Board(blocks);

        System.out.println("hamming is " + board.hamming());
        System.out.println("manhattan is " + board.manhattan());
    }

}
