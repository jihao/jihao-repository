package cn.heapstack.sudoku;

import java.util.ArrayList;

public class Cell {
	
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
	public Cell(int value)
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
	
	public Cell(Cell copy)
	{
		copyFrom(copy);
	}
	
	public String toString()
	{
		return this.value+" - "+this.editable+" - "+this.seedNotOkFlag+" | ";
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			System.out.println("RETUEN");
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		if (editable != other.editable)
			return false;
		if (seedNotOkFlag != other.seedNotOkFlag)
			return false;
		if (value != other.value)
			return false;
		if(valideValueArray.size()!=other.valideValueArray.size())
			return false;
		if(!valideValueArray.containsAll(other.valideValueArray))
			return false;
		return true;
	}

	public final void copyFrom(Cell from)
	{
		this.value = from.value;
		this.editable = from.editable;
		this.seedNotOkFlag = from.seedNotOkFlag;
		
		this.coord.x = from.coord.x;
		this.coord.y = from.coord.y;
		this.valideValueArray.clear();
		this.valideValueArray.addAll(from.valideValueArray);
	}
}
