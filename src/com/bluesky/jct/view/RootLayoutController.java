package com.bluesky.jct.view;

import com.bluesky.jct.LoginDialog;
import com.bluesky.jct.MainApp;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;


/**
 * This is the controller for the root layout
 * 
 * @author Dominik
 */
public class RootLayoutController {
	
	@FXML
	private MenuItem deleteProfile;
	
	// Reference to the main application
	private MainApp mainApp;

	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	@FXML
	private void initialize() {
		deleteProfile.setDisable(LoginDialog.getDisabledType());
	}
	
	
	@FXML
	private void handleSelfService() {
		// TODO
		System.out.println("Self-Service Portal");
	}
	
	
	@FXML
	private void handleView() {
		
    	Profile selectedProfile = ProfileOverviewController.getSelectedProfile(); 
    	
    	if (selectedProfile == null) {
    		String title = "View";
    		showInformationDialog(title);
    	} else {
			mainApp.showProfileEditDialog(selectedProfile);
		}
	}
	
	
	@FXML
	private void handleNew() {
		mainApp.showProfileWizard();
	}

	
    @FXML
    private void handleClone() {
  	
    	Profile selectedProfile = ProfileOverviewController.getSelectedProfile(); 
    	
    	if (selectedProfile == null) {
    		String title = "Clone";
    		showInformationDialog(title);
    	} else {
        	ProfileFunctions.cloneProfile(selectedProfile);
    	}
    }
    
    
    @FXML
    private void handleDelete() {
    	
    	Profile selectedProfile = ProfileOverviewController.getSelectedProfile(); 
    	
    	if (selectedProfile == null) {
    		String title = "Delete";
    		showInformationDialog(title);
    	} else {
    		ProfileFunctions.deleteProfile(selectedProfile);
    	}
    }
    
 
    @FXML
    private void handleAbout() {

    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("JCT 2015");
		alert.setHeaderText("About");
		alert.setContentText("Author: Dominik Rey\nVersion: 1.0 (Prototye for Bachelor Thesis, March 2015)");
		alert.showAndWait();
    }
    
    
    /**
     * Shows a message to inform the user that a profile needs to be selected.
     */
    private void showInformationDialog(String title) {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("No Profile selected");
		alert.setContentText("This function can only be performed when\n"
						   + "a profile from the table is selected.");
		alert.showAndWait();
    }
        

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
