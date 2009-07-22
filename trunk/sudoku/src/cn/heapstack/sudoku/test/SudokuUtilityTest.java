package cn.heapstack.sudoku.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cn.heapstack.sudoku.SudokuMatrixUtility;


public class SudokuUtilityTest {

	@Test
	public void testBuildMatrix() {

		String input = "004700009009400061300009800040007200000000000050006900700005400006100097008270000";
		int[][] result_matrix = SudokuMatrixUtility.buildMatrix(input);
		assertEquals(9, result_matrix.length);
		assertEquals(81, result_matrix.length
				* result_matrix[result_matrix.length - 1].length);

		for (int index = 0; index < input.length(); index++) {
			int i = index / 9;
			int j = index % 9;
			int expect = Integer.parseInt(String.valueOf(input.charAt(index)));
			int actual = result_matrix[i][j];
			assertEquals(expect, actual);
		}

		// Negative case
		String input_negative = "004700009";
		assertTrue(SudokuMatrixUtility.buildMatrix(input_negative).length==0);

		input_negative = "";
		assertTrue(SudokuMatrixUtility.buildMatrix(input_negative).length==0);

		input_negative = null;
		assertTrue(SudokuMatrixUtility.buildMatrix(input_negative).length==0);

		input_negative = "004770009009400061300009800040007200000000000050006900700005400006100097008270000";
		assertTrue(SudokuMatrixUtility.buildMatrix(input_negative).length==0);

	}

	@Test
	public void testExportMatrix2String() {
		int[][] init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0, 9 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 0 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 }, 
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 }, 
				{ 0, 0, 8, 2, 7, 0, 0, 0, 0 } };
		String result = SudokuMatrixUtility.exportMatrix2String(init_matrix);

		assertEquals(
						"004700009009400061300009800040007200000000000050006900700005400006100097008270000",
						result);
		int[][] result_matrix = SudokuMatrixUtility.buildMatrix(result);

		assertEquals(init_matrix.length, result_matrix.length);
		assertEquals(init_matrix[init_matrix.length - 1].length,	result_matrix[result_matrix.length - 1].length);

		for (int i = 0; i < init_matrix.length; i++) {
			for (int j = 0; j < init_matrix[init_matrix.length - 1].length; j++) {
				assertEquals(init_matrix[i][j], result_matrix[i][j]);
			}
		}

		// Negative case

	}

	@Test
	public void testVerifyMatrix() {
		int[][] init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0, 9 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 0 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 }, 
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 }, 
				{ 0, 0, 8, 2, 7, 0, 0, 0, 0 } };
		assertTrue(SudokuMatrixUtility.verifyMatrix(init_matrix));

		// Negative case
		init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 0 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 },
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 },
				{ 0, 0, 8, 2, 7, 0, 0, 0, 0 } };
		assertFalse(SudokuMatrixUtility.verifyMatrix(init_matrix));

		init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0, 9 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 0 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 },
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 } };
		assertFalse(SudokuMatrixUtility.verifyMatrix(init_matrix));

		init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0, 7 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 0 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 }, 
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 }, 
				{ 0, 0, 8, 2, 7, 0, 0, 0, 0 } };
		assertFalse(SudokuMatrixUtility.verifyMatrix(init_matrix));

		init_matrix = new int[][] { 
				{ 0, 0, 4, 7, 0, 0, 0, 0, 9 },
				{ 0, 0, 9, 4, 0, 0, 0, 6, 1 }, 
				{ 3, 0, 0, 0, 0, 9, 8, 0, 6 },
				{ 0, 4, 0, 0, 0, 7, 2, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 5, 0, 0, 0, 6, 9, 0, 0 }, 
				{ 7, 0, 0, 0, 0, 5, 4, 0, 0 },
				{ 0, 0, 6, 1, 0, 0, 0, 9, 7 }, 
				{ 0, 0, 8, 2, 7, 0, 0, 0, 0 } };
		assertFalse(SudokuMatrixUtility.verifyMatrix(init_matrix));
	}

}
