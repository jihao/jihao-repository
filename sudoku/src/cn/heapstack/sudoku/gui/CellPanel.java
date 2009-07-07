package cn.heapstack.sudoku.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class CellPanel extends JPanel {

	private static final long serialVersionUID = 7869722632969709398L;
	private static final String promoteString = "_";
	public static final Color notEditableBgColor = new Color(228,219,255);
	public static final Color editableBgColor = new Color(249,255,229);
	
	private JTextPane textPane = new JTextPane();
	private SimpleAttributeSet styleAttributes = new SimpleAttributeSet();
	private StyledDocument docOutput = new RegexStyledDocument("^[1-9]|\\" + promoteString + "$", styleAttributes);

	
	public ObservableCell cellInfo;
	
	private boolean drawHorizontal = false;
	private boolean drawVertical = false;
	private boolean drawX = false;
	
	public CellPanel(int value) {
		initStyles();
		initTextPane(value);
		initListeners();
	}

	public CellPanel(ObservableCell cell) {
		this(cell.value);
		this.cellInfo = cell;
	}

	
	
	public void paint(Graphics g) {
		super.paint(g);
		Dimension size = this.getSize();
	    
		if(drawHorizontal)
		{
			g.setColor(Color.magenta);
			g.drawLine(0, size.height/2-1, size.width, size.height / 2-1);   
			g.drawLine(0, size.height/2, size.width, size.height / 2);   
			g.drawLine(0, size.height/2 +1, size.width, size.height / 2+1);
		}
		if(drawVertical)
		{
			g.setColor(Color.orange);
			g.drawLine( size.width / 2-1, 0, size.width / 2-1, size.height);
			g.drawLine( size.width / 2, 0, size.width / 2, size.height);
			g.drawLine( size.width / 2+1, 0, size.width / 2+1, size.height);
		}
		if(drawX)
		{
			g.setColor(Color.pink);
			g.drawLine(0, 0, size.width, size.height);
			g.drawLine(size.width, 0, 0, size.height);
			
			g.drawLine(0, 1, size.width-2, size.height);
			g.drawLine(size.width-1, 0, 0, size.height-1);
			
			g.drawLine(1, 0, size.width, size.height-2);
			g.drawLine(size.width, 1, 1, size.height);
		}
	}

/*	*//**
	 * 注意这里重载paintComponent方法,
	 * 如果重载paint方法,clild 组件不能显示
	 *//*
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
	}*/
	
	public void clearAssitLines()
	{
		drawHorizontal = false;
		drawVertical = false;
		drawX = false;
	}
	
	private void initStyles() {
		StyleConstants.setFontSize(styleAttributes, 30);
		StyleConstants.setBold(styleAttributes, true);
		StyleConstants.setForeground(styleAttributes, Color.black);

		textPane.setCharacterAttributes(styleAttributes, true);
		textPane.setStyledDocument(docOutput);
		textPane.setDisabledTextColor(Color.black);
		this.add(textPane);
		textPane.setBackground(notEditableBgColor);
	}
	
	private void initTextPane(int value)
	{
		textPane.setSize(this.getWidth(), this.getHeight());
		try {
			if(value==0)
			{
				docOutput.insertString(docOutput.getLength(),String.valueOf('_'), styleAttributes);
				
				textPane.setEditable(true);
				textPane.setBackground(editableBgColor);
				this.setBackground(editableBgColor);
			}
			else
			{
				docOutput.insertString(docOutput.getLength(),String.valueOf(value), styleAttributes);
				textPane.setEditable(false);
				textPane.setBackground(notEditableBgColor);
				this.setBackground(notEditableBgColor);
			}
			textPane.repaint();
			this.repaint();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Called by regenerate soduku
	 */
	public void refreshValue()
	{
		try {
			if(this.cellInfo.value==0)
			{
				docOutput.insertString(docOutput.getLength(),String.valueOf('_'), styleAttributes);
				
				textPane.setEditable(true);
				textPane.setBackground(editableBgColor);
				this.setBackground(editableBgColor);
			}
			else
			{
				docOutput.insertString(docOutput.getLength(),String.valueOf(this.cellInfo.value), styleAttributes);
				textPane.setEditable(false);
				textPane.setBackground(notEditableBgColor);
				this.setBackground(notEditableBgColor);
			}
			textPane.repaint();
			this.repaint();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * called by Auto solve method 
	 */
	public void updateValue()
	{
		try {
			docOutput.insertString(docOutput.getLength(),String.valueOf(this.cellInfo.value), styleAttributes);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textPane.repaint();
	}
	
	private void initListeners()
	{
		docOutput.addDocumentListener(new MyDocumentListener());
		textPane.addKeyListener(new MyKeyListener());

		MouseListener mouseListener = new MyMouseListener();
		addMouseListener(mouseListener);
		textPane.addMouseListener(mouseListener);
	}
	
	public void doHighLight(Color c)
	{
		textPane.setBackground(c);
		this.setBackground(c);
	}
	
	private void notifyCellObservers() {
		cellInfo.setChanged();
		cellInfo.notifyObservers();
	}
	
	public boolean isDrawHorizontal() {
		return drawHorizontal;
	}

	public void setDrawHorizontal(boolean drawHorizontal) {
		this.drawHorizontal = drawHorizontal;
	}

	public boolean isDrawVertical() {
		return drawVertical;
	}

	public void setDrawVertical(boolean drawVertical) {
		this.drawVertical = drawVertical;
	}

	public boolean isDrawX() {
		return drawX;
	}

	public void setDrawX(boolean drawX) {
		this.drawX = drawX;
	}

	
	
	class MyDocumentListener implements DocumentListener {
		
		public void changedUpdate(DocumentEvent e) {
			//System.out.println("[Changed:]" + e.getType().toString() + e.getOffset());
			//notifyCellObservers();
			textPane.repaint();
		}

		
		public void insertUpdate(DocumentEvent e) {
			//System.out.println("[Insert:]" + e.getType().toString()	+ e.getOffset());
			try {
				String current = textPane.getText(0, 1);
				int value = 0;
				if(current.compareTo(promoteString)!=0)
				{
					value = Integer.parseInt(current);
				}
				cellInfo.value = value;
				notifyCellObservers();
				
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
			textPane.repaint();
		}

		
		public void removeUpdate(DocumentEvent e) {
			//System.out.println("[Remove:]" + e.getType().toString()	+ e.getOffset());
			textPane.repaint();
		}
	}

	class MyMouseListener implements MouseListener {
		
		public void mouseClicked(MouseEvent e) {
			// e.getComponent().setBackground(Color.white);
			// textPane.setBackground(Color.white);
			// textPane.select(0, 1);
			if(textPane.isEditable())
			{
				textPane.requestFocusInWindow();
			}
		}

		
		public void mouseEntered(MouseEvent e) {
		}

		
		public void mouseExited(MouseEvent e) {
		}

		
		public void mousePressed(MouseEvent e) {
		}

		
		public void mouseReleased(MouseEvent e) {
		}
	}

	class MyKeyListener implements KeyListener {
		
		public void keyPressed(KeyEvent e) {
			if(textPane.isEditable())
			{
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE
						|| e.getKeyCode() == KeyEvent.VK_DELETE
						|| e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_TAB) 
				{
					e.consume();
	
					try {
						String current = docOutput.getText(0, 1);
						if (!current.equals("")) {
							int nextValue;
							if (current.compareTo(promoteString) == 0) {
								nextValue = 0;
							} else {
								nextValue = Integer.parseInt(current);
							}
	
							if (nextValue == 0) {
								nextValue = 9;
							} else {
								nextValue = nextValue - 1;
							}
							if (nextValue == 0) {
								docOutput.insertString(0, promoteString,
										styleAttributes);
							} else {
								docOutput.insertString(0,
										String.valueOf(nextValue), styleAttributes);
							}
							cellInfo.value = nextValue;
							notifyCellObservers();
						}
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					
				} else if (e.getKeyCode() == KeyEvent.VK_INSERT
						|| e.getKeyCode() == KeyEvent.VK_SPACE
						|| e.getKeyCode() == KeyEvent.VK_UP) 
				{
					e.consume();
					try {
						String current = docOutput.getText(0, 1);
						if (!current.equals("")) {
							int nextValue;
							if (current.compareTo(promoteString) == 0) {
								nextValue = 0;
							} else {
								nextValue = Integer.parseInt(current);
							}
	
							if (nextValue == 9) {
								nextValue = 0;
							} else {
								nextValue = nextValue + 1;
							}
							if (nextValue == 0) {
								docOutput.insertString(0, promoteString,
										styleAttributes);
							} else {
								docOutput.insertString(0,
										String.valueOf(nextValue), styleAttributes);
							}
							
							cellInfo.value = nextValue;
							notifyCellObservers();
						}
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		}



		
		public void keyReleased(KeyEvent e) {
		}

		
		public void keyTyped(KeyEvent e) {
		}

	}


}
