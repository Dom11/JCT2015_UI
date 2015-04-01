package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Environment;
import com.bluesky.jct.rest.RestClient;


/**
 * This is the controller class for comboBox @category which contains all the methods
 * needed to use and work with it from different classes.
 * 
 * @category Environment
 * @author Dominik
 */
public class ComboBoxEnvironment {
	
	private static ObservableList<Environment> environmentData = FXCollections.observableArrayList();
	

	/**
	 * Loads the items from the DB through a REST call and fills them into an ObservableList.
	 */
	public static void loadEnvironmentData() {
		List<Environment> environments = RestClient.findAllEnvironment();
		environmentData.clear();
		
		try {
			for (Environment environment : environments) {
				environmentData.add(environment);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an ObservableList.
	 * 
	 * @return environmentData
	 */
	public static ObservableList<Environment> getEnvironmentData() {
		return environmentData;
	}
	
	
	/**
	 * Initialization of the ComboBox
	 */
	public static void iniEnvironmentCombobox(ComboBox<Environment> environmentComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		environmentComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Environment>() {
				@Override
				protected void updateItem(Environment item, boolean empty) {
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
		environmentComboBox.setConverter(new StringConverter<Environment>() {
			@Override
			public String toString(Environment environment) {
				if (environment == null) {
					return null;
				} else {
					return environment.getName();
				}
			}

			@Override
			public Environment fromString(String environmentString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}