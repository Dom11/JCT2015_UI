package com.bluesky.jct.view;

import com.bluesky.jct.ComboBoxJvmArgument;
import com.bluesky.jct.model.JvmArgument;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;



public class ProfileWizardControllerPage2 {
	@FXML
	private TextField jvmArgumentField;
	@FXML
	private ComboBox<JvmArgument> jvmArgumentComboBox;
	@FXML
	private Button createButton;
	
	private ObservableList<JvmArgument> jvmData = FXCollections.observableArrayList();
	
	public static boolean createClicked;
	
	private String newArgument = " ";
	
			
	/**
	 * The constructor. The constructor is called before the initialize() method.
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
		
		jvmArgumentComboBox.setEditable(true);
		
		jvmArgumentComboBox.setOnAction((event) -> {
			newArgument = jvmArgumentComboBox.getSelectionModel().getSelectedItem().toString();
			System.out.println(newArgument);
        });

	}
	
	
    @FXML
    private void handleCreate() {
    
//    	String jvmArgumentText = jvmArgumentField.getText();
//      	RestClient.createJvmArgument(jvmArgumentText);
       	
       	createClicked = true;
//       	createButton.setDisable(isCreateClicked());
//       	ProfileOverviewController.loadProfileViewData();

       	System.out.println("");
       	
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
