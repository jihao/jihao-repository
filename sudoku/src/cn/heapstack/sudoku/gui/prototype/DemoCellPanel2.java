package cn.heapstack.sudoku.gui.prototype;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.heapstack.gui.util.LookAndFeelManager;
import cn.heapstack.sudoku.gui.CellPanel;
import cn.heapstack.sudoku.gui.ObservableCell;

public class DemoCellPanel2 extends JFrame{

	private static final long serialVersionUID = -2750784386030989723L;
	private static final String VERSION = "v1.0";
	
	public DemoCellPanel2(String string) {
		super(string);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DemoCellPanel2 frame = new DemoCellPanel2("Sudoku " + VERSION);
		frame.setLocation(5, 5);
		frame.setPreferredSize(new Dimension(200, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setJMenuBar(frame.buildMenuBar());
		
		CellPanel cellPanel = new CellPanel(new ObservableCell(0));
		cellPanel.setDrawX(true);

		CellPanel cellPanel2 = new CellPanel(new ObservableCell(1));
		cellPanel2.setDrawHorizontal(true);

		CellPanel cellPanel3 = new CellPanel(new ObservableCell(2));
		cellPanel3.setDrawVertical(true);

		frame.getContentPane().setLayout(new GridLayout(3, 3));

		frame.getContentPane().add(cellPanel);
		frame.getContentPane().add(cellPanel3);
		frame.getContentPane().add(cellPanel2);
		
		
		JTextPane textPanel1 = new JTextPane();
		JTextPane textPanel2 = new JTextPane();
		JTextPane textPanel3 = new JTextPane();
		
		frame.getContentPane().add(textPanel1);
		frame.getContentPane().add(textPanel2);
		frame.getContentPane().add(textPanel3);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		panel1.setBackground(Color.magenta);
		panel2.setBackground(Color.pink);
		panel3.setBackground(Color.yellow);
		
		frame.getContentPane().add(panel1);
		frame.getContentPane().add(panel2);
		frame.getContentPane().add(panel3);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private JMenuBar buildMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Look & Feel");
		menuBar.add(menu);
		
		ButtonGroup lafMenuGroup = new ButtonGroup();
		
		HashMap<String, String> lafMap = LookAndFeelManager.getLookAndFeelMap();
		Iterator<String> itr = lafMap.keySet().iterator();
		while(itr.hasNext())
		{
			String key = itr.next();
			JMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(key));
			lafMenuGroup.add(mi);
			mi.addActionListener(new ChangeLookAndFeelAction(this,lafMap.get(key)));
			mi.setEnabled(LookAndFeelManager.isAvailableLookAndFeel(lafMap.get(key)));
			 
		}
		
		return menuBar;
	}

	class ChangeLookAndFeelAction extends AbstractAction {
		private static final long serialVersionUID = 74351688296592622L;
		private DemoCellPanel2 gui;
        private String laf;

        protected ChangeLookAndFeelAction(DemoCellPanel2 gui, String laf) {
            super("ChangeTheme");
            this.gui = gui;
            this.laf = laf;
        }

        public void actionPerformed(ActionEvent e) {
            gui.setLookAndFeel(laf);
        }
    }

	
	    public void setLookAndFeel(String laf) {
	    	try {
				UIManager.setLookAndFeel(laf);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			
			SwingUtilities.updateComponentTreeUI(this);
		}

}
