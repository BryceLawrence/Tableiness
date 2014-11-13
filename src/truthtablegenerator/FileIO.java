/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author McAllister
 */
public class FileIO {
	public String loadExpression(String fileName) {
		String expression = null;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(fileName));
			expression = fin.readLine();
		} catch (IOException e)  {
			System.out.println("ERROR Reading file " + fileName);
		}
		return expression;
	}
	
	public void saveExpression(String fileName, String expression) {
		if (!fileName.contains(".txt")) {
			fileName += ".txt"; 
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);
			PrintWriter fout = new PrintWriter(writer);
			fout.printf(expression, null);
		} catch (IOException e) {
			System.out.println("ERROR writing files");
		} finally {
			try {
				writer.close();
			} catch (IOException ex) {
				System.out.println("ERROR closing file");
			}
		}
	}
	
}
