package com.bluesky.jct.view;

import java.util.List;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
import com.bluesky.jct.ComboBoxPrefix;
import com.bluesky.jct.LoginDialog;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;


/**
 * Page 1 of the new profile wizard.
 * On this page, all the mandatory parameters to create a profile need to be entered. 
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage1 {
	@FXML
	private TextField profileNameField;
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private TextField profileDnsNameField;
	@FXML
	private TextField profileComponentField;	
	@FXML
	private ComboBox<Domain> domainComboBox;
	@FXML
	private ComboBox<Prefix> prefixComboBox;
	@FXML
	private ComboBox<Jbar> jbarComboBox;
	@FXML
	private ComboBox<Environment> environmentComboBox;
	@FXML
	private ComboBox<Host> hostComboBox;
	@FXML
	private ComboBox<Jira> jiraComboBox;
	@FXML
	private Button nextButton;

	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
	private ObservableList<Prefix> prefixData = FXCollections.observableArrayList();
	private ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	private ObservableList<Environment> environmentData = FXCollections.observableArrayList();	
	private ObservableList<Host> hostData = FXCollections.observableArrayList();
	private ObservableList<Jira> jiraData = FXCollections.observableArrayList();
	
	private static int createdProfileId = 0;
	
	public static Profile tempProfile = new Profile();
	private String profileDescription = null;
	private int domainId = 0;
	private int prefixId = 0;
   	private int jbarId = 0;
   	private int environmentId = 0;
   	private String profileDnsName = null;
   	private int hostId = 0;
   	private int jiraId = 0;
   	private String profileComponent = null;
	private String createdBy = LoginDialog.getUserName();

	public boolean nextClicked;
	
		
	/**
	 * Constructor.
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage1() {
		super();
		
//		tempProfile = new Profile();
/**		
		// load ComboBox data for the ones which were not yet loaded
		ComboBoxPrefix.loadPrefixData();
		ComboBoxHost.loadHostData();
		ComboBoxJira.loadJiraData();
		domainData = ComboBoxDomain.getDomainData();
		prefixData = ComboBoxPrefix.getPrefixData();
		jbarData = ComboBoxJbar.getJbarData();
		environmentData = ComboBoxEnvironment.getEnvironmentData();
		hostData = ComboBoxHost.getHostData();
		jiraData = ComboBoxJira.getJiraData();
*/		
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	
		profileNameField.setDisable(true);	
//		profileDescriptionField.setDisable(false);
//		profileDnsNameField.setDisable(false);
//		profileComponentField.setDisable(false);

		Tooltip t = new Tooltip("max. 255 characters");
		profileDescriptionField.setTooltip(t);
		profileDnsNameField.setTooltip(t);
		profileComponentField.setTooltip(t);
