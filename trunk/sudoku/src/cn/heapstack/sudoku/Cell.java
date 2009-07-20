package cn.heapstack.sudoku;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Cell extends Observable implements Serializable{
	private static final long serialVersionUID = -1509623841348906493L;
	
	public Point coordinate;
	public ArrayList<Integer> availableValueArray = new ArrayList<Integer>();
	private int value;
	private boolean editable = true;

	public Cell(Point coordinate, int value) {
		super();
		this.coordinate = coordinate;
		this.value = value;
		if(this.value!=0)
		{
			this.editable = false;
		}
	}

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
		if(this.value != value)
		{
			this.value = value;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean isEditable() {
		return editable;
	}

	@Override
	public String toString() {
		return "Cell [coordinate=" + coordinate + ", editable=" + editable
				+ ", value=" + value + "]";
	}

	public void resetValue(int i) {
		this.editable = false;
		this.value = i;
		if(value==0)
		{
			this.editable = true;
		}
		this.availableValueArray.clear();
	}
}