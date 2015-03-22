package com.bluesky.jct.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Page 3 of the new profile wizard.
 * Generating an RPM package.
 * 
 * @author Dominik
 */
public class ProfileWizardControllerPage3 {

	@FXML
	private Button nextButton;

	public static Profile tempProfile3;
	public static boolean createClicked;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage3() {
		super();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	
    
    @FXML
    private void handleNext() {

    	Date date = new Date();
    	tempProfile3.setRpmGenerationDate(date);

	   	// load next page of wizard
		ProfileWizardController.increasePageCounter();
		ProfileWizardControllerPage4.setTempProfile(tempProfile3);
		
		String headerText = "RPM";
		String contentText = "RPM has been generated.";
		ExceptionHandling.handleInformation(headerText, contentText);
		
		System.out.println(date.toString());
    }
    
    
	/**
	 * Senses whether user has clicked the create button.
	 * 
	 * @return true if clicked, false otherwise
	 */
	public boolean isCreateClicked() {
		return createClicked;
	}
	
	
	public static void setTempProfile(Profile tempProfile) {
		tempProfile3 = tempProfile;
	}
	
}