package Pieces;

public class KingCastleMove extends Move {

    King king;

    public KingCastleMove(King king, Board board) {
        super(king, -1, -1, board);
        this.king = king;
    }

    public String toString() {
        return ("Kingside castle for "+king.getColor());
    }

    public boolean doMove(Board board) {
        king.addTimeMoved();
        king.setHasCastled(true);
        board.squareContains(king.getRow(), 7).addTimeMoved();
        king.movePiece(king.getRow(), 6);
        board.squareContains(king.getRow(), 7).movePiece(king.getRow(), 5);
        return true;
    }

    public boolean undoMove(Board board) {
        king.subTimeMoved();
        king.setHasCastled(false);
        king.movePiece(king.getRow(), 4);
        board.squareContains(king.getRow(), 5).subTimeMoved();
        board.squareContains(king.getRow(), 5).movePiece(king.getRow(), 7);
        return true;
    }

















}
