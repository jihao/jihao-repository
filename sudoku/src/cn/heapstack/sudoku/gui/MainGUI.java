package cn.heapstack.sudoku.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cn.heapstack.gui.util.LookAndFeelManager;
import cn.heapstack.sudoku.Cell;
import cn.heapstack.sudoku.Generator;
import cn.heapstack.sudoku.SudokuCalculator;
import cn.heapstack.sudoku.SudokuMatrixUtility;


public class MainGUI extends JFrame implements Observer{

	private static final long serialVersionUID = 3442781640025318902L;

	private static final String VERSION = "v2.0";
	
	private int bigPanelRow = 0;
	private int bigPanelColumn = 0;
	private int smallPanelRow = 0;
	private int smallPanelColumn = 0;
	
	/**
	 * The sudoku problem initiate matrix
	 */
	private Cell[][] sudoku;
	
	/**
	 * 9 big panels, each contains 9 small CellPanels
	 */
	private JPanel[] bigPanels;
	
	/**
	 * The GUI panel matrix represent the sodoku Matrix
	 */
	private CellPanel[][] cellPanelMatrix;
	
	private Timer t ;
	private int timeCounter = 0;
	
	
	private JTextField textFiledDifficulty = new JTextField("5",3);
	private JCheckBox cb = new JCheckBox("Enable error hits");
	private JCheckBox cbDrawLineHelp = new JCheckBox("Enable help system");
	private JLabel levelLabel = new JLabel("1-1");
	/**
	 * Used for draw help line when cell value changed
	 */
	private int currentHelpNumber = -1;
	
	
	//**************************************************
	//
	//**************************************************
	private HashMap<String , Question> sudokuBankMap = new HashMap<String, Question>();
	public HashMap<String, Question> getSudokuBankMap() {
		return sudokuBankMap;
	}
	
	private MainGUI currentMainGUInstance;
	private SudokuBankDialog sudokuBank;


	public MainGUI(String string) {
		super(string);
		this.currentMainGUInstance = this;
		
		init_initSudokuBankMap();
		showMainGUI();
		init_sudokuBankDialog();
	}
	
