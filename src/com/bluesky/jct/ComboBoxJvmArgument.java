package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.rest.RestClient;


public class ComboBoxJvmArgument {
	
	private static ObservableList<JvmArgument> jvmData = FXCollections.observableArrayList();
	
	
	public ComboBoxJvmArgument() {
	}
	

	/**
	 * Loads the items from the DB via REST and fills them into an ObservableList.
	 */
	public static void loadJvmArgumentData() {
		List<JvmArgument> jvms = RestClient.findAllJvmArgument();
		jvmData.clear();
		
		try {
			for (JvmArgument jvm : jvms) {
				jvmData.add(jvm);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an observable list.
	 * 
	 * @return environmentData
	 */
	public static ObservableList<JvmArgument> getJvmArgumentData() {
		return jvmData;
	}
	
	
	/**
	 * Initialization of ComboBox Environment
	 */
	public static void iniJvmArgumentCombobox(ComboBox<JvmArgument> jvmArgumentComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		jvmArgumentComboBox.setCellFactory((comboBox) -> {
			return new ListCell<JvmArgument>() {
				@Override
				protected void updateItem(JvmArgument item, boolean empty) {
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
		jvmArgumentComboBox.setConverter(new StringConverter<JvmArgument>() {
			@Override
			public String toString(JvmArgument jvmArgument) {
				if (jvmArgument == null) {
					return null;
				} else {
					return jvmArgument.getName();
				}
			}

			@Override
			public JvmArgument fromString(String jvmArgumentString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}
