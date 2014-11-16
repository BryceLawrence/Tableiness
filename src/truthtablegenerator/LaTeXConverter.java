/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

/**
 * Used for converting a string to an TeX version of that string.
 * @author Bryce
 */
public class LaTeXConverter {
    /**
     * Static Method to convert a string to a TeX string.
     * @param toConvert The string to be converted to TeX
     * @return 
     */
    public static String toTex(String toConvert) {
        String converted = null;
        if(!toConvert.equals(null)) {
            converted = toConvert;
            converted = converted.replaceAll("\\*", "\\land ");
            converted = converted.replaceAll("\\+", "\\lor ");
            converted = converted.replaceAll("~", "\\lnot ");
            converted = converted.replaceAll(">", "\\RightArrow ");
            converted = converted.replaceAll("<", "\\LeftRightarrow ");
            converted = converted.replaceAll("#", "\\oplus ");            
        }
            
        return converted;
    }
    
}

