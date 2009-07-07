package cn.heapstack.sudoku.gui;

import java.util.ArrayList;
import java.util.Observable;

import cn.heapstack.sudoku.Coord;

public class ObservableCell extends Observable{
	
	public int value = 0;
	public boolean editable = false;//Ĭ��ֵ�õ�Ԫ���ǲ��ɱ༭��
	public boolean seedNotOkFlag = false;//�����־�������ִ˵�Ԫ���ܲ�������Ŀ��ֵ(seed)
	public Coord coord = new Coord();
	public ArrayList<Integer> valideValueArray = new ArrayList<Integer>();

	/**
	 * ���캯��
	 * �����Ԫ���ֵ��ʼ����ʱ��Ϊ����ֵ��
	 * 		��ʾ�����Ԫ���ǲ��ɱ༭��,
	 * 		�ҵ�ǰ���Ե�seed�ǲ������������Ԫ���
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
