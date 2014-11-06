/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthtablegenerator;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author McAllister
 */
public class Column {
	private String phase = null;
	private List <Integer> values = new ArrayList<>();

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void addValue(int value) {
		values.add(value);
	}	
}
