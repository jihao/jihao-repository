package cn.heapstack.sudoku;

import java.util.HashSet;

public class SudokuUtility {

	/**
	 * <p>
	 * ����Ȼ˳���ʾ�ľ����ַ�����������
	 * </p>
	 * 
	 * @param input
	 *            ����Ϊ81������Ȼ˳���ʾ�ľ����ַ��� <br/> example:061030800009050000000000045007010300090240500010600004000400009050000102302000000
	 * 
	 * @return �������Ϸ����򷵻ع����ľ��󣬷��򷵻� empty array
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
	 * ��������Ŀ������Ȼ˳�򵼳�����81��������ɵ��ַ���
	 * 
	 * @param matrix
	 *            �������
	 * @return �������Ϸ��򷵻ص������ַ��������򷵻�null
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
	 * ��֤���������Ŀ�Ƿ�Ϸ�
	 * 
	 * @param matrix
	 *            �������
	 * @return �Ϸ����� true,���򷵻� false
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
						// ������ͬ��Ԫ�أ����������Ƿ�
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
						// ������ͬ��Ԫ�أ����������Ƿ�
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
								// �Ź�������ͬ��Ԫ�أ����������Ƿ�
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
	 * ��ӡ����
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
	 * ��ӡ����
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
	 * ��ӡ����
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
