package com.bluesky.jct;

import java.util.List;

import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class GenericComboBox<E> {
	
	private ObservableList<E> data = FXCollections.observableArrayList();
	
	
	/**
	 * The type of the objects to be persisted with the created instance.
	 */
	private final Class<? extends E> clazz;
	
	// ---- Constructors
	
	
	/**
	 * Creates a new <code>GenericComboBoxImpl</code> instance for objects of the
	 * given type.
	 * 
	 * @param clazz
	 *            the type of the objects to be persisted with the created
	 *            instance.
	 */
	protected GenericComboBox(Class<? extends E> clazz) {
		this.clazz = clazz;
	}

	// ---- Methods

	
	/**
	 * Loads the items from the DB via REST and fills them into an ObservableList.
	 */
	public void loadData() {
		List<E> temp = RestClient.findAll(clazz);
		data.clear();
		
		try {
			for (E delegate : temp) {
				data.add(delegate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


}
