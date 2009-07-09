package cn.heapstack.sudoku;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

public class Executer {

	private Cell[][] sudoku;
	private Cell[][] backup;
	private int[][] matrix;
	
	private int depth = 1;
	private LinkedList<Coord> histroy = new LinkedList<Coord>();
	private boolean useOptimizedMethod = false;
	
	public Executer(int[][] init_matrix)
	{
		this.matrix = init_matrix;
		initSudokuExecuter();
	}
	
	/**
	 * 使用优化的DES方法解答数独
	 * @return
	 */
	public boolean calculateByOptimizedDFS()
	{
		useOptimizedMethod = true;
		return calculate();
	}
	
	/**
	 * 使用通常采用的DES方法解答数独
	 * @return
	 */
	public boolean calculateByNormalDFS()
	{
		useOptimizedMethod = false;
		return calculate();
	}
	
	public Cell[][] getSolvedSudoku()
	{
		return sudoku;
	}
	
	private boolean calculate()
	{
		//
		//printSudoku(); 
		System.out.println("[Executer][DEBUG] puzzle  matrix:");
		SudokuUtility.printMatrix(sudoku);
		
		answerByExactvalue();
		
		Date start = new Date();
		
		if(!verifyIfSudokuFinished())
		{
			tryWithDFS(findNextCellToTry());
		}
		
		Date end = new Date();
		
		boolean result = verifyIfSudokuFinished();
		System.out.print(result?"\r[Executer] 解答成功":"\r[Executer] 解答失败");
		System.out.print("\r\tCOST : ");
		System.out.print(end.getTime() - start.getTime());
		System.out.print(" ms, ");
		System.out.print("DEPTH : "+depth +"\n");
		
		
		
		//printSudoku();
		SudokuUtility.printMatrix(sudoku);
		
		return result;
	}
		

