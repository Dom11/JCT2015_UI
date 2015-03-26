package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


/**
 * This is the root controller for the SelfService Portal.
 * 
 * @author Dominik
 */
public class SelfServiceController {
	
	@FXML
	private ProgressBar progressBar;

	private static MainApp mainApp;
	private static Stage dialogStage;
	private static IntegerProperty pageCounter = new SimpleIntegerProperty(1);	
	private static IntegerProperty progressValue = new SimpleIntegerProperty(15);
	

    /**
     * Constructor
     */
    public SelfServiceController() {
    	super();
    }
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
    	progressBar.setProgress(getProgressValue());

		progressValue.addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() != oldValue.intValue()) {
				progressBar.setProgress(getProgressValue());
	    	} 
		});
	}

	
	/**
	 * Increase of page counter by 1 and increase of progress value by 25%.
	 * Loads the respective wizard page.
	 */
	public static void increasePageCounter() {
		pageCounter.set(pageCounter.add(1).get());
		progressValue.set(progressValue.add(25).get());
		mainApp.showSelfServicePortaldPage(getPageCounter());
	}
	
	
	/**
	 * Decrease of page counter by 1 and decrease of progress value by 25%.
	 * Loads the respective wizard page.
	 */
	public static void decreasePageCounter() {
		pageCounter.set(pageCounter.add(-1).get());
		progressValue.set(progressValue.add(-25).get());
		mainApp.showSelfServicePortaldPage(getPageCounter());
	}
	
	
	/**
	 * Provides the wizard page number.
	 * 
	 * @return pageCounter
	 */
	public static int getPageCounter() {
		return pageCounter.getValue();
	}
	
	
	/**
	 * Resets the page counter to 1.
	 * 
	 * @return pageCounter
	 */
	public static void resetPageCounter() {
		pageCounter.setValue(1);
	}
	
	
	/**
	 * Provides the progress value in %.
	 * 
	 * @return progressValue
	 */
	public static double getProgressValue() {
		double dProgressValue = progressValue.get();
		return dProgressValue/100;
	}
	
    
    /**
     * Closes the wizard window.
     */
    public static void closeWizard() {
    	Profile profile = new Profile();
    	ProfileFunctions.setTempProfile(profile);
    	dialogStage.close();
    }
    
    
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		SelfServiceController.mainApp = mainApp;
	}
	
	
	/**
     * Is called by the main application to set the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        SelfServiceController.dialogStage = dialogStage;
    }
    
}