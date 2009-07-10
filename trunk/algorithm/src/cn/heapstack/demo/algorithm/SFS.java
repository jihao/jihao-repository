package cn.heapstack.demo.algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


class Cell
{
	Point coordinate;
	Queue<Integer> availableValueQueue = new LinkedList<Integer>();
	int value = 0;
	boolean editable = false;
	

	public Cell(Point coordinate) {
		super();
		this.coordinate = coordinate;
	}

	public Cell(Point coordinate, int value) {
		super();
		this.coordinate = coordinate;
		this.value = value;
	}

	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}

	
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void clear()
	{
		this.value = 0;
	}
	
	public boolean hasNextValidValue()
	{
		return !availableValueQueue.isEmpty();
	}
	
	public int getNextValidValue()
	{
		return availableValueQueue.poll();
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
}
public class SFS {

	static Queue<Cell> queue = new LinkedList<Cell>();
	static Cell[][] matrix = new Cell[9][9];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		runSFS();

	}
	
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
		 //System.out.println("OK values:"+set);
		 return set;
	}
	
	public static void runSFS()
	{
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				matrix[i][j] = new Cell( new Point(i, j) );
			}
		}
		
		Cell current = matrix[0][0];
		queue.offer(current);
		while(!queue.isEmpty())
		{
			current = queue.poll();
			current.availableValueQueue.addAll(getValidCellValue(current,matrix));
			
			System.out.println(current.getCoordinate().x+""+current.getCoordinate().y);
			//fill valid value for this cell
			
			if(current.isEditable())
			{
				if(!current.hasNextValidValue())
				{
					current.clear();
					return;
				}
				else
				{
					current.setValue(current.getNextValidValue());
					//rebuild matrix valid value
				}
			}
			
			if(current.getCoordinate().y+1<9)
			{
				Cell left = matrix[current.getCoordinate().x][current.getCoordinate().y+1];
				if(!queue.contains(left))
					queue.offer(left);
			}
			
			if(current.getCoordinate().x+1<9)
			{
				Cell right = matrix[current.getCoordinate().x+1][current.getCoordinate().y];
				if(!queue.contains(right))
					queue.offer(right);
			}
		}
	}
}
