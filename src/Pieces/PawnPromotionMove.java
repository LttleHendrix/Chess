package Pieces;

public class PawnPromotionMove extends Move {

    Pawn pawn;
    Piece promotedPiece;
    String newPieceType;

    public PawnPromotionMove(Pawn pawn, int newRow, int newCol, Board board, String newPieceType) {
        super(pawn, -3, -3, board);
        this.pawn = pawn;
        takenPiece = board.squareContains(newRow, newCol);
        oldRow = pawn.getRow();
        oldCol = pawn.getCol();
        this.newRow = newRow;
        this.newCol = newCol;
        this.board = board;
        this.newPieceType = newPieceType;
    }

    public boolean doMove(Board board) {
        if(pawn == null) {
            System.out.println("Could not promote pawn, pawn is null");
            return false;
        }
        if(board.squareContains(oldRow, oldCol) == null) {
            System.out.println("There is no piece in square piece is coming from");
        }
        if(board.squareContains(newRow, newCol) != null) {
            if(pawn.getColor().equals(board.squareContains(newRow, newCol).getColor())) {
                System.out.println("Could not promoted pawn, taking piece of same color");
                return false;
            } else {
                takenPiece = board.squareContains(newRow, newCol);
                board.clearSquare(newRow, newCol);
            }
        }
        if(newPieceType.equals("Pieces.Queen")) {
            promotedPiece = new Queen(newRow, newCol, pawn.getColor());
        } else if(newPieceType.equals("Pieces.Rook")) {
            promotedPiece = new Rook(newRow, newCol, pawn.getColor());
        } else if(newPieceType.equals("Pieces.Bishop")) {
            promotedPiece = new Bishop(newRow, newCol, pawn.getColor());
        } else if(newPieceType.equals("Pieces.Knight")) {
            promotedPiece = new Knight(newRow, newCol, pawn.getColor());
        } else {
            System.out.println("PieceType is not a Pieces.Queen Pieces.Rook Pieces.Bishop or Pieces.Knight");
            return false;
        }
        board.clearSquare(oldRow, oldCol);
        board.addPiece(promotedPiece);
        board.boardPieces.sort(new PieceComparator());
        return true;
    }

    public boolean undoMove(Board board) {
        if(board.squareContains(newRow, newCol) == null) {
            System.out.println("No piece to be moved back (pawn promotion)");
            board.printPieces();
            board.printBoard();
            return false;
        }
        if(board.squareContains(oldRow, oldCol) != null) {
            System.out.println("Old Square is occupied (pawn promotion)");
            return false;
        }
        board.clearSquare(newRow, newCol);
        if(takenPiece != null) {
            board.addPiece(takenPiece);
        }
        board.addPiece(pawn);
        board.boardPieces.sort(new PieceComparator());
        return true;
    }

    public String toString() {
        return ("Pieces.Pawn promotion "+super.toString());
    }





}
