import java.util.*;
import java.io.*;

/* 1. Create a node A connecting all nodes on first row of block,
 *    create another node B connecting all nodes on last row of block.
 *    the weight of these edges of node A and B are 0.
 * 2. run dijkstra sssp algorithm on node A, whilst tracking the parent of 
 *    every node.
 * 3. to find path of crack, find the shortest path from A to B.
 *    this can be done by tracing the parents of nodes continually from B
 *    until A.
 */
public class blockcrushers {
    public static int[][] block;
    public static int[][] D;
    public static Node[][] p;
    public static boolean[][] visited;
    public static int h, w;
    public static int f[][] = {{0,1,1,1,0,-1,-1,-1},
        {1,1,0,-1,-1,-1,0,1}};
    public static int INF = 1000000000;

    public static boolean check(int x, int y) {
        if (y >= 0 && y < h+2 && x >= 1 && x < w+1) {
            return true;
        }
        return false;
    }

    public static void dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < h+2; i++) {
            for (int j = 1; j < w+1; j++) {
                D[i][j] = INF;
            }
        }
        D[0][1] = 0;
        pq.add(new Node(1, 0, 0));
        while(!pq.isEmpty()) {
            int x = pq.peek().x;
            int y = pq.peek().y;
            int dist = pq.peek().dist;
            pq.poll();
            visited[y][x] = true;
            for (int i = 0; i < 8; i++) {
                int x2 = x + f[0][i];
                int y2 = y + f[1][i];
                if (check(x2, y2) && !visited[y2][x2]) {
                    if (D[y2][x2] > D[y][x] + block[y2][x2]) {
                        D[y2][x2] = D[y][x] + block[y2][x2];
                        pq.add(new Node(x2, y2, D[y2][x2]));
                        p[y2][x2] = new Node(x, y, 0);
                    }
                }
            }
        }
        Node P = p[h+1][1];
        int px = P.x;
        int py = P.y;
        while (py != 1) {
            block[py][px] = -1;
            P = p[py][px];
            px = P.x;
            py = P.y;
        }
        block[py][px] = -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));
        String firstLine;
        while (!(firstLine = br.readLine()).equals("0 0")) {
            String[] dim = firstLine.split(" ");
            h = Integer.parseInt(dim[0]);
            w = Integer.parseInt(dim[1]);
            block = new int[h+2][w+1];
            visited = new boolean[h+2][w+1];
            D = new int[h+2][w+1];
            p = new Node[h+2][w+1];
            for (int i = 1; i <= h; i++) {
                char[] temp = br.readLine().toCharArray();
                for (int j = 1; j <= w; j++) {
                    block[i][j] = Integer.parseInt(Character.toString(temp[j-1]));
                }
            }
            dijkstra();
            for (int i = 1; i <= h; i++) {
                for (int j = 1; j <= w; j++) {
                    if (block[i][j] != -1) {
                        pw.print(block[i][j]);
                        continue;
                    }
                    pw.print(" ");
                }
                pw.println();
            }
            pw.println(" ");
        }
        pw.close();
    }
}

class Node implements Comparable<Node> {
    public int x;
    public int y;
    public int dist;

    Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    public int compareTo(Node n) {
        return this.dist - n.dist;
    }
}
