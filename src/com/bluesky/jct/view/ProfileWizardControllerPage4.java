package com.bluesky.jct.view;

import java.time.Duration;
import java.util.Date;

import org.reactfx.util.FxTimer;

import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;


/**
 * Page 4 of the new profile wizard.
 * sending the RPM package to the Satellite Server.
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage4 {

	@FXML
	private Button saveButton;
	@FXML
	private Button backButton;
	@FXML
	private ProgressBar profileCreationProgress;

	private Profile tempProfile;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage4() {
		super();
		tempProfile = ProfileFunctions.getTempProfile();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		saveButton.requestFocus();
		profileCreationProgress.setVisible(false);
	}
	
    
    @FXML
    private void handleSave() {

		profileCreationProgress.setVisible(true);
    	Date date = new Date();
    	tempProfile.setPackageSentDate(date);
    	
    	int environmentId = tempProfile.getEnvironmentId();
    	int hostId = tempProfile.getHostId();
    	int jbarId = tempProfile.getJbarId();
    	int jiraId = tempProfile.getJiraId();
    	int prefixId = tempProfile.getPrefixId();
    	int domainId = tempProfile.getDomainId();
    	String profileDescription = tempProfile.getProfileDescription();
    	String profileDnsName = tempProfile.getProfileDnsName();
    	String profileComponent = tempProfile.getProfileComponent();
    	int jvmId = tempProfile.getJvmId();
    	boolean profileStatus = true;
    	String createdBy = tempProfile.getCreatedBy();
    	Date rpmGenerationDate = tempProfile.getRpmGenerationDate();
    	Date packageSentDate = tempProfile.getPackageSentDate();
    	Integer version = tempProfile.getVersion();
    	
		FxTimer.runLater(Duration.ofSeconds(3), () -> {
    	
	    	if(RestClient.createProfile(
		   			environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
		   			profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version) == false) {
	
	    	   	// refresh data table on profile overview
	    	   	ProfileOverviewController.loadProfileViewData();
	    		
	    		String headerText = "Package";
	    		String contentText = "Package has been sent to Satellite and received.\n"
	    						   + "Your profile is now ready for use.";
	    		ExceptionHandling.handleInformation(headerText, contentText);
	    		
	    		ProfileWizardController.closeWizard();    		
	    	};
		});
    }
    
    
    @FXML
    private void handleBack() {
    	ProfileWizardController.decreasePageCounter();
    }

}