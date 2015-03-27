package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
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
 * Page 1 of the self-service portal.
 * On this page, all the mandatory parameters to create multiple profiles need to be entered. 
 * 
 * @author Dominik
 */
public class SelfServiceControllerPage1 {
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private ComboBox<Domain> domainComboBox;
	@FXML
	private ComboBox<Jbar> jbarComboBox;
	@FXML
	private ComboBox<Jira> jiraComboBox;
	@FXML
	private Button nextButton;


	private Profile tempProfile;
	private String profileDescription = "";
	private int domainId = 0;
	private int prefixId = 0;
   	private int jbarId = 0;
   	private int environmentId = 0;
   	private String profileDnsName = "";
   	private int hostId = 0;
   	private int jiraId = 0;
   	private String profileComponent = "";
	private String createdBy = LoginDialog.getUserName();

	public boolean nextClicked;

		
	/**
	 * Constructor.
	 * The constructor is called before the initialize() method.
	 */
	public SelfServiceControllerPage1() {
		super();
   		SelfServiceController.resetPageCounter();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		Tooltip t = new Tooltip("max. 255 characters");
		profileDescriptionField.setTooltip(t);
		
		// fill comboBoxes with information
		domainComboBox.setItems(ComboBoxDomain.getDomainData());
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		jbarComboBox.setItems(ComboBoxJbar.getJbarData());
		ComboBoxJbar.iniJbarCombobox(jbarComboBox);
		jiraComboBox.setItems(ComboBoxJira.getJiraData());
		ComboBoxJira.iniJiraCombobox(jiraComboBox);
		
		// retrieve tempProfile
		tempProfile = ProfileFunctions.getTempProfile();
		
		// if tempProfile not null then fill fields with information
		if (tempProfile.getProfileDescription() != null) {
			profileDescriptionField.setText(tempProfile.getProfileDescription());
			domainComboBox.setValue(getDomain(tempProfile.getDomainId()));
			jbarComboBox.setValue(getJbar(tempProfile.getJbarId()));
			jiraComboBox.setValue(getJira(tempProfile.getJiraId()));
		}		
	}
	
	// --- Getters to retrieve content based on foreign key
	
	private Domain getDomain(int domainId) {
		Domain domain = RestClient.findDomain(domainId);
		return domain;
	}

	private Jbar getJbar(int jbarId) {
		Jbar jbar = RestClient.findJbar(jbarId);
		return jbar;
	}	
	
	private Jira getJira(int jiraId) {
		Jira jira = RestClient.findJira(jiraId);
		return jira;
	}
	
	
	/**
	 * User clicks on Next button.
	 * Input check of comboBoxes and validation check of textField.
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
    	} else {
			profileDescription = profileDescriptionField.getText();
		   	domainId = domainComboBox.getSelectionModel().getSelectedItem().getId();
		   	jbarId = jbarComboBox.getSelectionModel().getSelectedItem().getId();
		   	jiraId = jiraComboBox.getSelectionModel().getSelectedItem().getId();
    	}
		   	
        // Description is mandatory and can only be 255 characters long
    	String errorMessage = "";
	   	if (profileDescription == null || profileDescription.length() == 0) {
            errorMessage += "Please enter a Profile Description!\n"; 
        } else {
        	if (profileDescription.length() > 255) {
        		errorMessage += "Lenght of description can only be 255 characters.\n"; 
        	}
        }
	   	if (errorMessage.length() != 0) {
        // Show warning message     
    	ExceptionHandling.handleWarning("Please correct invalid entry!", errorMessage);    		
        } else {
       		createTempProfile();
    	   	// load next page of wizard
       		SelfServiceController.increasePageCounter();
        }
	}

    
    @FXML
    private void handleCancel() {
    	SelfServiceController.closeWizard();
    }
	

	/**
	 * Checks whether all comboBoxes have something selected.
	 * 
	 * @return notNull (true if one or multiple selections are missing, false otherwise)
	 */
	private boolean checkComboBoxSelection() {
		
		boolean notNull = true;
		
    	if (domainComboBox.getValue() == null ||
	    		jbarComboBox.getValue() == null ||
	    		jiraComboBox.getValue() == null) {
    		
    		notNull = true;
    	} else {
    		notNull = false;
    	}
    	return notNull;
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