package Pieces;

public class Bishop extends Piece {

    private static double[][] squareWeight =
            {{-2,-1,-1,-1,-1,-1,-1,-2},
                    {-1,0,0,0,0,0,0,-1},
                    {-1,0,.5,1,1,.5,0,-1},
                    {-1,.5,.5,1,1,.5,.5,-1},
                    {-1,0,1,1,1,1,0,-1},
                    {-1,1,1,1,1,1,1,-1},
                    {-1,.5,0,0,0,0,.5,-1},
                    {-2,-1,-1,-1,-1,-1,-1,-2}
            };

    public Bishop(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 32;
    }

    public double getPieceValue() {
        if(color.equals("white")) {
            return pieceValue + squareWeight[row][col];
        } else if(color.equals("black")) {
            return pieceValue + squareWeight[7-row][7-col];
        } else {
            return pieceValue;
        }
    }

    public String toString() {
        if(color.equals("white")) {
            return "  Bishop |";
        } else if(color.equals("black")) {
            return ConsoleColors.RED + "  Bishop " + ConsoleColors.RESET + "|";
        } else {
            return " illgl clr";
        }
    }

    public boolean isLegalMove( Board board, int row, int col) {

        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || col < 0) {
            return false;
        }

        if(this.row == row || this.col == col){
            //Did not move diagonally
            return false;
        }

        if(Math.abs(row - this.row) != Math.abs(col - this.col)){
            return false;
        }

        int rowOffset;
        int colOffset;

        if(this.row < row){
            rowOffset = 1;
        }else{
            rowOffset = -1;
        }

        if(this.col < col){
            colOffset = 1;
        }else{
            colOffset = -1;
        }

        int y = this.col + colOffset;
        for(int x = this.row + rowOffset; x != row; x += rowOffset){

            if(board.squareContains(x, y) != null){
                return false;
            }

            y += colOffset;
        }

        if(board.squareContains(row, col) != null) {
            if(board.squareContains(row, col).getColor().equals(color)) {
                return false;
            }
        }

        if(badCheckMove(board, row, col)) {
            return false;
        }

        return true;

    }

    public boolean legalIgnoreCheck( Board board, int row, int col) {

        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || col < 0) {
            return false;
        }

        if(this.row == row || this.col == col){
            //Did not move diagonally
            return false;
        }

        if(Math.abs(row - this.row) != Math.abs(col - this.col)){
            return false;
        }

        int rowOffset;
        int colOffset;

        if(this.row < row){
            rowOffset = 1;
        }else{
            rowOffset = -1;
        }

        if(this.col < col){
            colOffset = 1;
        }else{
            colOffset = -1;
        }

        int y = this.col + colOffset;
        for(int x = this.row + rowOffset; x != row; x += rowOffset){

            if(board.squareContains(x, y) != null){
                return false;
            }

            y += colOffset;
        }

        if(board.squareContains(row, col) != null) {
            if(board.squareContains(row, col).getColor().equals(color)) {
                return false;
            }
        }

        return true;

    }

    public String getType() {
        return "Pieces.Bishop";
    }

}
