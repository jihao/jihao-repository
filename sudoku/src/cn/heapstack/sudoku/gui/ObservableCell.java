package cn.heapstack.sudoku.gui;

import java.util.ArrayList;
import java.util.Observable;

import cn.heapstack.sudoku.Coord;

public class ObservableCell extends Observable{
	
	public int value = 0;
	public boolean editable = false;//默认值该单元格是不可编辑的
	public boolean seedNotOkFlag = false;//这个标志用来区分此单元格能不能填入目标值(seed)
	public Coord coord = new Coord();
	public ArrayList<Integer> valideValueArray = new ArrayList<Integer>();

	/**
	 * 构造函数
	 * 如果单元格的值初始化的时候为非零值，
	 * 		表示这个单元格是不可编辑的,
	 * 		且当前尝试的seed是不能填入这个单元格的
	 * @param value
	 */
	public ObservableCell(int value)
	{
		this.value = value;
		if(value==0)
		{
			this.editable = true;
		}
		else
		{
			this.seedNotOkFlag = true;
		}
	}
	
	public void resetValue(int value)
	{
		this.editable = false;
		this.seedNotOkFlag = false;
		this.value = value;
		if(value==0)
		{
			this.editable = true;
		}
		else
		{
			this.seedNotOkFlag = true;
		}
	}
	
	public ObservableCell(ObservableCell copy)
	{
		copyFrom(copy);
	}

	public final void copyFrom(ObservableCell from)
	{
		this.value = from.value;
		this.editable = from.editable;
		this.seedNotOkFlag = from.seedNotOkFlag;
		
		this.coord.x = from.coord.x;
		this.coord.y = from.coord.y;
		this.valideValueArray.clear();
		this.valideValueArray.addAll(from.valideValueArray);
	}
	
	public void setChanged()
	{
		super.setChanged();
	}
}
