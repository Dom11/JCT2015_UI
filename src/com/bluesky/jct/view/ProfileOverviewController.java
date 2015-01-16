package com.bluesky.jct.view;

import java.util.ArrayList;
import java.util.List;

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

import com.bluesky.jct.MainApp;
import com.bluesky.jct.model.FXProfile;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.FXBeanMapper;


public class ProfileOverviewController {
	@FXML
	private TextField searchField;
	@FXML
	private TableView<FXProfile> profileTable;
//	@FXML
//	private TableView<Profile> profileTable;
	@FXML
	private TableColumn<FXProfile, String> profileDescriptionColumn;
	@FXML
	private TableColumn<FXProfile, String> profileHosteNameColumn;
	@FXML
	private TableColumn<FXProfile, String> profileInstanceColumn;
	@FXML
	private ComboBox<FXProfile> environmentComboBox;
	@FXML
	private ComboBox<FXProfile> jBarComboBox;

	private ObservableList<FXProfile> profileData = FXCollections.observableArrayList();

	private FXProfile tempProfile;
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
		List<Profile> profiles = RestClient.findAll();
		List<FXProfile> beanProfiles = new ArrayList<FXProfile>();

		try {
			for (Profile profile : profiles) {
				FXProfile beanProfile = new FXProfile();
				FXBeanMapper.copyProperties(beanProfile, profile);
				beanProfiles.add(beanProfile);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		profileData.clear();
		profileData.addAll(beanProfiles);
	}
		
		
//		profileData.add(new Profile(1, "UCM DEV environment_1", "DEV"));
//		profileData.add(new Profile(2, "UCM UAT environment_2", "UAT"));
//		profileData.add(new Profile(3, "UCM PRO environment_3", "PRO"));
//		profileData.add(new Profile(4, "CCM DEV environment_4", "DEV"));
//	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the profile table with the three columns.
		profileDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
		profileHosteNameColumn.setCellValueFactory(cellData -> cellData.getValue().hostNameProperty());
		profileInstanceColumn.setCellValueFactory(cellData -> cellData.getValue().instanceProperty());
		
		Tooltip t = new Tooltip("double click for more details");
		Tooltip.install(profileTable, t);

		/**
		 * search Field
		 * 
		 * @author Marco Jakob
		 */
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<FXProfile> filteredData = new FilteredList<>(profileData, p -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(profile -> {
				// If filter text is empty, display all profiles.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every profile with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (profile.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (profile.getEnvironment().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<FXProfile> sortedData = new SortedList<>(filteredData);
		
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
			return new ListCell<FXProfile>() {
				@Override
				protected void updateItem(FXProfile item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getEnvironment());
					}
				}
			};
		});

		// Define rendering of selected value shown in ComboBox.
		environmentComboBox.setConverter(new StringConverter<FXProfile>() {
			@Override
			public String toString(FXProfile profile) {
				if (profile == null) {
					return null;
				} else {
					return profile.getEnvironment();
				}
			}

			@Override
			public FXProfile fromString(String profileString) {
				return null; // No conversion fromString needed.
			}
		});

		// Handle ComboBox event.
		environmentComboBox.setOnAction((event) -> {
			FXProfile selectedProfile = environmentComboBox.getSelectionModel().getSelectedItem();
			System.out.println("ComboBox Action (selected: " + selectedProfile.getEnvironment().toString() + ")");
			});
		
		
		/**
		 * Combobox JBar Name
		 */
		// Define rendering of the list of values in ComboBox drop down.
		jBarComboBox.setCellFactory((comboBox) -> {
			return new ListCell<FXProfile>() {
				@Override
				protected void updateItem(FXProfile item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getJBarName());
					}
				}
			};
		});

		// Define rendering of selected value shown in ComboBox.
		jBarComboBox.setConverter(new StringConverter<FXProfile>() {
			@Override
			public String toString(FXProfile profile) {
				if (profile == null) {
					return null;
				} else {
					return profile.getJBarName();
				}
			}

			@Override
			public FXProfile fromString(String profileJBarNameString) {
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
	}

	
	public void deleteProfile(int selectedIndex) {
		profileData.remove(selectedIndex);
	}
	
	/**
	 * Returns the data as an observable list of Profiles.
	 * @return
	 */
	public ObservableList<FXProfile> getProfileData() {
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
