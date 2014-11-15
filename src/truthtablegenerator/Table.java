/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;


import java.util.ArrayList;
import java.util.List;

/**
 * Creates tables from logical expressions
 */
public class Table {
	private List<List <String>> fullTable = new ArrayList<>();
	private List<List <String>> compactTable = new ArrayList<>();
	
	/**
	 * Makes the full fullTable row by row
	 */
	public void makeFullTable() {
		int tableSize = (int) Math.pow(2 ,Expression.getVariableCount());
		fullTable.clear();
		fullTable.add(Expression.getFullExpression());
		for(int i = 0; i < tableSize; i++) {
			calcFullRow(i);
		}
		for (List<String> row : fullTable) {
			System.out.println(row);
		}
	}
	
	public void makeCompactTable() {
		String expression = Expression.getFullExpression().get(Expression.getFullExpression().size() - 1);
		System.out.println(expression);
		for (int i = 0; i < expression.length(); i ++) {
			ArrayList<String> s = new ArrayList<>();
			s.add(Character.toString(expression.charAt(i)));
			compactTable.add(s);
		}
		System.out.println(compactTable);
	}
	
	/**
	 * Gets the fullTable 
	 * The fullTable is a two dimensional List, or a list of lists broken up by ROWS of elements
	 * the first row is the expression, broken down to individual parts,
	 * the rest of the rows are the results one character at a time.
	 * @return fullTable
	 */
	public List<List<String>> getFullTable() {
		return fullTable;
	}
	
	/**
	 * Calculates a single row, and adds its result to the fullTable
	 * @param step the current row being calculated
	 */
	public void calcFullRow(int step) {
		int variableCount = Expression.getVariableCount();
		
		String binaryStep = Integer.toBinaryString(step);
		while (binaryStep.length() < variableCount) {
			binaryStep = "0" + binaryStep;
		}
		
		List <String> steps = new ArrayList<>(Expression.getFullExpression());
		List<Integer> results = new ArrayList<>();
		
		for (int i = 0; i < variableCount; i++) {
			results.add(Integer.parseInt(Character.toString(binaryStep.charAt(i))));
		}
		
		for (int i = variableCount; i < steps.size(); i++) {
			String currentStep = steps.get(i);
			for (int j = 0; j < variableCount; j++) {
				currentStep = currentStep.replace(steps.get(j).charAt(0), binaryStep.charAt(j));
			}
			results.add(Integer.parseInt(calcStep(currentStep, 0)));
		}
		List<String> stringResults  = new ArrayList<>();
		for (int r : results) {
			stringResults.add(Integer.toString(r));
		}
		fullTable.add(stringResults);
	}
	
	public String calcStep(String step, int startPoint) {
		int endPoint = 0;
		for (int i = startPoint; i < step.length(); i ++) {
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
				step =  step.substring(0, i) + calcStep(step.substring(i + 1, i + skip - 1), 0) + step.substring(i + skip);
			} else if (step.charAt(i) == ')') {
				endPoint = i;
				step = step.substring(startPoint, endPoint);
				startPoint = 0;
				endPoint = step.length();
				break;
			} else if (i == step.length() - 1) {
				endPoint = i;
				break;
			}
		}
		
		endPoint = step.length();
		for (int i = startPoint; i < endPoint; i++) {
			if (step.charAt(i) == '~') {
				step = step.substring(0, i) + BinaryMath.not(Character.getNumericValue(step.charAt(i + 1))) + step.substring(i + 2);
				i -= 1;
				endPoint -= 1;
			}
		}
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
}

