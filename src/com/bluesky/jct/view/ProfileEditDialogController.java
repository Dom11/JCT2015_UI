package com.bluesky.jct.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
import com.bluesky.jct.ComboBoxPrefix;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;


/**
 * Dialog to display/edit details of a profile
 * 
 * @author Dominik
 */
public class ProfileEditDialogController {
	@FXML
	private TextField profileNameField;
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private TextField profilePrefixField;
	@FXML
	private TextField profileJbarField;
	@FXML
	private TextField profileEnvironmentField;	
	@FXML
	private TextField profileDnsField;
	@FXML
	private TextField profileHostField;	
	@FXML
	private TextField profileJiraField;
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
	private Button startServer;
	
	@FXML
	private HBox hbox;

	private Profile profile;
	private Stage dialogStage;
	private boolean saveClicked = false;
	
	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
	private ObservableList<Prefix> prefixData = FXCollections.observableArrayList();
	private ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	private ObservableList<Environment> environmentData = FXCollections.observableArrayList();
	private ObservableList<Host> hostData = FXCollections.observableArrayList();
	private ObservableList<Jira> jiraData = FXCollections.observableArrayList();
	
	
	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public ProfileEditDialogController() {
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// hides the save/cancel button and shows start/stop button
		hbox.setVisible(false);
		startServer.setVisible(true);
		
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
	
	
	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	
	/**
	 * Sets the profile to be displayed in the dialog.
	 * 
	 * @param profile
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
		
		// Shows all the profile information
		profileNameField.setText(getProfileName(profile.getProfileId()));
		profileDescriptionField.setText(profile.getProfileDescription());
		domainComboBox.setValue(getDomain(profile.getDomainId()));
		prefixComboBox.setValue(getPrefix(profile.getPrefixId()));
//		profilePrefixField.setText(getPrefixName(profile.getPrefixId()));
		jbarComboBox.setValue(getJbar(profile.getJbarId()));
//		profileJbarField.setText(getJbarName(profile.getJbarId()));
		environmentComboBox.setValue(getEnvironment(profile.getEnvironmentId()));
//		profileEnvironmentField.setText(getEnvironmentName(profile.getEnvironmentId()));
		profileDnsField.setText(profile.getProfileDnsName());
		hostComboBox.setValue(getHost(profile.getHostId()));
//		profileHostField.setText(getHostName(profile.getHostId()));
		jiraComboBox.setValue(getJira(profile.getJiraId()));
//		profileJiraField.setText(getJiraName(profile.getJiraId()));
		profileComponentField.setText(profile.getProfileComponent());
		
		// sets the text fields as inactive
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(true);
		domainComboBox.setDisable(true);
		prefixComboBox.setDisable(true);
//		profilePrefixField.setDisable(true);
		jbarComboBox.setDisable(true);		
//		profileJbarField.setDisable(true);
		environmentComboBox.setDisable(true);
//		profileEnvironmentField.setDisable(true);
		profileDnsField.setDisable(true);
		hostComboBox.setDisable(true);
//		profileHostField.setDisable(true);	
		jiraComboBox.setDisable(true);
//		profileJiraField.setDisable(true);
		profileComponentField.setDisable(true);
	}
	
	
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
	 * Called when the user clicks the edit from the profile menu.
	 * Makes text fields to be editable.
	 */
	@FXML
	private void handleEditProfile() {
		// sets the text fields as active
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(false);
		domainComboBox.setDisable(false);
		prefixComboBox.setDisable(false);	
//		profilePrefixField.setDisable(true);
		jbarComboBox.setDisable(false);				
//		profileJbarField.setDisable(true);
		environmentComboBox.setDisable(false);		
//		profileEnvironmentField.setDisable(true);
		profileDnsField.setDisable(false);
		hostComboBox.setDisable(false);		
//		profileHostField.setDisable(true);
		jiraComboBox.setDisable(false);		
//		profileJiraField.setDisable(true);
		profileComponentField.setDisable(false);
		
		// show save/cancel button and hide start/stop button
		hbox.setVisible(true);
		startServer.setVisible(false);
		// focusing on first editable field
		profileDescriptionField.requestFocus();
	}
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        
        //TODO add missing validation checks 
/**        
        if (profileIdField.getText() == null || profileIdField.getText().length() == 0) {
            errorMessage += "No valid ID!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(profileIdField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid ID (must be an integer)!\n"; 
            }
        }   
*/
        /**
        if (profileJBarNameField.getText() == null || profileJBarNameField.getText().length() == 0) {
            errorMessage += "No valid JBar Name!\n"; 
        }
        */

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	
           	Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Invalid Fields");
    		alert.setHeaderText("Please correct invalid fields");
    		alert.setContentText(errorMessage);

    		alert.showAndWait();
    		
            return false;
        }
        
    }
    
    
    //TODO
    @FXML
    private void handleClone() {
    	System.out.println("start Wizard with certain fields filled out");
    }
    
    
	/**
	 * Called when the user clicks on the delete menu item.
	 */
	@FXML
	private void handleDelete() {
		ProfileFunctions.deleteProfile(profile);
	}
    
    
    //TODO
    @FXML
    private void handleLogFile() {
    	System.out.println("show Log-File");
    }
    
    
    //TODO
    @FXML
    private void handleStatusReport() {
    	System.out.println("show Status Report");
    }
    
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleInformation() {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Edit Dialog");
		alert.setContentText("For further assistance/support, please contact: ");

		alert.showAndWait();
    }
	
	
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}
	
	
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
        	
        	int profileId = profile.getProfileId();
        	int environmentId = environmentComboBox.getSelectionModel().getSelectedItem().getId();
        	int hostId = hostComboBox.getSelectionModel().getSelectedItem().getId();
        	int jbarId = jbarComboBox.getSelectionModel().getSelectedItem().getId();
        	int jiraId = jiraComboBox.getSelectionModel().getSelectedItem().getId();
        	int prefixId = prefixComboBox.getSelectionModel().getSelectedItem().getId();
        	int domainId = domainComboBox.getSelectionModel().getSelectedItem().getId();
        	String profileDescription = profileDescriptionField.getText();
        	String profileDnsName = profileDnsField.getText();
        	String profileComponentName = profileComponentField.getText();
        	Integer version = profile.getVersion();
        	
        	RestClient.editProfile(profileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponentName, version);
    
            saveClicked = true;
            dialogStage.close();
        }
    }
}
