import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Class to get Percolation statistics
 * 
 * @author eshumra
 *
 */
public class PercolationStats {
	private int trials;
	private Percolation pc;
	private int n;
	private double mean;
	private double stdDev;
	private double[] thresholds;

	/**
	 * Constructor
	 * 
	 * @param n
	 *            - A grid of size n*n will be created
	 * @param trials
	 *            - Number of trials
	 */
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}

		this.n = n;
		this.trials = trials;
		thresholds = new double[trials];
	}

	/**
	 * To find mean of percolation threshold
	 * 
	 * @return mean of percolation threshold
	 */
	public double mean() {
		double sum = 0;
		double threshold;
		for (int trial = 0; trial < trials; trial++) {
			pc = new Percolation(n);
			threshold = getThresholdLevel();
			thresholds[trial] = threshold;
			sum = sum + threshold;
		}
		mean = sum / trials;
		return mean;
	}

	/**
	 * To find standard deviation for percolation threshold
	 * 
	 * @return standard deviation
	 */
	public double stddev() {
		double sumErrorSquare = 0;
		for (int trial = 0; trial < trials; trial++) {
			double error = thresholds[trial] - mean;
			sumErrorSquare = sumErrorSquare + Math.pow(error, 2);
		}
		double meanErrorSquare = sumErrorSquare / (trials - 1);
		stdDev = Math.sqrt(meanErrorSquare);
		return stdDev;

	}

	/**
	 * @return - Confidence level low
	 */
	public double confidenceLo() {
		return mean - ((1.96 * stdDev) / Math.sqrt(trials));
	}

	/**
	 * @return - Confidence level high
	 */
	public double confidenceHi() {
		return mean + ((1.96 * stdDev) / Math.sqrt(trials));
	}

	/**
	 * Main method
	 * 
	 * @param args
	 *            - size of grid and number of trial
	 */
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
				Integer.parseInt(args[1]));
		StdOut.printf("Mean: %s", ps.mean());
		StdOut.println();
		StdOut.printf("stddev: %s", ps.stddev());
		StdOut.println();
		StdOut.printf("[%s,%s]", ps.confidenceLo(), ps.confidenceHi());
	}

	private double getThresholdLevel() {
		int row, col;
		double totalSite = n * n;
		double openSite;
		while (!pc.percolates()) {
			row = StdRandom.uniform(1, n + 1);
			col = StdRandom.uniform(1, n + 1);
			pc.open(row, col);
		}
		openSite = pc.numberOfOpenSites();
		double threshold = openSite / totalSite;
		return threshold;
	}
}
