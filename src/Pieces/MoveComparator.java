package Pieces;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {


    @Override
    public int compare(Move o1, Move o2) {
        if(o1.minimaxValue > o2.minimaxValue) {
            return -1;
        } else if(o1.minimaxValue < o2.minimaxValue) {
            return 1;
        } else {
            return 0;
        }
    }
}
