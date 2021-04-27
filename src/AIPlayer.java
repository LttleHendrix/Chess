import java.util.ArrayList;
import java.util.Random;

public class AIPlayer {

    Board board;
    String color;
    Random random = new Random();
    private int depthLimit;


    public AIPlayer(Board board, String color) {
        this.board = board;
        this.color = color;
        depthLimit = 2;
    }

    public Move getBestMove(int depth, int depthLimit, Board board, AIPlayer other, Move move) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        Move bestCheckedMove;

        if(depth >= depthLimit) {
            move.setMinimaxValue(heuristicScore(board));
        } else {
            for (int i = 0; i < possibleMoves.size(); i++) {
                Move temp = possibleMoves.get(i);
                temp.doMove();
                other.getBestMove(depth + 1, depth, board, this, temp);
                temp.undoMove();
            }
            if(possibleMoves == null || possibleMoves.isEmpty()) {
                move.setMinimaxValue(Integer.MIN_VALUE);
            } else {
                bestCheckedMove = possibleMoves.get(0);
                for(int i=0; i<possibleMoves.size(); i++) {
                    if(possibleMoves.get(i).minimaxValue > bestCheckedMove.minimaxValue) {
                        bestCheckedMove = possibleMoves.get(i);
                    }
                }
                move.setMinimaxValue(bestCheckedMove.minimaxValue);
            }
        }
        if(depth == 0) {
            Move best = possibleMoves.get(0);
            for(Move possibleMove : possibleMoves) {
                if(possibleMove.minimaxValue >= best.minimaxValue) {
                    best = possibleMove;
                }
            }
            move.setMove(best);
        }
        return move;
    }

    public Move getMove(AIPlayer other) {
        Move nextMove = new Move();

        //saveState();
        findMaxMove(0, nextMove, other);
        //restoreState();
        //incrementNumActionsExecuted();
        nextMove.board = board;
        return nextMove;

    }

    protected void findMaxMove(int currentDepth, Move incomingMove, AIPlayer other) {
        System.out.println("Finding max and Depth is "+currentDepth);
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        if(currentDepth >= depthLimit) {
            System.out.println("Heuristic score of this next board is: "+heuristicScore(board));
            board.printBoard();
            incomingMove.setMinimaxValue(heuristicScore(board));
        } else {
            for(int i=0; i<possibleMoves.size(); i++) {
                Move temp = possibleMoves.get(i);
                if(temp.doMove()) {
                    findMinMove(currentDepth, possibleMoves.get(i), other);
                    System.out.println("Trying to undo "+temp);
                    temp.undoMove();
                }
            }
            passUpMaxMinimaxValue(incomingMove, possibleMoves);
        }
        if(currentDepth == 0) {
            Move best = possibleMoves.get(0);
            for(int i=0; i<possibleMoves.size(); i++) {
                if(possibleMoves.get(i).minimaxValue > best.minimaxValue) {
                    best = possibleMoves.get(i);
                }
            }
            incomingMove.setMove(best);
        }
    }

    protected void findMinMove(int currentDepth, Move incomingMove, AIPlayer other) {
        System.out.println("Finding min and Depth is "+currentDepth);
        ArrayList<Move> possibleMoves = other.getPossibleMoves(board);
        Move move;
        for(Move possibleMove : possibleMoves) {
            move = possibleMove;
            if(move.doMove()) {
                findMaxMove(currentDepth + 1, possibleMove, other);
                move.undoMove();
            }
        }
        passUpMinMinimaxValue(incomingMove, possibleMoves);
    }



    protected void passUpMaxMinimaxValue(Move incomingMove, ArrayList<Move> possibleMoves) {
        System.out.println("Trying to pass up maxminimax value");
        if(possibleMoves == null || possibleMoves.isEmpty()) {
            incomingMove.setMinimaxValue(Integer.MIN_VALUE);
        } else {
            Move bestMove = possibleMoves.get(0);
            for(int i=1; i<possibleMoves.size(); i++) {
                System.out.println("Possible minimaxValue " + possibleMoves.get(i).minimaxValue);
                if(possibleMoves.get(i).minimaxValue > bestMove.minimaxValue) {
                    bestMove = possibleMoves.get(i);
                }
            }
            System.out.println("Max minimaxvalue "+bestMove.minimaxValue);
            incomingMove.setMinimaxValue(bestMove.minimaxValue);
        }
    }

    private void passUpMinMinimaxValue(Move incomingMove, ArrayList<Move> possibleMoves) {
        System.out.println("Trying to pass up minminimax value");
        if(possibleMoves == null || possibleMoves.isEmpty()) {
            incomingMove.setMinimaxValue(Integer.MAX_VALUE);
        } else {
            Move bestMove = possibleMoves.get(0);
            for(int i=1; i<possibleMoves.size(); i++) {
                if(possibleMoves.get(i).minimaxValue < bestMove.minimaxValue) {
                    bestMove = possibleMoves.get(i);
                }
            }
            System.out.println("Min minimaxvalue "+bestMove.minimaxValue);
            incomingMove.setMinimaxValue(bestMove.minimaxValue);
        }
    }


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
        System.out.println("Finding all possible moves for "+color);
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        if(board.getKing(color) != null && board.getKing(color).canCastleKingside(board)) {
            possibleMoves.add(new KingCastleMove(board.getKing(color)));
        }
        if(board.getKing(color) != null && board.getKing(color).canCastleQueenside(board)) {
            possibleMoves.add(new QueenCastleMove(board.getKing(color)));
        }

        for(int a=0; a<board.boardPieces.size(); a++) {
            if(board.boardPieces.get(a).getColor().equals(color)) {
                for(int i=0; i<8; i++) {
                    for(int j=0; j<8; j++) {
                        if(board.boardPieces.get(a).isLegalMove(board, i, j)) {
                            possibleMoves.add(new Move(board.boardPieces.get(a), i, j, board));
                        }
                    }
                }
            }
        }
        System.out.println("All moves found for "+color );
        return possibleMoves;
    }









}


