import java.util.*;
import java.io.*;

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

    
