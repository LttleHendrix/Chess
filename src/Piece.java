import java.util.Comparator;

public class Piece {

    int row = -1;
    int col = -1;
    String color = "none";


    public Piece(int row, int col, String color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public Piece clone() {
        return new Piece(row, col, color);
    }


    public boolean isInSquare(int row, int col) {
        if(this.row == row && this.col == col) {
            return true;
        } return false;
    }

    public void movePiece(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getColor() {
        return color;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public boolean isLegalMove(Board board, int newRow, int newCol) {
        return false;
    }

    public String getType() {
        return "Deez";
    }

    public boolean badCheckMove(Board board, int row, int col) {

        Board board1 = board.clone();
        Move move = new Move(this, row, col);
        move.doMove(board1);
        boolean check = board.isInCheck(this.color);
        move.undoMove(board1);
        return check;
    }



}