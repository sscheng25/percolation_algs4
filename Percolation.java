import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF UF = new WeightedQuickUnionUF(3 * 3 + 2);

    // convert 2d array to 1d array
    public int two2One(int row, int col) {
        return (3 * row + col + 1);
    }

    // creates n-by-n grid, with all sites initially blocked
    public int[][] grid;

    public Percolation(int n) {
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int m = 0; m < n; m++) {
                grid[i][m] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            UF.union(0, two2One(0, i));
        }
        for (int i = 0; i < n; i++) {
            UF.union(10, two2One(n - 1, i));
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row][col] = 1;
        if (row > 1 && grid[row - 1][col] == 1) UF.union(two2One(row - 1, col), two2One(row, col));
        if (row + 1 < 3 && grid[row + 1][col] == 1) UF.union(two2One(row + 1, col), two2One(row, col));
        if (col + 1 < 3 && grid[row][col + 1] == 1) UF.union(two2One(row, col + 1), two2One(row, col));
        if (col > 1 && grid[row][col - 1] == 1) UF.union(two2One(row, col - 1), two2One(row, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (UF.find(two2One(row, col)) == UF.find(0));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 0; i < 3; i++) {
            for (int m = 0; m < 3; m++) {
                if (grid[i][m] == 1) {
                    numOpen++;
                }
            }
        }
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (UF.find(0) == UF.find(10));
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 3; //StdIn.readInt();
        Percolation perc = new Percolation(n);

        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            perc.open(row, col);

            if (perc.isFull(row, col)) {
                System.out.print("FULL!\n");
            } else {
                System.out.print("Not full.\n");
            }

            if (perc.percolates()) {
                System.out.print("PERCOLATES!\n");
                System.out.println("Num of Open: " + perc.numberOfOpenSites());
            } else {
                System.out.print("Not percolated!\n");
                System.out.println("Num of Open: " + perc.numberOfOpenSites());
            }
        }
    }
}
