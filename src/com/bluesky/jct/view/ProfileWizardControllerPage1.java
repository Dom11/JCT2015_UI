package com.bluesky.jct.view;

import java.util.ArrayList;

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


	private Profile tempProfile;
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
	private boolean existing;
		
	/**
	 * Constructor.
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage1() {
		super();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		profileNameField.setDisable(true);	

		Tooltip t = new Tooltip("max. 255 characters");
		profileDescriptionField.setTooltip(t);
		profileDnsNameField.setTooltip(t);
		profileComponentField.setTooltip(t);
		
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
		
		// retrieve tempProfile
		tempProfile = ProfileFunctions.getTempProfile();
		System.out.println(tempProfile.getProfileDescription());
		
		// if tempProfile not null then fill fields with information
		if (tempProfile.getProfileDescription() != null) {
			profileNameField.setText(getProfileName(tempProfile.getProfileId()));
			profileDescriptionField.setText(tempProfile.getProfileDescription());
			domainComboBox.setValue(getDomain(tempProfile.getDomainId()));
			prefixComboBox.setValue(getPrefix(tempProfile.getPrefixId()));
			jbarComboBox.setValue(getJbar(tempProfile.getJbarId()));
			environmentComboBox.setValue(getEnvironment(tempProfile.getEnvironmentId()));
			profileDnsNameField.setText(tempProfile.getProfileDnsName());
			hostComboBox.setValue(getHost(tempProfile.getHostId()));
			jiraComboBox.setValue(getJira(tempProfile.getJiraId()));
			profileComponentField.setText(tempProfile.getProfileComponent());
		}		
	}
	
	// --- Getters to retrieve content based on foreign key
	
	private String getProfileName(int profileId) {
		ProfileView profileView = RestClient.findProfileView(profileId);
		return profileView.getProfileName();
	}
	
	private Domain getDomain(int domainId) {
		Domain domain = RestClient.findDomain(domainId);
		return domain;
	}

	private Prefix getPrefix(int prefixId) {
		Prefix prefix = RestClient.findPrefix(prefixId);
		return prefix;
	}

	private Jbar getJbar(int jbarId) {
		Jbar jbar = RestClient.findJbar(jbarId);
		return jbar;
	}	
	
	private Environment getEnvironment(int environmentId) {
		Environment environment = RestClient.findEnvironment(environmentId);
		return environment;
	}
	
	private Host getHost(int hostId) {
		Host host = RestClient.findHost(hostId);
		return host;
	}
	
	private Jira getJira(int jiraId) {
		Jira jira = RestClient.findJira(jiraId);
		return jira;
	}
	
	
	/**
	 * User clicks on Next button.
	 * Input check of comboBoxes and validation check of textFields.
	 * If everything is ok, next page will be loaded and a temporary Profile created.
	 */
    @FXML
    private void handleNext() {
    	
        // check whether mandatory fields are not blank
    	if (checkComboBoxSelection() == true) {
    		String headerText = "Mandatory information missing!";
    		String contentText = "Please check your Combo Box selections.\n"
					   		   + "Nothing selected on one or more Combo Boxes.";    		
    		ExceptionHandling.handleWarning(headerText, contentText);
    	}
    	if (checkAvailability() == true) {
	    	String headerText = "Profile already exists!";
	    	String contentText = "A Profile with the same parameters already exists.\n"
					   		   + "Please check your Prefix, JBar and Environment selections.";    		
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
		
		   	// if validation check is ok, temporary profile will get created
		   	if (ProfileFunctions.isProfileInputValid(profileDescription, profileDnsName, profileComponent)) {
		   		createTempProfile();

			   	// load next page of wizard
				ProfileWizardController.increasePageCounter();
		   	}
		};
    }

    
    @FXML
    private void handleCancel() {
    	ProfileWizardController.closeWizard();
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
	
	
	/**
	 * Checks whether a profile with the same parameters is already existing.
	 * 
	 * @return contains (true if existing, false otherwise)
	 */
	private boolean checkAvailability() {
		
		// build the profileName
		String tempProfileName = prefixComboBox.getSelectionModel().getSelectedItem().getName() + "_" + 
								 jbarComboBox.getSelectionModel().getSelectedItem().getName() + "_" + 
								 environmentComboBox.getSelectionModel().getSelectedItem().getName();
		
		
		
		ArrayList<String>profileNames = new ArrayList<String>();
		for (int i = 0; i < ProfileOverviewController.getProfileData().size(); i++) {
			profileNames.add(ProfileOverviewController.getProfileData().get(i).getProfileName());
			}
		
        if (profileNames.contains(tempProfileName) == true) {
        	existing = true;
        } else {
        	existing = false;
        	}
        return existing;		
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
		
		ProfileFunctions.setTempProfile(tempProfile);
	}

}