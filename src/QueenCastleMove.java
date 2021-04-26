public class QueenCastleMove extends Move{
    Piece king;

    public QueenCastleMove(Piece king) {
        super();
        this.king = king;
    }

    public String toString() {
        return ("Queenside castle for "+king.getColor());
    }

    public boolean doMove(Board board) {
        king.addTimeMoved();
        board.squareContains(king.getRow(), 0).addTimeMoved();
        king.movePiece(king.getRow(), 2);
        board.squareContains(king.getRow(), 0).movePiece(king.getRow(), 3);
        return true;
    }

    public boolean undoMove(Board board) {
        king.subTimeMoved();
        king.movePiece(king.getRow(), 4);
        board.squareContains(king.getRow(), 3).subTimeMoved();
        board.squareContains(king.getRow(), 3).movePiece(king.getRow(), 0);
        return true;
    }
}
