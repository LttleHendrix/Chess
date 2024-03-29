package Pieces;

import java.util.Scanner;

public class HumanPlayer extends Player{

    Scanner scanner = new Scanner(System.in);
    String color;
    Board board;

    public HumanPlayer(String color) {
        this.color = color;
    }

    public Move getMove(Board board, AIwithAlphaBetaPruning other) {

        while(true) {
            System.out.println("Pieces.Piece to move's Row:");
            int oldRow = scanner.nextInt();
            System.out.println("Pieces.Piece to move's Column:");
            int oldCol = scanner.nextInt();
            System.out.println("" + board.squareContains(oldRow, oldCol));
            System.out.println("Pieces.Piece's new Row:");
            int newRow = scanner.nextInt();
            System.out.println("Pieces.Piece's new Column:");
            int newCol = scanner.nextInt();
            System.out.println("" + board.squareContains(newRow, newCol));

            if(board.squareContains(oldRow, oldCol) != null) {
                if (board.squareContains(oldRow, oldCol).isLegalMove(board, newRow, newCol)) {
                    System.out.println("Is legal move");
                    // Pieces.King castle
                    if (board.getKing(color).canCastleKingside(board) && oldRow == board.getKing(color).getRow() && oldCol == 4 && newRow == board.getKing(color).getRow() && newCol == 6) {
                        return new KingCastleMove(board.getKing(color), board);
                        //queen castle
                    } else if (board.getKing(color).canCastleQueenside(board) && oldRow == board.getKing(color).getRow() && oldCol == 4 && newRow == board.getKing(color).getRow() && newCol == 2) {
                        return new QueenCastleMove(board.getKing(color), board);
                    } else if(board.squareContains(oldRow, oldCol).getType().equals("Pieces.Pawn")) {
                        if(color.equals("white")) {
                            if(oldRow == 1) {
                                System.out.println("pawn promotion for white");
                                return new PawnPromotionMove((Pawn)board.squareContains(oldRow, oldCol), newRow, newCol, board, "Pieces.Queen");
                            }
                        } else if(color.equals("black")) {
                            if(oldRow == 6) {
                                System.out.println("pawn promotion for black");
                                return new PawnPromotionMove((Pawn)board.squareContains(oldRow, oldCol), newRow, newCol, board, "Pieces.Queen");
                            }
                        }
                    }
                    System.out.println("Returning a normal move");
                    return new Move(board.squareContains(oldRow, oldCol), newRow, newCol, board);
                }
            }
        }
    }
}
