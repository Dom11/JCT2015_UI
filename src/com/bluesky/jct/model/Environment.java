package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for Environment object
 * 
 * @author Dominik
 */
public class Environment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty environmentId = new SimpleIntegerProperty();
	private StringProperty environmentName = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param environmentId, environmentName
	 */
	public Environment(int environmentId, String environmentName) {
		super();
		this.environmentId = new SimpleIntegerProperty(environmentId);
		this.environmentName = new SimpleStringProperty(environmentName);
	}
	
	
	// --- Getter and Setter
	
	public int getId() {
		return environmentId.get();
	}
	

	public IntegerProperty idProperty() {
		return environmentId;
	}
	
	
	public String getName() {
		return environmentName.get();
	}
	
	
	public void setName(String environmentName) {
		this.environmentName.set(environmentName);
	}
	

	public StringProperty nameProperty() {
		return environmentName;
	}
	
}