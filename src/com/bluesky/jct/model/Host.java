package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for Host object
 * 
 * @author Dominik
 */
public class Host implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty hostId = new SimpleIntegerProperty();
	private StringProperty hostName = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param jbarId, jbarName
	 */
	public Host(String hostName) {
		super();
		this.hostId = new SimpleIntegerProperty();
		this.hostName = new SimpleStringProperty(hostName);
	}
	
	
	// --- Getter and Setter
	
	public int getId() {
		return hostId.get();
	}

	
	public IntegerProperty idProperty() {
		return hostId;
	}
	
	
	public String getName() {
		return hostName.get();
	}
	

	public void setName(String hostName) {
		this.hostName.set(hostName);
	}
	

	public StringProperty nameProperty() {
		return hostName;
	}
	
}