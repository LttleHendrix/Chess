import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Board board = new Board();
        AIPlayer ai = new AIPlayer(board, "black");
        Scanner scanner = new Scanner(System.in);

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
            } else {
                System.out.println("Black's Turn----");
            }

            if(turn % 2 == 0) {
                if(board.movePiece(oldRow, oldCol, newRow, newCol, "white")) {
                    turn = turn + 1;
                } else {
                    System.out.println("Invalid move, try again.");
                }
            } else {
                ai.randomMove(board).doMove(board);
                turn = turn + 1;
            }
            board.printBoard();
        }






    }


}