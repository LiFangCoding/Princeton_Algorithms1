/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private int moves;
    private Queue<Board> queueOriginal;
    private Queue<Board> queueTwin;
    private Queue<Board> queueSol;

    private MinPQ<Board> minpq = new MinPQ<Board>(new Comparator<Board>() {
        @Override
        public int compare(Board o1, Board o2) {
            Integer o1Priority = o1.dimension() + o1.manhattan();
            Integer o2Priority = o2.dimension() + o2.manhattan();

            return o1Priority.compareTo(o2Priority);
        }
    });

    private MinPQ<Board> minpqTwin = new MinPQ<Board>(new Comparator<Board>() {
        @Override
        public int compare(Board o1, Board o2) {
            Integer o1Priority = o1.dimension() + o1.manhattan();
            Integer o2Priority = o2.dimension() + o2.manhattan();

            return o1Priority.compareTo(o2Priority);
        }
    });

    public Solver(Board board) {
        moves = 0;
        Board predecessor = null;
        queueOriginal = new LinkedList<>();

        minpq.insert(board);

        while (!minpq.isEmpty()) {
            predecessor = minpq.delMin();
            queueOriginal.add(predecessor);
            if (predecessor.isGoal()) {
                break;
            }

            for (Board neighbor : predecessor.neighbors()) {
                if (neighbor.equals(predecessor)) {
                    continue;
                }
                minpq.insert(neighbor);
            }
            moves++;
        }
    }



    //
//    // is the initial board solvable?
    public boolean isSolvable() {
        return true;
    }

    //    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    //
//    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return queueOriginal;
    }
//
//    // solve a slider puzzle (given below)
//    public static void main(String[] args) {
//
//    }
}
