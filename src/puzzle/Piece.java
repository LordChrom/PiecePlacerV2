package puzzle;

//stores the data for a particular orientation of a type of piece
public class Piece {
    long data;
    byte vPos;        //height of bottommost tile in right column (0 indexed)
    byte heightAbove; //height from the bottommost tile in the right column to the highest tile (in any column)

    //
    //    the piece       X marks the origin
    //       █                   █
    //      ███                 ██X
    //       █                   █
    //this example piece has a vPos of 1 and a heightAbove of 2.
    //vPos is used to determine if a piece would be placed off the bottom of the board
    //heightAbove is used to determine if the piece would be placed above the board
    //essentially, the lowest piece in the right column is treated as the piece's origin

    public Piece(long data){
        this.data = data;
        vPos =0;
        heightAbove=0;
        while((data&1)==0){
            vPos++;
            data>>=12;
        }
        while ((data&Dealer.bottomRow)!=0){
            heightAbove++;
            data>>=12;
        }
    }
}