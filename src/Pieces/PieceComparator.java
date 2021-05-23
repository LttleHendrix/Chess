package Pieces;

import java.util.Comparator;

public class PieceComparator implements Comparator<Piece> {

    public int compare(Piece o1, Piece o2) {
        if(o1.getRow() > o2.getRow()) {
            return 1;
        } else if(o1.getRow() < o2.getRow()) {
            return -1;
        } else if(o1.getCol() > o2.getCol()) {
            return 1;
        } else if(o1.getCol() < o2.getCol()) {
            return -1;
        } else {
            return 0;
        }
    }

}
