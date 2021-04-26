import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Robot;

public class Main {

    public static void main(String[] args) throws AWTException {

        Board board = new Board();
        AIPlayer ai = new AIPlayer(board, "black");
        Scanner scanner = new Scanner(System.in);
        Robot robot = new Robot();
        ArrayList<Move> completedMoves = new ArrayList<Move>();
        Move nextMove;

        System.out.println(new King(0, 0, "white").getClass());
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
        board.printBoard();

        int turn = 0;
        int oldRow = -1;
        int oldCol = -1;
        int newRow = -1;
        int newCol = -1;

        while(!board.gameIsOver()) {
            if(turn % 2 == 0) {
                System.out.println("White's turn ");
                System.out.println("Piece to move's Row:");
                oldRow = scanner.nextInt();
                System.out.println("Piece to move's Column:");
                oldCol = scanner.nextInt();
                System.out.println(""+board.squareContains(oldRow, oldCol));
                System.out.println("Piece's new Row:");
                newRow = scanner.nextInt();
                System.out.println("Piece's new Column:");
                newCol = scanner.nextInt();
                System.out.println(""+board.squareContains(newRow, newCol));
                System.out.println("Black's turn");
            } else {
                  System.out.println("Black's Turn----");
                  robot.delay(2500);
            }

            if(turn % 2 == 0) {
                if(board.squareContains(oldRow, oldCol) != null) {
                    if (board.squareContains(oldRow, oldCol).isLegalMove(board, newRow, newCol)) {
                        if (board.getKing("white").canCastleKingside(board) && oldRow == 7 && oldCol == 4 && newRow == 7 && newCol == 6) {
                            nextMove = new KingCastleMove(board.getKing("white"));
                            nextMove.doMove();
                            completedMoves.add(nextMove);
                            turn = turn + 1;
                        } else if (board.getKing("white").canCastleQueenside(board) && oldRow == 7 && oldCol == 4 && newRow == 7 && newCol == 2) {
                            nextMove = new QueenCastleMove(board.getKing("white"));
                            nextMove.doMove();
                            completedMoves.add(nextMove);
                            turn = turn + 1;
                        } else {
                            nextMove = new Move(board.squareContains(oldRow, oldCol), newRow, newCol, board);
                            nextMove.doMove();
                            completedMoves.add(nextMove);
                            turn = turn + 1;
                        }
                    }
                }
                else {
                    System.out.println("Invalid move, try again.");
                }

            } else {
                /**if(board.squareContains(oldRow, oldCol).isLegalMove(board, newRow, newCol)) {
                    nextMove = new Move(board.squareContains(oldRow, oldCol), newRow, newCol);
                    nextMove.doMove(board);
                    completedMoves.add(nextMove);
                    turn = turn + 1;
                } **/
                //nextMove = ai.randomMove(board);
                //nextMove = ai.getBestMove(0, 2, board, new AIPlayer(board, "white"), new Move());
                nextMove = ai.getMove(new AIPlayer(board, "white"));
                nextMove.doMove();
                completedMoves.add(nextMove);

                turn = turn + 1;
            }
            board.printBoard();
            robot.delay(70);
        }






    }


}
