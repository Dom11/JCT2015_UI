package com.bluesky.jct.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class User {
	
	private StringProperty userNameProperty = new SimpleStringProperty();
	private StringProperty passwordProperty = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param domainId, domainName
	 */
	public User() {
 		this.userNameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
	}
	
	
	public String getUserName() {
		return userNameProperty.get();
	}
	
	public void setUserName(String userNameProperty) {
		this.userNameProperty.set(userNameProperty);
	}

	public StringProperty userNameProperty() {
		return userNameProperty;
	}
	
	public String getPassword() {
		return passwordProperty.get();
	}
	
	public void setPassword(String passwordProperty) {
		this.passwordProperty.set(passwordProperty);
	}

	public StringProperty passwordProperty() {
		return passwordProperty;
	}
	
}
