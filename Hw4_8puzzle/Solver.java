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
    private boolean solvable;

    private Queue<Board> sol;
    private Queue<Board> solTwin;



    public Solver(Board board) {
        moves = 0;

        Board predecessor = null;
        sol = new LinkedList<>();
        MinPQ<Board> minpq = new MinPQ<Board>(new comparatorBoard());
        minpq.insert(board);

        Board predecessorTwin = null;
        solTwin = new LinkedList<>();
        MinPQ<Board> minpqTwin = new MinPQ<Board>( new comparatorBoard());
        minpqTwin.insert(board.twin());

        while (!minpq.isEmpty() && !minpqTwin.isEmpty()) {
            if (searchOneStep(minpq, sol, predecessor)) {
                solvable = true;
                break;
            }

            if (searchOneStep(minpqTwin, solTwin, predecessorTwin)) {
                solvable = false;
                break;
            }
            moves++;
        }
    }

    private boolean searchOneStep(MinPQ<Board> pq, Queue<Board> sol, Board predecessor) {
        Board b = pq.delMin();
        sol.add(b);

        if (b.isGoal()) {
            return true;
        }

        addNeighborTopq(b, pq, predecessor);
        predecessor = b;
        return false;
    }

    private void addNeighborTopq(Board b, MinPQ<Board> pq, Board predecessor) {
        for (Board neighbor : b.neighbors()) {
            if (neighbor.equals(predecessor)) {
                continue;
            }
            pq.insert(neighbor);
        }
    }


    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return sol;
        }
        return null;
    }

    public static void main(String[] args) {

    }

    private class comparatorBoard implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            Integer o1Priority = o1.dimension() + o1.manhattan();
            Integer o2Priority = o2.dimension() + o2.manhattan();
            return o1Priority.compareTo(o2Priority);
        }
    }
}
