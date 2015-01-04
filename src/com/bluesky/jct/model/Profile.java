package com.bluesky.jct.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Profile {
	
	private final StringProperty profileDescription;
	private final StringProperty profileHostName;
	private final StringProperty profileInstance;
	private final StringProperty profileEnvironment;
	private final StringProperty profileDomain;
	private final StringProperty profileJBarName;

	
	/**
	 * Default constructor.
	 */
	public Profile() {
		this(null, null);
	}
	
	/**
	 * Constructor with some default data.
	 * 
	 * @param profileId
	 * @param profileDescription
	 */
	public Profile(String profileDescription, String profileEnvironment) {
		this.profileDescription = new SimpleStringProperty(profileDescription);
		this.profileEnvironment = new SimpleStringProperty(profileEnvironment);		
		
		// Some initial dummy data, just for testing purposes.
		this.profileHostName = new SimpleStringProperty("srp0433lx");
		this.profileInstance = new SimpleStringProperty("JB1");
		this.profileDomain = new SimpleStringProperty("ZH");
		this.profileJBarName = new SimpleStringProperty("ESD");
	}
	
	public String getProfileDescription() {
		return profileDescription.get();
	}	
	
	public void setProfileDescription(String profileDescription) {
		this.profileDescription.set(profileDescription);
	}

	public StringProperty profileDescriptionProperty() {
		return profileDescription;
	}
	
	public String getProfileHostName() {
		return profileHostName.get();
	}
	
	public void setProfileHostName(String profileHostName) {
		this.profileHostName.set(profileHostName);
	}
	
	public StringProperty profileHostNameProperty() {
		return profileHostName;
	}

	public String getProfileInstance() {
		return profileInstance.get();
	}
	
	public void setProfileInstance(String profileInstance) {
		this.profileInstance.set(profileInstance);
	}
	
	public StringProperty profileInstanceProperty() {
		return profileInstance;
	}

	public String getProfileEnvironment() {
		return profileEnvironment.get();
	}
	
	public void setProfileEnvironment(String profileEnvironment) {
		this.profileEnvironment.set(profileEnvironment);
	}
	
	public StringProperty profileEnvironmentProperty() {
		return profileEnvironment;
	}

	public String getProfileDomain() {
		return profileDomain.get();
	}
	
	public void setProfileDomain(String profileDomain) {
		this.profileDomain.set(profileDomain);
	}
	
	public StringProperty profileDomainProperty() {
		return profileDomain;
	}

	public String getProfileJBarName() {
		return profileJBarName.get();
	}
	
	public void setProfileJBarName(String profileJBarName) {
		this.profileJBarName.set(profileJBarName);
	}
	
	public StringProperty profileJBarNameProperty() {
		return profileJBarName;
	}
}
