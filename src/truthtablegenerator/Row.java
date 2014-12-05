package truthtablegenerator;


import java.util.List;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author McAllister
 */
public class Row {
	private List<StringProperty> data;

	public Row(List<String> stringData) {
		setData(stringData);
	}
	
	private void setData(List<String> stringData) {
		for ( String s : stringData) {
			StringProperty newData = null;
			newData.set(s);
			data.add(newData);
		}
	}
	public StringProperty getDataAt(int index) {
		return data.get(index);		
	}
}
