import java.util.*;
import java.io.*;

public class humancannonball {
    private static double[][] p, D;
    private static double INF = 1000000000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(System.out)));

        String[] first = br.readLine().split(" ");
        String[] second = br.readLine().split(" ");
        int n = Integer.parseInt(br.readLine());
        Pair[] coords = new Pair[n+2];
        coords[0] = new Pair(Double.parseDouble(first[0]), Double.parseDouble(first[1]));
        coords[1] = new Pair(Double.parseDouble(second[0]), Double.parseDouble(second[1]));
        for (int i = 0; i < n; i++) {
            String[] temp = br.readLine().split(" ");
            coords[2+i] = new Pair(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
        }
        D = new double[n+2][n+2];
        for (int i = 0; i < n + 2; i++) {// Initialize D[][]
            for (int j = 0; j < n + 2; j++) {
                Pair pi = coords[i];
                Pair pj = coords[j];
                double x = Math.abs(pi.x - pj.x);
                double y = Math.abs(pi.y - pj.y);
                double dist = Math.sqrt(x*x + y*y);
                if (i == j) {
                    D[i][j] = 0;
                    continue;
                } 
                if (i == 0 || i == 1) {
                    double time = dist / 5;
                    D[i][j] = time;
                    continue;
                }
                if (dist < 50.0) {
                    double diff = 50.0 - dist;
                    double time = diff / 5;
                    D[i][j] = time + 2;
                    continue;
                }
                if (dist >= 50.0) {
                    double diff = dist - 50.0;
                    double time = diff / 5;
                    D[i][j] = time + 2;
                    continue;
                }
            }
        }
        for (int k = 0; k < n + 2; k++) {// Floyd Warshall's algorithm
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < n + 2; j++) {
                    if (D[i][k] + D[k][j] < D[i][j]) {
                        D[i][j] = D[i][k] + D[k][j];
                    }
                }
            }
        }
        pw.printf("%.6f", D[0][1]);
        pw.close();
    }
}

class Pair {
    public double x;
    public double y;

    Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
