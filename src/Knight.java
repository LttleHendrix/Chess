public class Knight extends Piece {

    public Knight(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 30;
    }

    public String toString() {
        if(color.equals("white")) {
            return "  Knight |";
        } else if(color.equals("black")) {
            return ConsoleColors.RED + "  Knight " + ConsoleColors.RESET + "|";
        } else {
            return " illgl clr";
        }
    }

    public boolean isLegalMove(Board board, int row, int col) {
        if(row > 8 || row < 0) {
            return false;
        }
        if(col > 8 || row < 0) {
            return false;
        }

        if((Math.abs(this.row - row) == 2 && Math.abs(this.col - col) == 1 )||
                (Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 2)) {
            if(board.squareContains(row, col) == null) {
                if(badCheckMove(board, row, col)) {
                    return false;
                }
                return true;
            } else if(color.equals("white")) {
                if(board.squareContains(row, col).getColor().equals("black")) {
                    if(badCheckMove(board, row, col)) {
                        return false;
                    }
                    return true;
                }
            } else if(color.equals("black")) {
                if(board.squareContains(row, col).getColor().equals("white")) {
                    if(badCheckMove(board, row, col)) {
                        return false;
                    }
                    return true;
                }
            }
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

        if((Math.abs(this.row - row) == 2 && Math.abs(this.col - col) == 1 )||
                (Math.abs(this.row - row) == 1 && Math.abs(this.col - col) == 2)) {
            if(board.squareContains(row, col) == null) {
                return true;
            } else if(color.equals("white")) {
                if(board.squareContains(row, col).getColor().equals("black")) {
                    return true;
                }
            } else if(color.equals("black")) {
                if(board.squareContains(row, col).getColor().equals("white")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getType() {
        return "Knight";
    }

}
