package cn.heapstack.sudoku.gui.prototype;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import cn.heapstack.sudoku.gui.CellPanel;

public class DemoCellPanel {

	private static final String VERSION = "v1.0";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sudoku " + VERSION);
		frame.setLocation(5, 5);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CellPanel cellPanel = new CellPanel(0);
		cellPanel.setDrawX(true);

		CellPanel cellPanel2 = new CellPanel(1);
		cellPanel2.setDrawHorizontal(true);

		CellPanel cellPanel3 = new CellPanel(2);
		cellPanel3.setDrawVertical(true);

		frame.getContentPane().setLayout(new GridLayout(1, 3));

		frame.getContentPane().add(cellPanel);
		frame.getContentPane().add(cellPanel3);
		frame.getContentPane().add(cellPanel2);
		frame.pack();

		cellPanel2.setVisible(true);
		cellPanel3.setVisible(true);

		frame.setVisible(true);

	}

}
