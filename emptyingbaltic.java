import java.util.*;
import java.io.*;

/* 1. depth refers to how much water can flow from adjacent points into that point's
 *    depth.
 * 2. for e.g point of depth -2 can allow max 2 m^3 of water to flow in
 * 3. a Node(x, y, z) is such that x and y are the x and y coordinates of a point 
 *    respectively and z represents the depth/amount of water that can slow from the 
 *    adjacent nodes
 * 4. PriorityQueue pq sorts nodes by their depth in ascending order(deepest to shallowest)
 * 5. first, we add the source node to pq and mark it as visited
 * 6. volume = 0
 * 7. while (!pq.isEmpty()) 
 *      n = pq.poll();
 *      for every adjacent node x of n that is not visited
 *        if x.z < 0
 *          newDepth = max(n.z, x.z)
 *          volume += -(newDepth)
 *          x.z = newDepth
 *          pq.add(x);
 *          mark x as visited
 *      end forloop
 *    end whileloop
 * 8. output volume
 */
public class emptyingbaltic {
    public static int h,w;
    public static int[][] map;
    public static int f[][] = {{1,-1,0,0,1,-1,1,-1},
        {0,0,1,-1,-1,1,1,-1}};
    public static boolean[][] visited;

    public static boolean check(int x, int y) {
        if (x >= 1 && x <= w && y >= 1 && y <= h) {
            return true;
        }
        return false;
    }

    public static long operate(int x, int y) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node first = new Node(x, y, map[y][x]);
        long volume = -1 * map[y][x];
        pq.add(first);
        visited[y][x] = true;
        while (!pq.isEmpty()) {
            Node n = pq.poll();
            for (int i = 0; i < 8; i++) {
                int x2 = n.x + f[1][i];
                int y2 = n.y + f[0][i];
                if (check(x2, y2) && map[y2][x2] < 0 && !visited[y2][x2]) {
                    int newDepth = Math.max(n.depth, map[y2][x2]);
                    pq.add(new Node(x2, y2, newDepth));
                    volume += -1 * newDepth; 
                    visited[y2][x2] = true;
                }
            }
        }
        return volume;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));
        String[] firstLine = br.readLine().split(" ");
        h = Integer.parseInt(firstLine[0]);
        w = Integer.parseInt(firstLine[1]);
        map = new int[h+1][w+1];
        visited = new boolean[h+1][w+1];
        for (int i = 1; i <= h; i++) {
            String[] temp = br.readLine().split(" ");
            for (int j = 1; j <= w; j++) {
                map[i][j] = Integer.parseInt(temp[j-1]);
            }
        }
        String[] lastLine = br.readLine().split(" ");
        int y = Integer.parseInt(lastLine[0]);
        int x = Integer.parseInt(lastLine[1]);
        pw.println(operate(x, y));
        pw.close();
    }
}

class Node implements Comparable<Node> {
    public int x;
    public int y;
    public int depth;

    Node(int x, int y, int depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public int compareTo(Node e2) {
        return (this.depth - e2.depth);
    }
}
