package truthtablegenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates tables from logical expressions
 * 
 * @author Bryce, McAllister, Tyler
 */
public class FullTableGenerator {

	private List<List<String>> fullTable = new ArrayList<>();
	/**
	 * Makes the full fullTable row by row
	 */
	private void makeFullTable() {
		int tableSize = (int) Math.pow(2, Expression.getVariableCount());
		fullTable.clear();
		fullTable.add(Expression.getFullExpression());
		for (int i = 0; i < tableSize; i++) {
			calcRow(i);
		}
	}

	/**
	 * Calculates a single row, and adds its result to the fullTable
	 *
	 * @param step the current row being calculated
	 */
	public void calcRow(int step) {
		int variableCount = Expression.getVariableCount();

		String binaryStep = Integer.toBinaryString(step);
		while (binaryStep.length() < variableCount) {
			binaryStep = "0" + binaryStep; // " step 2 = '10' but if i have 3 steps i need '010'
		}

		List<String> steps = new ArrayList<>(Expression.getFullExpression());
		List<Integer> results = new ArrayList<>();

		for (int i = 0; i < variableCount; i++) {
			results.add(Integer.parseInt(Character.toString(binaryStep.charAt(i))));
		}

		for (int i = variableCount; i < steps.size(); i++) {
			String currentStep = steps.get(i);
			for (int j = 0; j < variableCount; j++) {
				currentStep = currentStep.replace(steps.get(j).charAt(0), binaryStep.charAt(j));
			}
			results.add(Integer.parseInt(calcStep(currentStep)));
		}
		List<String> stringResults = new ArrayList<>();
		for (int r : results) {
			stringResults.add(Integer.toString(r));
		}
		fullTable.add(stringResults);
	}

	/**
	 * Calculates a single step. Recursively calculates parenthetical steps
	 *
	 * @param step the step to calculate
	 * @return the string result
	 */
	public String calcStep(String step) {
		int endPoint = 0;
		//find all outer parenthetical steps ( ...(...)...) only care about outside set
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
				//evaluate that parenthetical step
				step = step.substring(0, i) + calcStep(step.substring(i + 1, i + skip - 1)) + step.substring(i + skip);
			}
		}
		//how big is the string
		endPoint = step.length();
		
		// replace all not expressions (! and variable/constant) with what they evaluate. move i back and reduce endpoint by one to account for the missing character ("!1" -> "0")
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '~') {
				step = step.substring(0, i) + BinaryMath.not(Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 1;
				endPoint -= 1;
			}
		}
		
		//next steps folow order of operations and or imply then iff
		// replace all binary expressions (1 * 1) with what they evaluate. move i back and reduce endpoint by TWO to account for the missing character ("1*0" -> "0")
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '*') {
				step = step.substring(0, i - 1) + BinaryMath.and(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 2;
				endPoint -= 2;
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '+') {
				step = step.substring(0, i - 1) + BinaryMath.or(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 2;
				endPoint -= 2;
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '>') {
				step = step.substring(0, i - 1) + BinaryMath.implies(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 2;
				endPoint -= 2;
			}
		}
		for (int i = 0; i < endPoint; i++) {
			if (step.charAt(i) == '<') {
				step = step.substring(0, i - 1) + BinaryMath.iff(Character.getNumericValue(step.charAt(i - 1)),
						Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 2;
				endPoint -= 2;
			}
		}
		return step;
	}

	/**
	 * Generates the full table and then returns it. The fullTable is a two
	 * dimensional List, or a list of lists broken up by ROWS of elements the
	 * first row is the expression, broken down to individual Steps, the rest of
	 * the rows are the results one line at a time.
	 *
	 * @return fullTable
	 */
	public List<List<String>> getTable() {
		makeFullTable();
		return fullTable;
	}
}
