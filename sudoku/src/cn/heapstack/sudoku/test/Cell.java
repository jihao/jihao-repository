package cn.heapstack.sudoku.test;

import java.awt.Point;
import java.util.ArrayList;

public class Cell {
	public Point coordinate;
	public ArrayList<Integer> availableValueArray = new ArrayList<Integer>();
	public int value;
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

	public ArrayList<Integer> getAvailableValueArray() {
		return availableValueArray;
	}

	public void setAvailableValueArray(ArrayList<Integer> availableValueArray) {
		this.availableValueArray = availableValueArray;
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