import java.util.*;
import java.io.*;

public class communicationssatellite {
    public static Tuple[] dishes;
    public static ArrayList<Edge> edges;

    public static double solve(Tuple t1, Tuple t2) {
        double x = Math.abs(t1.x - t2.x);
        double y = Math.abs(t1.y - t2.y);
        double dist = Math.sqrt(x*x + y*y);
        double result = dist - t1.r - t2.r;
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));
        int N = Integer.parseInt(br.readLine());
        dishes = new Tuple[N+1];
        edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String[] temp = br.readLine().split(" ");
            int x = Integer.parseInt(temp[0]);
            int y = Integer.parseInt(temp[1]);
            int r = Integer.parseInt(temp[2]);
            dishes[i+1] = new Tuple(x, y, r);
        }
        for (int i = 1; i <= N; i++) {
            Tuple first = dishes[i];
            for (int j = 1 + i; j <= N; j++) {
                Tuple second = dishes[j];
                double dist = solve(first, second);
                edges.add(new Edge(i, j, dist));
            }
        }
        Collections.sort(edges);
        double output = 0;
        UnionFind UF = new UnionFind(N+1);
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (UF.isSameSet(e.first, e.second)) {
                continue;
            }
            UF.unionSet(e.first, e.second);
            output += e.dist;
        }
        pw.format("%.8f", output);
        pw.close();
    }
}

class Tuple {
    public double x;
    public double y;
    public double r;

    Tuple(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
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
        }
        return 1;
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
