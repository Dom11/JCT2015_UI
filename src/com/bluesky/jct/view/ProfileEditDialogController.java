package com.bluesky.jct.view;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;


/**
 * Dialog to display/edit details of a profile
 * 
 * @author Dominik
 */
public class ProfileEditDialogController {
	@FXML
	private TextField profileIdField;
	@FXML
	private TextField profileEnvironmentField;	
	@FXML
	private TextField profileHostField;	
	@FXML
	private TextField profileJbarField;
	@FXML
	private TextField profileJiraField;
	@FXML
	private TextField profilePrefixField;
	@FXML
	private TextField profileComponentField;
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private TextField profileDnsNameField;
	@FXML
	private TextField profileDomainField;


	@FXML
	private Button startServer;
	@FXML
	private HBox hbox;


	private int selectedIndex;
	private Stage dialogStage;
	private Profile profile;
	private boolean saveClicked = false;
	
	// Reference to the ProfileOverviewController Class
	@SuppressWarnings("unused")
	private ProfileOverviewController profileOverviewController;
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// hides the save/cancel button and shows start/stop button
		hbox.setVisible(false);
		startServer.setVisible(true);
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
	 * Is called by the main application to give a reference back to ProfileOverviewController.
	 * 
	 * @param profileOverviewController
	 */
	public void setProfileOverviewController(ProfileOverviewController profileOverviewController) {
		this.profileOverviewController = profileOverviewController;
	}
	
	
	/**
	 * Sets the profile to be displayed in the dialog.
	 * 
	 * @param profile
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
		
		// Shows all the profile information
		profileIdField.setText(Integer.toString(profile.getProfileId()));
		profileEnvironmentField.setText(Integer.toString(profile.getEnvironmentId()));	
		profileHostField.setText(Integer.toString(profile.getHostId()));		
		profileJbarField.setText(Integer.toString(profile.getJbarId()));
		profileJiraField.setText(Integer.toString(profile.getJiraId()));
		profilePrefixField.setText(Integer.toString(profile.getPrefixId()));
//		profileComponentField.setText(profile.getProfileComponent());		
		profileDescriptionField.setText(profile.getProfileDescription());
		profileDnsNameField.setText(profile.getProfileDnsName());
//		profileDomainField.setText(profile.getDomain());
		
		// sets the text fields as inactive
		profileIdField.setDisable(true);	
		profileEnvironmentField.setDisable(true);
		profileHostField.setDisable(true);		
		profileJbarField.setDisable(true);
		profileJiraField.setDisable(true);		
		profilePrefixField.setDisable(true);
		profileComponentField.setDisable(true);		
		profileDescriptionField.setDisable(true);
		profileDnsNameField.setDisable(true);
//		profileDomainField.setDisable(true);
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
	 * Called when the user clicks the edit from the profile menu.
	 * Makes text fields to be editable.
	 */
	@FXML
	private void handleEditProfile() {
		// sets the text fields as active
		profileIdField.setDisable(true);	
		profileEnvironmentField.setDisable(false);
		profileHostField.setDisable(false);		
		profileJbarField.setDisable(false);
		profileJiraField.setDisable(false);		
		profilePrefixField.setDisable(false);
		profileComponentField.setDisable(false);		
		profileDescriptionField.setDisable(false);
		profileDnsNameField.setDisable(false);
		
		// show save/cancel button and hide start/stop button
		hbox.setVisible(true);
		startServer.setVisible(false);
	}
	
	
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
            profile.setProfileComponent(profileComponentField.getText());
            profile.setProfileDescription(profileDescriptionField.getText());
            profile.setProfileDnsName(profileDnsNameField.getText());
    
            saveClicked = true;
            dialogStage.close();
        }
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        
        //TODO add missing validation checks 
        
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
	
	
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
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
		// show dialog and get confirmation from user
       	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete the follwoing Profile?");
		alert.setContentText("Profile Index: "
								+ selectedIndex 
								+ "\n" 
								+ profile.getProfileDescription());
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			// ... user clicks OK
			RestClient.deleteProfile(selectedIndex);
			alert.close();
	        dialogStage.close();
	        //TODO fix listener on for tableView and remove line below once fixed.
//			profileOverviewController.deleteProfile(selectedIndex);
//			profileOverviewController.refreshProfileData();

			
			
			
		} else {
		    // ... user clicks CANCEL or closed the dialog
			alert.close();
		}
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
}
