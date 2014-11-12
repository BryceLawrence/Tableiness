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
	private List <Column> columns = new ArrayList<>();
	private int size = 0;
	
	public void run() {
		int tableSize = (int) Math.pow(2 ,Expression.getSize());
		for(int i = 0; i < tableSize; i++) {
			calcRow(i);
		}
	}
	
	public void calcRow(int step) {
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
		System.out.println(results);
		/*
		for each step x
			for each prior step in reverse order y
				if step contains stepY
					replace stepX = (stepX-stepY)+resY
			replace remaining variables with binary
			evaluate step -> resX
		*/

		
		/*
		List <Integer> stepResults = new ArrayList<>();
		String binaryStep = Integer.toBinaryString(step);
		while (binaryStep.length() < variableCount) {
			binaryStep = "0" + binaryStep;
		}
		List <String> steps = new ArrayList<>(Expression.getFullExpression());
		for (int i = 0; i < variableCount; i ++) {
			for (int j = steps.size() - 1; j  >= 0 + i; j--) {
				steps.set(j, steps.get(j).replace("(" + steps.get(i) + ")", Character.toString(binaryStep.charAt(i))));
				steps.set(j, steps.get(j).replace(steps.get(i), Character.toString(binaryStep.charAt(i))));
			}
		}
		List<Integer> results = new ArrayList<>();
		//start of the list with the variables
		for (int i = 0; i < variableCount; i ++) {
			results.add(Integer.parseInt(steps.get(i)));
		}
System.out.println();
		List <String> steps2 =Expression.getFullExpression();
		for (int i = variableCount; i < steps.size(); i ++) {
System.out.println("Step: " + i);
			for (int j = i - 1; j  >=  variableCount; j --) {
				// replace all instances
System.out.println(steps.get(i) + " Replace " + steps.get(j) + "with result = " + results.get(j));
				steps.set(i, steps.get(i).replace(steps.get(j), Integer.toString(results.get(j))));
			}
			if (steps.get(i).length() == 2) {
				results.add(BinaryMath.not(steps.get(i).charAt(1)));
			} else {
				switch (steps.get(i).charAt(1)) {
					case '*':
						results.add(BinaryMath.and(Character.getNumericValue(steps.get(i).charAt(0)), 
						Character.getNumericValue(steps.get(i).charAt(2))));
						break;
					case '+':
						results.add(BinaryMath.or(Character.getNumericValue(steps.get(i).charAt(0)), 
						Character.getNumericValue(steps.get(i).charAt(2))));
						break;
					case '>':
						results.add(BinaryMath.implies(Character.getNumericValue(steps.get(i).charAt(0)), 
						Character.getNumericValue(steps.get(i).charAt(2))));
						break;
					case '<':
						results.add(BinaryMath.iff(Character.getNumericValue(steps.get(i).charAt(0)), 
						Character.getNumericValue(steps.get(i).charAt(2))));
						break;
				}
			}
		}
		System.out.println(binaryStep + " = " + results.get(steps.size() - 1));*/
	}
}

