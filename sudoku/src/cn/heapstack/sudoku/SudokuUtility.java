package cn.heapstack.sudoku;

import java.util.HashSet;

public class SudokuUtility {

	/**
	 * <p>
	 * 从自然顺序表示的矩阵字符串构建矩阵
	 * </p>
	 * 
	 * @param input
	 *            长度为81的用自然顺序表示的矩阵字符串 <br/> example:061030800009050000000000045007010300090240500010600004000400009050000102302000000
	 * 
	 * @return 如果输入合法，则返回构建的矩阵，否则返回 empty array
	 */
	public static int[][] buildMatrix(String inputParam) {
		String input = inputParam;
		int index = 0;
		if (input != null) {
			input = input.trim();
			if (input.length() == 81) {
				int[][] matrix = new int[9][9];
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						matrix[i][j] = Integer.parseInt(String.valueOf(input
								.charAt(index)));
						index++;
					}
				}
				if (verifyMatrix(matrix))
					return matrix;
			}
		}
		return new int[0][0];
	}

	/**
	 * 将矩阵题目按照自然顺序导出成由81个数字组成的字符串
	 * 
	 * @param matrix
	 *            输入矩阵
	 * @return 如何输入合法则返回导出的字符串，否则返回null
	 */
	public static String exportMatrix2String(int[][] matrix) {
		if (matrix != null) {
			if (verifyMatrix(matrix)) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						sb.append(matrix[i][j]);
					}
				}
				return sb.toString();
			}
		}
		return null;
	}

	/**
	 * 验证输入矩阵题目是否合法
	 * 
	 * @param matrix
	 *            输入矩阵
	 * @return 合法返回 true,否则返回 false
	 */
	public static boolean verifyMatrix(int[][] matrix) {
		if (matrix == null)
			return false;

		int row = matrix.length;
		if (row != 9)
			return false;

		for (int i = 0; i < row; i++) {
			if (matrix[i].length != 9)
				return false;
		}

		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matrix[i][j] < 0 || matrix[i][j] > 9)
					return false;
				else {
					if (matrix[i][j] != 0) {
						// 行有相同的元素，则输入矩阵非法
						if (!set.add(matrix[i][j])) {
							return false;
						}
					}
				}
			}
			set.clear();

			for (int j = 0; j < 9; j++) {
				if (matrix[j][i] < 0 || matrix[j][i] > 9) {
					return false;
				} else {
					if (matrix[j][i] != 0) {
						// 列有相同的元素，则输入矩阵非法
						if (!set.add(matrix[j][i])) {
							return false;
						}
					}
				}
			}
			set.clear();
		}

		for (int block_row = 0; block_row < 3; block_row++) {
			for (int block_colum = 0; block_colum < 3; block_colum++) {
				for (int i = block_row * 3; i < (block_row + 1) * 3; i++) {
					for (int j = block_colum * 3; j < (block_colum + 1) * 3; j++) {
						if (matrix[i][j] < 0 || matrix[i][j] > 9)
							return false;
						else {
							if (matrix[i][j] != 0) {
								// 九宫格有相同的元素，则输入矩阵非法
								if (!set.add(matrix[i][j]))
									return false;
							}
						}
					}
				}
				set.clear();
			}
		}

		return true;
	}
	
	/**
	 * 打印数独
	 */
	public static void printMatrix(int[][] matrix) {
		System.out.println("_____________________________________");
		for(int i=0;i<9;i++)
		{
			System.out.print("|");
			for(int j=0;j<9;j++)
			{
				System.out.print(" "+matrix[i][j]+" |");
			}
			System.out.println();
		}
		System.out.println("_____________________________________");
	}
	
	/**
	 * 打印数独
	 */
	public static void printMatrix(char[][] matrix) {
		System.out.println("_____________________________________");
		for(int i=0;i<9;i++)
		{
			System.out.print("|");
			for(int j=0;j<9;j++)
			{
				System.out.print(" "+matrix[i][j]+" |");
			}
			System.out.println();
		}
		System.out.println("_____________________________________");
	}
	/**
	 * 打印数独
	 */
	public static void printMatrix(Cell[][] matrix) {
		System.out.println("_____________________________________");
		for(int i=0;i<9;i++)
		{
			System.out.print("|");
			for(int j=0;j<9;j++)
			{
				System.out.print(" "+matrix[i][j].value+" |");
			}
			System.out.println();
		}
		System.out.println("_____________________________________");
	}
	
	/**
	 * 
	 * @param block_row
	 * @param block_column
	 */
	public static void printMarkBlock(int block_row, int block_column)
	{
		System.out.println(block[block_row][block_column]);
	}
	private static final String[][] block = new String[][]{
			{"b00","b01","b02"},
			{"b10","b11","b12"},
			{"b20","b21","b22"}};

}
