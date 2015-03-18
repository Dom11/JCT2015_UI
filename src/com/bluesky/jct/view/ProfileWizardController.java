package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class ProfileWizardController {
	
	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	@FXML
	private ProgressBar progressBar;


	private MainApp mainApp;
	private static Stage dialogStage;
	private IntegerProperty counter = new SimpleIntegerProperty(1);
	private double progressValue = 0.15;
	

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public ProfileWizardController() {
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		// listener on number of next/back clicks
		counter.addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() <= 1) {
	    		backButton.setDisable(true);
	    	} else {
	    		backButton.setDisable(false);
	    	} 
			if(newValue.intValue() >= 4) {
	    		nextButton.setDisable(true);
	    	} else {
	    		nextButton.setDisable(false);
	    	}
		});
		
	}

	
	@FXML
	private void handleNext() {

		progressValue = progressValue + 0.25;
    	progressBar.setProgress(progressValue);
    	
    	counter.set(counter.add(1).get());
    	mainApp.showProfileWizardPage(getPageNumber());
	}
	
	

	@FXML
	private void handleBack() {
		progressValue = progressValue - 0.25;
    	progressBar.setProgress(progressValue);
    	
    	counter.set(counter.add(-1).get());
    	mainApp.showProfileWizardPage(getPageNumber());
   	}
	
	
	private int getPageNumber() {
		return counter.get();
	}
	
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	
	/**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        ProfileWizardController.dialogStage = dialogStage;
    }
    
    
    public static Stage getDialogStage() {
    	return dialogStage;
    }

	
	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return
	 */
/**	public boolean isExitClicked() {
		return exitClicked;
	}
*/	
}
