package cn.heapstack.sudoku.gui;

/**
 * the bean stands for each sudoku matrix question
 * 
 * @author ehaojii
 *
 */
public class Question {
	
	private int level;
	private int subLevel;
	private boolean solved=false;
	private int costSeconds = 0;
	private String initProblem;
	
	public Question(int level, int subLevel, boolean solved, int costSeconds, String initProblem) {
		super();
		this.level = level;
		this.subLevel = subLevel;
		this.solved = solved;
		this.costSeconds = costSeconds;
		this.initProblem = initProblem;
	} 

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public int getCostSeconds() {
		return costSeconds;
	}

	public void setCostSeconds(int costSeconds) {
		this.costSeconds = costSeconds;
	}

	public int getLevel() {
		return level;
	}

	public int getSubLevel() {
		return subLevel;
	}

	public String getInitProblem() {
		return initProblem;
	}

	public void setInitProblem(String initProblem) {
		this.initProblem = initProblem;
	}
	
}
