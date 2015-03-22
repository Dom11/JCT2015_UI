package com.bluesky.jct.view;

import java.util.Date;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Page 3 of the new profile wizard.
 * Generating an RPM package.
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage4 {

	@FXML
	private Button saveButton;

	public static Profile tempProfile4;
	public static boolean createClicked;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage4() {
		super();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	
    
    @FXML
    private void handleSave() {
    	
    	Date date = new Date();
    	tempProfile4.setPackageSentDate(date);
    	
    	int environmentId = tempProfile4.getEnvironmentId();
    	int hostId = tempProfile4.getHostId();
    	int jbarId = tempProfile4.getJbarId();
    	int jiraId = tempProfile4.getJiraId();
    	int prefixId = tempProfile4.getPrefixId();
    	int domainId = tempProfile4.getDomainId();
    	String profileDescription = tempProfile4.getProfileDescription();
    	String profileDnsName = tempProfile4.getProfileDnsName();
    	String profileComponent = tempProfile4.getProfileComponent();
    	int jvmId = tempProfile4.getJvmId();
    	boolean profileStatus = tempProfile4.getProfileStatus();
    	String createdBy = tempProfile4.getCreatedBy();
    	Date rpmGenerationDate = tempProfile4.getRpmGenerationDate();
    	Date packageSentDate = tempProfile4.getPackageSentDate();
    	Integer version = tempProfile4.getVersion();
    	
    	RestClient.createProfile(
	   			environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
	   			profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);

	   	// refresh data table on profile overview
	   	ProfileOverviewController.loadProfileViewData();
		
		String headerText = "Package";
		String contentText = "Package has been sent and received.";
		ExceptionHandling.handleInformation(headerText, contentText);
		
		ProfileWizardController.closeWizard();
		
    }
    
    
	/**
	 * Senses whether user has clicked the create button.
	 * 
	 * @return true if clicked, false otherwise
	 */
	public boolean isCreateClicked() {
		return createClicked;
	}
	
	
	public static void setTempProfile(Profile tempProfile) {
		tempProfile4 = tempProfile;
	}
	
}