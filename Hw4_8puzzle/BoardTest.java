 import org.junit.jupiter.api.BeforeEach;

 import static org.junit.jupiter.api.Assertions.*;

 class BoardTest {
     Board board;
     Board goal;

     @BeforeEach
     void setUp() {
         int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
         board = new Board(blocks);
         int[][] goals = {{1,2,3}, {4,5,6}, {7,8,0}};
         goal = new Board(goals);
     }

     @org.junit.jupiter.api.Test
     void dimension() {
         assertEquals(3, board.dimension());
     }

     @org.junit.jupiter.api.Test
     void hamming() {
         assertEquals(5, board.hamming());
     }

     @org.junit.jupiter.api.Test
     void manhattan() {
         assertEquals(10, board.manhattan());
     }

     @org.junit.jupiter.api.Test
     void isGoal() {
         assertFalse(board.isGoal());
         assertTrue(goal.isGoal());
     }

     @org.junit.jupiter.api.Test
     void twin() {
     }

     @org.junit.jupiter.api.Test
     void equals() {

     }

     @org.junit.jupiter.api.Test
     void neighbors() {
         Iterable<Board> neighbors =  board.neighbors();
         for (Board b : neighbors) {
             System.out.println(b.toString());
         }
     }

     @org.junit.jupiter.api.Test
     public void testtoString() {
         System.out.println(board.toString());
     }

     @org.junit.jupiter.api.Test
     void main() {
     }
 }
