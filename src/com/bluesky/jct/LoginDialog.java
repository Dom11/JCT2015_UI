package com.bluesky.jct;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginDialog {
	
	private final static String PASSWORD = "1234";
	private final static BooleanProperty GRANTED_ACCESS = new SimpleBooleanProperty(false);
	private final static int MAX_ATTEMPTS = 3;
	private final static IntegerProperty ATTEMPTS = new SimpleIntegerProperty(0);

	
	public LoginDialog() {
	}
	
	private static String getUserName() {
		return System.getProperty("user.name");
	}
	

	/**
	 * Login Dialog
	 * Source http://code.makery.ch/blog/javafx-dialogs-official/
	 * 
	 * @author Marco Jakob, modified by Dominik Rey
	 */
	public static boolean openLoginDialog() {

		// Create the custom dialog.
		Dialog<String> dialog = new Dialog<>();
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Welcome " + getUserName() + "\nto JCT_2015 prototype");
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
		
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		
		Label info = new Label();
		info.setText("incorrect");
		info.setStyle("-fx-text-fill: #f70c0c");
		info.setVisible(false);
		
		grid.add(new Label("User:"), 0, 0);
		grid.add(userOption, 1, 0);		
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);
		grid.add(info, 2, 1);
		
		dialog.getDialogPane().setContent(grid);
		
		
		// Request focus on the username field by default.
		Platform.runLater(() -> password.requestFocus());	
		
		
		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		

		// listener when the user types into the password field.
		password.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
			boolean granted = password.getText().equals(PASSWORD);
			GRANTED_ACCESS.set(granted);
		});
		
		
		// listener on number of attempts.
		ATTEMPTS.addListener((obs, ov, nv) -> {
			if (MAX_ATTEMPTS == nv.intValue()) {
				// failed attemps
				Platform.exit();
			}
		});
		

		// Convert the result to a password when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				ATTEMPTS.set(ATTEMPTS.add(1).get());
				System.out.println("Attempts: " + ATTEMPTS.get());
				
				return (password.getText());
				}
			return null;
		});
		
		
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(PasswordString -> {
		    System.out.println(password.getText());
		});
		return GRANTED_ACCESS.get();
	}
}
