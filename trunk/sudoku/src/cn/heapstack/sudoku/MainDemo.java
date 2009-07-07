package cn.heapstack.sudoku;


public class MainDemo {

	/**
	 * Demo use Executer to solve a sudoku which was assigned by the command line parameter
	 * 
	 * use -Xss100m otherwise there will be a stack over flow
	 * 
	 * @param vm arguments
	 * 		-Xss30m
	 * 
	 * @param args
	 * 		81 length string represent for the sudoku problem<br> 
	 * 		which was encoding by nature order of the sudoku, <br>
	 * 		and "0" in the string means the field need to be solved<br>
	 * 
	 * <b>Example:</b><br>
	 * 004700009009400061300009800040007200000000000050006900700005400006100097008270000
	 * <br>
	 * <b>represents the matrix:</b>
	 * <br>
	 * {{0,0,4,7,0,0,0,0,9},<br>
		{0,0,9,4,0,0,0,6,1},<br>
		{3,0,0,0,0,9,8,0,0},<br>
		{0,4,0,0,0,7,2,0,0},<br>
		{0,0,0,0,0,0,0,0,0},<br>
		{0,5,0,0,0,6,9,0,0},<br>
		{7,0,0,0,0,5,4,0,0},<br>
		{0,0,6,1,0,0,0,9,7},<br>
		{0,0,8,2,7,0,0,0,0}};
	 * 
	 */
	public static void main(String[] args) {
		
		if(args.length==1)
		{			
			int[][] init_problem = SudokuUtility.buildMatrix(args[0]);
			if(init_problem.length==0)
			{
				System.out.println("Invalid parameter");
			}
			else
			{
				Executer ex = new Executer(init_problem);
				if(ex.calculateByNormalDES())
				{
//				if(ex.calculateByOptimizedDES())
//				{
					System.out.println("\r\r$_$_$_$_$_$_$_$ SUCCEED $_$_$_$_$_$_$_$");
					SudokuUtility.printMatrix(ex.getSolvedSudoku());
				}
			}
		}
		else
		{
			System.out.println("Please give the sudoku parameter");
		}
	}

}
