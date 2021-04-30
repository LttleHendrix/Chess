public class King extends Piece {

    public King(int row, int col, String color) {
        super(row, col, color);
        pieceValue = 1000;
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
        if(color == "white") {
            if(board.squareContains(7, 4) != null) {
                if (board.squareContains(7, 4).timesMoved == 0) {
                    if(board.squareContains(7, 7) != null) {
                        if(board.squareContains(7, 7).timesMoved == 0) {
                            if(board.squareContains(7, 5) == null) {
                                if(board.squareContains(7, 6) == null) {
                                    return true; }}}}}}
                               /** } else {
                                    System.out.println("7 6 is occupied");
                                }
                            } else {
                                System.out.println("7 5 is occupied");
                            }
                        } else {
                            System.out.println("Rook has moved ");
                        }
                    } else {
                        System.out.println("Rook square is empty");
                    }
                } else {
                    System.out.println("King has moved "+this.timesMoved+" times");
                }
            } else {
                System.out.println("King's square is empty");
            } **/
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
        return "King";
    }


}
