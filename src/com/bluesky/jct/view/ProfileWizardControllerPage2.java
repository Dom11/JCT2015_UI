package com.bluesky.jct.view;

import java.util.List;

import com.bluesky.jct.ComboBoxJvmArgument;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * Page 2 of the new profile wizard.
 * Creating or adding an existing JVM Argument to the created profile.
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage2 {
	@FXML
	private TextField jvmArgumentField;
	@FXML
	private ComboBox<JvmArgument> jvmArgumentComboBox;
	@FXML
	private Button createButton;
	
	private ObservableList<JvmArgument> jvmData = FXCollections.observableArrayList();
	private int index = 0;
	private int createdJvmId = 0;
	private String jvmArgumentText;
	public static boolean createClicked;
	
	public static Profile tempProfile2;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage2() {
		super();
		ComboBoxJvmArgument.loadJvmArgumentData();
		jvmData = ComboBoxJvmArgument.getJvmArgumentData();
//		tempProfile2 = ProfileWizardControllerPage1.getTempProfile();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		jvmArgumentComboBox.setItems(jvmData);
		ComboBoxJvmArgument.iniJvmArgumentCombobox(jvmArgumentComboBox);
		jvmArgumentComboBox.setValue(jvmData.get(index));
	}

	
	/**
	 * Saves the new value from the TextField as JvmArgument
	 * and selects that item from the refreshed ComboBox. 
	 */
    @FXML
    private void handleCreate() {
    	
    	jvmArgumentText = jvmArgumentField.getText();
    	
    	if (ProfileFunctions.isJvmArgumentInputValid(jvmArgumentText)) {
    		
    		// save Text as JvmArgument
    		RestClient.createJvmArgument(jvmArgumentText);
	    	
	       	// refresh ComboBox
	    	ComboBoxJvmArgument.loadJvmArgumentData();
	    	
	    	// select new created JvmArgument and clear TextField
	    	setNewJvmID();
	    	index = jvmData.size() - 1;
	       	jvmArgumentComboBox.setValue(jvmData.get(index));
	       	jvmArgumentField.setText(null);
	       	
	       	// set create Button as inactive
	       	createClicked = true;
	       	createButton.setDisable(isCreateClicked());   
    	}
    }
    
    
    @FXML
    private void handleNext() {
    	
    	int selectedJvmId = jvmArgumentComboBox.getValue().getId();
    	String selectedJvmArgument = jvmArgumentComboBox.getValue().getName();
    	
    	tempProfile2.setJvmId(selectedJvmId);
    	
/**    	
    	int profileId = ProfileWizardControllerPage1.getNewProfileID();
    	Profile profile = RestClient.findProfile(profileId);
    	
    	String profileDescription = profile.getProfileDescription();
	   	int domainId = profile.getDomainId();
	   	int prefixId = profile.getPrefixId();
	   	int jbarId = profile.getJbarId();
	   	int environmentId = profile.getEnvironmentId();
	   	String profileDnsName = profile.getProfileDnsName();
	   	int hostId = profile.getHostId();
	   	int jiraId = profile.getJiraId();
	   	String profileComponent = profile.getProfileComponent();
	   	int jvmId = createdJvmId;
	   	boolean profileStatus = profile.getProfileStatus();
	   	int version = profile.getVersion();
	   	
	   	RestClient.editProfile(profileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponent, jvmId, profileStatus, version);
*/    	
	   	// load next page of wizard
		ProfileWizardController.increasePageCounter();
		ProfileWizardControllerPage3.setTempProfile(tempProfile2);
		
		String headerText = "JVM Argument";
		String contentText = "Argument '" + selectedJvmArgument + "' has been added.";
		ExceptionHandling.handleInformation(headerText, contentText);
    }
    
    
	private void setNewJvmID() {
		List<JvmArgument> jvmArgumentList = (List<JvmArgument>) RestClient.findAllJvmArgument(); 
		int lastIndex = jvmArgumentList.size() -1;
		createdJvmId = jvmArgumentList.get(lastIndex).getId();
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
		tempProfile2 = tempProfile;
	}
	
}