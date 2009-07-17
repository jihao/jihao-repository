package cn.heapstack.sudoku.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class SudokuCalculator {

	private static Cell[][] matrix = new Cell[9][9];
	private static Stack<Cell> stack = new Stack<Cell>();
	/**
	 * calculate the available values for the current cell
	 * 
	 * @param current - current cell
	 * @param sudoku - the matrix
	 * @return available integer values array
	 */
	private static ArrayList<Integer> getValidCellValue(Cell current,Cell[][] sudoku)
	{
		Point c = current.getCoordinate();
		//System.out.println("row: "+row+" column:"+column);
		ArrayList<Integer> set = new ArrayList<Integer>();
		for(int i=1;i<10;i++)
		{
			set.add(i);
		}
		
		ArrayList<Integer> existed = new ArrayList<Integer>();		
		for(int i=0;i<9;i++)
		{
			//System.out.print(" & "+sudoku[row][i].value);
			if (sudoku[c.x][i].value != 0)
				existed.add(sudoku[c.x][i].value);
		}
		//System.out.println();
		for(int i=0;i<9;i++)
		{
			//System.out.print(" % "+sudoku[i][column].value);
			if(sudoku[i][c.y].value != 0)
				existed.add(sudoku[i][c.y].value);
		}
		
		//System.out.println();
		
		int block_row = c.x/3;
		int block_colum = c.y/3;
		for(int i=block_row*3;i<(block_row+1)*3;i++)
		{
			for(int j=block_colum*3;j<(block_colum+1)*3;j++)
			{
				//System.out.print(" $ "+sudoku[i][j].value);
				if(sudoku[i][j].value!=0)
					existed.add(sudoku[i][j].value);
			}
		}
		//System.out.println();
		//System.out.println(set);
		//System.out.println(existed);
		
		 set.removeAll(existed);
		 System.out.println("OK values:"+set);
		 return set;
	}
	
	
	
	public static void main(String[] args) {
		
		go();

	}

	private static Cell nextCell(Cell c)
	{
		int x = c.coordinate.x;
		int y = c.coordinate.y;
		x = x+1;
		y += x/9;
		x = x%9;
		
		if ( y==9 )
		{
			return null;
		}
		return matrix[x][y];
		 
	}


	private static void go() {
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				matrix[i][j] = new Cell( new Point(i, j) );
				System.out.println(i+":"+j);
				nextCell(matrix[i][j]);
			}
		}
		matrix[0][0].value = 8;
		matrix[0][0].editable = false;
		
		matrix[3][2].value = 8;
		matrix[3][2].editable = false;
		
		
		matrix[0][3].value = 6;
		matrix[0][3].editable = false;
		
		
		matrix[8][8].value = 8;
		matrix[8][8].editable = false;
		
		printMatrix(matrix);
		
		stack.add(matrix[1][0]);
		
		stack.peek().availableValueQueue.addAll( getValidCellValue(stack.peek(),matrix) );
		
		int blankCellSize = countBlankCellSize(matrix);
		System.out.println(blankCellSize);
		while(stack.size()!= blankCellSize)
		{
			Cell current = stack.peek();
			
			if(current.availableValueQueue.size()>0)
			{
				//Random r = new Random();
				//int tmp = r.nextInt(current.availableValueQueue.);
				
				current.setValue(current.availableValueQueue.poll());
				System.out.println(current);
				Cell next = nextCell(current);
				while( !next.editable)
				{
					next = nextCell(next);
					System.out.println("next:"+next);
				}
				next.value = 0;
				next.availableValueQueue.clear();
				next.availableValueQueue.addAll( getValidCellValue(next,matrix) );
				
				stack.add(next);
			}
			else
			{
				System.out.println("Pop:"+stack.pop());
			}
		}
		Cell top = stack.peek();
		top.value = top.availableValueQueue.poll();
		
		printMatrix(matrix);
		System.out.println(verifyMatrix(matrix));
	}



	private static int countBlankCellSize(Cell[][] m) {
		int result = 0;
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(m[i][j].editable)
					result = result+1;
			}
		}
		return result;
	}
	
	
	public static void printMatrix(Cell[][] matrix) {
		System.out.println("_____________________________________");
		for(int i=0;i<9;i++)
		{
			System.out.print("|");
			for(int j=0;j<9;j++)
			{
				System.out.print(" "+matrix[j][i].value+" |");
			}
			System.out.println();
		}
		System.out.println("_____________________________________");
	}
	
	
	public static boolean verifyMatrix(Cell[][] matrix) {
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
				if (matrix[i][j].value < 0 || matrix[i][j].value > 9)
					return false;
				else {
					if (matrix[i][j].value != 0) {
						if (!set.add(matrix[i][j].value)) {
							return false;
						}
					}
				}
			}
			set.clear();

			for (int j = 0; j < 9; j++) {
				if (matrix[j][i].value < 0 || matrix[j][i].value > 9) {
					return false;
				} else {
					if (matrix[j][i].value != 0) {
						if (!set.add(matrix[j][i].value)) {
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
						if (matrix[i][j].value < 0 || matrix[i][j].value > 9)
							return false;
						else {
							if (matrix[i][j].value != 0) {
								if (!set.add(matrix[i][j].value))
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
}
