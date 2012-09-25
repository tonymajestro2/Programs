/**
 * Canonical Backtracking.
 *
 * Place N Queens on a board such that no two queens can attack each other.
 * It's clear that exactly one queen must be placed in each row.
 * There can't be two queens in a row, but by pidgeon hole principle
 * there would have to be if there were a row without queen.
 *
 * Idea: Place k = 1, 2, 3, 4 etc. queens.  We try placing queen #k in each
 * column that isn't already threatened.  We mark all squares that are
 * threatened by at least one queen.  Backtrack when it's not possible
 * to place queen #k on row #k.  
 *
 * See also J. R. Bitner and E. M. Reingold, Backtrack programming techniques, 
 * Commun. ACM, 18 (1975), 651-656.  http://dx.doi.org/10.1145/361219.361224
 *
 * @author Godmar Back
 */
public class NQueens {
    int N;
    NQueens(int N) {
        this.N = N;
    }

    /* The board is represented as a 1D array (since we can clone it).
     * Positive value: number of queens threatening a square
     */
    final int EMPTY = 0;    // square is not threatened
    final int QUEEN = -1;   // square is occupied by queen

    /* mark in an existing board all fields as threatened
     * from (i, j) in horizontal, vertical, or diagonal direction
     */
    void mark(int board[], int i, int j, int is, int js) {
        while (i >= 0 && i < N && j >= 0 && j < N) {
            board[i * N + j]++;
            i += is;
            j += js;
        }
    }

    /* create a new board based on 'board', place a queen
     * at (i, j), and update threatened squares.
     * Return new board.
     */ 
    int [] setqueen(int [] board, int i, int j) { 
        int newboard[] = (int []) board.clone();
        mark(newboard, i, j, 0, 1);
        mark(newboard, i, j, 0, -1);
        mark(newboard, i, j, 1, 0);
        mark(newboard, i, j, -1, 0);
        mark(newboard, i, j, -1, -1);
        mark(newboard, i, j, -1, 1);
        mark(newboard, i, j, 1, -1);
        mark(newboard, i, j, 1, 1);
        newboard[i * N + j] = QUEEN;
        return newboard;
    }

    void printsolution(int []board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)  {
                if (board[i*N+j] == QUEEN)
                    System.out.print("Q");
                else
                    System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    /**
     * Canonical backtracking.
     *
     * @param queensleft - how many queens left to place on 'board'
     * @param board - partially placed board with (N - queensleft) queens.
     */
    int solve(int queensleft, int board[]) {
        int nsolutions = 0;

        /**
         * Solution found, output it.
         */
        if (queensleft == 0) {
            printsolution(board);
            return 1;
        }

        int nextrow = N - queensleft;   // next queen goes here
        // try each column
        for (int j = 0; j < N; j++)  {
            if (board[nextrow*N+j] == EMPTY) {
                // place a queen here.  
                // Note that 'board' is not changed - setqueen creates a new board,
                // which is subsequently garbage collected.
                nsolutions += solve(queensleft - 1, setqueen(board, nextrow, j));

                // No undo needed!
            }
        }
        return nsolutions;
    }

    public static void main(String []av) {
        int N = Integer.parseInt(av[0]);
        System.out.println(new NQueens(N).solve(N, new int[N*N]) + " solutions");;
    }
}
