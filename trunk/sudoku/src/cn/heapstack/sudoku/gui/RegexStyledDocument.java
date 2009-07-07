package cn.heapstack.sudoku.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class RegexStyledDocument extends DefaultStyledDocument {

	private static final long serialVersionUID = 8865124536884284645L;

	private Pattern pattern;
	private Matcher matcher;
	private AttributeSet styleAttributeSet;

	public RegexStyledDocument(String patternStr,AttributeSet attributeSet ) {
		this.pattern = Pattern.compile(patternStr);
		this.styleAttributeSet = attributeSet;
		
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		//System.out.print("insertString- offset:" + offs + ",string:" + str);
		//System.out.println("->Call Style : "+a.toString());
		
		
		matcher = pattern.matcher(str);
		//System.out.println("matcher reset to " + str);
		if (!matcher.matches()) {
			//System.out.println("DOES not match");
		}
		else
		{
			if(offs==0)
			{
				super.insertString(offs, str, styleAttributeSet);
				super.remove(1, getLength()-1);
			}
			else if(offs==getLength())
			{
				super.remove(0, getLength());
				super.insertString(0, str, styleAttributeSet);
			}
		}
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {
		//System.out.println("remove- offset:" + offs + ",len:" + len);
		super.remove(offs, len);
		super.insertString(0, "", styleAttributeSet);
	}
	
	
}
