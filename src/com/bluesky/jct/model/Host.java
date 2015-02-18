package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Host implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty hostId = new SimpleIntegerProperty();
	private StringProperty hostName = new SimpleStringProperty();

	
	/**
	 * Default constructor.
	 */ 
	public Host() {
		this(0, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param jbarId, jbarName
	 */
	public Host(int hostId, String hostName) {
		this.hostId = new SimpleIntegerProperty(hostId);
		this.hostName = new SimpleStringProperty(hostName);
	}
	
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
