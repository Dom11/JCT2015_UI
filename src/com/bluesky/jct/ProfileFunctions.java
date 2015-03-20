package com.bluesky.jct;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;
import com.bluesky.jct.view.ProfileOverviewController;


/**
 * This class contains all the static functions that will/can be called from different classes.
 * 
 * @author Dominik
 */
public class ProfileFunctions {
	
	
	public static void deleteProfile(Profile profile) {
		
		// show dialog and get confirmation from user
		int activeProfile = profile.getProfileId();
		
       	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete the following Profile?");
		alert.setContentText("Profile Index: " + activeProfile 
						 + "\nProfile Description: " + profile.getProfileDescription());
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			RestClient.deleteProfile(activeProfile);
			alert.close();

			String headerText = "";
  			String contentText = "Profile Index: " + activeProfile 
					+ "\nHTTP Status Code: " + RestClient.getHttpStatusCode();
  			
   			if (RestClient.isSuccessful() == false) {
   				headerText = "Profile could not be deleted!";
   			} else {
   				headerText = "Profile has been successfully deleted";
   			};
   			
            // Show information message     
        	ExceptionHandling.handleInformation(headerText, contentText);    		
			
        	// reload profile information to provide the latest status
           	ProfileOverviewController.loadProfileViewData();
		}
	}
	
	
    /**
     * Validates the user inputs for a profile.
     * This method should be called whenever a profile gets created or changed.
     * 
     * @return true if the inputs are valid
     */
    public static boolean isProfileInputValid(String profileDescription, String profileDnsName, String profileComponent) {

    	String headerText = "Please correct invalid entry!";
    	String errorMessage = "";
        
        // Description is mandatory and can only be 255 characters long
        if (profileDescription == null || profileDescription.length() == 0) {
            errorMessage += "Please enter a Profile Description!\n"; 
        } else {
        	if (profileDescription.length() > 255) {
        		errorMessage += "Lenght of description can only be 255 characters.\n"; 
        	}
        }
        
        // DNS Name is mandatory and can only be 255 characters long
        if (profileDnsName == null || profileDnsName.length() == 0) {
            errorMessage += "Please enter a DNS Name!\n"; 
        } else {
        	if (profileDnsName.length() > 255) {
        		errorMessage += "Lenght of DNS Name can only be 255 characters.\n"; 
        	}
        }
        
        // Component is optional but can only hold 255 characters
        if (profileComponent.length() > 255) {
    		errorMessage += "Component can only be 255 characters\n"; 
    	}

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show warning message     
        	ExceptionHandling.handleWarning(headerText, errorMessage);    		
            return false;
        }
    }
    
    
    /**
     * Validates the user inputs for a jvmArgument.
     * This method should be called whenever a jvmArgument gets created.
     * 
     * @return true if the inputs are valid
     */
    public static boolean isJvmArgumentInputValid(String jvmArgumentText) {
    	
    	String headerText = "Please correct invalid entry!";
    	String errorMessage = "";
        
        // Text box is empty
        if (jvmArgumentText.length() == 0) {
        	errorMessage += "No argument creation possible since text box is empty!\n";
        
        // JvmArgument can only be 255 characters long
        } else if (jvmArgumentText.length() > 255) {
        	errorMessage += "JVM Argument can only be 255 characters of lenght!\n"; 
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show warning message      	
        	ExceptionHandling.handleWarning(headerText, errorMessage);
            return false;
        }
    }
	
}