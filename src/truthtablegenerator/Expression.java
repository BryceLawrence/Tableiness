 package truthtablegenerator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
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
			workableExpression = workableExpression.replaceAll("!","~");
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
		ArrayList <Character> variables = new ArrayList<>();
		for( int i = workableExpression.length() - 1; i > 0 ; i--) { // first and last elements are just placeholder "@" skipp them
			if (Character.isLetter(workableExpression.charAt(i))) {
				boolean add = true;
				for (int j = 0; j < variables.size(); j++) {
					if (workableExpression.charAt(i) == variables.get(j)) { // the string is only 1 long so 0 is the whole string
						add = false;
					}
				}
				if (add) {
					variables.add(workableExpression.charAt(i));
				}
			}
		}
		Collections.reverse(variables); // reverse the order so it will be in compact form
		return variables;
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
				 i += steps.get(steps.size() - 1).length() + 1; 
				//get the size of the array-1 -> get last element -> get its length->
				//add 1 for the missing start parenthesis->set the index to that closing parenthesis, which will then move forward 
				 // when i++ happens
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
				int chain = 0;
				if (step.charAt(i + 1) == '~') {
					for (; step.charAt(i + chain + 1) == '~'; chain++) { // if the next item is a ~ increase the chain count
						System.out.println(chain);
					} // count the number of ~ in a row
					System.out.println(chain);
					i += chain; // skip that far ahead
					System.out.println(i);
				}
				if (Character.isLetter(step.charAt(i + 1))) {
					subSteps.add(step.substring(i, i + 2));
					for (; chain > 0; chain--) { // if there was a skipped chain of ~ add in a element for each skipped ~ that is
						// the a ~ plus the last entered element
						subSteps.add("~" + subSteps.get(subSteps.size() - 1));
					}
				} else { 
					int unclosedCount = 1;
					int skip = i + 2; // start the search on the character after the ( or 2 after the ~
					for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
						if (step.charAt(skip) == '(') {
							unclosedCount++;
						} else if (step.charAt(skip) == ')') {
							unclosedCount--;
						}
					}
					int end = i + skip;
					if (end > step.length()) {
						end = step.length();
					}
					subSteps.add(step.substring(i, end));
					for (; chain > 0; chain--) { // if there was a skipped chain of ~ add in a element for each skipped ~ that is
						// the a ~ plus the last entered element
						subSteps.add("~" + subSteps.get(subSteps.size() - 1));
					}
					i = i + skip; // skip to the closing parenthesis. (the pearenthetical expression plus the ~ and the opening parenthesis
				}
			} else if(step.charAt(i) == '(') {
				int unclosedCount = 1;
					int skip = i + 1; // start the search on the character after the ( or 2 after the ~
					for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
						if (step.charAt(skip) == '(') {
							unclosedCount++;
						} else if (step.charAt(skip) == ')') {
							unclosedCount--;
						}
					}
					i = i + skip;
			}
		}
		return subSteps;
	}
	
	private static ArrayList<String> getAndOrSteps(String step) {
		ArrayList <String> subSteps = new ArrayList<>();
		int lastNonimplies = 0; //there should be NO implies that are not in parentheses in this step
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '>') {
				lastNonimplies = i + 1;
			}
			if (step.charAt(i) == '*' || step.charAt(i) == '+') {
				if (Character.isLetter(step.charAt(i + 1))) {
					subSteps.add(step.substring(lastNonimplies, i + 2));
				} else if (step.charAt(i + 1) == '~' && Character.isLetter(step.charAt(i + 2))) {
					subSteps.add(step.substring(lastNonimplies, i + 3));
				} else {
					int unclosedCount = 1;
					int skip = i + 2; // start the search on the character after the ( or 2 after the +/*
					if (step.charAt(i + 1) =='~') { // if it is a ~ add an extra skip
						skip ++;
					}
					for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
						if (step.charAt(skip) == '(') {
							unclosedCount++;
						} else if (step.charAt(skip) == ')') {
							unclosedCount--;
						}
					}
					int end = i + skip;
					if (end > step.length()) {
						end = step.length();
					}
					subSteps.add(step.substring(lastNonimplies, end));
					i = i + skip; // skip to the closing parenthesis.
				}
			} else if(step.charAt(i) == '(') {
				int unclosedCount = 1;
				int skip = i + 1; // start the search on the character after the ( or 2 after the ~
				for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
					if (step.charAt(skip) == '(') {
						unclosedCount++;
					} else if (step.charAt(skip) == ')') {
						unclosedCount--;
					}
				}
				i = i + skip - 1; // -1 so that it ends on the closing parenthesis and not the next symbol. i++ will move it to far if not -1
			}
		}
		return subSteps;
	}
	 
	private static ArrayList<String> getImpliesSteps(String step) {
		ArrayList <String> subSteps = new ArrayList<>();
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '>') {
				//if the next character is a letter or a ~ followed by a letter...
				if (Character.isLetter(step.charAt(i + 1)) ||
				   (step.charAt(i + 1) == '~' && Character.isLetter(step.charAt(i + 2)))) {
					int end = i + 1;
					//... find the end of expression or the next implies ...
					for(;end < step.length(); end++) {
						if (step.charAt(end) == '>') {
							break;
						}
					}
					//... and add from beginning to end or next implies to subSteps
					subSteps.add(step.substring(0, end));
				} else {
					int unclosedCount = 1;
					int skip = i + 2; // start the search on the character after the ( or 2 after the +/*
					if (step.charAt(i + 1) =='~') { // if it is a ~ add an extra skip
						skip ++;
					}
					for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
						if (step.charAt(skip) == '(') {
							unclosedCount++;
						} else if (step.charAt(skip) == ')') {
							unclosedCount--;
						}
					}
					int end = i + skip;
					if (end > step.length()) {
						end = step.length();
					}
					for(;end < step.length(); end++) {
						if (step.charAt(end) == '>') {
							break;
						}
					}
					subSteps.add(step.substring(0, end));
					i = i + skip; // skip to the closing parenthesis.
				}
			} else if(step.charAt(i) == '(') {
				int unclosedCount = 1;
				int skip = i + 1; // start the search on the character after the ( or 2 after the ~
				for (; unclosedCount != 0; skip++) { //find the end of the parenthetical expression
					if (step.charAt(skip) == '(') {
						unclosedCount++;
					} else if (step.charAt(skip) == ')') {
						unclosedCount--;
					}
				}
				i = i + skip;
			}
		}
		return subSteps;
	}
	
	public static ArrayList<String> setLogicalSteps(ArrayList<String> parentheticalSteps) {
		//System.out.println("LOGICAL STEPS");
		ArrayList<String> steps = new ArrayList<>();
		for (int i = 0; i < parentheticalSteps.size(); i++) {
		//	calculate the steps for this STEP 
			System.out.println("	STEP " +( i + 1));
			ArrayList <String> notSteps = getNotSteps(parentheticalSteps.get(i));
			System.out.println("	NOT STEPS");
			System.out.println(notSteps);
			ArrayList <String> andOrSteps = getAndOrSteps(parentheticalSteps.get(i));
			System.out.println("	AND/OR STEPS");
			System.out.println(andOrSteps);
			ArrayList <String> impliesSteps = getImpliesSteps(parentheticalSteps.get(i));
			System.out.println("	IMPLIES STEPS");
			System.out.println(impliesSteps);
			
			//add the steps calculated in order of operations, then move i forward the muber of steps added
			steps.addAll(notSteps);
			steps.addAll(andOrSteps);
			steps.addAll(impliesSteps);
		}
		 return steps;
	 }
	
	public static void setFullExpression() {
		// steps, answer
		ArrayList <Character> vars = getVariables();
		System.out.println("VARIABLES");
		System.out.println(vars);
		ArrayList<String> steps = getParenthesesSteps(1);
		System.out.println("PARENTHETICAL STEPS");
		System.out.println(steps);
		ArrayList<String> logicalSteps = setLogicalSteps(steps);
		System.out.println("ALL STEPS");
		System.out.println(logicalSteps);
		
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
