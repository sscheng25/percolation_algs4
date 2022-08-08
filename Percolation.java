import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] grid;
    private final int len;  // length of the grid
    private final WeightedQuickUnionUF uf;
    // private final int top = 0;
    private final int bot;
    private int numOpen;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        len = n;
        grid = new boolean[n][n];
        bot = n * n + 1;
        numOpen = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);


        for (int i = 0; i < n; i++) {
            uf.union(0, two2One(0, i));
        }
        for (int i = 0; i < n; i++) {
            uf.union(bot, two2One(n - 1, i));
        }

    }

    // convert 2d array to 1d array
    private int two2One(int row, int col) {
        return (len * row + col + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IndexOutOfBoundsException();
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOpen++;
        }


        if (row - 1 > 0 && isOpen(row - 1, col))
            uf.union(two2One(row - 2, col - 1), two2One(row - 1, col - 1));
        if (row < len && isOpen(row + 1, col))
            uf.union(two2One(row, col - 1), two2One(row - 1, col - 1));
        if (col < len && isOpen(row, col + 1))
            uf.union(two2One(row - 1, col), two2One(row - 1, col - 1));
        if (col - 1 > 0 && isOpen(row, col - 1))
            uf.union(two2One(row - 1, col - 2), two2One(row - 1, col - 1));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return (grid[row - 1][col - 1]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return (uf.find(two2One(row - 1, col - 1)) == uf.find(0));
        }
        else {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        /*
        int numOpen = 0;

        for (int i = 0; i < len; i++) {
            for (int m = 0; m < len; m++) {
                if (grid[i][m]) {
                    numOpen++;
                }
            }
        }
        */
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (uf.find(0) == uf.find(bot));
    }

    // test client (optional)
    public static void main(String[] args) {
        /*
        System.out.print("Please enter the length of grids:\n");
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
        */

    }
}
