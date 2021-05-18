import java.util.ArrayList;
import java.util.Random;

public class AIwithAlphaBetaPruning extends Player{

    Board board;
    String color;
    Random random = new Random();
    private int depthLimit;
    AIwithAlphaBetaPruning other;

    public AIwithAlphaBetaPruning(Board board, String color) {
        this.board = board;
        this.color = color;
        depthLimit = 2;
        this.other = other;
    }

    public Move getMove(Board board, AIwithAlphaBetaPruning other) {
        Move nextMove = getPossibleMoves(board).get(0);

        calculateDepthLimit(board);

        //saveState();
        findMaxMove(0, nextMove, other, Integer.MIN_VALUE);
        //restoreState();
        //incrementNumActionsExecuted();
        nextMove.board = board;
        return nextMove;

    }

    private void calculateDepthLimit(Board board) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        if(possibleMoves.size() < 10) {
            System.out.println("Depth limit 4");
            depthLimit = 4;
        } else if(possibleMoves.size() < 20) {
            depthLimit = 3;
            System.out.println("Depth limit 3");
        } else {
            depthLimit = 2;
            System.out.println("Depth limit 2");
        }
    }

    protected void findMaxMove(int currentDepth, Move incomingMove, AIwithAlphaBetaPruning other, int highestAchievableMinimaxValue) {

        // Without sorting moves
        //ArrayList<Move> possibleMoves = getPossibleMoves(board);

        // With sorting moves
        ArrayList<Move> possibleMoves = getSortedPossibleMoves(board);

        if(currentDepth >= depthLimit) {
            incomingMove.setMinimaxValue(heuristicScore(board));
        } else {
            for(int i=0; i<possibleMoves.size(); i++) {
                if(currentDepth == 0) {
                    System.out.println("Solving move #"+(i+1)+"/"+possibleMoves.size()+" "+possibleMoves.get(i));
                }// else if(currentDepth == 1) {
               //     System.out.println("One layer deep solving "+i+" out of "+possibleMoves.size());
              //  } else if(currentDepth == 2) {
             //       System.out.println("Two layers deep solving "+i+" out of "+possibleMoves.size());
              //  }
                Move temp = possibleMoves.get(i);
                if(temp.doMove(board)) {
                    findMinMove(currentDepth, possibleMoves.get(i), other, highestAchievableMinimaxValue);
                    if(highestAchievableMinimaxValue < possibleMoves.get(i).minimaxValue) {
                        highestAchievableMinimaxValue = possibleMoves.get(i).minimaxValue;
                    }
                    if(currentDepth == 0) {
                        System.out.println("Best board state for this move is " + possibleMoves.get(i).minimaxValue);
                    }
                    temp.undoMove(board);
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

    protected void findMinMove(int currentDepth, Move incomingMove, AIwithAlphaBetaPruning other, int highestAchievableMinimaxValue) {

        // Without sorting moves
        //ArrayList<Move> possibleMoves = other.getPossibleMoves(board);

        // With sorting moves
        ArrayList<Move> possibleMoves = other.getSortedPossibleMoves(board);

        Move move;
        for(Move possibleMove : possibleMoves) {
            move = possibleMove;
            if(move.doMove(board)) {
                findMaxMove(currentDepth + 1, possibleMove, other, highestAchievableMinimaxValue);
                move.undoMove(board);
                if(highestAchievableMinimaxValue > possibleMove.minimaxValue) {
                    passUpMinMinimaxValue(incomingMove, possibleMoves);
                    return;
                }
            }
        }
        passUpMinMinimaxValue(incomingMove, possibleMoves);
    }



    protected void passUpMaxMinimaxValue(Move incomingMove, ArrayList<Move> possibleMoves) {
        if(possibleMoves == null || possibleMoves.isEmpty()) {
            incomingMove.setMinimaxValue(Integer.MIN_VALUE);
        } else {
            Move bestMove = possibleMoves.get(0);
            for(int i=1; i<possibleMoves.size(); i++) {
                if(possibleMoves.get(i).minimaxValue > bestMove.minimaxValue) {
                    bestMove = possibleMoves.get(i);
                }
            }
            incomingMove.setMinimaxValue(bestMove.minimaxValue);
        }
    }

    private void passUpMinMinimaxValue(Move incomingMove, ArrayList<Move> possibleMoves) {
        if(possibleMoves == null || possibleMoves.isEmpty()) {
            incomingMove.setMinimaxValue(Integer.MAX_VALUE);
        } else {
            Move bestMove = possibleMoves.get(0);
            for(int i=1; i<possibleMoves.size(); i++) {
                if(possibleMoves.get(i).minimaxValue < bestMove.minimaxValue) {
                    bestMove = possibleMoves.get(i);
                }
            }
            incomingMove.setMinimaxValue(bestMove.minimaxValue);
        }
    }

    // Please rewrite this so that you don't have to write double the code
    public int heuristicScore(Board board, String color) {
        //int score = heuristicScore();
        return 0;
    }


    private int heuristicScore(Board board) {
        int heuristic = 0;

        // For value of pieces 10 pawn 30 knight 32 bishop 50 rook 90 queen
        for(int i=0; i<board.boardPieces.size(); i++) {
            if(board.boardPieces.get(i).getColor().equals(color)) {
                heuristic = heuristic + board.boardPieces.get(i).getPieceValue();
            } else {
                heuristic = heuristic - board.boardPieces.get(i).getPieceValue();
            }
        }

        // two point boost/subtract if knight is in center
        for(int i=2; i<=5; i++) {
            for(int j=2; j<=5; j++) {
                Piece piece = board.squareContains(i, j);
                if(piece != null && piece.getType().equals("Knight")) {
                    if(piece.getColor().equals(color)) {
                        heuristic = heuristic + 2;
                    } else {
                        heuristic = heuristic - 2;
                    }
                }
            }
        }

        King king = board.getKing(color);
        if(king == null) {
            System.out.println("King is somehow null on this board ");
            board.printBoard();
        }
        if(king.hasCastled()) {
            heuristic = heuristic + 15;
        }
        king = board.getOtherKing(color);
        if(king.hasCastled()) {
            heuristic = heuristic - 15;
        }

        /** Rewrite, make it so that bishops should move out lol
        if(color.equals("white")) {
            for(int i=0; i<=5; i++) {
                for(int j=0; j<=7; j++) {
                    Piece piece = board.squareContains(i, j);
                    if(piece != null && piece.getType().equals("Bishop")) {
                        if(piece.getColor().equals(color)) {
                            heuristic = heuristic + 2;
                        } else {
                            heuristic = heuristic - 2;
                        }
                    }
                }
            }
        } else if(color.equals("black")) {
            for(int i=2; i<=7; i++) {
                for(int j=0; j<=7; j++) {
                    Piece piece = board.squareContains(i, j);
                    if(piece != null && piece.getType().equals("Bishop")) {
                        if(piece.getColor().equals(color)) {
                            heuristic = heuristic + 2;
                        } else {
                            heuristic = heuristic - 2;
                        }
                    }
                }
            }
        }
         **/


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

    public ArrayList<Move> getSortedPossibleMoves(Board board) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board);
        Move currentMove;

        for(int i=0; i<possibleMoves.size(); i++) {
            currentMove = possibleMoves.get(i);
            currentMove.doMove(board);
            currentMove.setMinimaxValue(heuristicScore(board));
            currentMove.undoMove(board);
        }

        possibleMoves.sort(new MoveComparator());

        return possibleMoves;
    }

    //public ArrayList<Move> getOpeningMoves(Board board) {
    //    ArrayList<Move> possibleMoves = getPossibleMoves(board);
    //}

    public ArrayList<Move> getPossibleMoves(Board board) {
        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        // Adds King and queen castle if legal
        if(board.getKing(color) != null && board.getKing(color).canCastleKingside(board)) {
            //System.out.println("adding a king castle");
            possibleMoves.add(new KingCastleMove(board.getKing(color), board));
        }
        if(board.getKing(color) != null && board.getKing(color).canCastleQueenside(board)) {
            //System.out.println("adding a queen castle");
            possibleMoves.add(new QueenCastleMove(board.getKing(color), board));
        }

        // EVENTUALLY TRY OPTIMIZING SO IT ONLY EVALUATES POSSIBLE SQUARES PER PIECE, NOT ALL 64
        //adding other legal moves
        for(int a=0; a<board.boardPieces.size(); a++) {
            if(board.boardPieces.get(a) != null) {
                if (board.boardPieces.get(a).getColor().equals(color)) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (board.boardPieces.get(a).isLegalMove(board, i, j)) {
                                //System.out.println("Move is " + board.boardPieces.get(a).row + board.boardPieces.get(a).col + "row " + i + " column " + j);
                                //board.printBoard();

                                boolean normalMove = true;
                                // adding move if it is a pawn promotion
                                if (board.boardPieces.get(a).getType().equals("Pawn")) {
                                    if (board.boardPieces.get(a).getColor().equals("white")) {
                                        if (board.boardPieces.get(a).getRow() == 1) {
                                            normalMove = false;
                                            //System.out.println("Adding a pawn promotion for white");
                                            possibleMoves.add(new PawnPromotionMove((Pawn) board.boardPieces.get(a), i, j, board, "Queen"));
                                        }
                                    } else if (board.boardPieces.get(a).getColor().equals("black")) {
                                        if (board.boardPieces.get(a).getRow() == 6) {
                                            normalMove = false;
                                            //System.out.println("Adding a pawn promotion for black");
                                            possibleMoves.add(new PawnPromotionMove((Pawn) board.boardPieces.get(a), i, j, board, "Queen"));
                                        }
                                    }
                                }

                                // if the move wasn't a castle or a pawn promotion
                                if (normalMove) {
                                    //System.out.println("Adding a new normal move");
                                    possibleMoves.add(new Move(board.boardPieces.get(a), i, j, board));
                                }
                            }
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }





}
