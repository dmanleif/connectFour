public class Game {
    boolean gameOver = false;
    Board board = new Board();
    Player player1 = new Human(Constants.P1Symbol);
    Player player2 = new Computer(Constants.P2Symbol, Constants.P1Symbol);
    
    Player[] players = {player1, player2};
    int playerIndex = 0;

    public void playGame() {
        
        while (!gameOver) {
            
            int columnPlayed = players[playerIndex].makeMove(board);
            
            int playerPiece = players[playerIndex].getPiece();
            
            int rowPlaced = board.placePiece(columnPlayed, playerPiece);
            if (rowPlaced == -1) continue;
            
            gameOver = board.isWin(playerPiece, rowPlaced, columnPlayed);

            if (!gameOver) {
                gameOver = board.isTie();
            }
            
            playerIndex = (playerIndex+1) % 2;

            board.printBoard();;
            
        }

        System.out.print("Game Over");

    }



    public void playRound(int col) {

        if (!gameOver) {
            
            int columnPlayed = players[playerIndex].makeMove(board);
            
            if (columnPlayed == -1) columnPlayed = col;
            int playerPiece = players[playerIndex].getPiece();
            
            int rowPlaced = board.placePiece(columnPlayed, playerPiece);
            if (rowPlaced == -1) return;
            
            gameOver = board.isWin(playerPiece, rowPlaced, columnPlayed);

            if (!gameOver) {
                gameOver = board.isTie();
            }
            
            playerIndex = (playerIndex+1) % 2;

            board.printBoard();
            
            
        }

        

        

    }


};
