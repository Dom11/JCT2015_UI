package com.bluesky.jct.model;

import java.io.Serializable;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for ProfileView object
 * 
 * @author Dominik
 */
public class ProfileView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty profileId = new SimpleIntegerProperty();
	private StringProperty profileName = new SimpleStringProperty();
	private StringProperty profileDescription = new SimpleStringProperty();
	private StringProperty profileComponent = new SimpleStringProperty();
	private StringProperty profileDnsName = new SimpleStringProperty();
	private StringProperty environmentName = new SimpleStringProperty();
	private StringProperty hostName = new SimpleStringProperty();
	private StringProperty jbarName = new SimpleStringProperty();
	private StringProperty jiraProjectKey = new SimpleStringProperty();
	private StringProperty prefixName = new SimpleStringProperty();
	private StringProperty domainName = new SimpleStringProperty();
	private BooleanProperty profileStatus = new SimpleBooleanProperty();
	private StringProperty jvmArgument = new SimpleStringProperty();
	private StringProperty createdBy = new SimpleStringProperty();
	private Date rpmGenerationDate = new Date();
	private Date packageSentDate = new Date();
	
	
	/**
	 * Constructor
	 * 
	 * @param profileId
	 * @param profileName
	 * @param profileDescription
	 * @param profileComponent
	 * @param profileDnsName
	 * @param environmentName
	 * @param hostName
	 * @param jbarName
	 * @param jiraProjectKey
	 * @param prefixName
	 * @param domainName
	 * @param profileStatus
	 * @param jvmArgument
	 * @param createdBy
	 * @param rpmGenerationDate
	 * @param packageSentDate
	 */
	public ProfileView(int profileId, String profileName, String profileDescription, String profileComponent, String profileDnsName, 
			String environmentName, String hostName, String jbarName, String jiraProjectKey, String prefixName, String domainName, 
			boolean profileStatus, String jvmArgument, String createdBy, Date rpmGenerationDate, Date packageSentDate) {
		super();
		this.profileId = new SimpleIntegerProperty(profileId);
		this.profileName = new SimpleStringProperty(profileName);
		this.profileDescription = new SimpleStringProperty(profileDescription);
		this.profileComponent = new SimpleStringProperty(profileComponent);
		this.profileDnsName = new SimpleStringProperty(profileDnsName);
		this.environmentName = new SimpleStringProperty(environmentName);
		this.hostName = new SimpleStringProperty(hostName);
		this.jbarName = new SimpleStringProperty(jbarName);
		this.jiraProjectKey = new SimpleStringProperty(jiraProjectKey);
		this.prefixName = new SimpleStringProperty(prefixName);		
		this.domainName = new SimpleStringProperty(domainName);
		this.profileStatus = new SimpleBooleanProperty(profileStatus);
		this.jvmArgument = new SimpleStringProperty(jvmArgument);
		this.createdBy = new SimpleStringProperty(createdBy);
		this.rpmGenerationDate = new Date();
		this.packageSentDate = new Date();	
	}
	
	
	// --- Getter and Setter
	
	public int getProfileId() {
		return profileId.get();
	}

	
	public IntegerProperty profileIdProperty() {
		return profileId;
	}
	
	
	public String getProfileName() {
		return profileName.get();
	}
	

	public StringProperty profileNameProperty() {
		return profileName;
	}
	
	
	public String getProfileDescription() {
		return profileDescription.get();
	}
	

	public StringProperty profileDescriptionProperty() {
		return profileDescription;
	}
	
	
	public String getProfileComponent() {
		return profileComponent.get();
	}
	

	public StringProperty profileComponentProperty() {
		return profileComponent;
	}
	

	public String getProfileDnsName() {
		return profileDnsName.get();
	}
	

	public StringProperty profileDnsNameProperty() {
		return profileDnsName;
	}

	
	public String getEnvironmentName() {
		return environmentName.get();
	}
	

	public StringProperty environmentNameProperty() {
		return environmentName;
	}
	

	public String getHostName() {
		return hostName.get();
	}

	
	public StringProperty hostNameProperty() {
		return hostName;
	}
	

	public String getJbarName() {
		return jbarName.get();
	}
	

	public StringProperty jbarNameProperty() {
		return jbarName;
	}
	

	public String getJiraProjectKey() {
		return jiraProjectKey.get();
	}
	

	public StringProperty jiraProjectKeyProperty() {
		return jiraProjectKey;
	}
	
	
	public String getPrefixName() {
		return prefixName.get();
	}
	

	public StringProperty prefixNameProperty() {
		return prefixName;
	}
	
	
	public String getDomainName() {
		return domainName.get();
	}
	

	public StringProperty domainNameProperty() {
		return domainName;
	}
	
	
	public boolean getProfileStatus() {
		return profileStatus.get();
	}
	

	public BooleanProperty profileStatusProperty() {
		return profileStatus;
	}
	
	
	public String getJvmArgument() {
		return jvmArgument.get();
	}
	

	public StringProperty jvmArgumentProperty() {
		return jvmArgument;
	}
	
	
	public String getCreatedBy() {
		return createdBy.get();
	}
	

	public StringProperty createdByProperty() {
		return createdBy;
	}
	
	
	public Date getRpmGenerationDate() {
		return rpmGenerationDate;
	}
	

	public Date getPackageSentDate() {
		return packageSentDate;
	}
	
}