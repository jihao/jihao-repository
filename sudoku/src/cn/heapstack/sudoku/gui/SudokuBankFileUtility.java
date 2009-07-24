package cn.heapstack.sudoku.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import cn.heapstack.sudoku.Generator;
import cn.heapstack.sudoku.SudokuMatrixUtility;


public class SudokuBankFileUtility {
    private static SudokuBankFileUtility instance;
    private static final String SavingFileName = ".save.dat";
    private static final String SudokuBankFileName = "SudokuBank.dat";
    private String savingFilePath;
    
    
    private SudokuBankFileUtility()
    {
        savingFilePath = System.getProperty("user.home");
        if (!savingFilePath.endsWith("/"))
        {
            savingFilePath = savingFilePath + "/";
        }
    }

    public static synchronized SudokuBankFileUtility getInstance()
    {
        if (instance == null)
        {
            instance = new SudokuBankFileUtility();
        }
        return instance;
    }
    
    public void initSudokuBank(HashMap<String, Question> sudokuBankMap)
    {
    	initSudokuBank1(sudokuBankMap);
    	initSudokuBank2(sudokuBankMap);
    }
    /**
     * the step 1 of initialize, fill the sudokuBankMap using the SudokuBank.dat file
     * 
     * @param sudokuBankMap
     */
    private void initSudokuBank1(HashMap<String, Question> sudokuBankMap)
    {
		try {
			URI uri = this.getClass().getResource(SudokuBankFileName).toURI();
			BufferedReader br = new BufferedReader(new FileReader(new File(uri)));
			String line = br.readLine();
			while(line!=null&& !line.isEmpty())
			{
				String[] lineArray = line.split(":");
				String[] levelArray = lineArray[0].split("-");
				Question q = new Question(Integer.parseInt(levelArray[0]), Integer.parseInt(levelArray[1]), false, 0, lineArray[1]);
				sudokuBankMap.put(lineArray[0], q);
				line = br.readLine();
			}
			br.close();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * the step 2 of initialize, update the sudokuBankMap using the save.dat file
     * 
     * @param sudokuBankMap
     */
    private void initSudokuBank2(HashMap<String, Question> sudokuBankMap)
    {
    	try {
			BufferedReader br = new BufferedReader(new FileReader(new File(savingFilePath+SavingFileName)));
			String line = br.readLine();
			while(line!=null&& !line.isEmpty())
			{
				String[] lineArray = line.split(":");
				Question q = sudokuBankMap.get(lineArray[0]);
				q.setSolved(Boolean.parseBoolean(lineArray[1]));
				q.setCostSeconds(Integer.parseInt(lineArray[2]));
				
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			saveSudokuRecord(sudokuBankMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public Question findFirstUnSolvedQuestion(HashMap<String, Question> sudokuBankMap)
    {
		for(int level=1;level<10;level++)
        {
        	for(int subLevel=1;subLevel<82;subLevel++)
        	{
        		String key = level+"-"+subLevel;
        		Question q = sudokuBankMap.get(key);
        		if(!q.isSolved())
        		{
        			return q;
        		}
        	}
        }
		return null;
    }

	public void saveSudokuRecord(HashMap<String, Question> sudokuBankMap)
    {
    	try {
    		File dest = new File(savingFilePath+SavingFileName);
        	dest.delete();
			dest.createNewFile();
			
			PrintWriter pw = new PrintWriter(dest);
			for(int level=1;level<10;level++)
	        {
	        	for(int subLevel=1;subLevel<82;subLevel++)
	        	{
	        		String key = level+"-"+subLevel;
	        		Question q = sudokuBankMap.get(key);
	        		
	        		pw.print(key);
	        		pw.print(":");
	        		pw.print(q.isSolved());
	        		pw.print(":");
	        		pw.print(q.getCostSeconds());
	        		pw.println();
	        	}
	        }
			pw.flush();
			pw.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    public void loadSudokuRecord(HashMap<String, Question> sudokuBankMap)
    {
    	try {
    		File dest = new File(savingFilePath+SavingFileName);
        	dest.delete();
			dest.createNewFile();
			
			PrintWriter pw = new PrintWriter(dest);
			for(int level=1;level<10;level++)
	        {
	        	for(int subLevel=1;subLevel<82;subLevel++)
	        	{
	        		String key = level+"-"+subLevel;
	        		Question q = sudokuBankMap.get(key);
	        		
	        		pw.print(key);
	        		pw.print(":");
	        		pw.print(q.isSolved());
	        		pw.print(":");
	        		pw.print(q.getCostSeconds());
	        		pw.println();
	        	}
	        }
			pw.flush();
			pw.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String [] args)
    {
		try {
			URI uri = SudokuBankFileUtility.class.getResource(SudokuBankFileName).toURI();
			File dest = new File(uri);
			dest.delete();
			dest.createNewFile();
			System.out.println( dest.exists() );
			System.out.println(dest);
			PrintWriter pw = new PrintWriter(dest);
			
			for(int level=1;level<10;level++)
	        {
	        	for(int subLevel=1;subLevel<82;subLevel++)
	        	{
	        		String key = level+"-"+subLevel;
	        		System.out.print(key);
	        		System.out.print(":");
	        		
	        		pw.print(key);
	        		pw.print(":");
	        		int[][] matrix = Generator.generateSudokuMatirx2(level);
	        		String initProblem = SudokuMatrixUtility.exportMatrix2String(matrix);
	        		pw.print(initProblem);
	        		System.out.println(initProblem);
	        		pw.println();
	        	}
	        }
			pw.flush();
			pw.close();	
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
