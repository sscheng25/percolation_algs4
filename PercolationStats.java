import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation perc;
    private int[] countOpen;
    private int numTrials;
    private double[] ratioOpen;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        perc = new Percolation(n);
        countOpen = new int[trials];
        ratioOpen = new double[trials];
        numTrials = trials;

        for (int i = 0; i < trials; i++) {
            int cOpen = 0;
            while (!perc.percolates()) {
                // randomly add a new seed in the grid
                int row = StdRandom.uniform(0, n);
                int col = StdRandom.uniform(0, n);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    cOpen++;
                }
            }
            double rOpen = cOpen * 1.0 / (n * n);
            countOpen[i] = cOpen;
            ratioOpen[i] = rOpen;
        }


    }

    public double numOpen() {
        return StdStats.mean(countOpen);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(ratioOpen);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ratioOpen);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(numTrials));
    }

    // test client
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);

        StdOut.println("Num Open                = " + percStats.numOpen());
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" +
                               percStats.confidenceLo() +
                               ", " +
                               percStats.confidenceHi() +
                               "]");

    }
}
