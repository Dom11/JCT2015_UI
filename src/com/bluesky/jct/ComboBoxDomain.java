package com.bluesky.jct;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import com.bluesky.jct.model.Domain;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;


/**
 * This is the controller class for comboBox @category which contains all the methods
 * needed to use and work with it from different classes.
 * 
 * @category Domain
 * @author Dominik
 */
public class ComboBoxDomain {
	
	private static ObservableList<Domain> domainData = FXCollections.observableArrayList();

	
	/**
	 * Loads the items from the DB through a REST call and fills them into an ObservableList.
	 */
	public static void loadDomainData() {
		List<Domain> domains = RestClient.findAllDomain(); 
		domainData.clear();
		
		try {
			for (Domain domain : domains) {
				domainData.add(domain);
			}
		} catch (Exception e) {

			String headerText = "Domain ComboBox";
			String contentText = "Please contact the System Administrator!";
			ExceptionHandling.handleError(headerText, contentText);
		}
	}
	
	
	/**
	 * Returns the loaded data as an ObservableList.
	 * 
	 * @return domainData
	 */
	public static ObservableList<Domain> getDomainData() {
		return domainData;
	}
	
	
	/**
	 * Initialization of the ComboBox
	 */
	public static void iniDomainCombobox(ComboBox<Domain> domainComboBox) {
		// Define rendering of the list of values in ComboBox drop down.
		domainComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Domain>() {
				@Override
				protected void updateItem(Domain item, boolean empty) {
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
		domainComboBox.setConverter(new StringConverter<Domain>() {
			@Override
			public String toString(Domain domain) {
				if (domain == null) {
					return null;
				} else {
					return domain.getName();
				}
			}

			@Override
			public Domain fromString(String domainString) {
				return null; // No conversion fromString needed.
			}
		});
	}

}