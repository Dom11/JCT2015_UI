package com.bluesky.jct.view;

import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;


/**
 * Page 3 of the self-service portal.
 * Generating an RPM package.
 * 
 * @author Dominik
 */
public class SelfServiceControllerPage3 {

	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	@FXML
	private HBox hbox;
	@FXML
	private TableView<Profile> profileTable;
	@FXML
	private TableColumn<Profile, Integer> jiraIdColumn;
	@FXML
	private TableColumn<Profile, String> descriptionColumn;
	@FXML
	private TableColumn<Profile, Integer> prefixIdColumn;
	@FXML
	private TableColumn<Profile, Integer> jbarIdColumn;
	@FXML
	private TableColumn<Profile, Integer> environmentIdColumn;

	private ObservableList<Profile> profileData = FXCollections.observableArrayList();
	
			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public SelfServiceControllerPage3() {
		super();
		profileData = ProfileFunctions.getTempProfiles();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		nextButton.isFocused();
		
		profileTable.setItems(profileData);	
	
		// Initialize the profileTable with the three columns.
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().profileDescriptionProperty());		
		jiraIdColumn.setCellValueFactory(cellData -> cellData.getValue().jiraIdProperty().asObject());
		prefixIdColumn.setCellValueFactory(cellData -> cellData.getValue().prefixIdProperty().asObject());
		jbarIdColumn.setCellValueFactory(cellData -> cellData.getValue().jbarIdProperty().asObject());
		environmentIdColumn.setCellValueFactory(cellData -> cellData.getValue().environmentIdProperty().asObject());
		
        for(int i = 0; i < profileData.size(); i++) {
            System.out.println(profileData.get(i).getEnvironmentId());
        }
        

		
		
	}
	
    
    @FXML
    private void handleNext() {
   		SelfServiceController.increasePageCounter();
    }
    
    
    @FXML
    private void handleBack() {
    	SelfServiceController.decreasePageCounter();
    }

}