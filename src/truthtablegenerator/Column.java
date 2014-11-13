/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;
import java.util.List;
import java.util.ArrayList;

/**
 * A column class. Stores a string header and a int list body
 */
public class Column {
	private String phase = null;
	private List <Integer> values = new ArrayList<>();
	
	/**
	 * Gets the header phase (a single character length string or longer)
	 * @return the phrase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * Sets the header phase (a single character length string or longer)
	 * @param phase the String to set the phrase to.
	 */
	public void setPhase(String phase) {
		this.phase = phase;
	}

	/**
	 * Gets the list of int values
	 * @return the list of int vales
	 */
	public List<Integer> getValues() {
		return values;
	}
	
	/**
	 * Adds a single int to the list
	 * @param value the value to add
	 */
	public void addValue(int value) {
		values.add(value);
	}	
	
	/**
	 * Adds a List of ints to the list values
	 * @param values the list of ints to add
	 */
	public void addMultipleValues(List<Integer> values) {
		this.values.addAll(values);
	}
}