	/**
	 * 创建数独题目
	 */
	private void initSudokuExecuter()
	{
		sudoku = new Cell[9][9];
		backup = new Cell[9][9];
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				sudoku[i][j] = new Cell(matrix[i][j]);
				backup[i][j] = new Cell(matrix[i][j]);
			}
		}
	}

	/**
	 * 使用深度优先的方法尝试解答数独
	 * @param c
	 * @return
	 */
	private boolean tryWithDFS(Coord c)
	{
		depth++;
		
		//如果是回溯
		if(histroy.contains(c))
		{	
			//System.out.println("回溯 - "+p + " : " +sudoku[p.x][p.y].value );
				//还有可以尝试的值
				if(sudoku[c.x][c.y].valideValueArray.size()>0)
				{
					sudoku[c.x][c.y].value = sudoku[c.x][c.y].valideValueArray.remove(0);
					//System.out.println("[修改] "+p + " : " +sudoku[p.x][p.y].value );
				}
				//没有可以尝试的值了，则继续回溯
				else
				{
					sudoku[c.x][c.y].valideValueArray.clear();
					sudoku[c.x][c.y].value = 0;

					//弹出当前坐标点
					histroy.pop();
					if(!histroy.isEmpty())
					{
						//回溯前一个坐标再次尝试
						return tryWithDFS(histroy.peek());
					}
					else
					{
						//数独无解
						//System.out.println("数独无解");
						return false;
					}
				}
		}
		//尝试下一个新的坐标点
		else
		{
			histroy.push(c);
			//System.out.println("尝试新坐标 : "+p);
			ArrayList<Integer> array = getValiedCellValue(c);
			
			if(array.size()>0)
			{
				sudoku[c.x][c.y].valideValueArray.addAll(array);
				
				//还有可以尝试的值
				//(sudoku[p.x][p.y].valideValueArray.size()>0)
				sudoku[c.x][c.y].value = sudoku[c.x][c.y].valideValueArray.remove(0);
				
			}
			//没有可以尝试的值了，则回溯
			else
			{
				sudoku[c.x][c.y].valideValueArray.clear();
				sudoku[c.x][c.y].value = 0;
				
				//弹出当前坐标点
				//System.out.println("POP:"+histroy.pop() +" is in histroy ? "+histroy.contains(p));
				if(!histroy.isEmpty())
				{
					//回溯前一个坐标再次尝试
					Coord previous = histroy.peek();
					//System.out.println("previous:"+previous +" is in histroy ? "+histroy.contains(previous));
					return tryWithDFS(previous);
				}
				else
				{
					//数独无解
					//System.out.println("数独无解");
					return false;
				}
			}
		}
		
		if(chooseMethodToVerify()==false)
		{
			Coord next = findNextCellToTry();
			//System.out.println("Next Cell:"+next );
			if(next!=null)
			{
				return tryWithDFS(next);
			}
			else
			{
				sudoku[c.x][c.y].valideValueArray.clear();
				sudoku[c.x][c.y].value = 0;
				
				//弹出当前坐标点
				histroy.pop();
				if(!histroy.isEmpty())
				{
					//回溯前一个坐标再次尝试
					Coord previous = histroy.peek();
					return  tryWithDFS(previous);
				}
				else
				{
					//数独无解
					return false;
				}
			}
		}
		else
		{
			//System.out.println("******解答成功*******");
			return true;
		}
	}
	
	/**
	 * 选择使用哪种方法进行深度优先过程中校验数独是否解答成功
	 * @return
	 */
	private boolean chooseMethodToVerify()
	{
		if(useOptimizedMethod)
			return answerByExactvalue();
		else
			return verifyIfSudokuFinished();
	}

	/**
	 * 通过填入计算得出的应该填哪个精确值的方法回答此数独<br>
	 * 
	 * 不一定所有的题目都可以使用这个方法一次解答出答案
	 * 
	 * 如果该方法不能暂时不能解答成功,则返回 false,否则返回 true<br>
	 * <br>
	 * 
	 * 该方法失败后，再配合使用深度优先的算法，解答此数独
	 * 算法较一般的深度优先效率高
	 * 
	 */
	private boolean answerByExactvalue() {
		
		backup();
		
		boolean OK = false;
		boolean makeprogress = true;
		while(!OK && makeprogress)
		{
			makeprogress = false;
			for(int seed =1;seed<10;seed++)
			{					
				//System.out.println("--------------------seed: "+seed+"-------------");	
				markSeed(seed,true);
				printSeedMap();
				
				//System.out.println("----- TRY SEED "+seed+" ---------");
				boolean succeed = fillExclusiveValue(seed);
				while(succeed) // keep trying until this seed no longer useful
				{
					markSeed(seed,true);
					makeprogress = true;
					
					//System.out.println("----- TRY AGAIN SEED "+seed+" ---------");
					succeed = fillExclusiveValue(seed);
				}
				
				printSeedMap();
				markSeed(seed,false);
				
				OK = verifyIfSudokuFinished();
				if(OK)
				{
					break;
				}
			}
			//System.out.println(OK+" : "+makeprogress);
			//System.out.println("_____________________________________________________");
		}
		if(!OK)
			restore();
		
		return OK;
	}

	/**
	 * 验证数独是否已经填完<br>
	 * 		先验证逐行是否符合1-9<br>
	 * 		再验证逐列是否符合1-9<br>
	 * 		再验证每个九宫格内是否符合1-9<br>
	 * @return
	 * 		有一项验证失败则返回 false,
	 * 		否则返回 true
	 */
	private boolean verifyIfSudokuFinished() {
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if (sudoku[i][j].value != 0)
				{
					set.add(sudoku[i][j].value);
				}
			}
			if(set.size()!=9)
				return false;
			set.clear();
			
			for(int j=0;j<9;j++)
			{
				if (sudoku[j][i].value != 0)
				{
					set.add(sudoku[j][i].value);
				}
			}
			if(set.size()!=9)
				return false;
			set.clear();
		}
		
		for(int block_row=0;block_row<3;block_row++)
		{
			for(int block_colum=0;block_colum<3;block_colum++)
			{
				for(int i=block_row*3;i<(block_row+1)*3;i++)
				{
					for(int j=block_colum*3;j<(block_colum+1)*3;j++)
					{
						if (sudoku[i][j].value != 0)
						{
							set.add(sudoku[i][j].value);
						}
					}
				}
			}
		}
		if(set.size()!=9)
			return false;
		set.clear();
		
		return true;
	}

	/**
	 * 找到下一个要填的坐标
	 * @return
	 */
	private Coord findNextCellToTry()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(sudoku[i][j].value==0)
				{
					sudoku[i][j].coord.x = i;
					sudoku[i][j].coord.y = j;
					return sudoku[i][j].coord;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 检查当前单元格可以填的有效值
	 * @param c
	 * @return
	 */
	private ArrayList<Integer> getValiedCellValue(Coord c)
	{
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
		 //System.out.println("OK values:"+set);
		 return set;
	}
	
	private boolean fillExclusiveValue(int seed) {
		boolean succeed = false;
		int counter = 0;
		int save_i = -1;
		int save_j = -1;
		for(int row_index=0;row_index<9;row_index++)
		{
			for(int column_index=0;column_index<9;column_index++)
			{
				if(sudoku[row_index][column_index].editable)
				{
					if(sudoku[row_index][column_index].seedNotOkFlag == false)
					{
						save_i = row_index;
						save_j = column_index;
						counter++;
					}
				}
			}
		
			//System.out.println("row:"+row_index + " counter:"+ counter);
	
			//如果行的解唯一
			if(counter==1)
			{
				sudoku[save_i][save_j].value=seed;
				sudoku[save_i][save_j].editable = false;
				sudoku[save_i][save_j].seedNotOkFlag = true;
				
				//System.out.print("行有唯一解：["+(save_i+1)+","+(save_j+1)+"]="+seed);
				//System.out.println("\tsudoku["+save_i+"]["+save_j+"]="+sudoku[save_i][save_j].value);
				
				succeed = true;
				save_i = -1;
				save_j = -1;
			}
			counter = 0;
		}
		
		if(succeed)
			return succeed;
		
		for(int column=0;column<9;column++)
		{
			for(int row=0;row<9;row++)
			{
				if(sudoku[row][column].editable)
				{
					if(sudoku[row][column].seedNotOkFlag == false)
					{
						save_i = row;
						save_j = column;
						counter++;
					}
				}
			}
			
			//System.out.println("column:"+column + " counter:"+ counter);
			
			//如果列的解唯一
			if(counter==1)
			{
				sudoku[save_i][save_j].value=seed;
				sudoku[save_i][save_j].editable = false;
				sudoku[save_i][save_j].seedNotOkFlag = true;
				
				//System.out.print("列有唯一解：["+(save_i+1)+","+(save_j+1)+"]="+seed);
				//System.out.println("\tsudoku["+save_i+"]["+save_j+"]="+sudoku[save_i][save_j].value);
			
				succeed = true;
				save_i = -1;
				save_j = -1;
			}
			counter = 0;
		}
		return succeed;
	}



	private void markSeed(int seed,boolean markflag)
	{
		//System.out.println("mark seed "+seed+" : "+markflag);
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if (sudoku[i][j].value == seed)
				{
					mark(i,j,markflag);
					//System.out.println("MARK "+i+"行"+j+"列 as "+markflag);
				}
			}
		}
	}
	
	
	private void printSeedMap()
	{
	
			//System.out.println("############################################");
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					//System.out.print(" "+(sudoku[i][j].conflictflag?'*':'_'));
					
					
					if(sudoku[i][j].editable)
					{
						//System.out.print(" "+(sudoku[i][j].seedNotOkFlag?(sudoku[i][j].value):"_"));
					}
					else
					{
						//System.out.print(" "+sudoku[i][j].value);
					}
					
				}
				//System.out.println();
			}
			//System.out.println("############################################");
	
	}
	
	/**
	 * 标记row行，column列，及[row,column]所在九宫格上的所有单元格的conflictFlag属性
	 * @param row
	 * @param column
	 * @param markflag
	 */
	private void mark(int row, int column,boolean markflag) {
		for(int i=0;i<9;i++)
		{
			if(sudoku[row][i].editable)
				sudoku[row][i].seedNotOkFlag = markflag;
		}
		for(int i=0;i<9;i++)
		{
			if(sudoku[i][column].editable)
				sudoku[i][column].seedNotOkFlag = markflag;
		}
		int block_row = row/3;
		int block_colum = column/3;
		for(int i=block_row*3;i<(block_row+1)*3;i++)
		{
			for(int j=block_colum*3;j<(block_colum+1)*3;j++)
			{
				if(sudoku[i][j].editable)
					sudoku[i][j].seedNotOkFlag = markflag;
			}
		}
	}

	private void backup()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				backup[i][j].copyFrom(sudoku[i][j]);
			}
		}
	}
	
	private void restore()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				sudoku[i][j].copyFrom(backup[i][j]);
			}
		}
	}
}
