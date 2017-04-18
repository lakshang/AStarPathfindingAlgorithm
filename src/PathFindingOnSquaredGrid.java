/*Lakshan Gunarathna
* w1584534
* 2014286*/


import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PathFindingOnSquaredGrid {
    static Scanner sc = new Scanner(System.in);
    static Stopwatch timerFlow;

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }

        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) return true;
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.circle(j, N - i - 1, .5);
                    } else StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    public static void path(boolean[][] arrayP, int Ai, int Aj, int Bi, int Bj, double costH, double costD, String method) {
        ArrayList<AStar_Algorithm> path = new AStar_Algorithm().path(Ai, Aj, Bi, Bj, arrayP, costD, costH, method);

        StdDraw.setPenColor(Color.cyan);
        StdDraw.filledSquare(Aj, arrayP.length - Ai - 1, .5);

        for (AStar_Algorithm node : path) {
            StdDraw.filledSquare(node.b, arrayP.length - node.a - 1, .5);
        }
    }

    public static void menu(boolean array[][], int Ai, int Aj, int Bi, int Bj, double costH, double costD) {
        System.out.print("PRESS [C] [E] [M] [0-Exit] : ");
        String input = sc.next();
        switch (input.toUpperCase()) {
            case "C":
                costD = 1.0;
                System.out.print("Cost : ");
                timerFlow = new Stopwatch();
                path(array, Ai, Aj, Bi, Bj, costH, costD, "C");
                StdOut.println("Elapsed time : " + timerFlow.elapsedTime());
                menu(array, Ai, Aj, Bi, Bj, costH, costD);
                break;

            case "E":
                costD = 1.4;
                System.out.print("Cost : ");
                timerFlow = new Stopwatch();
                path(array, Ai, Aj, Bi, Bj, costH, costD, "E");
                StdOut.println(" Elapsed time : " + timerFlow.elapsedTime());
                menu(array, Ai, Aj, Bi, Bj, costH, costD);
                break;

            case "M":
                costD = 2.0;
                System.out.print("Cost : ");
                timerFlow = new Stopwatch();
                path(array, Ai, Aj, Bi, Bj, costH, costD, "M");
                StdOut.println("Elapsed time : " + timerFlow.elapsedTime());
                menu(array, Ai, Aj, Bi, Bj, costH, costD);
                break;

            case "0":
                System.exit(0);
            default:
                System.out.println(" Invalid Input! ");
                System.exit(0);

        }
    }

    // test client
    public static void main(String[] args) {
        double costH = 1.0;
        double costD = 0;
        // boolean[][] open = StdArrayIO.readBoolean2D();

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(10, 0.5);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        System.out.println();
        //System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        System.out.println();
        //System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));
        System.out.println();

        // Reading the coordinates for points A and B on the input squared grid.

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis

        Scanner in = new Scanner(System.in);

        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis


        // System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
        // System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);
        menu(randomlyGenMatrix, Ai, Aj, Bi, Bj, costH, costD);


    }


}



