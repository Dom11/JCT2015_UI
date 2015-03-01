package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RootLayoutController {
	
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
	private void handleViewProfile() {
		
		int selectedProfileId = ProfileOverviewController.getSelectedProfileId();
		
		if(selectedProfileId == 0) {
			
	    	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(" ");
			alert.setHeaderText("No Profile selected");
			alert.setContentText("To view a Profile, please select a line item in the table.");

			alert.showAndWait();
			
		} else {
			
			Profile profile = RestClient.findProfile(selectedProfileId);
			mainApp.showProfileEditDialog(profile);
		}
	}
	
	
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleEdit() {
    	
    	
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Edit");
		alert.setContentText("To edit a Profile, please double click the line item in the table.");

		alert.showAndWait();
    }
    
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {

    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("JCT 2015");
		alert.setHeaderText("About");
		alert.setContentText("Author: Dominik Rey\nVersion: 1.0 (Prototye for Bachelor Thesis, March 2015)");

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
