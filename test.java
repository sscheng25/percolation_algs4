import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class test {
    public static void main(String[] args) {
        WeightedQuickUnionUF wQUF = new WeightedQuickUnionUF(9);
        //wQUF.union(4, 5);
        if (wQUF.find(4) == wQUF.find(5)) {
            System.out.println("Connected");
        } else {
            System.out.println("Not connected");
        }

    }
}