	private void showMainGUI() {
		Question q = SudokuBankFileUtility.getInstance().findFirstUnSolvedQuestion(this.sudokuBankMap);
		int[][] matrix;
		if (q==null)
		{
			matrix = Generator.generateSudokuMatirx(10);
			
		}
		else
		{
			matrix = SudokuMatrixUtility.buildMatrix(q.getInitProblem());;
			this.levelLabel.setText(q.getLevel()+"-"+q.getSubLevel());
		}
		this.sudoku = this.generateSudokuCellMatrix(matrix);
		
		this.bigPanels = new JPanel[9];
		this.cellPanelMatrix = new CellPanel[9][9];
		
		//frame.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(5, 5);
		this.setPreferredSize(new Dimension(780,600));
		
		JMenuBar menuBar = this.buildMenuBar();
		this.setJMenuBar(menuBar);
       
		JPanel sudokuPanel = this.buildSodokuPanel();
		sudokuPanel.setPreferredSize(new Dimension(600,600));
		
		ConfigPanel configPanel = this.buildConfigPanel();
		configPanel.setPreferredSize(new Dimension(180,600));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.X_AXIS));
		contentPanel.add(sudokuPanel);
		contentPanel.add(configPanel);
		
		this.getContentPane().add(contentPanel);
		this.pack();		
	}

	private void init_sudokuBankDialog() {
		sudokuBank = new SudokuBankDialog(currentMainGUInstance);
        sudokuBank.setBounds(this.getLocation().x, this.getLocation().y, 780, 600);
        sudokuBank.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}

	private void init_initSudokuBankMap() {
		SudokuBankFileUtility instance = SudokuBankFileUtility.getInstance();
		instance.initSudokuBank(sudokuBankMap);		
	}

	/**
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		
		LookAndFeelManager.setSystemLookAndFeel();
		//LookAndFeelManager.setMotifLookAndFeel();
		//LookAndFeelManager.setNapkinLookAndFeel();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() {
				MainGUI frame = new MainGUI("Sudoku "+ VERSION);
				frame.setVisible(true);
			}
		});
		
	}
	
	private class ChangeLookAndFeelAction extends AbstractAction {
		private static final long serialVersionUID = 74351688296592622L;
		private MainGUI gui;
        private String laf;

        protected ChangeLookAndFeelAction(MainGUI gui, String laf) {
            super("ChangeTheme");
            this.gui = gui;
            this.laf = laf;
        }

        public void actionPerformed(ActionEvent e) {
            gui.setLookAndFeel(laf);
        }
    }
	
	private class AboutAction implements ActionListener {
	
		public void actionPerformed(ActionEvent e) {
			showAbout();
		}
	
	}
	
	private class FileAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sudokuBank.setVisible(true);
		}	
	}

    /**
     * Shows the about information of this application
     */
    private void showAbout()
    {
        JOptionPane.showMessageDialog(this, 
        		"A small sudoku game application.\r\n\r\n"
                + "Version "+ "v1.0" + " Made on July 2008 by Ji Hao.\r\n"
                + "Version "+ MainGUI.VERSION + " Updated on July 2009 by Ji Hao.\r\n"
                + "Contact me: jacky.jihao@gmail.com\r\n"
                + "\r\n"
                + "\r\n"
                + "If you need the source code and the update info of this small application\r\n" 
                + "Please visit: http://code.google.com/p/jihao-repository/",
                "About sudoku game", 
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setLookAndFeel(String laf) {
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

	/*    *//** Creates a JRadioButtonMenuItem for the Look and Feel menu *//*
	public JMenuItem createLafMenuItem(JMenu menu, String label,
	        String mnemonic,
	        String accessibleDescription, String laf) {
	    JMenuItem mi = (JRadioButtonMenuItem) menu
	            .add(new JRadioButtonMenuItem(getString(label)));
	    lafMenuGroup.add(mi);
	    mi.setMnemonic(getMnemonic(mnemonic));
	    mi.getAccessibleContext()
	            .setAccessibleDescription(getString(accessibleDescription));
	    mi.addActionListener(new ChangeLookAndFeelAction(this, laf));
	
	    mi.setEnabled(isAvailableLookAndFeel(laf));
	
	    return mi;
	}*/
	
    private ConfigPanel buildConfigPanel() {
		return new ConfigPanel(this);
	}
    
	private JMenuBar buildMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenuItem mi = null ;
		
		JMenu filemenu = new JMenu("File");
		mi = new JMenuItem("Open Question Bank");
		filemenu.add(mi);
		mi.addActionListener(new FileAction());
		
		JMenu lafmenu = new JMenu("Look & Feel");
		
		ButtonGroup lafMenuGroup = new ButtonGroup();
		HashMap<String, String> lafMap = LookAndFeelManager.getLookAndFeelMap();
		Iterator<String> itr = lafMap.keySet().iterator();
		
		while(itr.hasNext())
		{
			String key = itr.next();
			mi = (JRadioButtonMenuItem) lafmenu.add(new JRadioButtonMenuItem(key));
			lafMenuGroup.add(mi);
			mi.addActionListener(new ChangeLookAndFeelAction(this,lafMap.get(key)));
			mi.setEnabled(LookAndFeelManager.isAvailableLookAndFeel(lafMap.get(key)));	 
		}
		if(mi != null)
			mi.setSelected(true);
		
		JMenu aboutMenu = new JMenu("Help");
		mi = new JMenuItem("About");
		mi.addActionListener(new AboutAction());
		aboutMenu.add(mi);
		
		menuBar.add(filemenu);
		menuBar.add(lafmenu);
		menuBar.add(aboutMenu);
		
		return menuBar;
	}

	/**
	 * Build 9 big panels, add 9 small cells to each big panel
	 * @return
	 * 		The sodoku panel
	 */
	private JPanel buildSodokuPanel()
	{
		JPanel panel = new JPanel();
		ImageBorder image_border = ImageBorder.generateDefaultImageBorder();
		panel.setBorder(image_border);
		panel.setOpaque(false);
		
		MoveMouseListener listener = new MoveMouseListener(panel);
		panel.addMouseListener(listener);
		panel.addMouseMotionListener(listener);
		
		for(int i=0;i<9;i++)
		{
			bigPanelRow = i/3;
			bigPanelColumn = i%3;
			
			bigPanels[i] = new JPanel();
			
			addCellPanelsToPanel(bigPanels[i]);
			
			if(i%2!=0)
			{
				//bigPanels[i].setBackground(Color.pink);
				bigPanels[i].setBorder(BorderFactory.createLineBorder(Color.green));
			}
			else
			{
				bigPanels[i].setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
			}
		}
		
		panel.setLayout(new GridLayout(3,3));
		for(int i=0;i<9;i++)
		{
			panel.add(bigPanels[i]);
		}
		
		return panel;
	}
	

	/**
	 * Add 9 small CellPanel to the panel;<br>
	 * init each CellPanel by sudoku[i][j];<br>
	 * init the cellPanelMatrix[i][j] by new CellPanel; 
	 * 
	 * @param panel
	 * 		The destination panel
	 * 
	 * @see #sudoku
	 * @see #cellPanelMatrix
	 */
	private void addCellPanelsToPanel(JPanel panel)
	{
		panel.setLayout(new GridLayout(3,3));
		CellPanel[] cells = new CellPanel[9];
		for(int i=0;i<9;i++)
		{
			smallPanelRow = i/3;
			smallPanelColumn = i%3;
			
			int x = bigPanelRow*3+smallPanelRow;
			int y = bigPanelColumn*3+smallPanelColumn;

			cells[i] = new CellPanel(sudoku[x][y]);
			cellPanelMatrix[x][y] = cells[i];
			
			cells[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			panel.add(cells[i]);	
		}
	}
	
	private Cell[][] generateSudokuCellMatrix(int[][] matrix)
	{
		Cell[][] cellMatrix = new Cell[9][9];
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				cellMatrix[i][j] = new Cell(new Point(i,j),matrix[i][j]);
				cellMatrix[i][j].coordinate.x = i;
				cellMatrix[i][j].coordinate.y = j;
				cellMatrix[i][j].addObserver(this);
			}
		}
		return cellMatrix;
	}

	public void update(Observable o, Object arg) {
		Cell source = (Cell)o;
		
		boolean isFinished = verifyIfSudokuFinished();
		if(!isFinished)
		{
			verifyInput(source);
			
			if(cbDrawLineHelp.isSelected())
			{
				if(currentHelpNumber!=-1)
					drawHelpLine(currentHelpNumber);
			}
		}
		else
		{
			t.stop();
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run() {
					if(timeCounter!=0)
					{
						int result = JOptionPane.showConfirmDialog(currentMainGUInstance, "Congratulation, you succeed !!!", "Congratulation", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE );
						if( result == JOptionPane.OK_OPTION )
						{
							System.out.println("[OK], cost:" + MathUtility.getTimeString(timeCounter));	
						}
						
						Question q = sudokuBankMap.get(levelLabel.getText());
						if(q.getCostSeconds()==0||q.getCostSeconds()>timeCounter)
						{
							q.setCostSeconds(timeCounter);
						}
						q.setSolved(true);
						SudokuBankFileUtility.getInstance().saveSudokuRecord(sudokuBankMap);
						
						updateSudokuBankDialog();
					}
			}

				
			});
		}
	}
	
	private void updateSudokuBankDialog() {
		init_sudokuBankDialog();
	}

	private void verifyInput(Cell source) {
		clearHighLight();
		if(cb.isSelected())
			verifyCoordByHighLight(source.coordinate);
	}
	
	private void clearHighLight() {
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(cellPanelMatrix[i][j].getBackground() == Color.yellow)
				{
					if(cellPanelMatrix[i][j].cellInfo.isEditable())
						cellPanelMatrix[i][j].doHighLight(CellPanel.editableBgColor);
					else
						cellPanelMatrix[i][j].doHighLight(CellPanel.notEditableBgColor);
				}
			}
		}
	}

	private void verifyCoordByHighLight(Point c) {
		
		int x = c.x;
		int y = c.y;
		//System.out.println(sudoku[x][y].value +"<->"+ cellPanelMatrix[x][y].cellInfo.value);
		for(int i=0;i<9;i++)
		{
			if(sudoku[x][i].getValue()!=0 &&
					sudoku[x][i].getValue() == cellPanelMatrix[x][y].cellInfo.getValue())
			{
				highLightCell(x, i);
			}
		}

		for(int i=0;i<9;i++)
		{
			if(sudoku[i][y].getValue()!=0 &&
					sudoku[i][y].getValue() == cellPanelMatrix[x][y].cellInfo.getValue())
			{
				highLightCell(i, y);
			}
		}
		
		int block_row = x/3;
		int block_colum = y/3;
		for(int i=block_row*3;i<(block_row+1)*3;i++)
		{
			for(int j=block_colum*3;j<(block_colum+1)*3;j++)
			{
				if (sudoku[i][j].getValue() != 0 &&
						sudoku[i][j].getValue() == cellPanelMatrix[x][y].cellInfo.getValue())
				{
					highLightCell(i, j);
				}
			}
		}
		
	}

	private void highLightCell(int x,int y)
	{
		cellPanelMatrix[x][y].doHighLight(Color.yellow);
	}
	
	private void drawHelpLine(int number)
	{
		clearAssistLines();
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if (cellPanelMatrix[i][j].cellInfo.getValue() == number)
				{
					drawHelpLine(i,j);
				}
			}
		}
	}
	
	private void clearUserFilledValue()
	{		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if (cellPanelMatrix[i][j].cellInfo.isEditable())
				{
					cellPanelMatrix[i][j].cellInfo.setValue(0);
					cellPanelMatrix[i][j].refreshValue();
				}
			}
		}
	}
	
	private void clearAssistLines()
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				cellPanelMatrix[i][j].clearAssitLines();
				cellPanelMatrix[i][j].repaint();
			}
		}
	}

	private void drawHelpLine(int row, int column) {
		for(int i=0;i<9;i++)
		{
			if(cellPanelMatrix[row][i].cellInfo.isEditable()
					&&cellPanelMatrix[row][i].cellInfo.getValue()==0)
			{
				cellPanelMatrix[row][i].setDrawHorizontal(true);
				cellPanelMatrix[row][i].repaint();
			}
		}
		for(int i=0;i<9;i++)
		{
			if(cellPanelMatrix[i][column].cellInfo.isEditable()
					&&cellPanelMatrix[i][column].cellInfo.getValue()==0)
			{
				cellPanelMatrix[i][column].setDrawVertical(true);
				cellPanelMatrix[i][column].repaint();
			}
		}
		int block_row = row/3;
		int block_colum = column/3;
		for(int i=block_row*3;i<(block_row+1)*3;i++)
		{
			for(int j=block_colum*3;j<(block_colum+1)*3;j++)
			{
				if(cellPanelMatrix[i][j].cellInfo.isEditable()
						&&cellPanelMatrix[i][j].cellInfo.getValue()==0)
				{
					cellPanelMatrix[i][j].setDrawX(true);
					cellPanelMatrix[i][j].repaint();
				}
			}
		}
		
	}

	public void reGenerateSudokuQuestion(int diffValue)
	{
		int[][] matrix = Generator.generateSudokuMatirx(diffValue);
		loadMatrix(matrix);
		
	}
	
	public void loadSudokuQuestion(int level, int subLevel)
	{
		timeCounter = 0;
		t.start();
		String levelString = level+"-"+subLevel;
		levelLabel.setText(levelString);
		Question q = sudokuBankMap.get(levelString);
		int[][] matrix = SudokuMatrixUtility.buildMatrix(q.getInitProblem());
		loadMatrix(matrix);
	}
	
	private void loadMatrix(int[][] matrix)
	{
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				sudoku[i][j].resetValue(matrix[i][j]);
				cellPanelMatrix[i][j].cellInfo = sudoku[i][j];
				cellPanelMatrix[i][j].refreshValue();
			}
		}
	}
	
	private boolean verifyIfSudokuFinished() {
		HashSet<Integer> set = new HashSet<Integer>();
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if (sudoku[i][j].getValue() != 0)
				{
					set.add(sudoku[i][j].getValue());
				}
			}
			if(set.size()!=9)
				return false;
			set.clear();
			
			for(int j=0;j<9;j++)
			{
				if (sudoku[j][i].getValue() != 0)
				{
					set.add(sudoku[j][i].getValue());
				}
			}
			if(set.size()!=9)
				return false;
			set.clear();
		}
		
		for(int block_row=0;block_row<3;block_row++)
		{
			for(int block_colum=0;block_colum<3;block_colum++)
			{
				for(int i=block_row*3;i<(block_row+1)*3;i++)
				{
					for(int j=block_colum*3;j<(block_colum+1)*3;j++)
					{
						if (sudoku[i][j].getValue() != 0)
						{
							set.add(sudoku[j][i].getValue());
						}
					}
				}
			}
		}
		if(set.size()!=9)
			return false;
		set.clear();
		
		return true;
	}
	

	
	class ConfigPanel extends JPanel implements ActionListener,Observer{

		private static final long serialVersionUID = 4100509515122280282L;
		
		private JPanel infoPanel = new JPanel();
		private JPanel timeCountingPanel = new JPanel();
		private JPanel assistantPanel = new JPanel();
		private JPanel miscPanel = new JPanel();

		private JButton btnReGenerate = new JButton("New game");
		private JButton btnAutoSolve = new JButton("Solution");
		private JButton btnClearUserFilledValue = new JButton("Reset");
		private JLabel label = new JLabel("Difficulty");
		
		private JToggleButton [] buttons = new JToggleButton[9];
		
		//private MainGUI mainGUI;

		public ConfigPanel(MainGUI mainGUI)
		{
			//this.mainGUI = mainGUI;
			this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			buildInfoPanel();
			buildAssistantPanel();
			buildMiscPanel();
			setSubPanelsSize();
		}
		
		private void buildInfoPanel()
		{
			JButton btnBank = new JButton("Question Bank");
			btnBank.addActionListener(new FileAction());
						
			final JLabel labelTime = new JLabel();
			labelTime.setForeground(Color.BLUE);
			t = new Timer(1000,new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					timeCounter++;
					String cost = MathUtility.getTimeString(timeCounter);
					labelTime.setText(cost);//Just for better UI
				}

				});
			
			t.setRepeats(true);
			t.start();
			
			infoPanel.setBorder(ImageBorder.generateDefaultImageBorder());
			infoPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2,2,2,2);
			c.gridx = 0;
			c.gridy = 0;
			infoPanel.add(new JLabel("Level: "),c);
			c.gridx = 1;
			c.gridy = 0;
			infoPanel.add(levelLabel,c);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			infoPanel.add(labelTime,c);
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;
			infoPanel.add(btnBank,c);
			

			
			this.add(infoPanel);
		}

		private void setSubPanelsSize() {
			Dimension preferredSize = new Dimension(180,180);
			this.assistantPanel.setPreferredSize(preferredSize);
			this.miscPanel.setPreferredSize(preferredSize);
			this.timeCountingPanel.setPreferredSize(preferredSize);
		}

		private void buildAssistantPanel()
		{
			JPanel subPanel = new JPanel(); 
			subPanel.setLayout(new GridLayout(3,3));
			subPanel.setPreferredSize(new Dimension(150,100));
			ButtonGroup g = new ButtonGroup();
			
			for(int i=1;i<10;i++)
			{
				String actionCommand = "help_draw_"+String.valueOf(i);
				
				JToggleButton b = new JToggleButton(String.valueOf(i));
				buttons[i-1] = b;
				//JButton b = new JButton(String.valueOf(i));
				b.setActionCommand(actionCommand);
				b.addActionListener(this);
				subPanel.add(b);
				
				g.add(b);
			}
			
			//assistantPanel.setBorder(BorderFactory.createTitledBorder("锟斤拷锟斤拷锟斤拷锟�));
			assistantPanel.setBorder(ImageBorder.generateDefaultImageBorder());
			assistantPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(2,2,2,2);
			c.gridx = 0;
			c.gridy = 0;
			cb.setSelected(true);
			assistantPanel.add(cb,c);
			c.gridx = 0;
			c.gridy = 1;
			assistantPanel.add(cbDrawLineHelp,c);
			c.gridx = 0;
			c.gridy = 2;
			assistantPanel.add(subPanel,c);
			
			cbDrawLineHelp.setActionCommand("enableDrawLineHelp");
			cbDrawLineHelp.addActionListener(this);
			this.add(assistantPanel);
		}
		
		private void buildMiscPanel()
		{
			textFiledDifficulty.setToolTipText("Please input value between 1 and 10");
			
			//miscPanel.setBorder(BorderFactory.createTitledBorder("锟斤拷锟斤拷锟斤拷锟�));
			miscPanel.setBorder(ImageBorder.generateDefaultImageBorder());
			miscPanel.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(4,2,4,2);
			c.gridx = 0;
			c.gridy = 0;
			miscPanel.add(label,c);
			c.gridx = 1;
			c.gridy = 0;
			miscPanel.add(textFiledDifficulty,c);
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 2;
			miscPanel.add(btnReGenerate,c);
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;
			miscPanel.add(btnAutoSolve,c);
			c.gridx = 0;
			c.gridy = 3;
			c.gridwidth = 2;
			miscPanel.add(btnClearUserFilledValue,c);
			
			
			
			btnReGenerate.setActionCommand("cmd_ReGenerate");
			btnAutoSolve.setActionCommand("cmd_Solve");
			btnClearUserFilledValue.setActionCommand("cmd_Reset");
			btnReGenerate.addActionListener(this);
			btnAutoSolve.addActionListener(this);
			btnClearUserFilledValue.addActionListener(this);
			
			this.add(miscPanel);
		}

		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if(command.compareTo("enableDrawLineHelp")==0)
			{
				if(!cbDrawLineHelp.isSelected())
				{	
					clearAssistLines();
				}
				else
				{
					for(int i=0;i<9;i++)
					{
						if(buttons[i].isSelected())
						{
							String cmd = buttons[i].getActionCommand();
							int number = Integer.parseInt(cmd.substring(cmd.length()-1));
							currentHelpNumber = number;
							drawHelpLine(number);
						}
					}
				}
			}
			else if(command.startsWith("help_draw_"))
			{
				if(cbDrawLineHelp.isSelected())
				{
					int number = Integer.parseInt(command.substring(command.length()-1));
					currentHelpNumber = number;
					drawHelpLine(number);
				}
			}
			else if(command.compareTo("cmd_ReGenerate")==0)
			{
				timeCounter = 0;
				t.start();
				String difficulty = textFiledDifficulty.getText().trim();
				int diffValue = 5;
				if(!difficulty.equals(""))
				{
					diffValue = Integer.parseInt(difficulty);
					if(diffValue<1 || diffValue>10)
						diffValue = 5;
				}
				reGenerateSudokuQuestion(diffValue);
			}
			else if(command.compareTo("cmd_Solve")==0)
			{
				t.stop();
				timeCounter = 0;
				
				boolean status= cb.isSelected();
				cb.setSelected(false);
				clearUserFilledValue();
				boolean isFinished = verifyIfSudokuFinished();
				if(!isFinished)
				{
					int [][] matrix = new int[9][9];
					for(int i=0;i<9;i++)
					{
						for(int j=0;j<9;j++)
						{
							matrix[i][j] = sudoku[i][j].getValue();
						}
					}
					
					SudokuCalculator ex = new SudokuCalculator(matrix);
					ex.addObserver(this);
					ex.answer();
					
				}
				cb.setSelected(status);
				
			}else if(command.compareTo("cmd_Reset")==0)
			{
				clearUserFilledValue();
				timeCounter = 0;
				t.start();
			}
			
			// END else if
			
		} //END actionPerformed

		@Override
		public void update(Observable o, Object arg) {
			if( o instanceof SudokuCalculator)
			{
				Cell[][] solved = ((SudokuCalculator) o).getSolvedSudoku();
				boolean succeed = SudokuMatrixUtility.verifyIfSudokuFinished(solved);
				if(succeed)
				{
					for(int i=0;i<9;i++)
					{
						for(int j=0;j<9;j++)
						{
							if(cellPanelMatrix[i][j].cellInfo.getValue() != solved[i][j].getValue())
							{
								cellPanelMatrix[i][j].cellInfo.setValue(solved[i][j].getValue());
								cellPanelMatrix[i][j].updateValue();
							}
						}
					}
				}
				else
				{
					t.stop();

							int result = JOptionPane.showConfirmDialog(this, "Sorry, no answer !!!", "Failed", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE );
							 if( result == JOptionPane.OK_OPTION )
							 {
								System.out.println("[OK], cost:" + MathUtility.getTimeString(timeCounter));
								
							 }
				}
				
				
				 
			}
			
		}
	} // END ConfigPanel


	
}
