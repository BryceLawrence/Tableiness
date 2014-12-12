package truthtablegenerator;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A class that holds a row from the truth table
 * @author McAllister
 */
public class Row {

	private List<SimpleStringProperty> dataProperty = new ArrayList<>();

	/**
	 * Adds a given list of strings to the object
	 * @param stringData list of strings to add to the row
	 */
	public Row(List<String> stringData) {
		setData(stringData);
	}

	/**
	 * converts the strings into SimpleStringProperties so it can be used by TableView
	 * @param stringData the list of strings to convert
	 */
	private void setData(List<String> stringData) {
		for (String s : stringData) {
			SimpleStringProperty newData = new SimpleStringProperty();
			newData.set(s);
			dataProperty.add(newData);
		}
	}
	
	/**
	 * get an element at index
	 * @param index the index
	 * @return the data at the index
	 */
	public StringProperty getDataAt(int index) {
		return dataProperty.get(index);
	}
}
