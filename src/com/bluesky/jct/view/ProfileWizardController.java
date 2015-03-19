package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
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
	private static IntegerProperty pageCounter = new SimpleIntegerProperty(1);	
	private static IntegerProperty progressValue = new SimpleIntegerProperty(15);
	
//	private IntegerProperty counter = new SimpleIntegerProperty(1);
//	private static double progressValue = 0.15;

	

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
		
		progressValue.addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() != oldValue.intValue()) {
//				double progress = newValue.doubleValue()/100;
				progressBar.setProgress(getProgressValue());
	    	} 
		});
	
		
//		progressBar.progressProperty().bind(progressV);
		
		// listener on number of next/back clicks
		pageCounter.addListener((observable, oldValue, newValue) -> {
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

//		progressValue = progressValue + 0.25;
//   	progressBar.setProgress(progressValue);
//    	
//   	counter.set(counter.add(1).get());
//    	mainApp.showProfileWizardPage(getPageNumber());
	}
	
	

	@FXML
	private void handleBack() {
//		progressValue = progressValue - 0.25;
//   	progressBar.setProgress(progressValue);
    	
//    	counter.set(counter.add(-1).get());
//    	mainApp.showProfileWizardPage(getPageNumber());
   	}
	
	
//	private int getPageNumber() {
//		return counter.get();
//	}
	
	
	public static void increasePageCounter() {
		pageCounter.set(pageCounter.add(1).get());
		progressValue.set(progressValue.add(25).get());
	}
	
	public static void decreasePageCounter() {
		pageCounter.set(pageCounter.add(-1).get());
		progressValue.set(progressValue.add(-25).get());
	}
	
	public static int getPageCounter() {
		return pageCounter.getValue();
	}
	
	public static double getProgressValue() {
		return progressValue.doubleValue()/100;
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
        this.dialogStage = dialogStage;
    }
    
    public static void closeWizard() {
    	dialogStage.close();
    }
    
    

    
    @FXML
    public void handleClose() {
    	dialogStage.close();
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
