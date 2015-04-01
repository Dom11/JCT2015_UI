package com.bluesky.jct.view;

import java.time.Duration;
import java.util.Date;

import org.reactfx.util.FxTimer;

import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;


/**
 * Page 3 of the new profile wizard.
 * Generating an RPM package.
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage3 {

	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	@FXML
	private HBox hbox;
	@FXML
	private ProgressBar profileCreationProgress;

	private Profile tempProfile;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage3() {
		super();
		tempProfile = ProfileFunctions.getTempProfile();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		nextButton.isFocused();
		profileCreationProgress.setVisible(false);
	}

    
    @FXML
    private void handleNext() {

		profileCreationProgress.setVisible(true);
    	Date date = new Date();
    	tempProfile.setRpmGenerationDate(date);

		FxTimer.runLater(Duration.ofSeconds(3), () -> {
		   	// load next page of wizard
			ProfileWizardController.increasePageCounter();
			ProfileFunctions.setTempProfile(tempProfile);
			
			String headerText = "RPM";
			String contentText = "Package has been successfully generated.";
			ExceptionHandling.handleInformation(headerText, contentText);		
		});
    }
    
    
    @FXML
    private void handleBack() {
    	ProfileWizardController.decreasePageCounter();
    }

}