package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Domain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty domainId = new SimpleIntegerProperty();
	private StringProperty domainName = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param domainId, domainName
	 */
	public Domain(String domainName) {
		super();
 		this.domainId = new SimpleIntegerProperty();
		this.domainName = new SimpleStringProperty(domainName);
	}
	
	public int getId() {
		return domainId.get();
	}

	public IntegerProperty idProperty() {
		return domainId;
	}
	
	public String getName() {
		return domainName.get();
	}
	
	public void setName(String domainName) {
		this.domainName.set(domainName);
	}

	public StringProperty nameProperty() {
		return domainName;
	}
}
