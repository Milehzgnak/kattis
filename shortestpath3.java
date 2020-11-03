import java.util.*;
import java.io.*;

/*Use Bellman Ford algorithm to find the shortest path from a single source vertex
  v represents vertex 0 to n-1
  e represents directed edge from vertex u to v with weight w
  for every v
    D[v] = INF
  D[s] = 0
  for 1 to V-1
    for every edge e
      if (D[u] != INF and D[v] > D[u] + w)
        D[v] = D[u] + w
  
  all vertices should have found their shortest path from source after v-1 relaxations
 
  run Bellman Ford again
  for 1 to V-1
    for every edge e
      if (D[u] != INF and D[v] > D[u] + w)
        D[v] = -INF

  if vertex v is still able to be relaxed, it means its connected to/ or part of a -ve
  weighted cycle. Hence we assign -INF to D[v]
  
  if D[v] = INF (not reachable from source)
    print "impossible"
  if D[v] = -INF (abitrarily short path from s to v)
    print "-Infinity"
  else:
    print D[v]
*/
public class shortestpath3 {
    public static Edge2[] edges;
    public static int[][] weight;
    public static int[] D;
    public static int m, n;

    public static void bellmanFord(int s) {
        int INF = 1000000000;
        for (int i = 0; i < n; i++) {
            D[i] = INF;
        }
        D[s] = 0;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                Edge2 e = edges[k];
                int first = e.first;
                int second = e.second;
                int w = e.weight;
                if (D[first] != INF && D[second] > (D[first] + w)) {
                    D[second] = D[first] + w;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                Edge2 e = edges[k];
                int first = e.first;
                int second = e.second;
                int w = e.weight;
                if (D[first] != INF && D[second] > D[first] + w) {
                    D[second] = INF * -1;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));
        String str;
        while (!(str = br.readLine()).equals("0 0 0 0")) {
            String[] temp = str.split(" ");
            n = Integer.parseInt(temp[0]);
            m = Integer.parseInt(temp[1]);
            int q = Integer.parseInt(temp[2]);
            int s = Integer.parseInt(temp[3]);
            edges = new Edge2[m];
            D = new int[n];
            for (int i = 0; i < m; i++) {
                String[] temp1 = br.readLine().split(" ");
                int first = Integer.parseInt(temp1[0]);
                int second = Integer.parseInt(temp1[1]);
                int w = Integer.parseInt(temp1[2]);
                edges[i] = new Edge2(first, second, w);
            }
            bellmanFord(s);
            for (int i = 0; i < q; i++) {
                int v = Integer.parseInt(br.readLine());
                if (D[v] == 1000000000) {
                    pw.println("Impossible");
                    continue;
                }
                if (D[v] == -1000000000) {
                    pw.println("-Infinity");
                    continue;
                }
                pw.println(D[v]);
            }
            pw.println();
        }
        pw.close();
    }
}

class Edge2 {
    int first;
    int second;
    int weight;

    Edge2(int f, int s, int w) {
        first = f;
        second = s;
        weight = w;
    }
}    
