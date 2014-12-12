package truthtablegenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates tables from logical expressions
 * 
 * @author Bryce, McAllister, Tyler
 */
public class CompactTableGenerator {

	private List<List<String>> compactTable = new ArrayList<>();

	/**
	 * Makes the full fullTable row by row
	 */
	private void makeTable() {
		int tableSize = (int) Math.pow(2, Expression.getVariableCount());
		compactTable.clear();
		String compactTableString = new String(Expression.getCompactExpression());
		List<String> expressionRow = new ArrayList<>();
		List<String> emptyRow = new ArrayList<>();

		for (int i = 0; i < compactTableString.length(); i++) {
			// break the expression into component parts
			expressionRow.add(Character.toString(compactTableString.charAt(i)));
			// create a list of equal size to expression row of spaces. used to initalize the result rows so i can set elements
			// of the row without any shifting
			emptyRow.add(" ");
		}

		compactTable.add(expressionRow);

		for (int i = 0; i < tableSize; i++) {
			// need to make a new unque instance or all "empty rows" are all the same and change together whenever one is edited
			List<String> emptyRowUnique = new ArrayList<>(emptyRow);
			compactTable.add(emptyRowUnique);
		}

		for (int i = 0; i < tableSize; i++) {
			calcRow(i, compactTableString);
		}
	}

	/**
	 * Prepares the String expression to be calculated.
	 *
	 * @param step the current row being calculated
	 * @param expression the expression to be evaluated
	 */
	private void calcRow(int step, String expression) {
		int variableCount = Expression.getVariableCount();

		String binaryStep = Integer.toBinaryString(step);
		while (binaryStep.length() < variableCount) {
			binaryStep = "0" + binaryStep;
		}

		int binaryCounter = binaryStep.length() - 1;
		int expLength = expression.length();
		for (int i = expression.length() - 1; i >= 0; i--) {
			if (Character.isLetter(expression.charAt(i))) {
				Character c = expression.charAt(i);
				for (int j = i; j >= 0; j--) {
					if (c.equals(expression.charAt(j))) {
						compactTable.get(step + 1).set(j, Character.toString(binaryStep.charAt(binaryCounter)));
						expression = expression.substring(0, j) + binaryStep.charAt(binaryCounter) + expression.substring(j + 1);
					}
				}
				binaryCounter--;
			}
			if (expression.charAt(i) == '0') {
				compactTable.get(step + 1).set(i, "0");
			}
			if (expression.charAt(i) == '1') {
				compactTable.get(step + 1).set(i, "1");
			}
		}

		int currentVar = binaryStep.length();

		calcStep(expression, 0, step + 1);
		//fullTable.add(stringResults);
	}

	/**
	 * Calculates a single step. Recursively calculates parenthetical steps
	 *
	 * @param startPoint the point to start the search. used for recursive calls.
	 * initial call should be 0.
	 * @param step the step to calculate
	 * @return string (used for recursion. not needed for original call.
	 */
	public String calcStep(String step, int s, int row) {
		int endPoint = 0;
		for (int i = 0; i < step.length(); i++) {
			if (step.charAt(i) == '(') {
				int parCount = 1;
				int skip = 1;
				for (; parCount > 0; skip++) {
					if (step.charAt(skip + i) == '(') {
						parCount++;
					}
					if (step.charAt(skip + i) == ')') {
						parCount--;
					}
				}
				String parStep = calcStep(step.substring(i + 1, i + skip - 1), i + 1 + s, row);
				String result = Character.toString(parStep.charAt(0));
				parStep += result + result; //the expression is all the same number. 
				//add two more of that number to account for the parentheses
				step = step.substring(0, i) + parStep + step.substring(i + skip);
			}
		}
		String expression = Expression.getCompactExpression();
		endPoint = step.length();
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '~') {
				String result = Integer.toString(BinaryMath.not(Character.getNumericValue(step.charAt(i + 1))));
				step = step.substring(0, i) + result + result + step.substring(i + 2);
				compactTable.get(row).set(i + s, result);
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '*') {
				String result = Integer.toString(BinaryMath.and(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))));
				step = step.substring(0, i - 1) + result + result + result + step.substring(i + 2);
				compactTable.get(row).set(i + s, result);
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '+') {
				String result = Integer.toString(BinaryMath.or(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))));
				step = step.substring(0, i - 1) + result + result + result + step.substring(i + 2);
				compactTable.get(row).set(i + s, result);
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '>') {
				String result = Integer.toString(BinaryMath.implies(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))));
				step = step.substring(0, i - 1) + result + result + result + step.substring(i + 2);
				compactTable.get(row).set(i + s, result);
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '<') {
				String result = Integer.toString(BinaryMath.iff(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))));
				step = step.substring(0, i - 1) + result + result + result + step.substring(i + 2);
				compactTable.get(row).set(i + s, result);
			}
		}
		return step;
	}

	/**
	 * Generates the Compact Table and then returns it The CompactTable is a two
	 * dimensional List, or a list of lists broken up by ROWS of elements the
	 * first row is the expression, broken down to individual characters, the rest
	 * of the rows are the results one line at a time.
	 *
	 * @return fullTable
	 */
	public List<List<String>> getTable() {
		makeTable();
		return compactTable;
	}
}
