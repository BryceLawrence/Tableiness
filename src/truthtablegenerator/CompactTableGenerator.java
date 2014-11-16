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
public class CompactTableGenerator {
	private List<List <String>> compactTable = new ArrayList<>();
	private List<Integer> displayOrder = new ArrayList<>();
	
	/**
	 * Makes the full fullTable row by row
	 */
	private void makeTable() {
		int tableSize = (int) Math.pow(2 ,Expression.getVariableCount());
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
		
		expressionRow.add("Result"); // add a result row to quickly see the result without finding the last step
		emptyRow.add(" "); // add one more space for the result row
		compactTable.add(expressionRow);
		
		for (int i = 1; i < tableSize; i++) {
			compactTable.add(emptyRow);
		}
		
		
		for(int i = 0; i < tableSize; i++) {
			//calcRow(i);
		}
		
		for (List<String> row : compactTable) {
			System.out.println(row);
		}
	}
	
	/**
	 * Calculates a single row, and adds its result to the fullTable
	 * @param step the current row being calculated
	 */
	private void calcFullRow(int step) {
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
			results.add(Integer.parseInt(calcStep(currentStep)));
		}
		List<String> stringResults  = new ArrayList<>();
		for (int r : results) {
			stringResults.add(Integer.toString(r));
		}
		//fullTable.add(stringResults);
	}
	
	/**
	 * Calculates a single step. Recursively calculates parenthetical steps
	 * @param step the step to calculate
	 * @return 
	 */
	public String calcStep(String step) {
		int endPoint = 0;
		for (int i = 0; i < step.length(); i ++) {
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
				step =  step.substring(0, i) + calcStep(step.substring(i + 1, i + skip - 1)) + step.substring(i + skip);
			} 
		}
		endPoint = step.length();
		for (int i = 0; i < endPoint; i++) {
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
	
	/**
	 * Generates the Compact Table and then returns it
	 * The CompactTable is a two dimensional List, or a list of lists broken up by ROWS of elements
	 * the first row is the expression, broken down to individual characters,
	 * the rest of the rows are the results one line at a time.
	 * @return fullTable
	 */
	public List<List<String>> getTable() {
		makeTable();
		return compactTable;
	}
}

