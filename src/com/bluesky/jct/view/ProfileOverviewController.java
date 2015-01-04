package com.bluesky.jct.view;

import com.bluesky.jct.MainApp;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;


public class ProfileOverviewController {
	@FXML
	private TextField searchField;
	@FXML
	private TableView<Profile> profileTable;
	@FXML
	private TableColumn<Profile, String> profileDescriptionColumn;
	@FXML
	private TableColumn<Profile, String> profileHosteNameColumn;
	@FXML
	private TableColumn<Profile, String> profileInstanceColumn;
	@FXML
	private ComboBox<Profile> environmentComboBox;
	@FXML
	private ComboBox<Profile> jBarComboBox;

	private ObservableList<Profile> profileData = FXCollections.observableArrayList();

	private Profile tempProfile;
	private int selectedIndex;
	private ProfileOverviewController profileOverviewController;

	// Reference to the main Application
	private MainApp mainApp;

	
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ProfileOverviewController() {
		// sets variable to reference to this class
		setProfileOverviewController(this);
		// some sample profile data
		profileData.add(new Profile("UCM DEV environment_1", "DEV"));
		profileData.add(new Profile("UCM UAT environment_2", "UAT"));
		profileData.add(new Profile("UCM PRO environment_3", "PRO"));
		profileData.add(new Profile("CCM DEV environment_4", "DEV"));
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the profile table with the three columns.
		profileDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().profileDescriptionProperty());
		profileHosteNameColumn.setCellValueFactory(cellData -> cellData.getValue().profileHostNameProperty());
		profileInstanceColumn.setCellValueFactory(cellData -> cellData.getValue().profileInstanceProperty());
		
		Tooltip t = new Tooltip("double click for more details");
		Tooltip.install(profileTable, t);

		/**
		 * search Field
		 * 
		 * @author Marco Jakob
		 */
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Profile> filteredData = new FilteredList<>(profileData, p -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(profile -> {
				// If filter text is empty, display all profiles.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every profile with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (profile.getProfileDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (profile.getProfileEnvironment().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Profile> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(profileTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		profileTable.setItems(sortedData);
	

		/**
		 * Combobox Environment
		 */
		// Define rendering of the list of values in ComboBox drop down.
		environmentComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Profile>() {
				@Override
				protected void updateItem(Profile item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getProfileEnvironment());
					}
				}
			};
		});

		// Define rendering of selected value shown in ComboBox.
		environmentComboBox.setConverter(new StringConverter<Profile>() {
			@Override
			public String toString(Profile profile) {
				if (profile == null) {
					return null;
				} else {
					return profile.getProfileEnvironment();
				}
			}

			@Override
			public Profile fromString(String profileString) {
				return null; // No conversion fromString needed.
			}
		});

		// Handle ComboBox event.
		environmentComboBox.setOnAction((event) -> {
			Profile selectedProfile = environmentComboBox.getSelectionModel().getSelectedItem();
			System.out.println("ComboBox Action (selected: " + selectedProfile.getProfileEnvironment().toString() + ")");
			});
		
		
		/**
		 * Combobox JBar Name
		 */
		// Define rendering of the list of values in ComboBox drop down.
		jBarComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Profile>() {
				@Override
				protected void updateItem(Profile item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getProfileJBarName());
					}
				}
			};
		});

		// Define rendering of selected value shown in ComboBox.
		jBarComboBox.setConverter(new StringConverter<Profile>() {
			@Override
			public String toString(Profile profile) {
				if (profile == null) {
					return null;
				} else {
					return profile.getProfileJBarName();
				}
			}

			@Override
			public Profile fromString(String profileJBarNameString) {
				return null; // No conversion fromString needed.
			}
		});

		// writes the tempProfileID to the variable on selection
		profileTable.getSelectionModel().selectedItemProperty().addListener(
						(observable, oldValue, newValue) -> tempProfile = newValue);

		// opens the Edit dialog on double click
		profileTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					selectedIndex = profileTable.getSelectionModel().getSelectedIndex();
					mainApp.showProfileEditDialog(tempProfile, selectedIndex, profileOverviewController);
				}
			}
		});
	}

	
	/**
	 * Called when the user clicks the new button. Opens a wizard to create a
	 * new profile.
	 */
	@FXML
	private void handleNewProfile() {
		System.out.println("start Wizard");
		RestClient.getAll();
	}

	
	public void deleteProfile(int selectedIndex) {
		profileData.remove(selectedIndex);
	}
	
	/**
	 * Returns the data as an observable list of Profiles.
	 * @return
	 */
	public ObservableList<Profile> getProfileData() {
		return profileData;
	}
	
	
	public void setProfileOverviewController(ProfileOverviewController profileOverviewController) {
		this.profileOverviewController = profileOverviewController;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
