package com.bluesky.jct.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.Date;

import org.reactfx.util.FxTimer;

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


/**
 * Dialog to display/edit details of a profile
 * 
 * @author Dominik
 */
public class ProfileEditDialogController {
	@FXML
	private MenuItem generateRPM;
	@FXML
	private MenuItem sendPackage;
	@FXML
	private MenuItem deleteProfile;
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
	private Label profileStatusLabel;
	@FXML
	private ProgressIndicator profileStatusProgress;
	@FXML
	private Button profileStatusButton;
	
	@FXML
	private HBox hbox;

	private Stage dialogStage;
	private boolean saveClicked = false;
	private boolean currentProfileStatus = false;
	
	private String profileStatusButtonText = "";
	private String profileStatusText = "";
	private String profileStatusTextColor = "";	
	
	private Profile profile;
	private int currentProfileId;
   	private int environmentId;
   	private int hostId;
   	private int jbarId;   	
   	private int jiraId;
	private int prefixId;   	
	private int domainId;
	private String profileDescription;
   	private String profileDnsName;
   	private String profileComponent;
	private int jvmId;
	private boolean profileStatus;
	private String createdBy;
	private Date rpmGenerationDate;
	private Date packageSentDate;
	private int version;

	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// hides the save/cancel button
		hbox.setVisible(false);
		// shows start/stop button
		profileStatusButton.setVisible(true);
		// hides the progressIndicator
		profileStatusProgress.setVisible(false);
		
		// set admin functions based on userType
		generateRPM.setDisable(LoginDialog.getDisabledType());
		sendPackage.setDisable(LoginDialog.getDisabledType());
		deleteProfile.setDisable(LoginDialog.getDisabledType());
		
		// provides a toolTip for the textFields
		Tooltip t = new Tooltip("max. 255 characters");
		profileDescriptionField.setTooltip(t);
		profileDnsField.setTooltip(t);
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
		
