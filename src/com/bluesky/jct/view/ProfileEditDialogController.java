package com.bluesky.jct.view;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;


/**
 * Dialog to display/edit details of a profile
 * 
 * @author Dominik
 */
public class ProfileEditDialogController {
	@FXML
	private TextField profileNameField;
	@FXML
	private TextField profileDescriptionField;
	@FXML
	private TextField profileDomainField;
	@FXML
	private TextField profilePrefixField;
	@FXML
	private TextField profileJbarField;
	@FXML
	private TextField profileEnvironmentField;	
	@FXML
	private TextField profileDnsField;
	@FXML
	private TextField profileHostField;	
	@FXML
	private TextField profileJiraField;
	@FXML
	private TextField profileComponentField;
	
	@FXML
	private ComboBox<Domain> domainComboBox;

	@FXML
	private Button startServer;
	@FXML
	private HBox hbox;

	
//	private int selectedIndex;
	private Stage dialogStage;
	private Profile profile;
	private boolean saveClicked = false;
	
	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
	
	// Reference to the ProfileOverviewController Class
	private ProfileOverviewController profileOverviewController;
	
	
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ProfileEditDialogController() {
		loadDomainData();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// hides the save/cancel button and shows start/stop button
		hbox.setVisible(false);
		startServer.setVisible(true);
		domainComboBox.setItems(domainData);
		
		iniDomainCombobox();
	}
	
	
	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	
	/**
	 * Is called by the main application to give a reference back to ProfileOverviewController.
	 * 
	 * @param profileOverviewController
	 *
	public void setProfileOverviewController(ProfileOverviewController profileOverviewController) {
		this.profileOverviewController = profileOverviewController;
	}
*/	
	
	/**
	 * Sets the profile to be displayed in the dialog.
	 * 
	 * @param profile
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
		
		// Shows all the profile information
		profileNameField.setText(getProfileName(profile.getProfileId()));
		profileDescriptionField.setText(profile.getProfileDescription());
		profileDomainField.setText(getDomainName(profile.getDomainId()));
		profilePrefixField.setText(getPrefixName(profile.getPrefixId()));
		profileJbarField.setText(getJbarName(profile.getJbarId()));
		profileEnvironmentField.setText(getEnvironmentName(profile.getEnvironmentId()));
		profileDnsField.setText(profile.getProfileDnsName());
		profileHostField.setText(getHostName(profile.getHostId()));
		profileJiraField.setText(getJiraName(profile.getJiraId()));
		profileComponentField.setText(profile.getProfileComponent());
		
		// sets the text fields as inactive
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(true);
		profileDomainField.setDisable(true);		
		profilePrefixField.setDisable(true);
		profileJbarField.setDisable(true);
		profileEnvironmentField.setDisable(true);
		profileDnsField.setDisable(true);
		profileHostField.setDisable(true);	
		profileJiraField.setDisable(true);
		profileComponentField.setDisable(true);
	}
	
	
	private String getProfileName(int profileId) {
		ProfileView profileView = RestClient.findProfileView(profileId);
		return profileView.getProfileName();
	}
	
	private String getDomainName(int domainId) {
		Domain domain = RestClient.findDomain(domainId);
		return domain.getName();
	}

	private String getPrefixName(int prefixId) {
		Prefix prefix = RestClient.findPrefix(prefixId);
		return prefix.getName();
	}

	private String getJbarName(int jbarId) {
		Jbar jbar = RestClient.findJbar(jbarId);
		return jbar.getName();
	}	
	
	private String getEnvironmentName(int environmentId) {
		Environment environment = RestClient.findEnvironment(environmentId);
		return environment.getName();
	}
	
	private String getHostName(int hostId) {
		Host host = RestClient.findHost(hostId);
		return host.getName();
	}
	
	private String getJiraName(int jiraId) {
		Jira jira = RestClient.findJira(jiraId);
		return jira.getName();
	}

	
	/**
	 * Returns true if the user clicked save, false otherwise.
	 * 
	 * @return
	 */
	public boolean isSaveClicked() {
		return saveClicked;
	}
	
	
	/**
	 * Called when the user clicks the edit from the profile menu.
	 * Makes text fields to be editable.
	 */
	@FXML
	private void handleEditProfile() {
		// sets the text fields as active
		profileNameField.setDisable(true);	
		profileDescriptionField.setDisable(false);
		profileDomainField.setDisable(true);		
		profilePrefixField.setDisable(true);
		profileJbarField.setDisable(true);
		profileEnvironmentField.setDisable(true);
		profileDnsField.setDisable(false);
		profileHostField.setDisable(true);	
		profileJiraField.setDisable(true);
		profileComponentField.setDisable(false);
		
		// show save/cancel button and hide start/stop button
		hbox.setVisible(true);
		startServer.setVisible(false);
		// focusing on first editable field
		profileDescriptionField.requestFocus();
	}
	
	
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleSave() {
        if (isInputValid()) {
        	
        	int profileId = profile.getProfileId();
        	int environmentId = profile.getEnvironmentId();
        	int hostId = profile.getHostId();
        	int jbarId = profile.getJbarId();
        	int jiraId = profile.getJiraId();
        	int prefixId = profile.getPrefixId();
        	int domainId = profile.getDomainId();
        	String profileDescription = profileDescriptionField.getText();
        	String profileDnsName = profileDnsField.getText();
        	String profileComponentName = profileComponentField.getText();
        	Integer version = profile.getVersion();
        	
        	RestClient.editProfile(profileId, environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, profileDnsName, profileComponentName, version);
    
            saveClicked = true;
            dialogStage.close();
        }
    }
    
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        
        //TODO add missing validation checks 
/**        
        if (profileIdField.getText() == null || profileIdField.getText().length() == 0) {
            errorMessage += "No valid ID!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(profileIdField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid ID (must be an integer)!\n"; 
            }
        }   
*/
        /**
        if (profileJBarNameField.getText() == null || profileJBarNameField.getText().length() == 0) {
            errorMessage += "No valid JBar Name!\n"; 
        }
        */

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	
           	Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Invalid Fields");
    		alert.setHeaderText("Please correct invalid fields");
    		alert.setContentText(errorMessage);

