package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Jbar;
import com.bluesky.jct.rest.RestClient;


/**
 * This is the controller class for comboBox @category which contains all the methods
 * needed to use and work with it from different classes.
 * 
 * @category Jbar
 * @author Dominik
 */
public class ComboBoxJbar {
	
	private static ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	
	
	/**
	 * Loads the items from the DB through a REST call and fills them into an ObservableList.
	 */
	public static void loadJbarData() {
		List<Jbar> jbars = RestClient.findAllJbar();
		jbarData.clear();
		
		try {
			for (Jbar jbar : jbars) {
				jbarData.add(jbar);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an ObservableList.
	 * 
	 * @return jbarData
	 */
	public static ObservableList<Jbar> getJbarData() {
		return jbarData;
	}
	
	
	/**
	 * Initialization of the ComboBox
	 */
	public static void iniJbarCombobox(ComboBox<Jbar> jbarComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		jbarComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Jbar>() {
				@Override
				protected void updateItem(Jbar item, boolean empty) {
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
		jbarComboBox.setConverter(new StringConverter<Jbar>() {
			@Override
			public String toString(Jbar jbar) {
				if (jbar == null) {
					return null;
				} else {
					return jbar.getName();
				}
			}

			@Override
			public Jbar fromString(String jbarString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}