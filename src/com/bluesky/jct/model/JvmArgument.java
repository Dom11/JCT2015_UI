package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class JvmArgument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty jvmId = new SimpleIntegerProperty();
	private StringProperty jvmArgument = new SimpleStringProperty();

	
	/**
	 * Default constructor.
	 */ 
//	public Domain() {
//		this(null);
//	}
	
	/**
	 * Constructor
	 * 
	 * @param domainId, domainName
	 */
	public JvmArgument(String jvmArgument) {
 		this.jvmId = new SimpleIntegerProperty();
		this.jvmArgument = new SimpleStringProperty(jvmArgument);
	}
	
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
