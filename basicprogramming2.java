import java.util.*;
import java.io.*;

public class basicprogramming2 {

    public static void twoSum(String[] temp, PrintWriter pw) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < temp.length; i++) {
            int curr = Integer.parseInt(temp[i]);
            int complement = 7777 - curr;
            if (curr != complement) {
               if (map.containsKey(complement)) {
                   pw.println("Yes");
                   return;
               }
               map.put(curr, 1);
            }
        }
        pw.println("No");
    }

    public static void unique(String[] temp, PrintWriter pw) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < temp.length; i++) {
            int curr = Integer.parseInt(temp[i]);
            if (map.containsKey(curr)) {
                pw.println("contains duplicate");
                return;
            }
            map.put(curr, 1);
        }
        pw.println("Unique");
    }

    public static void three(String[] temp, PrintWriter pw) {
        int[] count = new int[300000];
        HashMap<Integer, Integer> map = new HashMap<>();
        int result = 0;
        boolean have = false;
        for (int i = 0; i < temp.length; i++) {
            int curr = Integer.parseInt(temp[i]);
            count[curr] += 1;
            if (count[curr] > temp.length / 2) {
                result = curr;
                have = true;
                break;
            }
        }
        if (have) {
            pw.println(result);
            return;
        }
        pw.println(-1);
    }

    public static void median(String[] temp, PrintWriter pw) {
        int[] A = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            A[i] = Integer.parseInt(temp[i]);
        }
        Arrays.sort(A);
        if (temp.length % 2 == 0) {
            pw.print(A[temp.length/2 -1] + " ");
            pw.print(A[temp.length/2]);
        } else {
            pw.print(A[temp.length/2]);
        }
    }

    public static void range(String[] temp, PrintWriter pw) {
        int[] A = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            A[i] = Integer.parseInt(temp[i]);
        }
        Arrays.sort(A);
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 100 && A[i] <= 999) {
                pw.print(A[i] + " ");
            }
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new 
                    OutputStreamWriter(System.out)));

        String[] firstLine = br.readLine().split(" ");
        int N = Integer.parseInt(firstLine[0]);
        int action = Integer.parseInt(firstLine[1]);
        String[] temp = br.readLine().split(" ");
        if (action == 1) {
            twoSum(temp, pw);
        }
        if (action == 2) {
            unique(temp, pw);
        } 
        if (action == 3) {
            three(temp, pw);
        }
        if (action == 4) {
            median(temp, pw);
        } 
        if (action == 5) {
            range(temp, pw);
        }
        pw.close();
    }
}
