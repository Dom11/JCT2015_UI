package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Profile implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private IntegerProperty profileId = new SimpleIntegerProperty();
	private IntegerProperty environmentId = new SimpleIntegerProperty();
	private IntegerProperty hostId = new SimpleIntegerProperty();
	private IntegerProperty jbarId = new SimpleIntegerProperty();
	private IntegerProperty jiraId = new SimpleIntegerProperty();
	private IntegerProperty prefixId = new SimpleIntegerProperty();
	private StringProperty profileComponent = new SimpleStringProperty();
	private StringProperty profileDescription = new SimpleStringProperty();
	private StringProperty profileDnsName = new SimpleStringProperty();

	
	/**
	 * Default constructor.
	 */
	public Profile() {
		this(0, 0, 0, 0, 0, 0, null, null, null);
	}

	
	/**
	 * Constructor
	 * 
	 * @param environmentId, environmentId, hostId, jbarId, jiraId, prefixId, profileComponent, profileDescription, profileDnsName
	 */
	public Profile(int profileId, int environmentId, int hostId, int jbarId, int jiraId, int prefixId, String profileComponent, String profileDescription, String profileDnsName) {
		this.profileId = new SimpleIntegerProperty(profileId);
		this.environmentId = new SimpleIntegerProperty(environmentId);
		this.hostId = new SimpleIntegerProperty(hostId);
		this.jbarId = new SimpleIntegerProperty(jbarId);
		this.jiraId = new SimpleIntegerProperty(jiraId);
		this.prefixId = new SimpleIntegerProperty(prefixId);
		this.profileComponent = new SimpleStringProperty(profileComponent);
		this.profileDescription = new SimpleStringProperty(profileDescription);		
		this.profileDnsName = new SimpleStringProperty(profileDnsName);
	}

	public int getProfileId() {
		return profileId.get();
	}

	public IntegerProperty profileIdProperty() {
		return profileId;
	}
	
	public int getEnvironmentId() {
		return environmentId.get();
	}
	
	public void setEnvironmentId(int environmentId) {
		this.environmentId.set(environmentId);
	}

	public IntegerProperty environmentIdProperty() {
		return environmentId;
	}
	
	public int getHostId() {
		return environmentId.get();
	}
	
	public void setHostId(int hostId) {
		this.hostId.set(hostId);
	}

	public IntegerProperty hostIdProperty() {
		return hostId;
	}
	
	public int getJbarId() {
		return jbarId.get();
	}
	
	public void setJbarId(int jbarId) {
		this.jbarId.set(jbarId);
	}

	public IntegerProperty jbarIdProperty() {
		return jbarId;
	}
	
	public int getJiraId() {
		return jiraId.get();
	}
	
	public void setJiraId(int jiraId) {
		this.jiraId.set(jiraId);
	}

	public IntegerProperty jiraIdProperty() {
		return jiraId;
	}
	
	public int getPrefixId() {
		return prefixId.get();
	}
	
	public void setPrefixId(int prefixId) {
		this.prefixId.set(prefixId);
	}

	public IntegerProperty prefixIdProperty() {
		return prefixId;
	}
	
	public String getProfileComponent() {
		return profileComponent.get();
	}

	public void setProfileComponent(String profileComponent) {
		this.profileComponent.set(profileComponent);
	}

	public StringProperty profileComponentProperty() {
		return profileComponent;
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

	public String getProfileDnsName() {
		return profileDnsName.get();
	}

	public void setProfileDnsName(String profileDnsName) {
		this.profileDnsName.set(profileDnsName);
	}

	public StringProperty profileDnsNameProperty() {
		return profileDnsName;
	}
}