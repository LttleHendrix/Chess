package Pieces;

import java.util.ArrayList;

public class Board {

    ArrayList<Piece> boardPieces = new ArrayList<Piece>();
    ArrayList<Piece> removedPieces = new ArrayList<Piece>();
    PieceComparator sorter = new PieceComparator();

    public Board() {

    }

    // Builds a board that is a brand new chess game
    public static Board buildFreshBoard() {
        Board board = new Board();
        board.addPiece(new Pawn(1, 0, "black"));
        board.addPiece(new Pawn(1, 1, "black"));
        board.addPiece(new Pawn(1, 2, "black"));
        board.addPiece(new Pawn(1, 3, "black"));
        board.addPiece(new Pawn(1, 4, "black"));
        board.addPiece(new Pawn(1, 5, "black"));
        board.addPiece(new Pawn(1, 6, "black"));
        board.addPiece(new Pawn(1, 7, "black"));
        board.addPiece(new King(0, 4, "black"));
        board.addPiece(new Rook(0, 0, "black"));
        board.addPiece(new Rook(0, 7, "black"));
        board.addPiece(new Queen(0, 3, "black"));
        board.addPiece(new Knight(0, 6, "black"));
        board.addPiece(new Knight(0, 1, "black"));
        board.addPiece(new Bishop(0, 2, "black"));
        board.addPiece(new Bishop(0, 5, "black"));
        board.addPiece(new Pawn(6, 0, "white"));
        board.addPiece(new Pawn(6, 1, "white"));
        board.addPiece(new Pawn(6, 2, "white"));
        board.addPiece(new Pawn(6, 3, "white"));
        board.addPiece(new Pawn(6, 4, "white"));
        board.addPiece(new Pawn(6, 5, "white"));
        board.addPiece(new Pawn(6, 6, "white"));
        board.addPiece(new Pawn(6, 7, "white"));
        board.addPiece(new King(7, 4, "white"));
        board.addPiece(new Rook(7, 7, "white"));
        board.addPiece(new Rook(7, 0, "white"));
        board.addPiece(new Queen(7, 3, "white"));
        board.addPiece(new Knight(7, 6, "white"));
        board.addPiece(new Knight(7, 1, "white"));
        board.addPiece(new Bishop(7, 2, "white"));
        board.addPiece(new Bishop(7, 5, "white"));
        return board;
    }

    public Board(ArrayList<Piece> boardPieces, ArrayList<Piece> removedPieces) {
        this.boardPieces = boardPieces;
        this.removedPieces = removedPieces;
    }

    public boolean addPiece(Piece newPiece) {
        if(squareContains(newPiece.getRow(), newPiece.getCol()) != null) {
            boardPieces.sort(new PieceComparator());
            return false;
        } else {
            boardPieces.add(newPiece);
            boardPieces.sort(sorter);
            return true;
        }
    }


    public void printBoard() {
        String oneRow = "|";
        System.out.println("---------------------------------" +
                "------------------------------------------------");
        System.out.println("|         |         |         |         |         |         |         |         |");
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(squareContains(i, j) == null) {
                    oneRow = oneRow + "         |";
                } else {
                    oneRow = oneRow + squareContains(i, j).toString();
                }
            }
            oneRow = oneRow + " " + i + " row";
            System.out.println(oneRow);
            System.out.println("|         |         |         |         |         |         |         |         |");
            oneRow = "|";
            System.out.println("---------------------------------" +
                    "------------------------------------------------");
            if(i < 7) {
                System.out.println("|         |         |         |         |         |         |         |         |");
            }
        }
        System.out.println("|Column 0 |Column 1 |Column 2 |Column 3 |Column 4 |Column 5 |Column 6 |Column 7 |");
    }

    public void printPieces() {
        for(Piece piece: boardPieces) {
            System.out.println(""+piece.getColor()+" "+piece.toString()+" on "+piece.getRow()+piece.getCol());
        }
    }

    public Piece squareContains(int row, int col) {
        for(int i=0; i< boardPieces.size(); i++) {
            if (((Piece)boardPieces.get(i)).isInSquare(row, col)) {
                return (Piece)boardPieces.get(i);
            }
        }
        return null;
    }

    public boolean movePiece(int oldRow, int oldCol, int newRow, int newCol, String color) {

        if(squareContains(oldRow, oldCol) != null) {
            if(squareContains(oldRow, oldCol).isLegalMove(this, newRow, newCol)) {
                if(squareContains(oldRow, oldCol).getColor().equals(color)) {
                    clearSquare(newRow, newCol);
                    squareContains(oldRow, oldCol).movePiece(newRow, newCol);
                    boardPieces.sort(sorter);
                    return true;
                }
            }
        }
        boardPieces.sort(sorter);
        return false;
    }

    public boolean isSameBoardState(Board other) {
        if(this.boardPieces.size() != other.boardPieces.size()) {
            return false;
        }
        for(int i=0; i<boardPieces.size(); i++) {
            if(!this.boardPieces.get(i).isSamePiece(other.boardPieces.get(i))) {
                return false;
            }
        }
        return true;
    }


    public void clearSquare(int row, int col) {
        if(squareContains(row, col) != null) {
            removedPieces.add(squareContains(row, col));
            boardPieces.remove(squareContains(row, col));
        }
        boardPieces.sort(sorter);
    }

    public boolean isInCheck(String color) {

        for(int i=0; i<boardPieces.size(); i++) {
            if(boardPieces.get(i).getType() == "Pieces.King") {
                if(boardPieces.get(i).getColor().equals(color)) {
                    Piece king = boardPieces.get(i);
                    for(int j=0; j<boardPieces.size(); j++) {
                        if(boardPieces.get(j).legalIgnoreCheck(this, king.getRow(), king.getCol())) {
                            boardPieces.sort(sorter);
                            return true;
                        }
                    }
                }
            }
        }
        boardPieces.sort(sorter);
        return false;
    }

    public King getKing(String color) {
        for(int i=0; i<boardPieces.size(); i++) {
            if(boardPieces.get(i).getType().equals("Pieces.King")) {
                if(boardPieces.get(i).getColor().equals(color)) {
                    return (King) boardPieces.get(i);
                }
            }
        }
        return null;
    }

    public King getOtherKing(String color) {
        for(int i=0; i<boardPieces.size(); i++) {
            if(boardPieces.get(i).getType().equals("Pieces.King")) {
                if(!boardPieces.get(i).getColor().equals(color)) {
                    return (King) boardPieces.get(i);
                }
            }
        }
        return null;
    }


    public boolean gameIsOver() {
        return false;
    }

    public Board clone() throws CloneNotSupportedException{
        Board clone = (Board) super.clone();
        return clone;
    }


}
