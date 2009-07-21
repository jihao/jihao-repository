package cn.heapstack.sudoku.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SudokuBankDialog extends JDialog {

	private static final long serialVersionUID = -8876759042967627899L;
	private JTabbedPane tabPane = new JTabbedPane();
    private JButton cmdOk = new JButton(new OkAction());
    private JButton cmdCancel = new JButton(new CancelAction());
    
	public SudokuBankDialog(JFrame parent)
	{
		super(parent,"Sudoku Bank",true);
		

        this.getContentPane().setLayout(new BorderLayout());
        
        initDefaultSettings();
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

	public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception ignore)
                {
                }
                
                JFrame parent = new JFrame("Setting Panel");
                parent.setBounds(10, 10, 800, 800);
                parent.setVisible(true);

                parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                SudokuBankDialog sudokuBank = new SudokuBankDialog(parent);
                sudokuBank.setBounds(10, 10, 800, 800);
                sudokuBank.setVisible(true);
                sudokuBank.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            }

        });

    }
	
	private void initDefaultSettings()
    {
        
        //Settings settings = Settings.getInstance();
        
    }

    private void intiTabs()
    {
        for(int level=1;level<10;level++)
        {
        	JPanel levelPanel = new JPanel();
        	levelPanel.setLayout(new GridLayout(9, 9));
        	for(int subLevel=1;subLevel<82;subLevel++)
        	{
        		JPanel subLevelPanel = new JPanel();
   				subLevelPanel.setBorder(BorderFactory.createEtchedBorder(Color.white, Color.gray));        		
        		subLevelPanel.add(new JCheckBox(level+"-"+subLevel));
        		subLevelPanel.add(new JLabel("time : 01:25:48"));
        		if(subLevel%3==0)
        			subLevelPanel.setBackground(Color.yellow);
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
