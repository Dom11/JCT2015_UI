package com.bluesky.jct.view;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Date;

import org.reactfx.util.FxTimer;

import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;


/**
 * Page 4 of the self-service portal.
 * Generating an RPM packages.
 * Sending packages to Server.
 * 
 * @author Dominik
 */
public class SelfServiceControllerPage4 {

	@FXML
	private Button saveButton;
	@FXML
	private Button backButton;
	@FXML
	private HBox hbox;
	@FXML
	private ProgressBar profileCreationProgress;
	
	private ObservableList<Profile> profileData = FXCollections.observableArrayList();
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public SelfServiceControllerPage4() {
		super();
		profileData = ProfileFunctions.getTempProfiles();
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
		int newHostId = createNewHost();
		 	
        for (int index = 0; index < profileData.size(); index++) {
        	profileData.get(index).setRpmGenerationDate(date);
        	profileData.get(index).setPackageSentDate(date);
        	profileData.get(index).setProfileStatus(true);
        	
        	profileData.get(index).setProfileDnsName("Test DNS Name");
        	profileData.get(index).setHostId(newHostId);
        	profileData.get(index).setDomainId(1);
        }
    	
		FxTimer.runLater(Duration.ofSeconds(3), () -> {
			
	        for (int index = 0; index < profileData.size(); index++) {
	        	
	        	int environmentId = profileData.get(index).getEnvironmentId();
	        	int hostId = profileData.get(index).getHostId();
	        	int jbarId = profileData.get(index).getJbarId();
	        	int jiraId = profileData.get(index).getJiraId();
	        	int prefixId = profileData.get(index).getPrefixId();
	        	int domainId = profileData.get(index).getDomainId();
	        	String profileDescription = profileData.get(index).getProfileDescription();
	        	String profileDnsName = profileData.get(index).getProfileDnsName();
	        	String profileComponent = profileData.get(index).getProfileComponent();
	        	int jvmId = profileData.get(index).getJvmId();
	        	boolean profileStatus = profileData.get(index).getProfileStatus();
	        	String createdBy = profileData.get(index).getCreatedBy();
	        	Date rpmGenerationDate = profileData.get(index).getRpmGenerationDate();
	        	Date packageSentDate = profileData.get(index).getPackageSentDate();
	        	Integer version = profileData.get(index).getVersion();

	        	if (RestClient.createProfile(
			   			environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
			   			profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version) == false) {
	        		
	        	   	// refresh data table on profile overview
	        	   	ProfileOverviewController.loadProfileViewData();
	        	   	
	        		SelfServiceController.closeWizard();    	
	        	} else {
	        		SelfServiceController.decreasePageCounter();
	        	};
	        }
		});
    }
    
    
    /**
     * Creates a new Host where the Profiles will be running.
     * 
     * @return newHostId
     */
    private int createNewHost() {
    	
    	int numberOfHosts = ComboBoxHost.getHostData().size();
    	DecimalFormat myFormatter = new DecimalFormat("00000");
    	String output = myFormatter.format(numberOfHosts);
    	
    	String hostName = "srp" + output + "lx";
		RestClient.createHost(hostName);
		
		ComboBoxHost.loadHostData();
		int newHostId = ComboBoxHost.getHostData().get(numberOfHosts).getId();
		
		return newHostId;
    }
    
    
    @FXML
    private void handleBack() {
    	SelfServiceController.decreasePageCounter();
    }

}