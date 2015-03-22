package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for Prefix object
 * 
 * @author Dominik
 */
public class Prefix implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty prefixId = new SimpleIntegerProperty();
	private StringProperty prefixName = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param jbarId, jbarName
	 */
	public Prefix(int prefixId, String prefixName) {
		super();
		this.prefixId = new SimpleIntegerProperty(prefixId);
		this.prefixName = new SimpleStringProperty(prefixName);
	}
	
	
	// --- Getter and Setter
	
	public int getId() {
		return prefixId.get();
	}
	

	public IntegerProperty idProperty() {
		return prefixId;
	}
	
	
	public String getName() {
		return prefixName.get();
	}
	

	public void setName(String prefixName) {
		this.prefixName.set(prefixName);
	}
	

	public StringProperty nameProperty() {
		return prefixName;
	}
	
}