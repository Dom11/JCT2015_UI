package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for Jbar object
 * 
 * @author Dominik
 */
public class Jbar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty jbarId = new SimpleIntegerProperty();
	private StringProperty jbarName = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param jbarId, jbarName
	 */
	public Jbar(int jbarId, String jbarName) {
		super();
		this.jbarId = new SimpleIntegerProperty(jbarId);
		this.jbarName = new SimpleStringProperty(jbarName);
	}
	
	
	// --- Getter and Setter
	
	public int getId() {
		return jbarId.get();
	}
	

	public IntegerProperty idProperty() {
		return jbarId;
	}
	
	
	public String getName() {
		return jbarName.get();
	}
	

	public void setName(String jbarName) {
		this.jbarName.set(jbarName);
	}
	

	public StringProperty nameProperty() {
		return jbarName;
	}
	
}