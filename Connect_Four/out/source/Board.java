
public class Board {
    int[][] grid = new int[Constants.NUMROWS][Constants.NUMCOLS];
    int numPiecesPlaced = 0;

    public int placePiece(int col, int pSymbol) {
        // find lowest empty spot in column
        int rowToPlace = Constants.NUMROWS - 1;
        while (rowToPlace >= 0 && grid[rowToPlace][col] != 0) {
            --rowToPlace;
        }
        if (rowToPlace != -1) { // can place piece in column
            grid[rowToPlace][col] = pSymbol; // place piece
            ++numPiecesPlaced;
        }
        
        return rowToPlace;
    }
/* 
    public void removePiece(int col) {
        int rowToRemove = Constants.NUMROWS - 1;
        while (rowToRemove >= 0 && grid[rowToRemove][col] != 0) {
            --rowToRemove;
        }
        ++rowToRemove;

        grid[rowToRemove][col] = 0;
    }
  */

    public void removePiece(int row, int col) {
        grid[row][col] = 0;
    }

    public boolean isWin(int pSymbol, int row, int col) {
        return isHorizontalWin(row, pSymbol) || isVerticalWin(col, pSymbol) 
        || isUpDiagonalWin(row, col, pSymbol) || isDownDiagonalWin(row, col, pSymbol);
    }

    public boolean isTie() {
        return numPiecesPlaced == Constants.NUMROWS * Constants.NUMCOLS;
    }

    private boolean isHorizontalWin(int row, int pSymbol) {
        int count = 0;
        for (int col = 0; col < Constants.NUMCOLS; ++col) {
            if (grid[row][col] == pSymbol) {
                ++count;
                if (count == 4) {
                    return true;
                }
            }
            else {
                count = 0;
            }
        }
        return false;
    }

    private boolean isVerticalWin(int col, int pSymbol) {
        int count = 0;
        for (int row = 0; row < Constants.NUMROWS; ++row) {
            if (grid[row][col] == pSymbol) {
                ++count;
                if (count == 4) {
                    return true;
                }
            }
            else {
                count = 0;
            }
        }
        return false;
    }

    private boolean isUpDiagonalWin(int row, int col, int pSymbol) {
        // get starting point of diagonal
        while (row < Constants.NUMROWS - 1 && col > 0) {
            ++row;
            --col;
        }
        
        int count = 0;
        while (row >= 0 && col < Constants.NUMCOLS) {
            if (grid[row][col] == pSymbol) {
                ++count;
                if (count == 4) {
                    return true;
                }
                
            }
            else {
                count = 0;
            }
            --row;
            ++col;
        }
        return false;
    }

    private boolean isDownDiagonalWin(int row, int col, int pSymbol) {
        // get starting point of diagonal
        while (row > 0 && col >  0) {
            --row;
            --col;

        }
        
        int count = 0;
        while (row < Constants.NUMROWS && col < Constants.NUMCOLS) {
            if (grid[row][col] == pSymbol) {
                ++count;
                if (count == 4) {
                    return true;
                }
                
            }
            else {
                count = 0;
            }
            ++row;
            ++col;
        }
        return false;
    }

    public int getPiece(int row, int col) {
        return grid[row][col];
    }

    public void printBoard() {     
        System.out.println('\n');
        System.out.println('\n');
        System.out.println('\n');
        for(int i = 0; i < Constants.NUMROWS; i++) {
            for(int j = 0; j < Constants.NUMCOLS; j++) {
                System.out.printf("%4d ", grid[i][j]);
                
            }
            System.out.println('\n');
        }
        
    }
}
