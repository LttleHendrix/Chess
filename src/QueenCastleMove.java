public class QueenCastleMove extends Move{
    King king;

    public QueenCastleMove(King king, Board board) {
        super();
        this.king = king;
    }

    public String toString() {
        return ("Queenside castle for "+king.getColor());
    }

    public boolean doMove(Board board) {
        king.addTimeMoved();
        king.setHasCastled(true);
        board.squareContains(king.getRow(), 0).addTimeMoved();
        king.movePiece(king.getRow(), 2);
        board.squareContains(king.getRow(), 0).movePiece(king.getRow(), 3);
        return true;
    }

    public boolean undoMove(Board board) {
        king.subTimeMoved();
        king.setHasCastled(false);
        king.movePiece(king.getRow(), 4);
        board.squareContains(king.getRow(), 3).subTimeMoved();
        board.squareContains(king.getRow(), 3).movePiece(king.getRow(), 0);
        return true;
    }
}
