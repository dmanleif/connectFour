abstract class Player {
    int symbol;
    
    public Player(int symbolIn) {
        symbol = symbolIn;
    }

    public int getPiece() {
        return symbol;
    }


    abstract int makeMove(Board board);

    

};
