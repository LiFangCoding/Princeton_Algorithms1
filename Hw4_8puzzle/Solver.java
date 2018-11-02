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
    private int move = 0;
    private boolean solvable = false;
    private Queue<Board> solution;

    private Queue<Board> getSol(Board b) {
        Queue<Board> sol = new LinkedList<>();
        Board predecessor = null;
        MinPQ<Board> minpq = new MinPQ<Board>(new comparatorBoard());
        minpq.insert(b);

        while (!minpq.isEmpty()) {
            Board search = minpq.delMin();
            sol.add(search);

            if (search.isGoal()) {
                solvable = true;
                break;
            }

            for (Board neighbor : search.neighbors()) {
                if (neighbor.equals(predecessor)) {
                    continue;
                }
                minpq.insert(neighbor);
            }

            predecessor = search;
            move++;
        }

        return sol;
    }

    public Solver(Board board) {
        solution = getSol(board);
    }


//    private boolean searchOneStep(MinPQ<Board> pq, Queue<Board> sol) {
//        predecessor = pq.delMin();
//        sol.add(predecessor);
//
//        if (predecessor.isGoal()) {
//            return true;
//        }
//
//        addNeighborTopq(predecessor, pq);
//        return false;
//    }

//    private void addNeighborTopq(Board b, MinPQ<Board> pq) {
//        for (Board neighbor : b.neighbors()) {
//            if (neighbor.equals(b)) {
//                continue;
//            }
//            pq.insert(neighbor);
//        }
//    }


    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return move;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return solution;
        }
        return null;
    }

    private class comparatorBoard implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            Integer o1Priority = o1.manhattan() + o1.;
            Integer o2Priority = o2.manhattan();
            return o1Priority.compareTo(o2Priority);
        }
    }
}
