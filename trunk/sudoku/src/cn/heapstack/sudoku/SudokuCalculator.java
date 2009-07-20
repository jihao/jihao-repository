package cn.heapstack.sudoku;

import java.awt.Point;
import java.util.Observable;
import java.util.Stack;

public class SudokuCalculator extends Observable {

	private Cell[][] matrix = new Cell[9][9];
	private Stack<Cell> stack = new Stack<Cell>();

	public SudokuCalculator(int[][] init_problem) {
		super();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				matrix[i][j] = new Cell(new Point(i, j), init_problem[i][j]);
			}
		}
	}

	public Cell[][] getSolvedSudoku() {
		return matrix;
	}

	/**
	 * 
	 * using a stack to answer the sudoku problem
	 * <br>
	 * the while loop stops when the stack size equals to the to be filled empty cell total sum
	 * 
	 * 
	 * @return
	 */
	public boolean answer() {
		boolean hasAnswer = true;
		boolean isSudokuMatrix = SudokuUtility.verifyMatrix(matrix);
		if (!isSudokuMatrix) {
			return false;
		}

		Cell firstEditableCell = findFirstEditableCell();
		firstEditableCell.availableValueArray.addAll(SudokuUtility
				.getValidCellValue(firstEditableCell, matrix));

		stack.add(firstEditableCell);
		int editableCellSize = countEditableCellSize();
		// System.out.println("To be filled cell size:"+editableCellSize);
		try {
			while (stack.size() != editableCellSize) {
				Cell current = stack.peek();

				if (current.availableValueArray.size() > 0) {
					current.setValue(current.availableValueArray.remove(0));
					// System.out.println(current);
					Cell next = nextCell(current);
					while (!next.isEditable()) {
						next = nextCell(next);
						// System.out.println("next:"+next);
					}
					next.setValue(0);
					next.availableValueArray.clear();
					next.availableValueArray.addAll(SudokuUtility
							.getValidCellValue(next, matrix));

					stack.add(next);
				} else {
					current = stack.pop();
					current.setValue(0);
					current.availableValueArray.clear();
					current.availableValueArray.addAll(SudokuUtility
							.getValidCellValue(current, matrix));

					// System.out.println("Pop:"+current);
				}
			}
			Cell top = stack.peek();
			top.setValue(top.availableValueArray.remove(0));
		} catch (java.util.EmptyStackException e) {
			e.printStackTrace();
			hasAnswer = false;
		}

		// SudokuUtility.printMatrix(matrix);
		hasAnswer = SudokuUtility.verifyIfSudokuFinished(matrix);

		this.setChanged();
		this.notifyObservers();

		return hasAnswer;
	}

	private Cell findFirstEditableCell() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matrix[j][i].isEditable())
					return matrix[j][i];
			}
		}
		return null;
	}

	private int countEditableCellSize() {
		int result = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matrix[i][j].isEditable())
					result = result + 1;
			}
		}
		return result;
	}

	private Cell nextCell(Cell c) {
		int x = c.coordinate.x;
		int y = c.coordinate.y;
		x = x + 1;
		y += x / 9;
		x = x % 9;

		if (y == 9) {
			return null;
		}
		return this.matrix[x][y];
	}
}
