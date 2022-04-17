package puzzle;

import java.util.ArrayList;
import static puzzle.Dealer.typeCount;

//tells the things to do the stuff, also times them and prints stuff
public class Main {
    public static final char[] flavors = "█▓▒░X+".toCharArray(); //array of different characters to use for printing

    //boards with starting conditions
    public static final long b477 = 0b1111111111111111100101101000100000000000100000000000100000000000L;
    public static final type[] p477 = {type.j,type.d,type.L};
    public static final long b478 = 0b1111111111111111101000001011100000000000100000000000100000000000L;
    public static final type[] p478 = {type.d,type.L,type.j};
    public static final long b479 = 0b1111111111111111101001001000100000000000100000000000100000000000L;
    public static final type[] p479 = {type.L,type.l,type.j};
    public static final long b480 = 0b1111111111111111100100000010100000000000100000000000100000000000L;
    public static final type[] p480 = {type.l,type.j,type.ln};

    public enum type{l,d,L,j,hp,sc,bc,dl,u,ln,sq,pl}//the pieces have names, how nice

    public static void main(String[] args) {
        timeSolves(1000000 );
    }

    public static void timeSolves(int count){
        System.out.println("doing "+count+" Solves");
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Solver.solve();
//            solveInitial(b480,p480);
        }
        long timeTaken = System.currentTimeMillis()-time;
        System.out.println("completed in " + timeTaken+"ms, with an average of "+(double)timeTaken/count+"ms");
        printSolution();
    }

    private static void solveInitial(long board, type[] pieces){ //solves a board with initial conditions
        LinkList head = new LinkList();
        topLoop: for (int i = 0; i < typeCount; i++) {
            for (type t : pieces) {
                if (t.ordinal() == i)
                    continue topLoop;
            }
            LinkList nu = new LinkList();
            nu.type=i;
            nu.next=head.next;
            head.next=nu;
        }
        Solver.solve(board,head,0);
    }

    private static void printSolution(){printSolution(Solver.solution);}
    private static void printSolution(ArrayList<Long> solution){ //does what it says
        char[][] board = new char[5][11];
        for (int i = 0; i < solution.size(); i++) {
            long l = solution.get(i);
            for (int x = 0; x < 11; x++) {
                for (int y = 0; y < 5; y++) {
                    if((l&((1L<<x)<<(y*12)))!=0)
                        board[y][10-x]=flavors[i%flavors.length];
                }
            }
        }
        for (int i = 4; i>=0; i--)
            System.out.println(board[i]);
    }
}