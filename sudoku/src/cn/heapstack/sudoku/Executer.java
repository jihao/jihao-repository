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
	 * ʹ���Ż���DES�����������
	 * @return
	 */
	public boolean calculateByOptimizedDFS()
	{
		useOptimizedMethod = true;
		return calculate();
	}
	
	/**
	 * ʹ��ͨ�����õ�DES�����������
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
		System.out.print(result?"\r[Executer] ���ɹ�":"\r[Executer] ���ʧ��");
		System.out.print("\r\tCOST : ");
		System.out.print(end.getTime() - start.getTime());
		System.out.print(" ms, ");
		System.out.print("DEPTH : "+depth +"\n");
		
		
		
		//printSudoku();
		SudokuUtility.printMatrix(sudoku);
		
		return result;
	}
		

	/**
	 * ����������Ŀ
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
	 * ʹ��������ȵķ������Խ������
	 * @param c
	 * @return
	 */
	private boolean tryWithDFS(Coord c)
	{
		depth++;
		
		//����ǻ���
		if(histroy.contains(c))
		{	
			//System.out.println("���� - "+p + " : " +sudoku[p.x][p.y].value );
				//���п��Գ��Ե�ֵ
				if(sudoku[c.x][c.y].valideValueArray.size()>0)
				{
					sudoku[c.x][c.y].value = sudoku[c.x][c.y].valideValueArray.remove(0);
					//System.out.println("[�޸�] "+p + " : " +sudoku[p.x][p.y].value );
				}
				//û�п��Գ��Ե�ֵ�ˣ����������
				else
				{
					sudoku[c.x][c.y].valideValueArray.clear();
					sudoku[c.x][c.y].value = 0;

					//������ǰ�����
					histroy.pop();
					if(!histroy.isEmpty())
					{
						//����ǰһ�������ٴγ���
						return tryWithDFS(histroy.peek());
					}
					else
					{
						//�����޽�
						//System.out.println("�����޽�");
						return false;
					}
				}
		}
		//������һ���µ������
		else
		{
			histroy.push(c);
			//System.out.println("���������� : "+p);
			ArrayList<Integer> array = getValiedCellValue(c);
			
			if(array.size()>0)
			{
				sudoku[c.x][c.y].valideValueArray.addAll(array);
				
				//���п��Գ��Ե�ֵ
				//(sudoku[p.x][p.y].valideValueArray.size()>0)
				sudoku[c.x][c.y].value = sudoku[c.x][c.y].valideValueArray.remove(0);
				
			}
			//û�п��Գ��Ե�ֵ�ˣ������
			else
			{
				sudoku[c.x][c.y].valideValueArray.clear();
				sudoku[c.x][c.y].value = 0;
				
				//������ǰ�����
				//System.out.println("POP:"+histroy.pop() +" is in histroy ? "+histroy.contains(p));
				if(!histroy.isEmpty())
				{
					//����ǰһ�������ٴγ���
					Coord previous = histroy.peek();
					//System.out.println("previous:"+previous +" is in histroy ? "+histroy.contains(previous));
					return tryWithDFS(previous);
				}
				else
				{
					//�����޽�
					//System.out.println("�����޽�");
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
				
				//������ǰ�����
				histroy.pop();
				if(!histroy.isEmpty())
				{
					//����ǰһ�������ٴγ���
					Coord previous = histroy.peek();
					return  tryWithDFS(previous);
				}
				else
				{
					//�����޽�
					return false;
				}
			}
		}
		else
		{
			//System.out.println("******���ɹ�*******");
			return true;
		}
	}
	
	/**
	 * ѡ��ʹ�����ַ�������������ȹ�����У�������Ƿ���ɹ�
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
	 * ͨ���������ó���Ӧ�����ĸ���ȷֵ�ķ����ش������<br>
	 * 
	 * ��һ�����е���Ŀ������ʹ���������һ�ν�����
	 * 
	 * ����÷���������ʱ���ܽ��ɹ�,�򷵻� false,���򷵻� true<br>
	 * <br>
	 * 
	 * �÷���ʧ�ܺ������ʹ��������ȵ��㷨����������
	 * �㷨��һ����������Ч�ʸ�
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
	 * ��֤�����Ƿ��Ѿ�����<br>
	 * 		����֤�����Ƿ����1-9<br>
	 * 		����֤�����Ƿ����1-9<br>
	 * 		����֤ÿ���Ź������Ƿ����1-9<br>
	 * @return
	 * 		��һ����֤ʧ���򷵻� false,
	 * 		���򷵻� true
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
	 * �ҵ���һ��Ҫ�������
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
	 * ��鵱ǰ��Ԫ����������Чֵ
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
	
			//����еĽ�Ψһ
			if(counter==1)
			{
				sudoku[save_i][save_j].value=seed;
				sudoku[save_i][save_j].editable = false;
				sudoku[save_i][save_j].seedNotOkFlag = true;
				
				//System.out.print("����Ψһ�⣺["+(save_i+1)+","+(save_j+1)+"]="+seed);
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
			
			//����еĽ�Ψһ
			if(counter==1)
			{
				sudoku[save_i][save_j].value=seed;
				sudoku[save_i][save_j].editable = false;
				sudoku[save_i][save_j].seedNotOkFlag = true;
				
				//System.out.print("����Ψһ�⣺["+(save_i+1)+","+(save_j+1)+"]="+seed);
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
					//System.out.println("MARK "+i+"��"+j+"�� as "+markflag);
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
	 * ���row�У�column�У���[row,column]���ھŹ����ϵ����е�Ԫ���conflictFlag����
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
