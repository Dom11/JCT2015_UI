package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Host;
import com.bluesky.jct.rest.RestClient;


/**
 * This is the controller class for comboBox @category which contains all the methods
 * needed to use and work with it from different classes.
 * 
 * @category Host
 * @author Dominik
 */
public class ComboBoxHost {
	
	private static ObservableList<Host> hostData = FXCollections.observableArrayList();
	
	
	/**
	 * Loads the items from the DB through a REST call and fills them into an ObservableList.
	 */
	public static void loadHostData() {
		List<Host> hosts = RestClient.findAllHost();
		hostData.clear();
		
		try {
			for (Host host : hosts) {
				hostData.add(host);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns the loaded data as an ObservableList.
	 * 
	 * @return hostData
	 */
	public static ObservableList<Host> getHostData() {
		return hostData;
	}
	
	
	/**
	 * Initialization of the ComboBox
	 */
	public static void iniHostCombobox(ComboBox<Host> hostComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		hostComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Host>() {
				@Override
				protected void updateItem(Host item, boolean empty) {
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
		hostComboBox.setConverter(new StringConverter<Host>() {
			@Override
			public String toString(Host host) {
				if (host == null) {
					return null;
				} else {
					return host.getName();
				}
			}

			@Override
			public Host fromString(String hostString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}