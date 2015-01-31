package com.bluesky.jct.view;

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
import com.bluesky.jct.model.Environment;
import com.bluesky.jct.model.Jbar;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.model.ProfileView;
import com.bluesky.jct.rest.RestClient;


public class ProfileOverviewController {
	@FXML
	private TextField searchField;
	@FXML
	private TableView<ProfileView> profileTable;
	@FXML
	private TableColumn<ProfileView, String> profileNameColumn;
	@FXML
	private TableColumn<ProfileView, String> profileDescriptionColumn;
	@FXML
	private TableColumn<ProfileView, String> profileHosteNameColumn;
	@FXML
	private TableColumn<ProfileView, String> profilePrefixColumn;
	@FXML
	private ComboBox<Environment> environmentComboBox;
	@FXML
	private ComboBox<Jbar> jbarComboBox;

	private ObservableList<ProfileView> profileData = FXCollections.observableArrayList();
	private ObservableList<Environment> environmentData = FXCollections.observableArrayList();
	private ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	
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
		
		// fill ObservableLists with information
		loadProfileViewData();
		loadEnvironmentData();
		loadJbarData();
	}
		

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the profileTable with the three columns.
		profileNameColumn.setCellValueFactory(cellData -> cellData.getValue().profileNameProperty());
		profileDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().profileDescriptionProperty());
		profileHosteNameColumn.setCellValueFactory(cellData -> cellData.getValue().hostNameProperty());
		profilePrefixColumn.setCellValueFactory(cellData -> cellData.getValue().prefixNameProperty());
		environmentComboBox.setItems(environmentData);
		jbarComboBox.setItems(jbarData);
		
		iniEnvironmentCombobox();
		iniJbarCombobox();
		
		Tooltip t = new Tooltip("double click for more details");
		Tooltip.install(profileTable, t);

		/**
		 * Filtering based on search Field and ComboBox
		 * (basic inputs from Marco Jakob's search Field)
		 * 
		 * @author Dominik Rey
		 */
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<ProfileView> filteredData = new FilteredList<>(profileData, p -> true);
		
		// 2.1 Set the filter Predicate whenever the Search Field changes.
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(profileView -> {
				// If filter text is empty, display all profiles.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare Name, Description, HostName, Prefix of every profile with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				if (profileView.getProfileName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches profileName
				} else if (profileView.getProfileDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches profileDescription.					
				} else if (profileView.getHostName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches HostName.
				} else if (profileView.getPrefixName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches PrefixName.
				}
				return false; // Does not match.
			});
		});
		
		// 2.2.1 Set the filter Predicate whenever the Environment ComboBox selection changes.
		environmentComboBox.setOnAction((event) -> {
			String selectedEnvironment = environmentComboBox.getSelectionModel().getSelectedItem().getName();

			filteredData.setPredicate(profileView -> {
					// If filter text is empty, display all profiles.
					if (selectedEnvironment == null || selectedEnvironment.isEmpty()) {
						return true;
					}
					
					// Compare Environment of every profile with filter text.
					String lowerCaseFilter = selectedEnvironment.toLowerCase();
					if (profileView.getEnvironmentName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches profileEnvironment
					}
					return false; // Does not match.
				});
			});

		// 2.2.2 Set the filter Predicate to null on double click.
		environmentComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					environmentComboBox.setValue(null);
					filteredData.setPredicate(profileView -> {
							return true;
					});
				}
			}
		});
		
		
		// 2.3.1 Set the filter Predicate whenever the Jbar ComboBox selection changes.		
		jbarComboBox.setOnAction((event) -> {
			String selectedJbar = jbarComboBox.getSelectionModel().getSelectedItem().getName();

			filteredData.setPredicate(profileView -> {
					// If filter text is empty, display all profiles.
					if (selectedJbar == null || selectedJbar.isEmpty()) {
						return true;
					}
					
					// Compare Jbar of every profile with filter text.
					String lowerCaseFilter = selectedJbar.toLowerCase();
					if (profileView.getJbarName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches profileEnvironment
					}
					return false; // Does not match.
				});
			});
		
		// 2.3.2 Set the filter Predicate to null on double click.
		jbarComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					jbarComboBox.setValue(null);
					filteredData.setPredicate(profileView -> {
							return true;
					});
				}
			}
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ProfileView> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator. Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(profileTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		profileTable.setItems(sortedData);


		// opens the Edit dialog on double click
		profileTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					selectedIndex = profileTable.getSelectionModel().getSelectedItem().getProfileId();
					Profile profile = RestClient.findProfile(selectedIndex);
					mainApp.showProfileEditDialog(profile, profileOverviewController);
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
	
	
	public void loadProfileViewData() {
		List<ProfileView> profileView = RestClient.findAllProfiles();
		profileData.clear();

		try {
			for (ProfileView profiles : profileView) {
				profileData.add(profiles);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void loadEnvironmentData() {
		List<Environment> environments = RestClient.findAllEnvironment();
		environmentData.clear();
		
		try {
			for (Environment environment : environments) {
				environmentData.add(environment);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void loadJbarData() {
		List<Jbar> jbars = RestClient.findAllJbar();
		jbarData.clear();
		
		try {
			for (Jbar jbar : jbars) {
				jbarData.add(jbar);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Combobox Environment
	 */
	private void iniEnvironmentCombobox() {
		// Define rendering of the list of values in ComboBox drop down.
		environmentComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Environment>() {
				@Override
				protected void updateItem(Environment item, boolean empty) {
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
		environmentComboBox.setConverter(new StringConverter<Environment>() {
			@Override
			public String toString(Environment environment) {
				if (environment == null) {
					return null;
				} else {
					return environment.getName();
				}
			}

			@Override
			public Environment fromString(String environmentString) {
				return null; // No conversion fromString needed.
			}
		});
	}
	
	
	/**
	 * Combobox Jbar
	 */
	private void iniJbarCombobox() {
		// Define rendering of the list of values in ComboBox drop down.
		jbarComboBox.setCellFactory((comboBox) -> {
			return new ListCell<Jbar>() {
				@Override
				protected void updateItem(Jbar item, boolean empty) {
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
		jbarComboBox.setConverter(new StringConverter<Jbar>() {
			@Override
			public String toString(Jbar jbar) {
				if (jbar == null) {
					return null;
				} else {
					return jbar.getName();
				}
			}

			@Override
			public Jbar fromString(String jbarNameString) {
				return null; // No conversion fromString needed.
			}
		});
	}

	
	/**
	 * Returns data as an observable list.
	 * 
	 * @return profileData
	 */
	public ObservableList<ProfileView> getProfileData() {
		return profileData;
	}

	
	/**
	 * Returns data as an observable list.
	 * 
	 * @return environmentData
	 */
	public ObservableList<Environment> getEnvironmentData() {
		return environmentData;
	}
	
	
	/**
	 * Returns data as an observable list.
	 * 
	 * @return jbarData
	 */
	public ObservableList<Jbar> getJbarData() {
		return jbarData;
	}
	

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setProfileOverviewController(ProfileOverviewController profileOverviewController) {
		this.profileOverviewController = profileOverviewController;
	}
}
