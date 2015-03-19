package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
import com.bluesky.jct.ComboBoxPrefix;
import com.bluesky.jct.MainApp;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


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
	private Stage dialogStage;
	
	public boolean saveClicked;
	
		
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ProfileWizardControllerPage1() {
		
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
	
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
//    public void setDialogStage(Stage dialogStage) {
 //       dialogStage = ProfileWizardController.getDialogStage();
 //   }
    

    @FXML
    private void handleSave() {
    	
    	ProfileWizardController.increasePageCounter();
    	System.out.println(ProfileWizardController.getPageCounter());
    	System.out.println(ProfileWizardController.getProgressValue());
/**    
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
       	
       	RestClient.createProfile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponent, profileStatus, version);
       	
       	saveClicked = true;
       	saveButton.setDisable(isSaveClicked());
       	ProfileOverviewController.loadProfileViewData();
*/       	
    }

    
    @FXML
    private void handleCancel() {
    	ProfileWizardController.closeWizard();
    }
	

	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}
}
