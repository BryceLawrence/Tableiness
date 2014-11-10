/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

import com.oracle.deploy.update.UpdateCheck;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.objects.NativeString;

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
			for (int j = i - 1; j  >=  variableCount; j --) {
				// replace all instances
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
		System.out.println(binaryStep + " = " + results.get(steps.size() - 1));
	}
}

