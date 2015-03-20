package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
import com.bluesky.jct.ComboBoxPrefix;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


/**
 * Page 1 of the new profile wizard.
 * Here, a new profile can be created. 
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
	private Button saveButton;
	
	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
	private ObservableList<Prefix> prefixData = FXCollections.observableArrayList();
	private ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	private ObservableList<Environment> environmentData = FXCollections.observableArrayList();	
	private ObservableList<Host> hostData = FXCollections.observableArrayList();
	private ObservableList<Jira> jiraData = FXCollections.observableArrayList();
	
	public boolean saveClicked;
	
		
	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage1() {
		super();
		
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
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(false);
		profileDnsNameField.setDisable(false);
		profileComponentField.setDisable(false);
		
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
	}
	
	
    @FXML
    private void handleSave() {
    	
        // check whether mandatory fields are not blank
    	if (checkComboBoxSelection() == true) {
    		
          	Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Mandatory information missing!");
    		alert.setContentText("Please check your Combo Box selections.\n"
    						   + "Nothing selected on one or more boxes.");
    		alert.showAndWait();
    		
		} else {
    	
			String profileDescription = profileDescriptionField.getText();
		   	int domainId = domainComboBox.getSelectionModel().getSelectedItem().getId();
		   	int prefixId = prefixComboBox.getSelectionModel().getSelectedItem().getId();
		   	int jbarId = jbarComboBox.getSelectionModel().getSelectedItem().getId();
		   	int environmentId = environmentComboBox.getSelectionModel().getSelectedItem().getId();
		   	String profileDnsName = profileDnsNameField.getText();
		   	int hostId = hostComboBox.getSelectionModel().getSelectedItem().getId();
		   	int jiraId = jiraComboBox.getSelectionModel().getSelectedItem().getId();
		   	String profileComponent = profileComponentField.getText();
		   	boolean profileStatus = false;
		   	Integer version = 0;
		
		   	// validation check
		   	if (ProfileFunctions.isProfileInputValid(profileDescription, profileDnsName, profileComponent)) {
		   	
		   	// if ok, profile will be created
		   	RestClient.createProfile(
		   			environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
		   			profileDnsName, profileComponent, profileStatus, version);
		   	
		   	saveClicked = true;
		   	saveButton.setDisable(isSaveClicked());
		   	
		   	// refresh data table on profile overview
		   	ProfileOverviewController.loadProfileViewData();
		
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
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return saveClicked
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}
	
	
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

}