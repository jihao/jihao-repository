package cn.heapstack.sudoku.gui.prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DemoNineCells {

	private static final String VERSION = "v1.0";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sudoku " + VERSION);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(5, 5);
		frame.setPreferredSize(new Dimension(400, 400));

		Random r = new Random();

		JPanel[] panel = new JPanel[9];
		for (int i = 0; i < 9; i++) {
			panel[i] = new JPanel();
			panel[i].setBackground(new Color(r.nextInt(255), r.nextInt(255), r
					.nextInt(255)));
		}

		JPanel content = (JPanel) frame.getContentPane();
		content.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			content.add(panel[i]);
		}

		frame.pack();
		frame.setVisible(true);

	}

}
