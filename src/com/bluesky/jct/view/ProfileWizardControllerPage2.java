package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxJvmArgument;
import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public class ProfileWizardControllerPage2 {
	@FXML
	private TextField jvmArgumentField;
	@FXML
	private ComboBox<JvmArgument> jvmArgumentComboBox;
	@FXML
	private Button createButton;
	
	private ObservableList<JvmArgument> jvmData = FXCollections.observableArrayList();
	private int index = 0;
	public static boolean createClicked;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage2() {
		ComboBoxJvmArgument.loadJvmArgumentData();
		jvmData = ComboBoxJvmArgument.getJvmArgumentData();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		jvmArgumentComboBox.setItems(jvmData);
		ComboBoxJvmArgument.iniJvmArgumentCombobox(jvmArgumentComboBox);
		jvmArgumentComboBox.setValue(jvmData.get(index));
	}

	
	/**
	 * Saves the new value from the TextField as JvmArgument
	 * and selects that item from the refreshed ComboBox. 
	 */
    @FXML
    private void handleCreate() {
    	
    	if (isInputValid()) {
    		// save Text as JvmArgument
	    	String jvmArgumentText = jvmArgumentField.getText();
	    	RestClient.createJvmArgument(jvmArgumentText);
	       	// refresh ComboBox
	    	ComboBoxJvmArgument.loadJvmArgumentData();
	    	// select new created JvmArgument and clear TextField
	    	index = jvmData.size() - 1;
	       	jvmArgumentComboBox.setValue(jvmData.get(index));
	       	jvmArgumentField.setText(null);
	       	// set create Button as inactive
	       	createClicked = true;
	       	createButton.setDisable(isCreateClicked());   
    	}
    }
    
    
    /**
     * Validates the user input in the TextField.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        // JvmArgument can only be 45 characters long
        if (jvmArgumentField.getText().length() > 45) {
        		errorMessage += "JVM Argument can only be 45 characters long\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
          	Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Invalid Entry");
    		alert.setHeaderText("Please correct your input");
    		alert.setContentText(errorMessage);

    		alert.showAndWait();
    		
            return false;
        }
    }
    
    
	/**
	 * Returns true if the user clicked create, false otherwise.
	 * 
	 * @return
	 */
	public boolean isCreateClicked() {
		return createClicked;
	}
}
