package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for JvmArgument object
 * 
 * @author Dominik
 */
public class JvmArgument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty jvmId = new SimpleIntegerProperty();
	private StringProperty jvmArgument = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param jvmArgument
	 */
	public JvmArgument(String jvmArgument) {
		super();
 		this.jvmId = new SimpleIntegerProperty();
		this.jvmArgument = new SimpleStringProperty(jvmArgument);
	}
	
	
	// --- Getter and Setter
	
	public int getId() {
		return jvmId.get();
	}
	

	public IntegerProperty idProperty() {
		return jvmId;
	}
	
	
	public String getName() {
		return jvmArgument.get();
	}
	
	
	public void setName(String jvmArgument) {
		this.jvmArgument.set(jvmArgument);
	}

	
	public StringProperty nameProperty() {
		return jvmArgument;
	}
	
}