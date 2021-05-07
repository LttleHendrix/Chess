public class Pawn extends Piece {


    public Pawn(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 10;
    }

    public int getPieceValue() {

        // makes pawns more valuable the further up you move them
        int forwardsBonus = 0;
        if(color.equals("white")) {
            forwardsBonus = 6-row;
        } else if(color.equals("black")) {
            forwardsBonus = row - 1;
        }








        return pieceValue + forwardsBonus;
    }

    public String toString() {
        if(color.equals("white")) {
            return "   pawn  |";
        } else if(color.equals("black")) {
            return ConsoleColors.RED + "   pawn  " + ConsoleColors.RESET + "|";
        } else {
            return " illgl clr";
        }
    }

    @Override
    public boolean isLegalMove(Board board, int row, int col) {

        // If rows or columns are outside of board return false
        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || row < 0) {
            return false;
        }

        // Checks for black pieces, returning false if you are in check after the move
        if(color.equals("black")) {
            // if in same column moving one square towards an empty space return true
            if(col == this.col && row == (this.row+ 1) ) {
                if(board.squareContains(row, col) == null) {
                    if(badCheckMove(board, row, col)) {
                        return false;
                    }
                    return true;
                }
            }
            // if moving diagonally into a white occupied square return true
            if(Math.abs(col - this.col) == 1 && row == (this.row + 1)) {
                if(board.squareContains(row, col) != null) {
                    if(board.squareContains(row, col).getColor().equals("white")) {
                        if(badCheckMove(board, row, col)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
            // if moving exactly two squares from its starting spot, return true
            if(this.row == 1 && row == 3 && this.col == col) {
                if(board.squareContains(this.row + 1, col) == null) {
                    if(board.squareContains(row, col) == null) {
                        if(badCheckMove(board, row, col)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        // Same checks for white pieces, rows reversed
        if(color.equals("white")) {
            if(col == this.col && row == (this.row-1)) {
                if(board.squareContains(row, col) == null) {
                    if(badCheckMove(board, row, col)) {
                        return false;
                    }
                    return true;
                }
            }
            if(Math.abs(col - this.col) == 1 && row == (this.row - 1)) {
                if(board.squareContains(row, col) != null) {
                    if(board.squareContains(row, col).getColor().equals("black")) {
                        if(badCheckMove(board, row, col)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
            if(this.row == 6 && row == 4 && this.col == col) {
                if(board.squareContains(this.row - 1, col) == null) {
                    if(board.squareContains(row, col) == null) {
                        if(badCheckMove(board, row, col)) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getType() {
        return "Pawn";
    }

    public boolean legalIgnoreCheck(Board board, int row, int col) {

        // If rows or columns are outside of board return false
        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || row < 0) {
            return false;
        }

        // Checks for black pieces, returning false if you are in check after the move
        if(color.equals("black")) {
            // if in same column moving one square towards an empty space return true
            if(col == this.col && row == (this.row+ 1) ) {
                if(board.squareContains(row, col) == null) {
                    return true;
                }
            }
            // if moving diagonally into a white occupied square return true
            if(Math.abs(col - this.col) == 1 && row == (this.row + 1)) {
                if(board.squareContains(row, col) != null) {
                    if(board.squareContains(row, col).getColor().equals("white")) {
                        return true;
                    }
                }
            }
            // if moving exactly two squares from its starting spot, return true
            if(this.row == 1 && row == 3 && this.col == col) {
                if(board.squareContains(this.row + 1, col) == null) {
                    if(board.squareContains(row, col) == null) {
                        return true;
                    }
                }
            }
            return false;
        }

        // Same checks for white pieces, rows reversed
        if(color.equals("white")) {
            if(col == this.col && row == (this.row-1)) {
                if(board.squareContains(row, col) == null) {
                    return true;
                }
            }
            if(Math.abs(col - this.col) == 1 && row == (this.row - 1)) {
                if(board.squareContains(row, col) != null) {
                    if(board.squareContains(row, col).getColor().equals("black")) {
                        return true;
                    }
                }
            }
            if(this.row == 6 && row == 4 && this.col == col) {
                if(board.squareContains(this.row - 1, col) == null) {
                    if(board.squareContains(row, col) == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
