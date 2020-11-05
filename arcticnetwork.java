import java.util.*;
import java.io.*;

/* 1.Find the distances between every pair of outposts
 * 2.Sort the distances between every pair of outposts in ascending order
 * 3.Using Kruskal's MST algo, find the MST of the outposts
 * 4.Omit S - 1 longest edges from the MST
 * 5.Output the longest edge of the MST
 */
public class arcticnetwork {
    public static pair[] outposts;
    public static ArrayList<Edge> edges;

    public static double solve(pair p1, pair p2) {//to find distance between 2 outpost
        double x = Math.abs(p1.x - p2.x);
        double y = Math.abs(p1.y - p2.y);
        double result = Math.sqrt(x*x + y*y);
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));
        int testcases = Integer.parseInt(br.readLine());
        while (testcases != 0) {
            String[] firstLine = br.readLine().split(" ");
            int S = Integer.parseInt(firstLine[0]);
            int P = Integer.parseInt(firstLine[1]);
            outposts = new pair[P+1];
            edges = new ArrayList<>();
            for (int i = 0; i < P; i++) {
                String[] temp = br.readLine().split(" ");
                int x = Integer.parseInt(temp[0]);
                int y = Integer.parseInt(temp[1]);
                outposts[i+1] = new pair(x, y);
            }
            for (int i = 1; i < outposts.length; i++) {//finding all distances between every outpost
                pair first = outposts[i];
                for (int j = 1 + i; j < outposts.length; j++) {
                    pair second = outposts[j];
                    double dist = solve(first, second);
                    edges.add(new Edge(i, j, dist));
                }
            }
            Collections.sort(edges);
            ArrayList<Double> distances = new ArrayList<>();
            UnionFind UF = new UnionFind(P+1);
            for (int i = 0; i < edges.size(); i++) {//Kruskal's algorithm
                Edge e = edges.get(i);
                if (UF.isSameSet(e.first, e.second)) {
                    continue;
                }
                UF.unionSet(e.first, e.second);
                distances.add(e.dist);
            }
            Collections.sort(distances);
            double d = distances.get(distances.size() - 1 - (S - 1));//output (S - 2)th largest edge
            pw.format("%.2f%n",d);
            testcases--;
        }
        pw.close();
    }
}

class UnionFind {
    public int[] p;
    public int[] rank;
    public int[] setSize;
    public int numSets;

    public UnionFind(int N) {
        p = new int[N];
        rank = new int[N];
        setSize = new int[N];
        numSets = N;
        for (int i = 0; i < N; i++) {
            p[i] = i;
            rank[i] = 0;
            setSize[i] = 1;
        }
    }

    public int findSet(int i) { 
        if (p[i] == i) return i;
        else {
            p[i] = findSet(p[i]);
            return p[i]; 
        } 
    }

    public Boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

    public void unionSet(int i, int j) { 
        if (!isSameSet(i, j)) { 
            numSets--; 
            int x = findSet(i), y = findSet(j);
            // rank is used to keep the tree short
            if (rank[x] > rank[y]) { 
                p[y] = x; 
                setSize[x] = setSize[x] + setSize[y]; 
            }
            else { 
                p[x] = y; 
                setSize[y] = setSize[x] + setSize[y];
                if (rank[x] == rank[y]) 
                    rank[y] = rank[y]+1; 
            } 
        } 
    }

    public int numDisjointSets() { return numSets; }

    public int sizeOfSet(int i) { return setSize[findSet(i)]; }
}

class pair {
    public double x;
    public double y;

    pair(double x, double y) {
        this.x = x;
        this.y= y;
    }
}

class Edge implements Comparable<Edge> {
    public int first;
    public int second;
    public double dist;

    Edge(int f, int s, double d) {
        first = f;
        second = s;
        dist = d;
    }

    public int compareTo(Edge e) {
        if (this.dist - e.dist < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
