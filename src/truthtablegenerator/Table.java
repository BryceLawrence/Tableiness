/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Table {
	private List<List <String>> table = new ArrayList<>();
	
	/**
	 * Makes the full table row by row
	 */
	public void makeFullTable() {
		int tableSize = (int) Math.pow(2 ,Expression.getSize());
		table.clear();
		table.add(Expression.getFullExpression());
		for(int i = 0; i < tableSize; i++) {
			calcFullRow(i);
		}
		for (List<String> row : table) {
			System.out.println(row);
		}
	}
	
	/**
	 * Gets the table 
	 * The table is a two dimensional List, or a list of lists broken up by ROWS of elements
	 * the first row is the expression, broken down to individual parts,
	 * the rest of the rows are the results one character at a time.
	 * @return table
	 */
	public List<List<String>> getFullTable() {
		
		return table;
	}
	
	/**
	 * Calculates a single row, and adds its result to the table
	 * @param step the current row being calculated
	 */
	public void calcFullRow(int step) {
		int variableCount = Expression.getSize();
		
		String binaryStep = Integer.toBinaryString(step);
		while (binaryStep.length() < variableCount) {
			binaryStep = "0" + binaryStep;
		}
		
		List <String> steps = new ArrayList<>(Expression.getFullExpression());
		List<Integer> results = new ArrayList<>();
		
		for (int i = 0; i < variableCount; i ++) {
			results.add(Integer.parseInt(Character.toString(binaryStep.charAt(i))));
		}
		
		if (steps.size() > 1) {	// if there is just a single variable without steps, skip this
			String phrase = steps.get(variableCount);
			for (int i = variableCount; i < steps.size(); i++) {
				phrase = steps.get(i);
				for (int j = i - 1; j  >= variableCount; j--) {
					if (steps.get(i).contains(steps.get(j))) {
						// first try to replace surrounded in (); (a*b)*c, a*b wont be surrounded in () in last step so add it
						phrase = phrase.replace("(" + steps.get(j) + ")", Integer.toString(results.get(j)));
						//then try to replace without (); a*b*c, doenst have () so try without
						phrase = phrase.replace(steps.get(j), Integer.toString(results.get(j)));
					}
				}
				for (int j = 0; j < variableCount; j++) {
					phrase = phrase.replace(steps.get(j).charAt(0), binaryStep.charAt(j));
				}
				if (phrase.length() == 2) {
					results.add(BinaryMath.not(Character.getNumericValue(phrase.charAt(1))));
				} else {
					switch (phrase.charAt(1)) {
						case '*':
							results.add(BinaryMath.and(Character.getNumericValue(phrase.charAt(0)), 
							Character.getNumericValue(phrase.charAt(2))));
							break;
						case '+':
							results.add(BinaryMath.or(Character.getNumericValue(phrase.charAt(0)), 
							Character.getNumericValue(phrase.charAt(2))));
							break;
						case '>':
							results.add(BinaryMath.implies(Character.getNumericValue(phrase.charAt(0)), 
							Character.getNumericValue(phrase.charAt(2))));
							break;
						case '<':
							results.add(BinaryMath.iff(Character.getNumericValue(phrase.charAt(0)), 
							Character.getNumericValue(phrase.charAt(2))));
							break;
					}
				}
			}
		}
		List<String> stringResults  = new ArrayList<>();
		for (int r : results) {
			stringResults.add(Integer.toString(r));
		}
		table.add(stringResults);
	}
}

