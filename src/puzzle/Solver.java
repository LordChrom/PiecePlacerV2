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
            prev = current;
            current = current.next;
            if(current==null)
                return false;
            prev.next = current.next;

            for (int i = 0; i < pieces[current.type].length; i++) {
                Piece p = pieces[current.type][i];
                if ((y + p.heightAbove <= 5) && (y >= p.vPos)) {
                    long shiftedPiece = p.data<<(x+12*(y- p.vPos));
                    if ((board & shiftedPiece) == 0) {
                        if(solve(board | shiftedPiece,avail,x)) {
                            solution.add(shiftedPiece);
                            return true;
                        }
                    }
                }
            }
            prev.next=current;
        }
    }
}