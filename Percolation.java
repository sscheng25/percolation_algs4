import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private int[][] grid;
    private int len;  // length of the grid
    private WeightedQuickUnionUF UF;
    private int top = 0;
    private int bot;

    // convert 2d array to 1d array
    public int two2One(int row, int col) {
        return (len * row + col + 1);
    }

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        len = n;
        grid = new int[n][n];
        bot = n * n + 1;
        UF = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 0; i < n; i++) {
            for (int m = 0; m < n; m++) {
                grid[i][m] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            UF.union(top, two2One(0, i));
        }
        for (int i = 0; i < n; i++) {
            UF.union(bot, two2One(n - 1, i));
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        grid[row][col] = 1;
        if (row > 0 && isOpen(row - 1, col))
            UF.union(two2One(row - 1, col), two2One(row, col));
        if (row + 1 < len && isOpen(row + 1, col))
            UF.union(two2One(row + 1, col), two2One(row, col));
        if (col + 1 < len && isOpen(row, col + 1))
            UF.union(two2One(row, col + 1), two2One(row, col));
        if (col > 0 && isOpen(row, col - 1))
            UF.union(two2One(row, col - 1), two2One(row, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (UF.find(two2One(row, col)) == UF.find(top));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int numOpen = 0;
        for (int i = 0; i < len; i++) {
            for (int m = 0; m < len; m++) {
                if (grid[i][m] == 1) {
                    numOpen++;
                }
            }
        }
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (UF.find(top) == UF.find(bot));
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation perc = new Percolation(n);

        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            perc.open(row, col);

            if (perc.isFull(row, col)) {
                System.out.print("FULL!\n");
            }
            else {
                System.out.print("Not full.\n");
            }

            if (perc.percolates()) {
                System.out.print("PERCOLATES!\n");
                System.out.println("Num of Open: " + perc.numberOfOpenSites());
            }
            else {
                System.out.print("Not percolated!\n");
                System.out.println("Num of Open: " + perc.numberOfOpenSites());
            }
        }
    }
}
