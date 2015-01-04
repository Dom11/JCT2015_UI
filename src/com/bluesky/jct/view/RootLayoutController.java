package com.bluesky.jct.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class RootLayoutController {

	
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
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}