package puzzle;

import java.util.ArrayList;

import static puzzle.Dealer.typeCount;

public class Solver {
    public static final long rightWall =Dealer.rightRow;
    public static final Piece[][] pieces = Dealer.generateAllPieces();

    public static ArrayList<Long> solution = new ArrayList<>(typeCount);

    public static void solve(){
        solution.clear();
        solve(Dealer.boundaries, Dealer.makeAvailabilityList(),0);
    }

    public static boolean solve(long board, LinkList avail, int x){
        if(~board == 0) return true;

        while(((~board)&(rightWall<<x))==0) x++;
        int y = 0;
        for(long i = 1L<<x; (i&board)!=0; i=1L<<(x+y*12))
            y++;

        LinkList current = avail;
        LinkList prev;
        while (true) {
            prev = current;             //linked list stuff to make it so the node we're currently using
            current = current.next;     //is temporarily removed from the list
            if(current==null)           //
                return false;           //
            prev.next = current.next;   //

            for(Piece p: pieces[current.type]){                   //for every orientation of the current type of piece
                if ((y + p.heightAbove <= 5) && (y >= p.vPos)) {    //if the piece would fit above the floor and below the ceiling
                    long shiftedPiece = p.data<<(x+12*(y-p.vPos));   //shift the piece into place
                    if ((board & shiftedPiece) == 0)                  //if the piece would fit with the existing tiles
                        if(solve(board | shiftedPiece,avail,x)) {       //try recursively placing solving the resulting board.
                            solution.add(shiftedPiece);
                            return true;
                        }
                }
            }
            prev.next=current; //puts the linked list node back
        }
    }
}