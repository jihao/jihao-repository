package cn.heapstack.sudoku.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

public class TestSFS {

	static Point[][] matrix = new Point[9][9];
	static Vector<Point> trace = new Vector<Point>();
	public static void main(String args[])
	{
		
		for(int i =0;i<9;i++)
		{
			for(int j =0;j<9;j++)
			{
				matrix[i][j] = new Point(i, j);
			}
		}
		
		Point p = matrix[0][0];
		SFS(p);
	}
	
	static void SFS(Point p)
	{
		if(trace.contains(p))
			return;
		trace.add(p);
		System.out.println(p);
		Point left = left(p);
		
		Point right = right(p);
		
		try
		{
			SFS(left);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			
		}
		try
		{
			SFS(right);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	static Point left(Point p)
	{
		return matrix[p.x][p.y+1];
	}
	
	static Point right(Point p)
	{
		return matrix[p.x+1][p.y];
	}
}
