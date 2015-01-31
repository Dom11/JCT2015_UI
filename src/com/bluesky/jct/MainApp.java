package com.bluesky.jct;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.bluesky.jct.model.Profile;
import com.bluesky.jct.view.ProfileEditDialogController;
import com.bluesky.jct.view.ProfileOverviewController;
import com.bluesky.jct.view.RootLayoutController;


public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
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
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("JCT 2015 (Prototype)");
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Welcome information");
		alert.setHeaderText("JCT 2015 (Prototype)");
		alert.setContentText("Welcome " + getUserName() + ", the tool is about to load.");
		alert.showAndWait();

		initRootLayout();

		showProfileOverview();
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
	 * Opens a dialog to display the details for the specific profile.
	 * If the user clicks Edit, he/she can adjust the entries and needs to confirm changes with Save.
	 * The changes are saved into the provided profile object and true is returned.
	 * 
	 * @param profile the profile object to be displayed/edited
	 * @return true if the user clicked OK, false otherwise
	 */
	public boolean showProfileEditDialog(Profile profile, ProfileOverviewController profileOverviewController) {
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
			controller.setProfileOverviewController(profileOverviewController);
			
			// Show the dialog and wait until the user closes it.
			dialogStage.showAndWait();			
			
			return controller.isSaveClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	/**
	 * Returns the user name.
	 * @return
	 */
    private String getUserName() {
        return System.getProperty("user.name");
    }
}