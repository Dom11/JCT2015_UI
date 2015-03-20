package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxJvmArgument;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


/**
 * Page 2 of the new profile wizard.
 * Creating or adding an existing JVM Argument to the created profile.
 * 
 * @author Dominik
 */
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
		super();
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
    	
    	String jvmArgumentText = jvmArgumentField.getText();
    	
    	if (ProfileFunctions.isJvmArgumentInputValid(jvmArgumentText)) {
    		
    		// save Text as JvmArgument
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
	 * Senses whether user has clicked the create button.
	 * 
	 * @return true if clicked, false otherwise
	 */
	public boolean isCreateClicked() {
		return createClicked;
	}
	
}