		// listener for status button
		profileStatusButton.setOnAction((event) -> {
			if(currentProfileStatus == true) {
				currentProfileStatus = false;
			} else {
				currentProfileStatus = true;
			}
			delayedAction();
		});
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
	 * Sets the profile to be displayed in the dialog
	 * and the fields and comboBoxes as inactive.
	 * 
	 * @param profile
	 */
	public void setProfile(Profile selectedProfile) {
		this.profile = selectedProfile;
		currentProfileId = profile.getProfileId();
	   	environmentId = profile.getEnvironmentId();
	   	hostId = profile.getHostId();
	   	jbarId = profile.getJbarId();   	
	   	jiraId = profile.getJiraId();
		prefixId = profile.getPrefixId();   	
		domainId = profile.getDomainId();
		profileDescription = profile.getProfileDescription();
	   	profileDnsName = profile.getProfileDnsName();
	   	profileComponent = profile.getProfileComponent();
		jvmId = profile.getJvmId();
		profileStatus = profile.getProfileStatus();
		createdBy = profile.getCreatedBy();
		rpmGenerationDate = profile.getRpmGenerationDate();
		packageSentDate = profile.getPackageSentDate();
		version = profile.getVersion();		
		
		// Shows all the profile information
		profileNameField.setText(getProfileName(profile.getProfileId()));
		profileDescriptionField.setText(profile.getProfileDescription());
		domainComboBox.setValue(getDomain(profile.getDomainId()));
		prefixComboBox.setValue(getPrefix(profile.getPrefixId()));
		jbarComboBox.setValue(getJbar(profile.getJbarId()));
		environmentComboBox.setValue(getEnvironment(profile.getEnvironmentId()));
		profileDnsField.setText(profile.getProfileDnsName());
		hostComboBox.setValue(getHost(profile.getHostId()));
		jiraComboBox.setValue(getJira(profile.getJiraId()));
		profileComponentField.setText(profile.getProfileComponent());
		currentProfileStatus = profile.getProfileStatus();
		profileStatusButton.setText(getProfileStatusButtonText(currentProfileStatus));		
		profileStatusLabel.setText(getProfileStatusLabelText(currentProfileStatus));
		profileStatusLabel.setStyle(getProfileStatusTextColor(currentProfileStatus));
		
		// sets the text fields and comboBoxes as inactive
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(true);
		domainComboBox.setDisable(true);
		prefixComboBox.setDisable(true);
		jbarComboBox.setDisable(true);		
		environmentComboBox.setDisable(true);
		profileDnsField.setDisable(true);
		hostComboBox.setDisable(true);
		jiraComboBox.setDisable(true);
		profileComponentField.setDisable(true);
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
	
	private String getProfileStatusButtonText(boolean profileStatus) {
		if(profileStatus == true) {
			profileStatusButtonText = "Stop";
		} else {
			profileStatusButtonText = "Start";
		}
		return profileStatusButtonText;
	}
	
	private String getProfileStatusLabelText(boolean profileStatus) {
		if(profileStatus == true) {
			profileStatusText = "running";
		} else {
			profileStatusText = "stopped";
		}
		return profileStatusText;
	}
	
	private String getProfileStatusTextColor(boolean profileStatus) {
		if(profileStatus == true) {
			profileStatusTextColor = "-fx-text-fill: #25ba47";
		} else {
			profileStatusTextColor = "-fx-text-fill: #f70c0c";		
		}
		return profileStatusTextColor;
	}
	
	
	/**
	 * This method hides the status label and shows the progressIndicator for 3 seconds.
	 * During the 3 seconds, it will save the new profile status and adjusts the button text, label text and label color.
	 * After the 3 seconds, progressIndicator will be hidden and status label shown.
	 */
	private void delayedAction() {
		
		profileStatusLabel.setVisible(false);
		profileStatusProgress.setVisible(true);
		
		FxTimer.runLater(Duration.ofSeconds(3), () -> {
			saveInformation();
			profileStatusButton.setText(getProfileStatusButtonText(currentProfileStatus));
			profileStatusLabel.setText(getProfileStatusLabelText(currentProfileStatus));
			profileStatusLabel.setStyle(getProfileStatusTextColor(currentProfileStatus));
			profileStatusLabel.setVisible(true);
			profileStatusProgress.setVisible(false);			
		});
	}
	
	
	/**
	 * Does input validation and sends a REST call to edit profile entries.
     * It refreshes the table of the GUI and closes the current stage.
	 */
	private void saveInformation() {
		
		currentProfileId = profile.getProfileId();
    	environmentId = environmentComboBox.getSelectionModel().getSelectedItem().getId();
    	hostId = hostComboBox.getSelectionModel().getSelectedItem().getId();
    	jbarId = jbarComboBox.getSelectionModel().getSelectedItem().getId();
    	jiraId = jiraComboBox.getSelectionModel().getSelectedItem().getId();
    	prefixId = prefixComboBox.getSelectionModel().getSelectedItem().getId();
    	domainId = domainComboBox.getSelectionModel().getSelectedItem().getId();
    	profileDescription = profileDescriptionField.getText();
    	profileDnsName = profileDnsField.getText();
    	profileComponent = profileComponentField.getText();
    	jvmId = profile.getJvmId();
    	profileStatus = currentProfileStatus;
    	createdBy = profile.getCreatedBy();
    	rpmGenerationDate = profile.getRpmGenerationDate();
    	packageSentDate = profile.getPackageSentDate();
    	version = profile.getVersion();
    	
    	if (ProfileFunctions.isProfileInputValid(profileDescription, profileDnsName, profileComponent)) {
        	
        	RestClient.editProfile(currentProfileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
        			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
        	
        	ProfileOverviewController.loadProfileViewData();
			setProfile(RestClient.findProfile(currentProfileId));
    	}
	}
		
	
	
	/**
	 * Called when the user clicks the edit from the profile menu.
	 * Text fields and comboBoxes will get enabled.
	 */
	@FXML
	private void handleEditProfile() {
		// sets the textFields and comobBoxes as active
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(false);
		domainComboBox.setDisable(false);
		prefixComboBox.setDisable(false);	
		jbarComboBox.setDisable(false);				
		environmentComboBox.setDisable(false);		
		profileDnsField.setDisable(false);
		hostComboBox.setDisable(false);		
		jiraComboBox.setDisable(false);		
		profileComponentField.setDisable(false);
		
		// show save/cancel button and hide start/stop button
		hbox.setVisible(true);
		profileStatusButton.setVisible(false);
		
		// focusing on first editable field
		profileDescriptionField.requestFocus();
	}
    
    
    @FXML
    private void handleClone() {
		ProfileFunctions.cloneProfile(profile);
    }
    
    
    @FXML
    private void handleGenerateRPM() {

    	Date date = new Date();
    	rpmGenerationDate = date;
    	
    	RestClient.editProfile(currentProfileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
    			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
    	
    	ProfileOverviewController.loadProfileViewData();
		setProfile(RestClient.findProfile(currentProfileId));
    	
		String headerText = "RPM";
		String contentText = "Package has been successfully generated.";
		ExceptionHandling.handleInformation(headerText, contentText);		
    }
    
    
    @FXML
    private void handleSendPackage() {
    	Date date = new Date();
    	packageSentDate = date;

    	RestClient.editProfile(currentProfileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
    			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
    	
    	ProfileOverviewController.loadProfileViewData();
		setProfile(RestClient.findProfile(currentProfileId));
		
		String headerText = "RPM";
		String contentText = "Package has been successfully generated.";
		ExceptionHandling.handleInformation(headerText, contentText);		
    }
    
    
	@FXML
	private void handleDelete() {
		ProfileFunctions.deleteProfile(profile);
		dialogStage.close();
	}
    
    
    @FXML
    private void handleLogFile() {
    	ProfileFunctions.showLogFile(profile);
    }
    
    
    @FXML
    private void handleStatusReport() {
    	ProfileFunctions.showStatusReport(profile);
    }
    
    
    /**
     * Opens the About dialog.
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
     * Called when the user clicks Save.
     */
    @FXML
    private void handleSave() {
    	
    	saveInformation();
    	saveClicked = true;
            
        String headerText = "Update successful";
        String contentText = "Modifications have been sucessfully saved!";
        ExceptionHandling.handleInformation(headerText, contentText);
            
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
	
}