package com.bluesky.jct;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.view.ProfileOverviewController;


public class ProfileFunctions {
	
	
	public static void editProfile(Profile profile) {
		
	}
	
	
	public static void deleteProfile(Profile profile) {
		
		// show dialog and get confirmation from user
		int activeProfile = profile.getProfileId();
		
       	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete the following Profile?");
		alert.setContentText("Profile Index: " + activeProfile 
						 + "\nProfile Description: "	+ profile.getProfileDescription());
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			RestClient.deleteProfile(activeProfile);
			alert.close();

			String headerText = null;
   			if (RestClient.isSuccessful() == false) {
   				headerText = "Profile could not be deleted!";
   			} else {
   				headerText = "Profile has been successfully deleted";
   			};
			
          	Alert info = new Alert(AlertType.INFORMATION);
           	info.setTitle("Information");
           	info.setHeaderText(headerText);
           	info.setContentText("Profile Index: " + activeProfile 
    						+ "\nHTTP Status Code: " + RestClient.getHttpStatusCode());
           	info.showAndWait();
           	ProfileOverviewController.loadProfileViewData();
	        
		} else {
		    // ... user clicks CANCEL or closed the dialog
			alert.close();
		}
	}
}
