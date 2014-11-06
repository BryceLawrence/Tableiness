 package truthtablegenerator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * The Expression class. Holds the entered Expression, validates it, and does boolean algebra on it.
 * A Singleton Class.
 * @author McAllister, ...
 */
public class Expression {
	private static final Expression expression = new Expression();
	private static String enteredExpression = null;
	private static String workableExpression = null;
	private static int size = 0;
	private static List <String> variableList = new ArrayList<>();
	private Expression() {
	}
	
	/**
	 * setter for fullExpression
	 * @param expression the user entered string "expression"
	 */
	public static void setEnteredExpression (String expression) {
		enteredExpression = expression;
	}
	
	/**
	 * Converts the expression into a workable one with only single character symbols and no spaces or uppercase letters.
	 */
	public static void cleanup() {
		if (!enteredExpression.equals(null)) { //dont do if there is no expression
			workableExpression = enteredExpression.toLowerCase();
			workableExpression = workableExpression.replaceAll("\\s","");
			workableExpression = workableExpression.replaceAll("-->",">");
			workableExpression = workableExpression.replaceAll("\\\\/","+");
			workableExpression = workableExpression.replaceAll("/\\\\","*");
			workableExpression = workableExpression.replaceAll("!","`");
			workableExpression = "@" + workableExpression + "@";
		}
	}
	
	/**
	 * Validates the users Expression
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate() { //needs to throw error codes for display
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			// get size
			return true;
		}
		return false; // note false is only if there are nonmatching parentheses. all other errors are thrown.
	}
	
	/**
	 * Validates the users Expression and sets the 
	 * @param expression The string the user entered.
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate(String expression) { //needs to throw error codes for display
		setEnteredExpression(expression);
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			// get sizereturn true;
		}
		return false; // note false is only if there are nonmatching parentheses. all other errors are thrown.
	}
	
	/**
	 * Checks if the expression is invalid. 
	 * @param unclosedCount number of left parentheses w/o right ones. if ever negative,  expression is FALSE.
	 *		Initial pass in should be 0;
	 * @param position the position in the String. Initial call should be 1
	 * @return unclosedCount. If not 0, then the expression is invalid
	 */
	private static int invalidate(int unclosedCount) { //throws error codes
		//insert code here	
		return unclosedCount;
	}
	
	
	 private static ArrayList<Character> getVariables() {
		ArrayList <Character> vars = new ArrayList<>();
		for( int i = workableExpression.length() - 1; i > 0 ; i--) { // first and last elements are just placeholder "@" skipp them
			if (Character.isLetter(workableExpression.charAt(i))) {
				boolean add = true;
				for (int j = 0; j < vars.size(); j++) {
					if (workableExpression.charAt(i) == vars.get(j)) { // the string is only 1 long so 0 is the whole string
						add = false;
					}
				}
				if (add) {
					vars.add(workableExpression.charAt(i));
				}
			}
		}
		return vars;
	 }
	 
	 private static ArrayList<String> getParenthesesSteps(int startPoint) {
		 ArrayList <String> steps = new ArrayList<>();
		 for (int i = startPoint; i < workableExpression.length() - 1; i++) {
			 if (workableExpression.charAt(i) == ')') {
				 steps.add(workableExpression.substring(startPoint, i));
				 return steps;
			 }
			  if (workableExpression.charAt(i) == '(') {
				 steps.addAll(getParenthesesSteps(i +1));
				 i += steps.get(steps.size() - 1).length() + 2; 
				//get the size of the array-1 -> get last element -> get its length->
				//add 2 for the missing parentheses->set the index to that spot, imediately after the right parenthesis
			 }
		 }
		 steps.add(workableExpression.substring(1, workableExpression.length()-1));
		 //the full expression minus the 2 "@"
		 return steps;
	 }
	 
	private static ArrayList<String> getNotSteps(String step) {
		ArrayList <String> subSteps = new ArrayList<>();
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '~') {
				if (Character.isLetter(step.charAt(i + 1))) {
					subSteps.add(step.substring(i, i + 1));
				}
				if (step.charAt(i + 1) == '(') {
					int unclosedCount = 1;
					int skip = i + 2; // start the search on the character after the ( or 2 after the ~
					for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
						if (step.charAt(skip) == '(') {
							unclosedCount++;
						}
						if (step.charAt(skip) == ')') {
							unclosedCount--;
						}
					}
					subSteps.add(step.substring(i, skip + 2));
					i += skip + 2; // skip to the closing parenthesis. (the pearenthetical expression plus the ~ and the opening parenthesis
				}
			}
		}
		return subSteps;
	}
	 
	public static ArrayList<String> setLogicalSteps(ArrayList<String> steps) {
		for (int i = 0; i < steps.size(); i++) {
			ArrayList <String> notSteps = getNotSteps(steps.get(i));
			System.out.println(notSteps);
		}
		 return steps;
	 }
	
	public static void setFullExpression() {
		// steps, answer
		ArrayList <Character> vars = getVariables();
		System.out.println(vars);
		ArrayList<String> steps = getParenthesesSteps(1);
		System.out.println(steps);
		steps = setLogicalSteps(steps);
		
	}
	
	public static void setCompactExpression() {
	}

	public static List<String> getFullExpression() {
		return null;
	}
	
	public static String getCompactExpression() {
		return null;
	}
	
	public static String evaluateFullExpression(int row) {
		return null;
	}
	 
	public static String evaluateCompactExpression(int row) {
		return null;
	}
	
}
