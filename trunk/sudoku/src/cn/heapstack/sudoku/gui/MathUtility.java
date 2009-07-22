package cn.heapstack.sudoku.gui;

public class MathUtility {

	/**
	 * @param seconds
	 * @return example: 0:23:15
	 */
	public static String getTimeString(int seconds) {
		int s = seconds%60;
		int m = (seconds/60)%60;
		int h = (seconds/3600)%60;
		String cost = "";
		if(h<10)
		{
			cost = cost.concat("0");
		}
		cost = cost.concat(String.valueOf(h));
		cost = cost.concat(":");
		if(m<10)
		{
			cost = cost.concat("0");
		}
		cost = cost.concat(String.valueOf(m));
		cost = cost.concat(":");
		if(s<10)
		{
			cost = cost.concat("0");
		}
		cost = cost.concat(String.valueOf(s));
		return cost;
	}
}
