package com.bluesky.jct;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Login Dialog to set the user type to Admin or Regular
 * 
 * @author Dominik Rey
 */
public class LoginDialog {
	
	private final static BooleanProperty GRANTED_ACCESS = new SimpleBooleanProperty(false);
	private final static String USER = System.getProperty("user.name");
	private final static String ADMIN = "Administrator";
	private static String REST_SERVICE_URL = "";
	private static final String REST_SERVICE_URL_LOCAL = "http://localhost:8080/RestService/rest";
	private static final String REST_SERVICE_URL_OPENSHIFT = "http://jct2015openshift-designby3.rhcloud.com/RestService/rest";
	private static String TEST_REST_SERVICE_URL = "";
	private static final String TEST_REST_SERVICE_URL_LOCAL = "http://localhost:8080";
	private static final String TEST_REST_SERVICE_URL_OPENSHIFT = "http://jct2015openshift-designby3.rhcloud.com";
	private static boolean disabled = true;
	private static String buttonText = "";


	/**
	 * Login Dialog
	 * Opens a dialog to select user type (Administrator or Regular)
	 * and provides 3 attempts to enter the right password.
	 * 
	 * @return GRANTED_ACCESS (boolean)
	 */
	public static boolean openLoginDialog() {

		// Create the custom dialog.
		Dialog<String> dialog = new Dialog<>();
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Welcome " + USER + "\nto JCT_2015 prototype");
		stage.getIcons().add(new Image("file:resources/images/Secure_128.png"));
		dialog.setGraphic(new ImageView("file:resources/images/JCT_2015_64.png"));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ComboBox<String> userOption = new ComboBox<String>();
		ObservableList<String> userOptionChoices = FXCollections.observableArrayList();
		userOptionChoices.add(new String("Regular"));
		userOptionChoices.add(new String("Administrator"));
		userOption.setItems(userOptionChoices);
		userOption.setValue("Regular");
		userOption.setMaxWidth(140);
		
		ComboBox<String> connectionOption = new ComboBox<String>();
		ObservableList<String> connectionOptionChoices = FXCollections.observableArrayList();
		connectionOptionChoices.add(new String("OpenShift"));
		connectionOptionChoices.add(new String("Local"));
		connectionOption.setItems(connectionOptionChoices);
		connectionOption.setValue("OpenShift");
		connectionOption.setMaxWidth(140);		
		
		grid.add(new Label("User Type:"), 0, 1);
		grid.add(userOption, 1, 1);
		grid.add(new Label("Connection to:"), 0, 2);
		grid.add(connectionOption, 1, 2);	
		
		dialog.getDialogPane().setContent(grid);
		
		
		// Request focus on the username field by default.
		Platform.runLater(() -> userOption.requestFocus());	
		
		
		// listener on user combobox selection.
		userOption.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches(ADMIN) == true) {
				disabled = false;
			} else {
				disabled = true;
			}
		});
				
		
		// listener on connection combobox selection.
		connectionOption.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue.matches("OpenShift") == true) {
						REST_SERVICE_URL = REST_SERVICE_URL_OPENSHIFT;
						TEST_REST_SERVICE_URL = TEST_REST_SERVICE_URL_OPENSHIFT;
					} else {
						REST_SERVICE_URL = REST_SERVICE_URL_LOCAL;
						TEST_REST_SERVICE_URL = TEST_REST_SERVICE_URL_LOCAL;
					}
				});
		
		
		// Convert the result to a password when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				buttonText = "ok";
				return buttonText;
				}
			buttonText = "cancel";
			return buttonText;
		});
		
		
		Optional<String> result = dialog.showAndWait();
		
		// checks the number of attempts in case password is incorrect
		result.ifPresent(buttonText -> {			
			if (buttonText == "ok") {
				GRANTED_ACCESS.set(true);
			} else {
				Platform.exit();
			}
		});
		return GRANTED_ACCESS.get();
	}
	
	
	/**
	 * Provides the System's user name.
	 * 
	 * @return USER
	 */
	public static String getUserName() {
		return USER;
	}
	
	
	/**
	 * This boolean value can be used in order to show/hide certain buttons or functions.
	 * Administrator sees everything, Regular sees what has been defined.
	 * 
	 * @return disabled
	 */
	public static boolean getDisabledType() {
		return disabled;
	}
	
	
	/**
	 * Provides the URL for the REST API depending on the selection during Log-in.
	 * 
	 * @return REST_SERVICE_URL
	 */
	public static String getConnection() {
		return REST_SERVICE_URL;
	}
	
	
	/**
	 * Provides the URL to test the connection to the Application Server where the REST API is located.
	 * 
	 * @return REST_SERVICE_URL
	 */
	public static String getTestConnection() {
		return TEST_REST_SERVICE_URL;
	}
	
}