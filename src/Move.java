public class Move {

    Piece thisPiece;
    Piece takenPiece;
    Board board;
    int oldRow;
    int oldCol;
    int newRow;
    int newCol;
    int minimaxValue;

    public Move(Piece piece, int newRow, int newCol, Board board) {
        thisPiece = piece;
        takenPiece = board.squareContains(newRow, newCol);
        oldRow = piece.getRow();
        oldCol = piece.getCol();
        this.newRow = newRow;
        this.newCol = newCol;
        this.board = board;
    }

    public Move() {

    }

    public void setMove(Move move) {
        this.thisPiece = move.thisPiece;
        this.takenPiece = move.takenPiece;
        this.oldRow = move.oldRow;
        this.oldCol = move.oldCol;
        this.newRow = move.newRow;
        this.newCol = move.newCol;
    }

    public String toString() {
        return ("Row:"+oldRow+" Col:"+oldCol+" to Row:"+newRow+" Col:"+newCol);
    }

    public boolean doMove() {
        System.out.println("Doing move");
        printMove();
        if(board.squareContains(oldRow, oldCol) == null) {
            System.out.println("Could not do move");
            return false;
        }
        if(board.squareContains(newRow, newCol) != null) {
            if(board.squareContains(oldRow, oldCol).getColor().equals(board.squareContains(newRow, newCol).getColor())) {
                System.out.println("Could not do move");
                return false;
            }
            board.squareContains(oldRow, oldCol).addTimeMoved();
            takenPiece = board.squareContains(newRow, newCol);
            board.clearSquare(newRow, newCol);
        }
        thisPiece.movePiece(newRow, newCol);
        board.boardPieces.sort(new PieceComparator());
        return true;
    }

    public boolean undoMove() {
        System.out.println("Undoing move");
        printMove();
        if(board.squareContains(newRow, newCol) == null) {
            System.out.println("No piece to be moved back");
            board.printPieces();
            board.printBoard();
            return false;
        }
        if(board.squareContains(oldRow, oldCol) != null) {
            System.out.println("Old square is occupied");
            return false;
        }
        thisPiece.movePiece(oldRow, oldCol);
        board.squareContains(oldRow, oldCol).subTimeMoved();
        if(takenPiece != null) {
            board.addPiece(takenPiece);
        }
        board.boardPieces.sort(new PieceComparator());
        return true;
    }

    public void setMinimaxValue(int value) {
        this.minimaxValue = value;
    }

    public void printMove() {
        String toPrint = "Moving " + thisPiece + " from " +oldRow+" "+oldCol;
        toPrint += " to " + newRow + " "+newCol;
        if(takenPiece == null) {
            toPrint += " and no piece taken";
        } else {
            toPrint += " and "+takenPiece+" was taken";
        }
        System.out.println(toPrint);
    }

    public boolean badCheckMove() {
        System.out.println("Checking for check");
        this.doMove();
        boolean check = board.isInCheck(thisPiece.color);
        this.undoMove();
        System.out.println("badCheckMove complete");
        System.out.println(""+check);
        return check;
    }






}
