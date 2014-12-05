package truthtablegenerator;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
	private List<SimpleStringProperty> dataProperty = new ArrayList<>();

	public Row(List<String> stringData) {
		setData(stringData);
	}
	
	private void setData(List<String> stringData) {
	          // System.out.println(stringData);
            for ( String s : stringData) {
			SimpleStringProperty newData = new SimpleStringProperty();
			newData.set(s);
			dataProperty.add(newData);
		}
	}
	public StringProperty getDataAt(int index) {
		return dataProperty.get(index);		
	} 
}
