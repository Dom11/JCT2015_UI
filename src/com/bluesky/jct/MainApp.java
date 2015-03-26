package com.bluesky.jct;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;
import com.bluesky.jct.view.ProfileEditDialogController;
import com.bluesky.jct.view.ProfileOverviewController;
import com.bluesky.jct.view.ProfileWizardController;
import com.bluesky.jct.view.RootLayoutController;
import com.bluesky.jct.view.SelfServiceController;
import com.bluesky.jct.view.StatisticsController;


/**
 * This is the main class of the whole prototype application.
 * It holds the methods to open the according stages and scenes.
 * 
 * @author Dominik
 * @version 31.03.2015
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private BorderPane profileWizardRoot;
	private BorderPane selfServiceRoot;
	

	public static void main(String[] args) {
		launch(args);
	}	
	

	@Override
	public void start(Stage primaryStage) {
		
		// opens the login
		if(LoginDialog.openLoginDialog() == false) {
			System.exit(0);
		};

		// after successful login, primary stage will be loaded
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JCT 2015 (Prototype)");
	    this.primaryStage.getIcons().add(new Image("file:resources/images/JCT_2015_64.png"));

		initRootLayout();
		
		// connectivity to REST API and DB will be checked
		checkConnectivity();
		
		// data will be loaded and main screen opened
		showProfileOverview();
	}

	
	/**
	 * Returns the main stage.
	 * 
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	/**
	 * Initializes the root layout.
	 */
	private void initRootLayout() {

		try {
			// Load root Layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Shows the profile overview inside the root layout.
	 */
	private void showProfileOverview() {

		try {
			// Load profile overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileOverview.fxml"));
			AnchorPane profileOverview = (AnchorPane) loader.load();

			// Set profile overview into the center of root layout.
			rootLayout.setCenter(profileOverview);

			// Give the controller access to the main application.
			ProfileOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Opens a dialog to display the details for a specific profile.
	 * 
	 * @param profile
	 * @return true if the user clicked OK, false otherwise
	 */
	public boolean showProfileEditDialog(Profile profile) {

		try {
			// Load the fxml file and create a new stage for the pop-up dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Selection Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the profile into the controller.
			ProfileEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProfile(profile);

			// Show the dialog and wait until the user closes it.
			dialogStage.showAndWait();

			return controller.isSaveClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	/**
	 * This method starts the wizard to create a new profile. 
	 */
	public void showProfileWizard() {

		try {
			// Load root from wizard.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileWizardRoot.fxml"));
			profileWizardRoot = (BorderPane) loader.load();
			
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Profile Wizard");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(profileWizardRoot);
			dialogStage.setScene(scene);

			// Give the controller access to the main app.
			ProfileWizardController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			
			showProfileWizardPage(1);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * loads the page (AnchorPane) in the new profile wizard according to the pageNumber. 
	 * 
	 * @param pageNumber
	 */
	public void showProfileWizardPage(int pageNumber) {

		try {
			// Load page from wizard.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileWizardPage" + pageNumber + ".fxml"));
			AnchorPane profileWizardPage = (AnchorPane) loader.load();

			// Set profile overview into the center of root layout.
			profileWizardRoot.setCenter(profileWizardPage);		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method starts the wizard to create a new profile. 
	 */
	public void showSelfServicePortal() {

		try {
			// Load root from wizard.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SelfServiceRoot.fxml"));
			selfServiceRoot = (BorderPane) loader.load();
			
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Self-Service Portal");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(selfServiceRoot);
			dialogStage.setScene(scene);

			// Give the controller access to the main app.
			SelfServiceController controller = loader.getController();
			controller.setMainApp(this);
			controller.setDialogStage(dialogStage);
			
			showSelfServicePortaldPage(1);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * loads the page (AnchorPane) in the selfService portal to the pageNumber. 
	 * 
	 * @param pageNumber
	 */
	public void showSelfServicePortaldPage(int pageNumber) {

		try {
			// Load page from wizard.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SelfServicePage" + pageNumber + ".fxml"));
			AnchorPane selfServicePage = (AnchorPane) loader.load();

			// Set profile overview into the center of root layout.
			selfServiceRoot.setCenter(selfServicePage);		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Checks the connectivity of the REST API as well as the DB.
	 * In case one of the connection attempts fails, an error message will provide further information. 
	 */
	public void checkConnectivity() {
		
		// TODO check method whether there is a better way to do than currently coded

		if (RestClient.checkConnectionRestServer() == true) {
			
			String headerText = "Server Error";
			String contentText = "The Rest Service is currently not accessible!\n"
					           + "Please contact the System Administrator.";
			ExceptionHandling.handleError(headerText, contentText);
			System.exit(0);

		} else {
			if (RestClient.checkConnectionDB() == true) {
				
				String headerText = "DB Server Error";
				String contentText = "The Database Server is currently not accessible!\n"
								   + "Please contact the System Administrator.";
				ExceptionHandling.handleError(headerText, contentText);
				System.exit(0);
			};
		}
	}
	
	
	/**
	 * Opens a dialog to show profile statistics.
	 */
	public void showProfileStatistics() {
	    try {
	        // Load the fxml file and create a new stage for the popup.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ProfileStatistics.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Profile Statistics");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the persons into the controller.
	        StatisticsController controller = loader.getController();
	        controller.setProfileData(ProfileOverviewController.getProfileData());

	        dialogStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}