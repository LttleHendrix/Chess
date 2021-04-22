import java.util.ArrayList;
import java.util.Random;

public class AIPlayer {

    Board board;
    String color;
    Random random = new Random();


    public AIPlayer(Board board, String color) {
        this.board = board;
        this.color = color;
    }

    public Move getBestMove(int depth, int depthLimit, Board board, AIPlayer other, Move move) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        Move bestCheckedMove;

        if(depth >= depthLimit) {
            move.setMaxMinimaxValue(heuristicScore(board));
        } else {
            for (int i = 0; i < possibleMoves.size(); i++) {
                Move temp = possibleMoves.get(i);
                temp.doMove(board);
                other.getBestMove(depth + 1, depth, board, this, move);
                temp.undoMove(board);
            }
            if(possibleMoves == null || possibleMoves.isEmpty()) {
                move.setMaxMinimaxValue(Integer.MIN_VALUE);
            } else {
                bestCheckedMove = possibleMoves.get(0);
                for(int i=0; i<possibleMoves.size(); i++) {
                    if(possibleMoves.get(i).maxMinimaxValue > bestCheckedMove.maxMinimaxValue) {
                        bestCheckedMove = possibleMoves.get(i);
                    }
                }
                move.setMaxMinimaxValue(bestCheckedMove.maxMinimaxValue);
            }
        }
        if(possibleMoves.size() > 0) {
            Move best = possibleMoves.get(0);
            for(Move possibleMove : possibleMoves) {
                if(possibleMove.maxMinimaxValue > best.maxMinimaxValue) {
                    best = possibleMove;
                }
            }
            move.setMove(best);
        }
        return move;
    }

    /**private Move getBestMove(int depthLimit, int depth, Board board, AIPlayer other) {



    }**/

    public int heuristicScore(Board board) {
        int heuristic = 0;

        for(int i=0; i<board.boardPieces.size(); i++) {
            if(board.boardPieces.get(i).getColor().equals(color)) {
                heuristic = heuristic + board.boardPieces.get(i).pieceValue;
            } else {
                heuristic = heuristic - board.boardPieces.get(i).pieceValue;
            }
        }
        return heuristic;
    }

    public Move randomMove(Board board) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);

        if(possibleMoves.size() > 0) {
            for(int i=0; i< possibleMoves.size(); i++) {
                System.out.println("Legal move: "+possibleMoves.get(i));
            }
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        } else {
            System.out.println("No legal moves found");
            return null;
        }
    }

    public ArrayList<Move> getPossibleMoves(Board board) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        if(board.getKing(color).canCastleKingside(board)) {
            possibleMoves.add(new KingCastleMove(board.getKing(color)));
        }
        if(board.getKing(color).canCastleQueenside(board)) {
            possibleMoves.add(new QueenCastleMove(board.getKing(color)));
        }

        for(int a=0; a<board.boardPieces.size(); a++) {
            if(board.boardPieces.get(a).getColor().equals(color)) {
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        if(board.boardPieces.get(a).isLegalMove(board, i, j)) {
                            possibleMoves.add(new Move(board.boardPieces.get(a), i, j));
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }









}


