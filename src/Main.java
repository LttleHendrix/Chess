import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Robot;

public class Main {

    public static void main(String[] args) throws AWTException {

        Scanner scanner = new Scanner(System.in);
        Player whitePlayer;
        Player blackPlayer;
        AIwithAlphaBetaPruning otherComp;
        Board board = new Board();
        Robot robot = new Robot();
        String color = "lol";
        ArrayList<Move> completedMoves = new ArrayList<Move>();
        Move nextMove;


        while(!color.equals("white") && !color.equals("black")) {
            System.out.println("Is the computer going to be white or black");
            color = scanner.nextLine();
        }

        if(color.equals("white")) {
            blackPlayer = new HumanPlayer("black");
            whitePlayer = new AIwithAlphaBetaPruning(board, "white");
            otherComp = new AIwithAlphaBetaPruning(board, "black");
        } else if(color.equals("black")) {
            whitePlayer = new HumanPlayer("white");
            blackPlayer = new AIwithAlphaBetaPruning(board, "black");
            otherComp = new AIwithAlphaBetaPruning(board, "white");
        } else {
            System.out.println("Big bad problem");
            return;
        }


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
            } else {
                  System.out.println("Black's Turn----");
            }

            if(turn % 2 == 0) {
                nextMove = whitePlayer.getMove(board, otherComp);
            } else {
                nextMove = blackPlayer.getMove(board, otherComp);
            }
            nextMove.doMove(board);
            turn = turn + 1;
            board.printBoard();
        }






    }


}
