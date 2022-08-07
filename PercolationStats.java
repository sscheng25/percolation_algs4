import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int numTrials;
    private final double[] ratioOpen;
    private final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        Percolation perc = new Percolation(n);
        ratioOpen = new double[trials];
        numTrials = trials;

        for (int i = 0; i < trials; i++) {
            int cOpen;
            while (!perc.percolates()) {
                // randomly add a new seed in the grid
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                }
            }
            cOpen = perc.numberOfOpenSites();
            double rOpen = cOpen * 1.0 / (n * n);
            ratioOpen[i] = rOpen;
        }


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
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(numTrials));
    }

    // test client
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" +
                               percStats.confidenceLo() +
                               ", " +
                               percStats.confidenceHi() +
                               "]");

    }
}
