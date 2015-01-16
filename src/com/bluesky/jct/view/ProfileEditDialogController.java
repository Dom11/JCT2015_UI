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

import com.bluesky.jct.model.FXProfile;


/**
 * Dialog to display/edit details of a profile
 * 
 * @author Dominik
 */
public class ProfileEditDialogController {
	@FXML
	private TextField profileIdField;
	@FXML
	private TextField profileJBarNameField;
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private TextField profileEnvironmentField;
	@FXML
	private TextField profileBuildField;
	@FXML
	private TextField profileDomainField;
	@FXML
	private TextField profileInstanceField;
	@FXML
	private TextField profileServerField;
	@FXML
	private Button startServer;
	@FXML
	private HBox hbox;


	private int selectedIndex;
	private Stage dialogStage;
	private FXProfile profile;
	private boolean saveClicked = false;
	
	// Reference to the ProfileOverviewController Class
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
	public void setProfile(FXProfile profile, int selectedIndex) {
		this.profile = profile;
		this.selectedIndex = selectedIndex;
		
		// Shows all the profile information
		profileIdField.setText(Integer.toString(selectedIndex));
		profileJBarNameField.setText(profile.getJBarName());
		profileDescriptionField.setText(profile.getDescription());
		profileEnvironmentField.setText(profile.getEnvironment());
		//profileBuildField.setText(profile.get);
		profileDomainField.setText(profile.getDomain());
		profileInstanceField.setText(profile.getInstance());
		profileServerField.setText(profile.getHostName());
		
		// sets the text fields as inactive
		profileIdField.setDisable(true);	
		profileJBarNameField.setDisable(true);
		profileDescriptionField.setDisable(true);
		profileEnvironmentField.setDisable(true);
		profileBuildField.setDisable(true);
		profileDomainField.setDisable(true);
		profileInstanceField.setDisable(true);
		profileServerField.setDisable(true);
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
		profileJBarNameField.setDisable(false);
		profileDescriptionField.setDisable(false);
		profileEnvironmentField.setDisable(false);
		profileBuildField.setDisable(false);
		profileDomainField.setDisable(false);
		profileInstanceField.setDisable(false);
		profileServerField.setDisable(false);
		
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
            profile.setJBarName(profileJBarNameField.getText());
            profile.setDescription(profileDescriptionField.getText());
            profile.setEnvironment(profileEnvironmentField.getText());         
//            profile.setProfileBuild(profileBuildField.getText());       
            profile.setDomain(profileDomainField.getText());       
            profile.setInstance(profileInstanceField.getText());       
            profile.setHostName(profileServerField.getText());       
    
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
 
        if (profileJBarNameField.getText() == null || profileJBarNameField.getText().length() == 0) {
            errorMessage += "No valid JBar Name!\n"; 
        }

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
		
       	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete the follwoing Profile?");
		alert.setContentText("Profile Index: "
								+ selectedIndex 
								+ "\n" 
								+ profile.getDescription());
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user clicks OK
			alert.close();
	        dialogStage.close();
			profileOverviewController.deleteProfile(selectedIndex);
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
