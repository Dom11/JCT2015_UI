package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Prefix;
import com.bluesky.jct.rest.RestClient;


/**
 * This is the controller class for comboBox @category which contains all the methods
 * needed to use and work with it from different classes.
 * 
 * @category Prefix
 * @author Dominik
 */
public class ComboBoxPrefix {
	
	private static ObservableList<Prefix> prefixData = FXCollections.observableArrayList();
	
	
	/**
	 * Loads the items from the DB through a REST call and fills them into an ObservableList.
	 */
	public static void loadPrefixData() {
		List<Prefix> prefixes = RestClient.findAllPrefix();
		prefixData.clear();
		
		try {
			for (Prefix prefix : prefixes) {
				prefixData.add(prefix);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an ObservableList.
	 * 
	 * @return prefixData
	 */
	public static ObservableList<Prefix> getPrefixData() {
		return prefixData;
	}
	
	
	/**
	 * Initialization of the ComboBox
	 */
	public static void iniPrefixCombobox(ComboBox<Prefix> prefixComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		prefixComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Prefix>() {
				@Override
				protected void updateItem(Prefix item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName());
					}
				}
			};
		});
		
		// Define rendering of selected value shown in ComboBox.
		prefixComboBox.setConverter(new StringConverter<Prefix>() {
			@Override
			public String toString(Prefix prefix) {
				if (prefix == null) {
					return null;
				} else {
					return prefix.getName();
				}
			}

			@Override
			public Prefix fromString(String prefixString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}