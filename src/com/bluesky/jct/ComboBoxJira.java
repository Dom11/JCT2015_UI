package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Jira;
import com.bluesky.jct.rest.RestClient;


public class ComboBoxJira {
	
	private static ObservableList<Jira> jiraData = FXCollections.observableArrayList();
	
	
	public ComboBoxJira() {
	}
	

	/**
	 * Loads the items from the DB via REST and fills them into an ObservableList.
	 */
	public static void loadJiraData() {
		List<Jira> jiras = RestClient.findAllJira();
		jiraData.clear();
		
		try {
			for (Jira jira : jiras) {
				jiraData.add(jira);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an observable list.
	 * 
	 * @return jiraData
	 */
	public static ObservableList<Jira> getJiraData() {
		return jiraData;
	}
	
	
	/**
	 * Initialization of ComboBox Jira
	 */
	public static void iniJiraCombobox(ComboBox<Jira> jiraComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		jiraComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Jira>() {
				@Override
				protected void updateItem(Jira item, boolean empty) {
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
		jiraComboBox.setConverter(new StringConverter<Jira>() {
			@Override
			public String toString(Jira jira) {
				if (jira == null) {
					return null;
				} else {
					return jira.getName();
				}
			}

			@Override
			public Jira fromString(String jiraString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}