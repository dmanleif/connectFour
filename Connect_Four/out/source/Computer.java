import java.util.HashMap;

public class Computer extends Player{


    int opponentSymbol = Constants.P1Symbol;

    public Computer(int symbolIn, int opponentSymbolIn) {
        super(symbolIn);
        opponentSymbol = opponentSymbolIn;
    }

    public int makeMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int col = 0; col < Constants.NUMCOLS; ++col) {
            int rowPlaced = board.placePiece(col, symbol);
            if (rowPlaced != -1) {
                int stateScore = minimax(board, 8, false, Integer.MIN_VALUE, Integer.MAX_VALUE, rowPlaced, col);
                if (stateScore > bestScore) {
                    bestScore = stateScore;
                    bestMove = col;
                }
                board.removePiece(rowPlaced, col);
            } 
        }
        return bestMove;
    }

    private int minimax(Board board, int depth, boolean isMaximizer, int alpha, int beta, int prevRowPlayed, int prevColPlayed) {
        if (!isMaximizer && board.isWin(symbol, prevRowPlayed, prevColPlayed)) {
            return 1000 + depth;
        }

        if (isMaximizer && board.isWin(opponentSymbol, prevRowPlayed, prevColPlayed)) {
            return -1000 - depth;
        }
        
        if (isMaximizer && board.isTie()) {
            return 0;
        }

        if (!isMaximizer && depth == 0) {
            return heuristic(board, symbol, opponentSymbol); // heuristic
        }

        if (isMaximizer && depth == 0) {
            return -1*heuristic(board, opponentSymbol, symbol); // heuristic
        }

        
        if (isMaximizer) {
            int bestVal = Integer.MIN_VALUE;
            for (int col = 0; col < Constants.NUMCOLS; ++col) {
                int row = board.placePiece(col, symbol);
                if (row != -1) {
                    bestVal = Math.max(bestVal, minimax(board, depth-1, false, alpha, beta, row, col));
                    alpha = Math.max(alpha, bestVal);
                    board.removePiece(row, col);

                    if (bestVal >= beta) return bestVal;

                    
                }

                
            }
            return bestVal;
        }
        else {
            int bestVal = Integer.MAX_VALUE;
            for (int col = 0; col < Constants.NUMCOLS; ++col) {
                int row = board.placePiece(col, opponentSymbol);
                if (row != -1) {
                    bestVal = Math.min(bestVal, minimax(board, depth-1, true, alpha, beta, row, col));
                    beta = Math.min(beta, bestVal);
                    board.removePiece(row, col);

                    if (bestVal <= alpha) return bestVal;
                    
                    
                }
            }
            return bestVal;
        }
    }

    private int heuristic(Board board, int playerSymbol, int oppSymbol) {
        // calculate points for rows

        int score = 0;
        HashMap<Integer, Integer> freqs = new HashMap<Integer, Integer>();
        for (int row = 0; row < Constants.NUMROWS; ++row) {
            for (int col = 0; col < Constants.NUMCOLS; ++col) {
                if (col < 4) {
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);
                }
                else {
                    // remove piece from window
                    freqs.put(board.getPiece(row, col-4), freqs.get(board.getPiece(row, col-4)) - 1);
                    
                    // add piece to window
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);

                   
                }
                // evaluate window
                score += evaluateWindow(freqs, playerSymbol, oppSymbol);
            }
            freqs.clear();
        }

        

        for (int col = 0; col < Constants.NUMCOLS; ++col) {
            for (int row = 0; row < Constants.NUMROWS; ++row) {
                if (row < 4) {
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);
                }
                else {
                    // remove piece from window
                    freqs.put(board.getPiece(row-4, col), freqs.get(board.getPiece(row-4, col)) - 1);
                    
                    // add piece to window
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);

                    
                }
                // evaluate window
                score += evaluateWindow(freqs, playerSymbol, oppSymbol);
                
            }
            freqs.clear();
        }
        
        { // up diagonals
        int[][] dir = {{1,0},{0,1}};
        int startRow = 3;
        int startCol = 0;
        int dirInd = 0;
        for (int diag = 0; diag < 6; ++diag) {
            int diagSize = 0;
            int row = startRow;
            int col = startCol;
            
            while (row >= 0 && col < Constants.NUMCOLS) {
                if (diagSize < 4) {
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);

                }
                else {
                    // remove piece from window
                    freqs.put(board.getPiece(row+4, col-4), freqs.get(board.getPiece(row+4, col-4)) - 1);
                    
                    // add piece to window
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);
                }
                ++diagSize;
                // evaluate window
                score += evaluateWindow(freqs, playerSymbol, oppSymbol);

                --row;
                ++col;
            }
            if (diag == 2) ++dirInd;
            startRow += dir[dirInd][0];
            startCol += dir[dirInd][1];
            freqs.clear();
        }
        }

        {
        int[][] dir = {{-1,0},{0,1}};
        int startRow = 2;
        int startCol = 0;
        int dirInd = 0;
        for (int diag = 0; diag < 6; ++diag) {
            int diagSize = 0;
            int row = startRow;
            int col = startCol;
            
            while (row < Constants.NUMROWS && col < Constants.NUMCOLS) {
                if (diagSize < 4) {
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);
                }
                else {
                    // remove piece from window
                    freqs.put(board.getPiece(row-4, col-4), freqs.get(board.getPiece(row-4, col-4)) - 1);
                    
                    // add piece to window
                    freqs.merge(board.getPiece(row, col), 1, Integer::sum);

                    // evaluate window
                    score += evaluateWindow(freqs, playerSymbol, oppSymbol);
                }
                ++diagSize;
                // evaluate window
                score += evaluateWindow(freqs, playerSymbol, oppSymbol);

                ++row;
                ++col;
            }
            if (diag == 2) ++dirInd;
            startRow += dir[dirInd][0];
            startCol += dir[dirInd][1];
            freqs.clear();
        }
        }
        
        return score;
        




    }

    private int evaluateWindow(HashMap<Integer, Integer> freqs, int playerSymbol, int oppSymbol) {
        int empty = 0;
        
        if (freqs.getOrDefault(playerSymbol, 0) == 3 && freqs.getOrDefault(empty, 0) == 1) {
            return 5;
        }
        if (freqs.getOrDefault(playerSymbol, 0) == 2 && freqs.getOrDefault(empty, 0) == 2) {
            return 2;
        }
        if (freqs.getOrDefault(oppSymbol, 0) == 3 && freqs.getOrDefault(empty, 0) == 1) {
            return -10;
        }
        return 0;


    }


}
