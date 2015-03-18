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


public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private BorderPane profileWizardRoot;


	/**
	 * Constructor
	 */
	public MainApp() {
	}
	

	public static void main(String[] args) {
		launch(args);
	}	
	

	@Override
	 public void start(Stage primaryStage) {
		
		//TODO finalize Login Dialog with userList to distinguish between admin and regular user
//		if(LoginDialog.openLoginDialog() == false) {
//			System.exit(0);
//		};


		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JCT 2015 (Prototype)");
	    this.primaryStage.getIcons().add(new Image("file:resources/images/JCT_2015_64.png"));

		initRootLayout();
//		checkConnectivity();
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
	 * 
	 * @return true/false
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
			
			showProfileWizardPage(1);
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * loads the right page on the new profile wizard. 
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
	public boolean showProfileWizardNew() {

		try {
			// Load the fxml file and create a new stage for the pop-up dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ProfileWizard.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Wizard");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the profile into the controller.
			ProfileWizardController controller = loader.getController();
//			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it.
			dialogStage.showAndWait();

//			return controller.isExitClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
*/
	
	public void checkConnectivity() {

		if (RestClient.checkConnectionRestServer() == false) {
			
			ExceptionHandling.handleError("Server Error",
					"The Rest Service is currently not accessible!\nPlease contact the System Administrator.");
			System.exit(0);

		} else {
			if (RestClient.checkConnectionDB() == false) {

				ExceptionHandling.handleError("DB Server Error",
						"The Database Server is currently not accessible!\nPlease contact the System Administrator.");
				System.exit(0);
			};
		}
	}

}