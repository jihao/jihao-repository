package cn.heapstack.sudoku.gui.prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DemoDrawlines {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Demo paint on panel ");
		frame.setLocation(5, 5);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyPanel p1 = new MyPanel();
		p1.setBackground(Color.white);
		p1.drawVertical = true;

		MyPanel p2 = new MyPanel();
		p2.setBackground(Color.LIGHT_GRAY);
		p2.drawHorizontal = true;

		frame.getContentPane().setLayout(new GridLayout(1, 2));
		frame.getContentPane().add(p1);
		frame.getContentPane().add(p2);
		frame.pack();
		frame.setVisible(true);
	}

}

class MyPanel extends JPanel {
	private static final long serialVersionUID = 4027957764459633468L;
	public boolean drawHorizontal = false;
	public boolean drawVertical = false;

	/*
	 * @Override public void paint(Graphics g) { super.paint(g);
	 * 
	 * Point p = this.getLocation(); Dimension size = this.getSize();
	 * 
	 * System.out.println(p+":"+size);
	 * 
	 * if (draw_horizontal) { g.setColor(Color.red);
	 * 
	 * g.drawLine(0, size.height / 2, size.width, size.height / 2); }
	 * 
	 * if (draw_vertical) { g.setColor(Color.blue);
	 * 
	 * g.drawLine( size.width / 2, 0, size.width / 2, size.height); } }
	 */

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension size = this.getSize();

		if (drawHorizontal) {
			g.setColor(Color.red);

			g.drawLine(0, size.height / 2, size.width, size.height / 2);
		}

		if (drawVertical) {
			g.setColor(Color.blue);

			g.drawLine(size.width / 2, 0, size.width / 2, size.height);
		}

	}
}
