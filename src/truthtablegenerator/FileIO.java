package truthtablegenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Handles saving and loading
 *
 * @author Bryce, McAllister, Tyler
 */
public class FileIO {

	/**
	 * Loads the help contents
	 *
	 * @param target file to load
	 * @return the string of contents to display
	 */
	public String loadHelpContents(String target) {
		String contents = "";
		try {
			BufferedReader fin = new BufferedReader(new FileReader(("src/resources/help" + target + ".txt")));
			String line;
			while ((line = fin.readLine()) != null) {
				contents += line + "\n";
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("ERROR Reading Help File");
		}

		return contents;
	}

	/**
	 * Loads an expression from a file
	 *
	 * @param fileName the target file to load
	 * @return the string expression
	 */
	public String loadExpression(String fileName) {
		String expression = null;
		try {
			BufferedReader fin = new BufferedReader(new FileReader(fileName));
			expression = fin.readLine();
		} catch (IOException e) {
			System.out.println("ERROR Reading file " + fileName);
		}
		return expression;
	}

	/**
	 * Saves the an expression string in a txt file
	 *
	 * @param fileName where to save the file
	 * @param expression the expression to save
	 */
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

	/**
	 * Saves the full and compact tables so a LaTeX editor can render it
	 *
	 * @param fileName The File the table is to be saved to
	 * @param compact The compact table
	 * @param full The full table
	 */
	public void saveLaTeXTable(String fileName, List<List<String>> compact, List<List<String>> full) {
		if (!fileName.contains(".txt")) {
			fileName += ".txt";
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);
			PrintWriter fout = new PrintWriter(writer);
			fout.println("*****************");
			fout.println("*  FULL TABLE   *");
			fout.println("*****************");
			fout.println();

			writeTable(fout, compact);

			fout.println("*****************");
			fout.println("* COMPACT TABLE *");
			fout.println("*****************");
			fout.println();

			writeTable(fout, full);

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

	/**
	 * Writes a table to the file in LaTeX format
	 *
	 * @param fout the PrintWriter used to write to the file
	 * @param table the table to write
	 * @throws IOException
	 */
	private void writeTable(PrintWriter fout, List<List<String>> table) throws IOException {
		// Establish correct beginning table parameters
		fout.print("\\begin{tabular}[c]{");
		for (int c = 0; c < table.get(0).size(); c++) {
			fout.print("c ");
		}
		fout.println("}");

		for (int i = 0; i < table.size(); i++) {
			List<String> row = table.get(i);
			for (int j = 0; j < row.size(); j++) {
				// generage LaTeX header row
				if (row.get(j).contains("*") || row.get(j).contains("+")
						|| row.get(j).contains(">") || row.get(j).contains("<")
						|| row.get(j).contains("~") || row.get(j).contains("#")) {
					fout.print(LaTeXConverter.toTexTable(row.get(j)));
				} else {
					fout.print(row.get(j)); // insert cell contents
				}

				if (j == (row.size() - 1)) {

				} else {
					fout.print(" & "); // designates next column
				}
			}

			if (i == (table.size() - 1)) {
				fout.println("");
			} else if (i == 0) {
				fout.println("\\\\ \\hline"); // adds line under header
			} else {
				fout.println("\\\\"); // designates next row
			}
		}
		fout.println("\\end{tabular}");
		fout.println();
	}
}