/**		
		domainComboBox.setItems(domainData);
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		prefixComboBox.setItems(prefixData);
		ComboBoxPrefix.iniPrefixCombobox(prefixComboBox);
		jbarComboBox.setItems(jbarData);
		ComboBoxJbar.iniJbarCombobox(jbarComboBox);		
		environmentComboBox.setItems(environmentData);
		ComboBoxEnvironment.iniEnvironmentCombobox(environmentComboBox);
		hostComboBox.setItems(hostData);
		ComboBoxHost.iniHostCombobox(hostComboBox);		
		jiraComboBox.setItems(jiraData);
		ComboBoxJira.iniJiraCombobox(jiraComboBox);
*/		
		
		
		// fill comboBoxes with information
		domainComboBox.setItems(ComboBoxDomain.getDomainData());
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		prefixComboBox.setItems(ComboBoxPrefix.getPrefixData());
		ComboBoxPrefix.iniPrefixCombobox(prefixComboBox);
		jbarComboBox.setItems(ComboBoxJbar.getJbarData());
		ComboBoxJbar.iniJbarCombobox(jbarComboBox);
		environmentComboBox.setItems(ComboBoxEnvironment.getEnvironmentData());
		ComboBoxEnvironment.iniEnvironmentCombobox(environmentComboBox);
		hostComboBox.setItems(ComboBoxHost.getHostData());
		ComboBoxHost.iniHostCombobox(hostComboBox);
		jiraComboBox.setItems(ComboBoxJira.getJiraData());
		ComboBoxJira.iniJiraCombobox(jiraComboBox);
	}
	
	
    @FXML
    private void handleNext() {
    	
        // check whether mandatory fields are not blank
    	if (checkComboBoxSelection() == true) {
    		String headerText = "Mandatory information missing!";
    		String contentText = "Please check your Combo Box selections.\n"
					   		   + "Nothing selected on one or more boxes.";    		
    		ExceptionHandling.handleWarning(headerText, contentText);
    		
		} else {
    	
			profileDescription = profileDescriptionField.getText();
		   	domainId = domainComboBox.getSelectionModel().getSelectedItem().getId();
		   	prefixId = prefixComboBox.getSelectionModel().getSelectedItem().getId();
		   	jbarId = jbarComboBox.getSelectionModel().getSelectedItem().getId();
		   	environmentId = environmentComboBox.getSelectionModel().getSelectedItem().getId();
		   	profileDnsName = profileDnsNameField.getText();
		   	hostId = hostComboBox.getSelectionModel().getSelectedItem().getId();
		   	jiraId = jiraComboBox.getSelectionModel().getSelectedItem().getId();
		   	profileComponent = profileComponentField.getText();
//		   	profileStatus = false;
		
		   	// validation check
		   	if (ProfileFunctions.isProfileInputValid(profileDescription, profileDnsName, profileComponent)) {
		   		
		   		createTempProfile();
		   	
			   	// if ok, profile will be created
//			   	if (RestClient.createProfile(
//			   			environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, 
//			   			profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version) == false) {
			   	
//			   	nextClicked = true;
//			   	nextButton.setDisable(isNextClicked());
			   	
			   	// refresh data table on profile overview
//			   	ProfileOverviewController.loadProfileViewData();
			
			   	// load next page of wizard
				ProfileWizardController.increasePageCounter();
				ProfileWizardControllerPage2.setTempProfile(tempProfile);
				
//				setNewProfileID();

//			   	};
		   	}
		};
    }

    
    @FXML
    private void handleCancel() {
    	ProfileWizardController.closeWizard();
    }
	

	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return nextClicked
	 */
	private boolean isNextClicked() {
		return nextClicked;
    }
	
	
	/**
	 * Checks whether all comboBoxes have something selected.
	 * 
	 * @return notNull (true if one or multiple selections are missing, false otherwise)
	 */
	private boolean checkComboBoxSelection() {
		
		boolean notNull = true;
		
    	if (domainComboBox.getValue() == null ||
	    		prefixComboBox.getValue() == null ||
	    		jbarComboBox.getValue() == null ||
	    		environmentComboBox.getValue() == null ||
	    		hostComboBox.getValue() == null ||
	    		jiraComboBox.getValue() == null) {
    		
    		notNull = true;
    	} else {
    		notNull = false;
    	}
    	return notNull;
	}
	

	private void setNewProfileID() {
		List<Profile> profileList = (List<Profile>) RestClient.findAllProfile(); 
		int lastIndex = profileList.size() -1;
		createdProfileId = profileList.get(lastIndex).getProfileId();
	}
	
	
	private int getNewProfileID() {
		return createdProfileId;
	}
	
	
	/**
	 * Creates a temporary Profile based on the user inputs.
	 */
	private void createTempProfile() {
		tempProfile.setDomainId(domainId);
		tempProfile.setPrefixId(prefixId);
		tempProfile.setJbarId(jbarId);
		tempProfile.setEnvironmentId(environmentId);
		tempProfile.setProfileDescription(profileDescription);
		tempProfile.setProfileDnsName(profileDnsName);
		tempProfile.setHostId(hostId);
		tempProfile.setJiraId(jiraId);
		tempProfile.setProfileComponent(profileComponent);
		tempProfile.setCreatedBy(createdBy);
	}
	
	
	/**
	 * Provides the temporary Profile information.
	 * 
	 * @return tempProfile
	 */
	public static Profile getTempProfile() {
		return tempProfile;
	}
	
}