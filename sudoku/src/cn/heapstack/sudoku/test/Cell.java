package cn.heapstack.sudoku.test;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class Cell {


	public Point coordinate;
	public Queue<Integer> availableValueQueue = new LinkedList<Integer>();
	int value;
	public boolean editable = true;

	public Cell(Point coordinate) {
		super();
		this.coordinate = coordinate;
	}
	
	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}

	public Queue<Integer> getAvailableValueQueue() {
		return availableValueQueue;
	}

	public void setAvailableValueQueue(Queue<Integer> availableValueQueue) {
		this.availableValueQueue = availableValueQueue;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return "Cell [coordinate=" + coordinate + ", editable=" + editable
				+ ", value=" + value + "]";
	}
}