package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class ProfileWizardController {
	
	@FXML
	private ProgressBar progressBar;

	private static MainApp mainApp;
	private static Stage dialogStage;
	private static IntegerProperty pageCounter = new SimpleIntegerProperty(1);	
	private static IntegerProperty progressValue = new SimpleIntegerProperty(15);
	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		ProfileWizardController.mainApp = mainApp;
	}
	
	
	/**
     * Is called by the main application to set the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        ProfileWizardController.dialogStage = dialogStage;
    }
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
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
		mainApp.showProfileWizardPage(getPageCounter());
	}
	
	
	/**
	 * Decrease of page counter by 1 and decrease of progress value by 25%.
	 * Loads the respective wizard page.
	 */
	public static void decreasePageCounter() {
		pageCounter.set(pageCounter.add(-1).get());
		progressValue.set(progressValue.add(-25).get());
		mainApp.showProfileWizardPage(getPageCounter());
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
	 * Provides the progress value in %.
	 * 
	 * @return progressValue
	 */
	public static double getProgressValue() {
		return progressValue.doubleValue()/100;
	}
	
    
    /**
     * Closes the wizard window.
     */
    public static void closeWizard() {
    	dialogStage.close();
    }
    
   
    //TODO remove if not needed
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
