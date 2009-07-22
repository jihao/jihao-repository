package cn.heapstack.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class SudokuBankDialog extends JDialog {

	private static final long serialVersionUID = -8876759042967627899L;
	private JTabbedPane tabPane = new JTabbedPane();
    private JButton cmdOk = new JButton(new OkAction());
    private JButton cmdCancel = new JButton(new CancelAction());
    private MainGUI mainGUI;
    private int selected_level = 1;
	private int selected_subLevel = 1;
	
	public SudokuBankDialog(MainGUI mainGUI)
	{
		super(mainGUI,"Sudoku Bank",true);
		this.mainGUI = mainGUI;

        this.getContentPane().setLayout(new BorderLayout());
        
        
        intiTabs();
        initOKCancelButtons();

        // Set frame to middle of screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        int heightScreen = tk.getScreenSize().height / 2;
        int widthScreen = tk.getScreenSize().width / 2;
        this.setLocation((widthScreen - this.getWidth()) / 2, (heightScreen - this.getHeight()) / 2);

        this.pack();
        updateGui();
	}


    private void intiTabs()
    {
        for(int level=1;level<10;level++)
        {
        	JPanel levelPanel = new JPanel();
        	levelPanel.setLayout(new GridLayout(9, 9));
        	ButtonGroup bg = new ButtonGroup();
        	for(int subLevel=1;subLevel<82;subLevel++)
        	{
        		JPanel subLevelPanel = new JPanel();
        		JRadioButton jrb = new JRadioButton(level+"-"+subLevel);
        		jrb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String name = ((JRadioButton)e.getSource()).getText();
						String []levelArray = name.split("-");
						selected_level = Integer.valueOf(levelArray[0]);
						selected_subLevel = Integer.valueOf(levelArray[1]);
						
					}
				});
        		bg.add(jrb);
   				if(subLevel==1)
   				{
   					jrb.setSelected(true);
   				}
   				
   				subLevelPanel.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));
        		subLevelPanel.add(jrb);
        		
   				Question q = mainGUI.getSudokuBankMap().get(level+"-"+subLevel);
    			if(q!=null )
    			{
    				if(q.getCostSeconds()!=0)
    				{
    					subLevelPanel.add(new JLabel("time: "+MathUtility.getTimeString(q.getCostSeconds())));
    				}
    				if(!q.isSolved())
    				{
    					subLevelPanel.setBackground(Color.yellow);
    					jrb.setBackground(subLevelPanel.getBackground());
    				}
    			}
        		
    			levelPanel.add(subLevelPanel);
        		
        	}
        	tabPane.addTab("Level"+level, levelPanel);
        	
        	//TODO: download from Internet to build a extension tab
        }
        
        tabPane.setAutoscrolls(true);
        this.getContentPane().add(tabPane, BorderLayout.CENTER);
    }
    
    private void initOKCancelButtons()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(cmdOk);
        buttonPanel.add(cmdCancel);

        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateGui()
    {
        
    }
    
    
    private void doCancel()
    {
        
        this.setVisible(false);
    }

    private void doOk()
    {    	
    	mainGUI.loadSudokuQuestion(selected_level, selected_subLevel);
    	
        this.setVisible(false);
    }
    
    private class OkAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;

        public OkAction()
        {
            super("Ok");
        }

        public void actionPerformed(ActionEvent ev)
        {
            doOk();
        }

    }

    private class CancelAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;

        public CancelAction()
        {
            super("Cancel");
        }

        public void actionPerformed(ActionEvent ev)
        {
            doCancel();
        }

    }
}
