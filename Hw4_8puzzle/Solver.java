import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
    private SearchNode result;

    public Solver(Board board) {
        if (board == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<SearchNode> minpq = new MinPQ<>(new ComparatorSearchNode());
        SearchNode inital = new SearchNode(0, board, null);
        result = search(minpq, inital);
    }

    private SearchNode search(MinPQ<SearchNode> minpq, SearchNode initial) {
        minpq.insert(initial);

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
        return result != null;
    }

    public int moves() {
        return result.getMove();
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> s = new Stack<>();

        while (result != null) {
            s.push(result.getBoard());
            result = result.getPredecessor();
        }
        return s;
    }

    private class ComparatorSearchNode implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int o1Priority = o1.getPriority();
            int o2Priority = o2.getPriority();
            return Integer.compare(o1Priority, o2Priority);
        }
    }

    private class SearchNode {
        private int move;
        private Board board;
        private SearchNode predecessor;

        public SearchNode(int move, Board b, SearchNode predecessor) {
            this.move = move;
            this.board = b;
            this.predecessor = predecessor;
        }

        public int getMove() {
            return move;
        }

        public int getPriority() {
            return board.manhattan() + move;
        }

        public Board getBoard() {
            return board;
        }

        public SearchNode getPredecessor() {
            return predecessor;
        }

        public Iterable<SearchNode> neighbors() {
            Queue<SearchNode> neighbors = new LinkedList<>();

            for (Board next : this.board.neighbors()) {
                neighbors.add(new SearchNode(move + 1, next, this));
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
