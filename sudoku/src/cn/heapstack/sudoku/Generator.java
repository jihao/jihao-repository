package cn.heapstack.sudoku;

import java.util.ArrayList;
import java.util.Random;


public class Generator {
	private static int[] seed = new int[9];
	private static int[][] matrix = new int[9][9];

//	private final String[][] block = new String[][]{
//		{"b1","b2","b3"},
//		{"b4","b5","b6"},
//		{"b7","b8","b9"}};
	
	
	private static int[] generateInitArray()
	{
		ArrayList<Integer> array = new ArrayList<Integer>();
		Random r = new Random();
		while(array.size()<9)
		{
			int next = r.nextInt(9)+1;
			if(!array.contains(next))
			{
				array.add(next);
			}
		}
		while(!array.isEmpty())
		{
			seed[array.size()-1] = array.remove(array.size()-1);
		}
		return seed;
	}
	
	private static void generateCompleteSudoku()
	{
		int index = 0;
		for(int i=3;i<6;i++)
		{
			for(int j=3;j<6;j++)
			{
				matrix[i][j] = seed[index];
				index++;
			}
		}
		
		System.out.println("[Generator][DEBUG] - before generate complete Sudoku matrix:");
		SudokuUtility.printMatrix(matrix);
	
		for(int i=3;i<6;i++)
		{
			//���û� b5 -> b4
			matrix[3][i-3] = matrix[5][i];
			matrix[4][i-3] = matrix[3][i];
			matrix[5][i-3] = matrix[4][i]; 
			//���û� b5 -> b6
			matrix[3][i+3] = matrix[4][i];
			matrix[4][i+3] = matrix[5][i];
			matrix[5][i+3] = matrix[3][i]; 
			//���û� b5 -> b2
			matrix[i-3][3] = matrix[i][5];
			matrix[i-3][4] = matrix[i][3];
			matrix[i-3][5] = matrix[i][4];
			//���û� b5 -> b8
			matrix[i+3][3] = matrix[i][4];
			matrix[i+3][4] = matrix[i][5];
			matrix[i+3][5] = matrix[i][3];
		}

		for(int i=3;i<6;i++)
		{
			//���û� b4 -> b1			
			matrix[i-3][0] = matrix[i][1];
			matrix[i-3][1] = matrix[i][2];
			matrix[i-3][2] = matrix[i][0];
			//���û� b4 -> b7
			matrix[i+3][0] = matrix[i][2];
			matrix[i+3][1] = matrix[i][0];
			matrix[i+3][2] = matrix[i][1];
			//���û� b6 -> b3
			matrix[i-3][6] = matrix[i][7];
			matrix[i-3][7] = matrix[i][8];
			matrix[i-3][8] = matrix[i][6];			
			//���û� b6 -> b9
			matrix[i+3][6] = matrix[i][8];
			matrix[i+3][7] = matrix[i][6];
			matrix[i+3][8] = matrix[i][7];
		}
		

		System.out.println("[Generator][DEBUG] - after generate complete Sudoku matrix:");
		SudokuUtility.printMatrix(matrix);
	}
	
	
	/**
	 * 
	 * @param difficultLevel
	 * 	1 - 5
	 * 
	 * hide 26 - 56 
	 * @return
	 */
	public static int[][] generateSudokuMatirx(int difficultLevelParam)
	{
		int difficultLevel = difficultLevelParam;
		generateInitArray();
		generateCompleteSudoku();
		
		if(difficultLevel<1 || difficultLevel>10)
		{
			difficultLevel = 1;
		}
		
		int hideCount =  20+difficultLevel*6;
		
		Random r = new Random();
		for(int i=0;i<hideCount;i++)
		{
			int next = r.nextInt(81);
			int x = next/9;
			int y = next%9;
						
			matrix[x][y] = 0;
		}
		
		System.out.println("[Generator][DEBUG] - difficult level: "+difficultLevel+", hide: "+hideCount+" cells");
		System.out.println("[Generator][DEBUG] - Sudoku matrix:");
		SudokuUtility.printMatrix(matrix);
		
		return matrix;
	}
	
	
	/**
	 * Demo use Generator to generate a sudoku, then use Executer to solve it
	 * @param agrs
	 */
	public static void main(String[] agrs)
	{
		Executer ex = new Executer(Generator.generateSudokuMatirx(10));
		ex.calculateByOptimizedDES();
	}

}