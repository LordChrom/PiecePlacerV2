package puzzle;

import java.util.HashSet;

//generates the data for every orientation of the pieces
public class Dealer {

    //how are pieces and the board stored?
    //well basically each long holds 5 rows of 12 bits each. 1 bit is the left wall and the rest are the board's tiles
    //pieces are represented the same way.
    //this is nice because the pieces can be moved just by bit shifting, and they can be checked against or added to
    //the board all at once with a single bitwise operation
    public static final long boundaries = 0b1111100000000000100000000000100000000000100000000000100000000000L;
    public static final long bottomRow = 0b11111111111;
    public static final long rightRow = 0b000000000001000000000001000000000001000000000001000000000001L;

    public static final long[] pieceTypes = { //the bit data for each type of piece.
            0b000000000000000000000001000000000001000000000011L, //l  L
            0b000000000000000000000001000000000011000000000011L, //d  d
            0b000000000001000000000001000000000001000000000011L, //L  tall L
            0b000000000001000000000001000000000011000000000001L, //j  drunk L
            0b000000000001000000000001000000000011000000000010L, //hp harry potter scar
            0b000000000000000000000000000000000001000000000011L, //sc small corner
            0b000000000000000000000001000000000001000000000111L, //bc bigger corner
            0b000000000000000000000001000000000011000000000110L, //dl diagonal line
            0b000000000000000000000000000000000101000000000111L, //u  cup
            0b000000000001000000000001000000000001000000000001L, //ln |
            0b000000000000000000000000000000000011000000000011L, //sq square
            0b000000000000000000000010000000000111000000000010L, //pl +
    };
    public static final int typeCount = pieceTypes.length;

    public static Piece[][] generateAllPieces(){
        Piece[][] ret = new Piece[typeCount][];
        for (int type = 0; type < typeCount; type++) { //for every type of piece
            HashSet<Long> set = new HashSet<>(); //make a set for all the orientations. Duplicates are auto ignored
            long piece = pieceTypes[type];       //get the standard orientation
            for (int i = 0; i < 4; i++) {//loop that adds every orientation of the piece
                set.add(piece);
                set.add(flipV(piece));
                piece = rotate(piece);
            }
            ret[type] = new Piece[set.size()];
            int i = 0;
            for(long l : set) //makes an array of Pieces from the set of longs
                ret[type][i++]= new Piece(l);
        }
        return ret;
    }

    public static long flipV(long piece){ //flips piece vertically
        long out = 0;
        for (int y = 0; y < 5; y++)
            out|=((piece>>(y*12))&bottomRow)<<((4-y)*12);
        while((out&bottomRow)==0) out>>=12; //aligns to bottom
        return out;
    }

    public static long rotate(long piece){ //rotates piece
        long out = 0;
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++) //iterates through each bit
                out|= (((piece & ((1L<<x)<<(12*y))) ==0?0L:1L)<<y)<<(12*(4-x)); //moves bit from (x,y) to (y,4-x)
        while((out&bottomRow)==0) out>>=12; //aligns to bottom
        return out;
    }

    public static LinkList makeAvailabilityList(){ //makes a blank linked list for available pieces
        LinkList head = new LinkList();
        for (int i = 0; i < typeCount; i++) {
            LinkList nu = new LinkList();
            nu.type=i;
            nu.next=head.next;
            head.next=nu;
        }
        return head;
    }

}