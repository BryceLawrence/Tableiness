package truthtablegenerator;

/**
 * Used for converting a string to an TeX version of that string.
 *
 * @author Bryce, McAllister, Tyler
 */
public class LaTeXConverter {

	/**
	 * Static Method to convert a string to a TeX string.
	 *
	 * @param toConvert The string to be converted to TeX
	 * @return
	 */
	public static String toTex(String toConvert) {
		String converted = null;
		if (!toConvert.equals(null)) {
			converted = toConvert;
			converted = converted.replaceAll("\\*", "\\\\land ");
			converted = converted.replaceAll("\\+", "\\\\lor ");
			converted = converted.replaceAll("~", "\\\\lnot ");
			converted = converted.replaceAll(">", "\\\\rightarrow ");
			converted = converted.replaceAll("<", "\\\\leftrightarrow ");
			converted = converted.replaceAll("#", "\\\\oplus ");
		}

		return converted;
	}

	/**
	 * converts a string into a LaTeX table renderable string
	 *
	 * @param toConvert
	 * @return the formated string
	 */
	public static String toTexTable(String toConvert) {
		String converted = null;
		if (!toConvert.equals(null)) {
			converted = toConvert;
			converted = converted.replaceAll("\\*", "\\$\\\\land \\$");
			converted = converted.replaceAll("\\+", "\\$\\\\lor \\$");
			converted = converted.replaceAll("~", "\\$\\\\lnot \\$");
			converted = converted.replaceAll(">", "\\$\\\\rightarrow \\$");
			converted = converted.replaceAll("<", "\\$\\\\leftrightarrow \\$");
			converted = converted.replaceAll("#", "\\$\\\\oplus \\$");
		}

		return converted;
	}
}
