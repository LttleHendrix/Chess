public class Rook extends Piece {

    boolean hasMoved;

    public Rook(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 50;
        hasMoved = false;
    }

    public int getPieceValue() {
        return 50;
    }

    public String toString() {
        if(color.equals("white")) {
            return "   Rook  |";
        } else if(color.equals("black")) {
            return ConsoleColors.RED + "   Rook  " + ConsoleColors.RESET + "|";
        } else {
            return " illgl clr";
        }
    }

    public boolean isLegalMove(Board board, int row, int col) {
        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || row < 0) {
            return false;
        }
        if (this.row == row && this.col == col) {
            return false;
        }
        if(board.squareContains(row, col) != null) {
            if(color.equals(board.squareContains(row, col).getColor())) {
                return false;
            }
        }

        if(this.row == row || this.col == col) {
            int offset;

            if(this.row != row){
                if(this.row < row){
                    offset = 1;
                }else{
                    offset = -1;
                }

                for(int i = this.row + offset; i != row; i += offset){
                    //Go from currentRow to newRow, and check every space
                    if(board.squareContains(i, this.col) != null){
                        return false;
                    }
                }
            }

            //Now do the same for columns
            if(this.col != col){
                if(this.col < col){
                    offset = 1;
                }else{
                    offset = -1;
                }

                for(int i = this.col + offset; i != col; i += offset){
                    //Go from currentCol to newCol, and check every space
                    if(board.squareContains(this.row, i) != null){
                        return false;
                    }
                }
            }
            if(badCheckMove(board, row, col)) {
                return false;
            }
            return true;
        }



        return false;
    }


    public boolean legalIgnoreCheck(Board board, int row, int col) {
        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || row < 0) {
            return false;
        }
        if (this.row == row && this.col == col) {
            return false;
        }
        if(board.squareContains(row, col) != null) {
            if(color.equals(board.squareContains(row, col).getColor())) {
                return false;
            }
        }

        if(this.row == row || this.col == col) {
            int offset;

            if(this.row != row){
                if(this.row < row){
                    offset = 1;
                }else{
                    offset = -1;
                }

                for(int i = this.row + offset; i != row; i += offset){
                    //Go from currentRow to newRow, and check every space
                    if(board.squareContains(i, this.col) != null){
                        return false;
                    }
                }
            }

            //Now do the same for columns
            if(this.col != col){
                if(this.col < col){
                    offset = 1;
                }else{
                    offset = -1;
                }

                for(int i = this.col + offset; i != col; i += offset){
                    //Go from currentCol to newCol, and check every space
                    if(board.squareContains(this.row, i) != null){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
