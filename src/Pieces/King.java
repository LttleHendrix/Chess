package Pieces;

public class King extends Piece {

    public boolean hasCastled;

    public King(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 1000;
        hasCastled = false;
    }

    public double getPieceValue() {
        return 1000;
    }

    public String toString() {
        if(color.equals("white")) {
            return "   King  |";
        } else if(color.equals("black")) {
            return ConsoleColors.RED + "   King  " + ConsoleColors.RESET + "|";
        } else {
            return " illgl clr";
        }
    }

    public boolean canCastleKingside(Board board) {

        // If any of the spaces from rook to king are attacked, cannot castle
        for(int i=0; i<board.boardPieces.size(); i++) {
            if(!board.boardPieces.get(i).getColor().equals(color)) {
                if(board.boardPieces.get(i).isLegalMove(board, this.row, 4)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 5)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 6)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 7)) {
                    return false;
                }
            }
        }

        // If white, if there's a piece that hasn't moved in both squares
        // and the other squares in between are empty, return true
        if(color == "white") {
            if(board.squareContains(7, 4) != null) {
                if (timesMoved == 0) {
                    if(board.squareContains(7, 7) != null) {
                        if(board.squareContains(7, 7).timesMoved == 0) {
                            if(board.squareContains(7, 5) == null) {
                                if(board.squareContains(7, 6) == null) {
                                    return true;
                               } else {
                                    //System.out.println("7 6 is occupied");
                                }
                            } else {
                                //System.out.println("7 5 is occupied");
                            }
                        } else {
                            //System.out.println("Pieces.Rook has moved ");
                        }
                    } else {
                        //System.out.println("Pieces.Rook square is empty");
                    }
                } else {
                    //System.out.println("Pieces.King has moved "+this.timesMoved+" times");
                }
            } else {
                //System.out.println("Pieces.King's square is empty");
            }
        } else if(color == "black") {
            if(board.squareContains(0, 4) != null) {
                if (board.squareContains(0, 4).timesMoved == 0) {
                    if(board.squareContains(0, 7) != null) {
                        if(board.squareContains(0, 7).timesMoved == 0) {
                            if(board.squareContains(0, 5) == null) {
                                if(board.squareContains(0, 6) == null) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canCastleQueenside(Board board) {

        for(int i=0; i<board.boardPieces.size(); i++) {
            if(!board.boardPieces.get(i).getColor().equals(color)) {
                if(board.boardPieces.get(i).isLegalMove(board, this.row, 0)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 1)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 2)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 3)) {
                    return false;
                } else if(board.boardPieces.get(i).isLegalMove(board, this.row, 4)) {
                    return false;
                }
            }
        }


        if(color == "white") {
            if(board.squareContains(7, 4) != null) {
                if (board.squareContains(7, 4).timesMoved == 0) {
                    if(board.squareContains(7, 0) != null) {
                        if(board.squareContains(7, 0).timesMoved == 0) {
                            if(board.squareContains(7, 1) == null) {
                                if(board.squareContains(7, 2) == null) {
                                    if(board.squareContains(7, 3) == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if(color == "black") {
            if(board.squareContains(0, 4) != null) {
                if (board.squareContains(0, 4).timesMoved == 0) {
                    if(board.squareContains(0, 0) != null) {
                        if(board.squareContains(0, 0).timesMoved == 0) {
                            if(board.squareContains(0, 1) == null) {
                                if(board.squareContains(0, 2) == null) {
                                    if(board.squareContains(0, 3) == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isLegalMove(Board board, int row, int col) {

        //checking if king's castling
        if(color.equals("white")) {
            if(row == 7 && col == 6) {
                return canCastleKingside(board);
            }
        } else if(color.equals("black")) {
            if(row == 0 && col == 6) {
                return canCastleKingside(board);
            }
        }

        //checking if queens castling
        if(color.equals("white")) {
            if(row == 7 && col == 2) {
                return canCastleQueenside(board);
            }
        } else if(color.equals("black")) {
            if(row == 0 && col == 2) {
                return canCastleQueenside(board);
            }
        }

        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || col < 0) {
            return false;
        }
        if(this.row == row && this.col == col) {
            return false;
        }


        if(Math.abs(row - this.row) > 1 || Math.abs(col - this.col) > 1) {
            return false;
        }

        if(color.equals("black")) {
            if(board.squareContains(row, col) == null) {
                if(badCheckMove(board, row, col)) {
                    return false;
                }
                return true;
            } else if(board.squareContains(row, col).getColor().equals("white")) {
                if(badCheckMove(board, row, col)) {
                    return false;
                }
                return true;
            }
        }

        if(color.equals("white")) {
            if(board.squareContains(row, col) == null) {
                if(badCheckMove(board, row, col)) {
                    return false;
                }
                return true;
            } else if(board.squareContains(row, col).getColor().equals("black")) {
                if(badCheckMove(board, row, col)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean legalIgnoreCheck(Board board, int row, int col) {
        if(row > 7 || row < 0) {
            return false;
        }
        if(col > 7 || col < 0) {
            return false;
        }
        if(this.row == row && this.col == col) {
            return false;
        }
        if(Math.abs(row - this.row) > 1 || Math.abs(col - this.col) > 1) {
            return false;
        }

        if(color.equals("black")) {
            if(board.squareContains(row, col) == null) {
                return true;
            } else if(board.squareContains(row, col).getColor().equals("white")) {
                return true;
            }
        }

        if(color.equals("white")) {
            if(board.squareContains(row, col) == null) {
                return true;
            } else if(board.squareContains(row, col).getColor().equals("black")) {
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return "Pieces.King";
    }

    public void setHasCastled(boolean lol) {
        hasCastled = lol;
    }

    public boolean hasCastled() {
        return hasCastled;
    }


}
