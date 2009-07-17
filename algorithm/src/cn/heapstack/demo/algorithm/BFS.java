package cn.heapstack.demo.algorithm;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;


public class BFS {

	static Queue<Cell> queue = new LinkedList<Cell>();
	static Cell[][] matrix = new Cell[9][9];
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		runBFS();

	}
	
	
	
	public static void runBFS()
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
			
			System.out.println(current.getCoordinate().x+""+current.getCoordinate().y);			

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
