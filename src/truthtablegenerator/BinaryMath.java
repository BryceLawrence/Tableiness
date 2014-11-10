/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;

/**
 * Collection of binary math operators.
 */
public class BinaryMath {
	/**
	 * NEGATES a given variable
	 * @param RHS The variable to negate
	 * @return ~RHS
	 */
	public static int not (int RHS) {
		return (1 - RHS); // 1-0=1, 1-1=0
	}
	
	/**
	 * ANDs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS /\ RHS
	 */
	public static int and (int LHS, int RHS) {
		return LHS * RHS; // 1*1=1, 0*1=0, 0*0=0
	}
	
	/**
	 * ORs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS \/ RHS
	 */
	public static int or(int LHS, int RHS) {
		return ((LHS + RHS) > 0) ? 1 : 0; // 0+0=0, 1+0=1, 1+1=2
	}
	
	/**
	 * Does LHS IMPLY RHS
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS --> RHS
	 */
	public static int implies(int LHS, int RHS) {
		return ((LHS - RHS) == 1) ? 0 : 1; // 1-0=1, 0-1=0, 1-1=0, 0-0=0
	}
	
	/**
	 * Does LHS IMPLY RHS and does RHS IMPLY LHS (does RHS == LHS)
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS &lt;-&gt; RHS 
	 */
	public static int iff(int LHS, int RHS) {
		return (LHS == RHS) ? 1 : 0; // must be equal
	}
	
	/**
	 * NEGATES a given variable
	 * @param RHS The variable to negate
	 * @return ~RHS
	 */
	public static int not (String RHS) {
		return (1 - Integer.parseInt(RHS)); // 1-0=1, 1-1=0
	}
	
	/**
	 * ANDs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS /\ RHS
	 */
	public static int and (String LHS, String RHS) {
		return Integer.parseInt(LHS) * Integer.parseInt(RHS); // 1*1=1, 0*1=0, 0*0=0
	}
	
	/**
	 * ORs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS \/ RHS
	 */
	public static int or(String LHS, String RHS) {
		return ((Integer.parseInt(LHS) + Integer.parseInt(RHS)) > 0) ? 1 : 0; // 0+0=0, 1+0=1, 1+1=2
	}
	
	/**
	 * Does LHS IMPLY RHS
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS --> RHS
	 */
	public static int implies(String LHS, String RHS) {
		return ((Integer.parseInt(LHS) - Integer.parseInt(RHS)) == 1) ? 0 : 1; // 1-0=1, 0-1=0, 1-1=0, 0-0=0
	}
	
	/**
	 * Does LHS IMPLY RHS and does RHS IMPLY LHS (does RHS == LHS)
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS &lt;-&gt; RHS 
	 */
	public static int iff(String LHS, String RHS) {
		return (Integer.parseInt(LHS) == Integer.parseInt(RHS)) ? 1 : 0; // must be equal
	}
	
	/**
	 * NEGATES a given variable
	 * @param RHS The variable to negate
	 * @return ~RHS
	 */
	public static int not (char RHS) {
		return (1 - Character.getNumericValue(RHS)); // 1-0=1, 1-1=0
	}
	
	/**
	 * ANDs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS /\ RHS
	 */
	public static int and (char LHS, char RHS) {
		return Character.getNumericValue(LHS) * Character.getNumericValue(RHS); // 1*1=1, 0*1=0, 0*0=0
	}
	
	/**
	 * ORs two variables
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS \/ RHS
	 */
	public static int or(char LHS, char RHS) {
		return ((Character.getNumericValue(LHS) + Character.getNumericValue(RHS)) > 0) ? 1 : 0; // 0+0=0, 1+0=1, 1+1=2
	}
	
	/**
	 * Does LHS IMPLY RHS
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS --> RHS
	 */
	public static int implies(char LHS, char RHS) {
		return ((Character.getNumericValue(LHS) - Character.getNumericValue(RHS)) == 1) ? 0 : 1; // 1-0=1, 0-1=0, 1-1=0, 0-0=0
	}
	
	/**
	 * Does LHS IMPLY RHS and does RHS IMPLY LHS (does RHS == LHS)
	 * @param LHS Left hand side
	 * @param RHS Right hand side
	 * @return LHS &lt;-&gt; RHS 
	 */
	public static int iff(char LHS, char RHS) {
		return (Character.getNumericValue(LHS) == Character.getNumericValue(RHS)) ? 1 : 0; // must be equal
	}
}
