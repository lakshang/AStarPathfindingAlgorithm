/*Lakshan Gunarathna
* w1584534
* 2014286*/

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar_Algorithm {


    double heuCost = 0; //Heuristic Cost
    double totCost = 0;

    int a;
    int b;
    AStar_Algorithm parent;

    AStar_Algorithm() {
    }

    AStar_Algorithm(int a, int b) {
        this.a = a;
        this.b = b;

    }

    PriorityQueue<AStar_Algorithm> openBlocks;
    boolean[][] closedBlocks;
    AStar_Algorithm startNode;
    AStar_Algorithm endNode;
    AStar_Algorithm array[][];


    public void findPath(int i, int j, double costD, double costH) {
        openBlocks.add(startNode);

        AStar_Algorithm currentNode;
        AStar_Algorithm l;

        while (true) {
            currentNode = openBlocks.poll();
            if (currentNode == null) break;
            closedBlocks[currentNode.a][currentNode.b] = true;
            if (currentNode.equals(array[i][j])) return;
            if (currentNode.a - 1 >= 0) {
                l = array[currentNode.a - 1][currentNode.b];
                calcCost(currentNode, l, currentNode.totCost + costH);

                if (currentNode.b - 1 >= 0) {
                    l = array[currentNode.a - 1][currentNode.b - 1];
                    //Update the Cost
                    calcCost(currentNode, l, currentNode.totCost + costD);
                }
                if (currentNode.b + 1 < array[0].length) {
                    l = array[currentNode.a - 1][currentNode.b + 1];
                    //Update the Cost
                    calcCost(currentNode, l, currentNode.totCost + costD);
                }
            }
            if (currentNode.b - 1 >= 0) {
                l = array[currentNode.a][currentNode.b - 1];
                //Update the Cost
                calcCost(currentNode, l, currentNode.totCost + costH);
            }

            if (currentNode.b + 1 < array[0].length) {
                l = array[currentNode.a][currentNode.b + 1];
                //Update the Cost
                calcCost(currentNode, l, currentNode.totCost + costH);
            }
            if (currentNode.a + 1 < array.length) {
                l = array[currentNode.a + 1][currentNode.b];
                //Update the Cost
                calcCost(currentNode, l, currentNode.totCost + costH);

                if (currentNode.b - 1 >= 0) {
                    l = array[currentNode.a + 1][currentNode.b - 1];
                    //Update the Cost
                    calcCost(currentNode, l, currentNode.totCost + costD);
                }
                if (currentNode.b + 1 < array[0].length) {
                    l = array[currentNode.a + 1][currentNode.b + 1];
                    //Update the Cost
                    calcCost(currentNode, l, currentNode.totCost + costD);
                }

            }

        }

    }

    public ArrayList<AStar_Algorithm> path(int Ai, int Aj, int Bi, int Bj, boolean arrayPath[][], double costD, double costH) {
        array = new AStar_Algorithm[arrayPath.length][arrayPath.length];
        closedBlocks = new boolean[arrayPath.length][arrayPath.length];

        openBlocks = new PriorityQueue<>((Object o1, Object o2) -> {
            AStar_Algorithm c1 = (AStar_Algorithm) o1;
            AStar_Algorithm c2 = (AStar_Algorithm) o2;

            return c1.totCost < c2.totCost ? -1 :
                    c1.totCost > c2.totCost ? 1 : 0;
        });


        startNode = new AStar_Algorithm(Ai, Aj);
        endNode = new AStar_Algorithm(Bi, Bj);

        for (int x = 0; x < arrayPath.length; ++x) {
            for (int z = 0; z < arrayPath.length; ++z) {
                array[x][z] = new AStar_Algorithm(x, z);
                array[x][z].heuCost = Math.abs(x - Bi) + Math.abs(z - Bj);
                //System.out.println(array[x][z].heuCost + " ");
            }
        }
        for (int i = 0; i < arrayPath.length; i++) {
            for (int x = 0; x < arrayPath.length; x++) {
                array[i][x] = new AStar_Algorithm(i, x);
                if (arrayPath[i][x] == false) array[i][x] = null;
            }
        }
        startNode.totCost = 0;

        findPath(Bi, Bj, costH, costD);
        ArrayList<AStar_Algorithm> path = new ArrayList<>();

        if (closedBlocks[Bi][Bj]) {
            AStar_Algorithm currentNode = array[Bi][Bj];
            path.add(currentNode);
            System.out.println(Double.toString(currentNode.totCost));
            while (currentNode.parent != null) {
                path.add(currentNode);
                currentNode = currentNode.parent;
            }
        } else System.out.println(" Path not Found ! ");

        return path;
    }

    void calcCost(AStar_Algorithm currentNode, AStar_Algorithm l, double cost) {
        if (l == null || closedBlocks[l.a][l.b]) return;
        double finalCost = l.heuCost + cost;

        boolean openIn = openBlocks.contains(l);
        if (!openIn || finalCost < l.totCost) {
            l.totCost = finalCost;
            l.parent = currentNode;
            if (!openIn) openBlocks.add(l);
        }

    }

}
