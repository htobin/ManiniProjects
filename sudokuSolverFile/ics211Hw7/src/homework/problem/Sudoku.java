package homework.problem;

/* 
 * find a solution to a Sudoku problem
 * @author	Biagioni, Edoardo
 * @date	October 23, 2013
 * @missing	fillSudoku, to be implemented by the students in ICS 211
 */
public class Sudoku {

	/*
	 * check that the sudoku rules hold in this sudoku puzzle. cells that
	 * contain 0 are not checked.
	 * 
	 * @param the sudoku to be checked
	 * 
	 * @param whether to print the error found, if any
	 * 
	 * @return true if this sudoku obeys all of the sudoku rules, otherwise
	 * false
	 */
	public static boolean checkSudoku(int[][] sudoku, boolean printErrors) {
		if (sudoku.length != 9) {
			if (printErrors) {
				System.out.println("sudoku has " + sudoku.length + " rows, should have 9");
			}
			return false;
		}
		for (int i = 0; i < sudoku.length; i++) {
			if (sudoku[i].length != 9) {
				if (printErrors) {
					System.out.println("sudoku row " + i + " has " + sudoku[i].length + " cells, should have 9");
				}
				return false;
			}
		}
		/* check each cell for conflicts */
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cell = sudoku[i][j];
				if (cell == 0) {
					continue; /* blanks are always OK */
				}
				if ((cell < 1) || (cell > 9)) {
					if (printErrors) {
						System.out.println("sudoku row " + i + " column " + j + " has illegal value " + cell);
					}
					return false;
				}
				/* does it match any other value in the same row? */
				for (int m = 0; m < sudoku.length; m++) {
					if ((j != m) && (cell == sudoku[i][m])) {
						if (printErrors) {
							System.out.println(
									"sudoku row " + i + " has " + cell + " at both positions " + j + " and " + m);
						}
						return false;
					}
				}
				/* does it match any other value it in the same column? */
				for (int k = 0; k < sudoku.length; k++) {
					if ((i != k) && (cell == sudoku[k][j])) {
						if (printErrors) {
							System.out.println(
									"sudoku column " + j + " has " + cell + " at both positions " + i + " and " + k);
						}
						return false;
					}
				}
				/* does it match any other value in the 3x3? */
				for (int k = 0; k < 3; k++) {
					for (int m = 0; m < 3; m++) {
						int testRow = (i / 3 * 3) + k; /* test this row */
						int testCol = (j / 3 * 3) + m; /* test this col */
						if ((i != testRow) && (j != testCol) && (cell == sudoku[testRow][testCol])) {
							if (printErrors) {
								System.out.println("sudoku character " + cell + " at row " + i + ", column " + j
										+ " matches character at row " + testRow + ", column " + testCol);
							}
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/*
	 * convert the sudoku to a printable string
	 * 
	 * @param the sudoku to be converted
	 * 
	 * @param whether to check for errors
	 * 
	 * @return the printable version of the sudoku
	 */
	public static String toString(int[][] sudoku, boolean debug) {
		if ((!debug) || (checkSudoku(sudoku, true))) {
			String result = "";
			for (int i = 0; i < sudoku.length; i++) {
				if (i % 3 == 0) {
					result = result + "+-------+-------+-------+\n";
				}
				for (int j = 0; j < sudoku.length; j++) {
					if (j % 3 == 0) {
						result = result + "| ";
					}
					if (sudoku[i][j] == 0) {
						result = result + "  ";
					} else {
						result = result + sudoku[i][j] + " ";
					}
				}
				result = result + "|\n";
			}
			result = result + "+-------+-------+-------+\n";
			return result;
		}
		return "illegal sudoku";
	}

	/*
	 * find an assignment of values to sudoku cells that makes the sudoku valid
	 * 
	 * @param the sudoku to be filled
	 * 
	 * @return whether a solution was found if a solution was found, the sudoku
	 * is filled in with the solution if no solution was found, restores the
	 * sudoku to its original value
	 */

	/*
	 * helper method
	 * 
	 * @param s, the sudoku puzzle
	 * 
	 * @param x, the column
	 * 
	 * @param y, the row
	 * 
	 * @return goes through each individual block, looking to enter a value into
	 * each sudoku block. Return true: the value entered in the block passes
	 * checkSudoku, then it goes onto the next block: Returns false: value does
	 * not pass checkSudoku, tries again, and backtracks to get a proper answers.
	 */
	public static boolean helper(int[][] s, int x, int y) {
		// if you've reached then end of the puzzle
		if (x == 8 && y == 8) {
			// if the box is empty
			if (s[x][y] == 0) {
				/*
				 * insert a number if it doesn't work then go to the the next
				 * number until it passes checkSudoku.
				 */
				for (int value = 1; value < 10; value++) {
					s[x][y] = value;
					if (checkSudoku(s, false)) {
						return true;
					}
				}
				// return false if the value doesn't work, repeats the for loop
				return false;
			}
			// the value has worked
			return true;
		}
		// if value is not 0, then move over to the right, if at the last column
		// go down a row
		if (s[x][y] != 0) {
			if (x < 8) {
				x++;
			} else {
				x = 0;
				y++;
			}
			// if helper returns a true value, start helper again
			if (helper(s, x, y)) {
				return true;
			}
			//back tracks if the number doesn't work
			return false;
		}
		//if the number is 0, then go through all of the values and find one that works
		for (int value = 1; value < 10; value++) {
			s[x][y] = value;
			if (checkSudoku(s, false)) {
				if (helper(s, x, y)) {
					return true;
				}
			}

		}
		//if all the options don't work return false, then resets it to 0
		s[x][y] = 0;
		return false;
	}
	//fill sudoku starts at the beginning and calls helper to do recursion
	public static boolean fillSudoku(int[][] s) {
		return helper(s, 0, 0);

	}
}