    		alert.showAndWait();
    		
            return false;
        }
        
    }
	
	
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
    //TODO
    @FXML
    private void handleClone() {
    	System.out.println("start Wizard with certain fields filled out");
    }
    
    
	/**
	 * Called when the user clicks on the delete menu item.
	 */
	@FXML
	private void handleDelete() {
		// show dialog and get confirmation from user
		int activeProfile = profile.getProfileId();
		
       	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Are you sure you want to delete the following Profile?");
		alert.setContentText("Profile Index: "
								+ activeProfile 
								+ "\nProfile Description: " 
								+ profile.getProfileDescription());
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			// ... user clicks OK
			RestClient.deleteProfile(activeProfile);
			alert.close();

	        //TODO fix listener on for tableView and remove line below once fixed.
//			profileOverviewController.deleteProfile(selectedIndex);
//			profileOverviewController.refreshProfileData();

			String headerText = null;
			
   			if (RestClient.isSuccessful() == false) {
   				headerText = "Profile could not be deleted!";
   			} else {
   				headerText = "Profile has been successfully deleted";
   			};
			
          	Alert info = new Alert(AlertType.INFORMATION);
           	info.setTitle("Information");
           	info.setHeaderText(headerText);
           	info.setContentText("Profile Index: "
    								+ activeProfile 
    								+ "\nHTTP Status Code: " 
    								+ RestClient.getHttpStatusCode());
           	info.showAndWait();
           	profileOverviewController.loadProfileViewData();
	        dialogStage.close();
	        
		} else {
		    // ... user clicks CANCEL or closed the dialog
			alert.close();
		}
	}
    
    
    //TODO
    @FXML
    private void handleLogFile() {
    	System.out.println("show Log-File");
    }
    
    
    //TODO
    @FXML
    private void handleStatusReport() {
    	System.out.println("show Status Report");
    }
    
    
    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleInformation() {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Edit Dialog");
		alert.setContentText("For further assistance/support, please contact: ");

		alert.showAndWait();
    }
    
    
	public void loadDomainData() {
		List<Domain> domains = RestClient.findAllDomain();
		domainData.clear();
		
		try {
			for (Domain domain : domains) {
				domainData.add(domain);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
	/**
	 * Returns data as an observable list.
	 * 
	 * @return environmentData
	 */
	public ObservableList<Domain> getDomainData() {
		return domainData;
	}
	
	
	/**
	 * Combobox Domain
	 */
	private void iniDomainCombobox() {
		// Define rendering of the list of values in ComboBox drop down.
		domainComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Domain>() {
				@Override
				protected void updateItem(Domain item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName());
					}
				}
			};
		});
		
		// Define rendering of selected value shown in ComboBox.
		domainComboBox.setConverter(new StringConverter<Domain>() {
			@Override
			public String toString(Domain domain) {
				if (domain == null) {
					return null;
				} else {
					return domain.getName();
				}
			}

			@Override
			public Domain fromString(String domainString) {
				return null; // No conversion fromString needed.
			}
		});
	}
	
	
	public void setProfileOverviewController(ProfileOverviewController profileOverviewController) {
		this.profileOverviewController = profileOverviewController;
	}
}
