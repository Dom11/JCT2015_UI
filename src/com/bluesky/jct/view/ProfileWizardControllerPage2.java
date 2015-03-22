package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxJvmArgument;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;

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
	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	
	private Profile tempProfile;	
	private ObservableList<JvmArgument> jvmData = FXCollections.observableArrayList();
	private int index = 0;
	private String jvmArgumentText;
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileWizardControllerPage2() {
		super();
		tempProfile = ProfileFunctions.getTempProfile();
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
    	
    	jvmArgumentText = jvmArgumentField.getText();
    	
    	if (ProfileFunctions.isJvmArgumentInputValid(jvmArgumentText)) {
    		
    		// save Text as JvmArgument
    		RestClient.createJvmArgument(jvmArgumentText);
	    	
	       	// refresh ComboBox
	    	ComboBoxJvmArgument.loadJvmArgumentData();
	    	
	    	// select new created JvmArgument and clear TextField
	    	index = jvmData.size() - 1;
	       	jvmArgumentComboBox.setValue(jvmData.get(index));
	       	jvmArgumentField.setText(null);
    	}
    }
    
    
	/**
	 * User clicks on Next button.
	 * Selected jvmArgument from the comboBox will be added to the temporary Profile.
	 */
    @FXML
    private void handleNext() {
    	
    	int selectedJvmId = jvmArgumentComboBox.getValue().getId();
    	String selectedJvmArgument = jvmArgumentComboBox.getValue().getName();
    	
    	tempProfile.setJvmId(selectedJvmId);

	   	// load next page of wizard
		ProfileWizardController.increasePageCounter();
		ProfileFunctions.setTempProfile(tempProfile);
		
		String headerText = "JVM Argument";
		String contentText = "Argument '" + selectedJvmArgument + "' has been added.";
		ExceptionHandling.handleInformation(headerText, contentText);
    }
    
    
    @FXML
    private void handleBack() {
    	ProfileWizardController.decreasePageCounter();
    }

}