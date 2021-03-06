import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private boolean isSolvable;
    private int move;
    private Iterable<Board> sol;

    public Solver(Board board) {
        if (board == null) {
            throw new java.lang.IllegalArgumentException();
        }

        MinPQ<SearchNode> minpq = new MinPQ<>(new ComparatorSearchNode());
        SearchNode inital = new SearchNode(board, 0, null, true);
        SearchNode twin = new SearchNode(board.twin(), 0, null, false);
        SearchNode end = search(minpq, inital, twin);

        isSolvable = (end != null && end.isOriginal);
        move = isSolvable ? end.move : -1;
        sol = getSolution(end);
    }

    private SearchNode search(MinPQ<SearchNode> minpq, SearchNode initial, SearchNode twin) {
        minpq.insert(initial);
        minpq.insert(twin);

        while (!minpq.isEmpty()) {
            SearchNode search = minpq.delMin();

            if (search.reachGoal()) {
                return search;
            }

            for (SearchNode next : search.neighbors()) {
                SearchNode predecessor = search.predecessor;
                if (next.equals(predecessor)) {
                    continue;
                }
                minpq.insert(next);
            }
        }
        return null;
    }


    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return move;
    }

    public Iterable<Board> solution() {
        return sol;
    }

    private Iterable<Board> getSolution(SearchNode end) {
        if (!isSolvable()) {
            return null;
        }

        SearchNode solEnd = end;
        Stack<Board> s = new Stack<>();

        while (solEnd != null) {
            s.push(solEnd.board);
            solEnd = solEnd.predecessor;
        }
        return s;
    }

    private class ComparatorSearchNode implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.priority != o2.priority) {
                return Integer.compare(o1.priority, o2.priority);
            } else {
                if (o1.manhatt != o2.manhatt) {
                    return Integer.compare(o1.manhatt, o2.manhatt);
                } else {
                    return Integer.compare(o1.board.hamming(), o2.board.hamming());
                }
            }
        }
    }

    private class SearchNode {
        private int move;
        private int manhatt;
        private int priority;

        private Board board;
        private SearchNode predecessor;
        private boolean isOriginal;


        public SearchNode(Board b, int move, SearchNode predecessor, boolean isOriginal) {
            this.move = move;
            this.board = b;
            this.predecessor = predecessor;
            this.isOriginal = isOriginal;
            this.manhatt = board.manhattan();
            this.priority = manhatt + move;
        }

        public Iterable<SearchNode> neighbors() {
            Queue<SearchNode> neighbors = new LinkedList<>();

            for (Board next : this.board.neighbors()) {
                neighbors.add(new SearchNode(next, move + 1, this, this.isOriginal));
            }

            return neighbors;
        }

        public boolean reachGoal() {
            return this.board.isGoal();
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

            SearchNode that = (SearchNode) y;
            return this.board.equals(that.board);
        }
    }
}
