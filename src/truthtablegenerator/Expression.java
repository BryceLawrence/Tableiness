	 package truthtablegenerator;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Expression class. Holds the entered Expression, validates it, and does boolean algebra on it.
 * A Singleton Class.
 */
public class Expression {
	private static final Expression expression = new Expression(); // Eager singleton, NOT threadsafe
	private static String enteredExpression = null;
	private static String workableExpression = null;
	private static int size = 0;
	private static List <String> variableList = new ArrayList<>();
	private static List <String> steps = new ArrayList<>();
	private static List <String> fullExpression = new ArrayList<>();
	
	/**
	 * A Singleton constructor
	 */
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
			workableExpression = workableExpression.replaceAll("true", "1");
			workableExpression = workableExpression.replaceAll("false", "0");
			workableExpression = workableExpression.replaceAll("\\s","");
			workableExpression = workableExpression.replaceAll("-->",">");
			workableExpression = workableExpression.replaceAll("<->","<");
			workableExpression = workableExpression.replaceAll("\\\\/","+");
			workableExpression = workableExpression.replaceAll("/\\\\","*");
			workableExpression = workableExpression.replaceAll("!","~");
			workableExpression = "@" + workableExpression + "@"; // used for validation to prevent null pointer issues
			// removed in getParentheticalSteps
		}
	}
	
	/**
	 * Validates the users Expression
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate() throws ValidationException { //needs to throw error codes for display
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			//need to reset the lists so that they dont just stack onto old expressions
			variableList.clear();
			steps.clear();
			fullExpression.clear();
			//cleanup(); This was pointless.
			setFullExpression();
			return true;
		}
		return false; // note false is only if there are nonmatching parentheses. all other errors are thrown.
	}
	
	/**
	 * Validates the users Expression and sets the 
	 * @param expression The string the user entered.
	 * @return true if valid, false if non-matching braces. throws operator/variable expressions
	 */
	public static boolean validate(String expression) throws ValidationException { //needs to throw error codes for display
		setEnteredExpression(expression);
		cleanup(); // prepares the expression to be worked on
		if (invalidate(0) == 0) { // no open parentheses yet and start at spot 1. (0 is an "@")
			// get size
			return true;
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
	private static int invalidate(int unclosedCount) throws ValidationException{ //throws error codes
		//insert code here	
		int pos;
		// Note on position. Our string has an '@' at position 0, so the users
		// first character is at our position 1. All validation errors will 
		// return a postion indexed starting at 1.
		int parCount = 0;
		char checking;
		char RHS;

		for (pos = 0; pos < workableExpression.length(); pos++) {
			checking = workableExpression.charAt(pos);
			//end of string
			if (checking == '@'  && pos > 0) {
				if(parCount > 0) {
					throw new ValidationException("Too many open parentheses");
				}
				return parCount;
			}

			RHS = workableExpression.charAt(pos + 1);

			// unbalanced parentheses


			// No expression
			if (checking == '@' && RHS == '@') {
				 throw new ValidationException("Empty Logical Expression");
			}

			// Main body of verification
			if (Character.isLetter(checking)) {
				if (RHS == '(' || RHS == '~') { 
					throw new ValidationException("Missing Logical Operator at: " + pos);
				} else if (Character.isLetter(RHS)) {
					throw new ValidationException("Adjacent Variables at: " + pos);
				} else if (RHS == '1' || RHS == '0') {
					throw new ValidationException("Variable Adjacent to a Constant at: " + pos);
				}
			} else if (checking == '1' || checking == '0') {
				if (RHS == '(' || RHS == '~') { 
					throw new ValidationException("Missing Logical Operator at: " + pos);
				} else if (RHS == '1' || RHS == '0') {
					throw new ValidationException("Adjacent Constants at: " + pos);
				} else if (Character.isLetter(RHS)) {
					throw new ValidationException("Constant Adjacent to a Variable at: " + pos);
				}
			} else {
				switch (checking) {
					case '+':
					case '*':
					case '>':
					case '<':
					case '~':
					case '@':
						if(RHS == '*' || RHS == '+' || RHS == '>' || RHS == '<' || RHS == '@') {
								throw new ValidationException("Missing Variable at " + pos);
						} else if (RHS == ')') {
								throw new ValidationException("Invalid Parenthesis at: " + pos);
						}
						break;
					case '(':
						parCount++;
						//error check here
						if (RHS == '*' || RHS == '+' || RHS == '>' || RHS == '<') {
							throw new ValidationException("Missing Variable at: " + pos);
						} else if(RHS == '@') {
							throw new ValidationException("Invalid open parenthesis at: " + (pos - 1));
						} else if (RHS == ')') {
							throw new ValidationException("Empty parenthetical statement at: " + pos);
						}
						break;
					case ')':
						parCount--;
						// error checking
						if (Character.isLetter(RHS) || RHS == '~' || RHS == '(') {
							throw new ValidationException("Missing Logical Operator at: " + pos);
						}
						if (parCount < 0) {
							throw new ValidationException("You have more closed parentheses than open parentheses");
						}
						break;
					default:
						return parCount;               
				}
			}                
		}
		if (parCount > 0) {
			throw new ValidationException("You have " + parCount + "more open parentheses than closed parentheses");
		}
		return unclosedCount;
	}
	
	/**
	 * Determines what variables are in the expression and returns a list of them in Half-As-Fast format
	 * @return the variables in Half-As-Fast format
	 */
	 private static List<String> calculateVariables() {
		List <Character> variables = new ArrayList<>();
		for( int i = workableExpression.length() - 1; i > 0 ; i--) { // first and last elements are just placeholder "@" skip them
			if (Character.isLetter(workableExpression.charAt(i))) {
				boolean add = true;
				for (int j = 0; j < variables.size(); j++) {
					if (workableExpression.charAt(i) == variables.get(j)) {
						add = false;
					}
				}
				if (add) {
					variables.add(workableExpression.charAt(i));
				}
			}
		}
		Collections.reverse(variables); // reverse the order so it will be in compact form going forwards not backwards
		//convert the list of chars to a list of strings of length one
		List<String> sVariables = new ArrayList<>();
		for (Character variable : variables) {
			sVariables.add(variable.toString());
		}
		// return the string list
		return sVariables;
	}
	
	/**
	 * Recursively finds all parenthetical ParentheticalSteps and returns them in the logical order of operations (OoO) (inner first, then left to right)
	 * @param startPoint The point to start the search at. The initial call should be 0.
	 * @return all parenthetical ParentheticalSteps in OoO
	 */
	private static List<String> calculateParenthesesSteps(int startPoint) {
		List <String> steps = new ArrayList<>();
		for (int i = startPoint; i < workableExpression.length(); i++) {
			if (workableExpression.charAt(i) == ')') {
				steps.add(workableExpression.substring(startPoint, i)); // that is the end of this step
				return steps; // return it to the caller (the last calculateParenthesesSteps), 
			}
			if (workableExpression.charAt(i) == '(') {
				steps.addAll(calculateParenthesesSteps(i +1));  // recursivly find the closing parenthesis
				i += steps.get(steps.size() - 1).length() + 1; 
				//get the size of the array - 1 -> get last element -> get its length->
				//add 1 for the missing start parenthesis -> sets the index to that closing parenthesis, 
				//which will then move forward when i++ happens
			}
		}
		steps.add(workableExpression.substring(1, workableExpression.length() - 1));
		//the full expression minus the 2 "@"
		return steps;
	}
	 
	/**
	 * Given a string and the position of a opening '(', return the position of the closing ')'
	 * @param step the string to search in.
	 * @param startSearch the spot to start the search. Must be the starting open '(' character
	 * @return the position of the closing ')'
	 */
	 private static int findClosingParenthesis(String step, int startSearch) {
		int skip = startSearch + 1; // start on the char AFTER the starting '('
		int unclosedCount = 1; //we skipped the starting '(' so start at one
		System.out.println(step);
		while (unclosedCount != 0) { //find the end of the parenthetical expression
			if (step.charAt(skip) == '(') {
				unclosedCount++; // add a level
			} else if (step.charAt(skip) == ')') {
				unclosedCount--; // reduce a level
			}
			if (unclosedCount != 0) { //if we are not done increment. if we are, incrementing may move out of bounds
				skip++;
			}
		}
		return skip;
	 }
	 
	/**
	 * Given a String, find all NOT ParentheticalSteps in it.
	 * @param step the step to search for not ParentheticalSteps
	 * @return a list of the ParentheticalSteps found in left to right order
	 */
	private static List<String> calculateNotSteps(String step) {
		List <String> subSteps = new ArrayList<>();
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '~') {
				int chain = 0; // reset each time through the loop. 
				if (step.charAt(i + 1) == '~') {
					// if the next item is a ~ increase the chain count count the number of ~ in a row
					for (; step.charAt(i + chain + 1) == '~'; chain++) {} // repeat until not ~ anymore
					i += chain; // skip that far ahead
				}
				if (Character.isLetter(step.charAt(i + 1)) || step.charAt(i + 1) == '1' || step.charAt(i + 1) == '0') { // if the next character is a letter
					subSteps.add(step.substring(i, i + 2)); // add it to our list
					for (; chain > 0; chain--) { // if there was a skipped chain of ~ add in a element for each skipped ~ that is the a ~ plus the last entered element
						subSteps.add("~" + subSteps.get(subSteps.size() - 1));
					}
				} else { // a "!(...)"
					int skip = findClosingParenthesis(step, i + 1) + 1; // find the closing ')' and increment by one; i+1 is the (
					subSteps.add(step.substring(i, skip));
					for (; chain > 0; chain--) { // if there was a skipped chain of ~ add in a element for each skipped ~ that is
						// the a ~ plus the last entered element
						subSteps.add("~" + subSteps.get(subSteps.size() - 1));
					}
					i = skip; // skip to the closing parenthesis. (the pearenthetical expression plus the ~ and the opening parenthesis
				}
			} else if(step.charAt(i) == '(') { // we dont want to seach in a layer that was already evaluated
				i = findClosingParenthesis(step, i) + 1; // find the closing ')' and increment by one;
			}
		}
		return subSteps;
	}
	
	/**
	 * Given a String, find all (Insert Binary Logic Operator Here) statements
	 * @param step The String to parse
	 * @param target The operand to look for
	 * @return a list of all ParentheticalSteps found, from left to right
	 */
	private static List<String> calculateBinarySteps(String step, char target) {
		List <String> subSteps = new ArrayList<>();
		int lastStartPoint = 0; // the start point for the substrings is the beginning 
		/*
		IMPORTANT: 
		An operator step should NEVER contain another operator of lower precidence that is not within parenthesis of a higher level of precidence
			examples when evaluating ANDs:
			p /\ q				the step is p /\ q
			p \/ q /\ r			the step is q /\ r 
			p /\ q \/ r			the step is p /\ q
			p \/ q /\ r \/ s	the step is q /\ r
			(p \/ q) /\ r		the step is (p \/ q) /\ r
			p /\ (q \/ r)		the step is p /\ (q \/ r)
		An operator step SHOULD contain all operators of higher precidence in its step
			examples when evaluating ORs:
			p \/ q				the step is p \/ q
			p \/ q /\ r			the step is p \/ q /\ r
			p /\ q \/ r			the step is p /\ q \/ r
			p /\ q \/ r /\ s	the step is p /\ q \/ r /\ s
		*/
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '(') { // if a ( is found, dont evaluate its innards
				i = findClosingParenthesis(step, i); // find the closing ')' and move to it
			} 
			if (target == '*') { //if we are searching for ANDS, and we run into an OR, IMPLY, or IFF
				if (step.charAt(i) == '<' || step.charAt(i) == '>' || step.charAt(i) == '+') {
					lastStartPoint = i + 1; // set the lastStartPoint to the character after that OR, IMPLY, or IFF
				}
			}else if (target == '+') { //if we are searching for ORs, and we run into an IMPLY or an IFF
				if (step.charAt(i) == '<' || step.charAt(i) == '>') {
					lastStartPoint = i + 1; // set the lastStartPoint to the character after that IMPLY or IFF
				}
			} else if (target == '>') { //if we are searching for IMPLIES, and we run into an IFF
				if (step.charAt(i) == '<') {
					lastStartPoint = i + 1; // set the lastStartPoint to the character after that IFF
				}
			}
			//note IFF has lowest precidence, so it will always include everything from the beginning
			if (step.charAt(i) == target) {
				i++;//move forward one and begin the search for a terminating character (equal or lower precidence)
				while (i < step.length()) { // search to the end.
				/*
				NOTE :
				on this step, search forward until a operator of EQUAL or LOWER precidence is found and stop there. Example:
				p /\ q /\ r				has two AND steps		p /\ q 
										and							p /\ q /\ r	
				p \/ q /\ r \/ s		has two OR steps		p \/ q /\ r 
										and							p \/ q /\ r \/ s
				*/
					if (step.charAt(i) == '(') { // if a ( is found, dont evaluate its innards
						i = findClosingParenthesis(step, i); // find the closing ')' and move to it
					} 
					if (target == '*') { // for ANDs go until an AND, OR, IMPLY, or IFF
						if (step.charAt(i) == '<' || step.charAt(i) == '>' || step.charAt(i) == '+' || step.charAt(i) == '*') {
							subSteps.add(step.substring(lastStartPoint, i)); 
							i--; // move back one so we will ++ to the new operator
							break; // exit the while loop
						}
					} else if (target == '+') { // for ORs go until an OR, IMPLY, or IFF
						if (step.charAt(i) == '<' || step.charAt(i) == '>' || step.charAt(i) == '+') {
							subSteps.add(step.substring(lastStartPoint, i));
							i--; // move back one so we will ++ to the new operator
							break; // exit the while loop
						}
					} else if (target == '>') { // for IMPLIES go until an IMPLY, or IFF
						if (step.charAt(i) == '<' || step.charAt(i) == '>') {
							subSteps.add(step.substring(lastStartPoint, i));
							i--; // move back one so we will ++ to the new operator
							break; // exit the while loop
						}
					} else if (target == '<') { // for IFFs go until another IFF
						if (step.charAt(i) == '<') {
							subSteps.add(step.substring(lastStartPoint, i));
							i--; // move back one so we will ++ to the new operator
							break; // exit the while loop
						}
					}
					i++; 
				}
				if (i == step.length()) { // if we reached the end of the step in the while loop, nothing was added, so add everything
					// from the lastStartPoint to the end
					subSteps.add(step.substring(lastStartPoint, i));
				}
			}
		}
		return subSteps;
	}
	
	/**
	 * For Each parenthetical step, Sets all logical ParentheticalSteps (~,/\, \/,-->, and &lt:->).
	 * @param parentheticalSteps the ParentheticalSteps to walk through
	 * @return the list of ParentheticalSteps in Order of Operations
	 */
	public static List<String> calculateLogicalSteps(List<String> parentheticalSteps) {
		//System.out.println("LOGICAL STEPS");
		List<String> steps = new ArrayList<>();
		for (int i = 0; i < parentheticalSteps.size(); i++) {
		//	calculate the ParentheticalSteps for this STEP 
//System.out.println("	STEP " +( i + 1));
			List <String> notSteps = calculateNotSteps(parentheticalSteps.get(i));
//System.out.println("	NOT STEPS");
//System.out.println(notSteps);
			List <String> andSteps = calculateBinarySteps(parentheticalSteps.get(i), '*');
//System.out.println("	AND STEPS");
//System.out.println(andSteps);
			List <String> orSteps = calculateBinarySteps(parentheticalSteps.get(i), '+');
//System.out.println("	OR STEPS");
//System.out.println(orSteps);
			List <String> impliesSteps = calculateBinarySteps(parentheticalSteps.get(i), '>');
//System.out.println("	IMPLY STEPS");
//System.out.println(impliesSteps);
			List <String> iffSteps = calculateBinarySteps(parentheticalSteps.get(i), '<');
//System.out.println("	IFF STEPS");
//System.out.println(iffSteps);
			
			//add the ParentheticalSteps calculated in order of operations, then move i forward the muber of ParentheticalSteps added
			steps.addAll(notSteps);
			steps.addAll(andSteps);
			steps.addAll(orSteps);
			steps.addAll(impliesSteps);
			steps.addAll(iffSteps);
		}
		 return steps;
	 }
	
	/**
	 * Evaluates workable expression, finding all variables, 
	 */
	public static void setFullExpression() {
		//find all variables
		List <String> variables = calculateVariables();
//System.out.println("	VARIABLES");
//System.out.println(variables);
		variableList.addAll(variables);
		List<String> parentheticalSteps = calculateParenthesesSteps(1);
//System.out.println("	PARENTHETICAL STEPS");
//System.out.println(parentheticalSteps);
		List<String> logicalSteps = calculateLogicalSteps(parentheticalSteps);
//System.out.println("	ALL STEPS");
//System.out.println(logicalSteps);
		steps.addAll(logicalSteps);
		
		fullExpression.addAll(variableList);
		fullExpression.addAll(steps);
		size = variables.size();
//System.out.println("	FULL EXPRESSION");
//System.out.println(fullExpression);
	}
	
	/* Is this NOT just the workable expression without the @s
	public static void setCompactExpression() {
	}*/

	public static int getSize() {
		return size;
	}
	public static List<String> getFullExpression() {
		return fullExpression;
	}
	
	public static String getCompactExpression() {
		return null;
	}
}
