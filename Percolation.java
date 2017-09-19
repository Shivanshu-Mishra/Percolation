import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * 
 * @author eshumra
 *
 */
public class Percolation {
	private int[][] grid;
	private WeightedQuickUnionUF wquf;
	private int virtualTop;
	private int virtualBottom;
	private int openSite = 0;
	private static final int TOP_ROW_POS = 1;
	// Extra elements for blocked element, virtual top,virtual bottom
	private static final int EXTRA_ELEMENT = 3;

	public Percolation(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException(
					"Value of n cannot be less than or equal to 0");
		}
		grid = new int[n + 1][n + 1];
		int totalElement = n * n;
		virtualTop = totalElement + 1;
		virtualBottom = totalElement + 2;
		// Valid open Sites in the grid,blocked site,virtual top and virtual
		// bottom
		wquf = new WeightedQuickUnionUF(totalElement + EXTRA_ELEMENT);
	}

	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			int newElement = ++openSite;
			grid[row][col] = newElement;
			connectSide(row - 1, col, newElement);
			connectSide(row + 1, col, newElement);
			connectSide(row, col - 1, newElement);
			connectSide(row, col + 1, newElement);

			if (row == TOP_ROW_POS && row == grid.length - 1) {
				wquf.union(virtualTop, newElement);
				wquf.union(virtualBottom, newElement);
			} else if (row == TOP_ROW_POS) {
				wquf.union(virtualTop, newElement);
			} else if (row == grid.length - 1) {
				wquf.union(virtualBottom, newElement);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		validate(row, col);
		if (grid[row][col] > 0) {
			return true;
		}
		return false;
	}

	public int numberOfOpenSites() {
		return openSite;
	}

	public boolean isFull(int row, int col) {
		validate(row, col);
		return wquf.connected(virtualTop, grid[row][col]);
	}

	public boolean percolates() {
		return wquf.connected(virtualTop, virtualBottom);
	}

	private void connectSide(int row, int col, int newOpenSite) {
		if (row == 0 || col == 0 || row == grid.length || col == grid.length) {
			return;
		}

		if (isOpen(row, col)) {
			wquf.union(grid[row][col], newOpenSite);
		}
	}

	private void validate(int row, int col) {
		if (row < 1 || col < 1 || row > grid.length || col > grid.length) {
			throw new IllegalArgumentException(
					"Specified values are out of range");
		}
	}
}
