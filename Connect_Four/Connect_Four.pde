int bs = 100;
Game game = new Game();
int playerMove;
PFont f;

void setup() {
    size(700, 700);
    ellipseMode(CORNER); 
    f = createFont("Arial",16,true);
    
}

void mousePressed() {
    int col = mouseX / bs;
    game.playRound(col);
    
}

void mouseReleased() {
    game.playRound(0);
}

void draw() {
    
    background(196);
    
    for (int row = 0; row < Constants.NUMROWS; ++row) {
        for (int col = 0; col < Constants.NUMCOLS; ++col) {
            fill(255);
            rect(col*bs, row*bs, bs, bs);
            if (game.board.getPiece(row, col) > 0) {
                if (game.board.getPiece(row, col) == 1) fill(#007fff);
                if (game.board.getPiece(row, col) == 2) fill(#DC143C);
                ellipse(col*bs, row*bs, bs, bs);
            }
        }
    }
    stroke(175);
    textAlign(CENTER);
    textFont(f, 32);
    if (game.gameOver && game.playerIndex == 1) {
        text("Player Wins", 350, 650);
    }
    else if (game.gameOver && game.playerIndex == 0) {
        text("Computer Wins", 350, 650);
    }
    else if (game.playerIndex == 0) {

        text("Click Column To Place Piece", 350, 650);
    }
    else {
        text("Computer Making Move...", 350, 650);
    }

    
}




