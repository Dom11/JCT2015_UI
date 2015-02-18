package com.bluesky.jct.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.MainApp;
import com.bluesky.jct.model.*;
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
	private ComboBox<Domain> domainComboBox;
	@FXML
	private ComboBox<Environment> environmentComboBox;
	@FXML
	private ComboBox<Jbar> jbarComboBox;
	
	
	@FXML
	private TextField Test;

	private ObservableList<ProfileView> profileData = FXCollections.observableArrayList();
	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
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
//		ComboBoxDomain.loadDomainData();
		ComboBoxEnvironment.loadEnvironmentData();
		ComboBoxJbar.loadJbarData();
//		domainData = ComboBoxDomain.getDomainData();
		environmentData = ComboBoxEnvironment.getEnvironmentData();
		jbarData = ComboBoxJbar.getJbarData();		
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
		
		
		ComboBoxDomain.loadDomainData();
		domainData = ComboBoxDomain.getDomainData();
		domainComboBox.setItems(domainData);
		environmentComboBox.setItems(environmentData);
		jbarComboBox.setItems(jbarData);
		
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		ComboBoxEnvironment.iniEnvironmentCombobox(environmentComboBox);
		ComboBoxJbar.iniJbarCombobox(jbarComboBox);
		
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
		
		// 2.1.1 Set the filter Predicate whenever the Search Field changes.
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
			
		// 2.1.2 Set the filter Predicate to null on ESC.
		searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					searchField.setText(null);
					filteredData.setPredicate(profileView -> {
							return true;
					});
				}
			}
		});
		
		// 2.2.1 Set the filter Predicate whenever the Domain ComboBox selection changes.
		domainComboBox.setOnAction((event) -> {
			String selectedDomain = domainComboBox.getSelectionModel().getSelectedItem().getName();

			filteredData.setPredicate(profileView -> {
					// If filter text is empty, display all profiles.
					if (selectedDomain == null || selectedDomain.isEmpty()) {
						return true;
					}
					
					// Compare Environment of every profile with filter text.
					String lowerCaseFilter = selectedDomain.toLowerCase();
					if (profileView.getDomainName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
						return true; // Filter matches profileEnvironment
					}
					return false; // Does not match.
				});
			});

		// 2.2.2 Set the filter Predicate to null on double click.
		domainComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					domainComboBox.setValue(null);
					filteredData.setPredicate(profileView -> {
							return true;
					});
				}
			}
		});
		
		// 2.3.1 Set the filter Predicate whenever the Environment ComboBox selection changes.
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

		// 2.3.2 Set the filter Predicate to null on double click.
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
		
		
		// 2.4.1 Set the filter Predicate whenever the Jbar ComboBox selection changes.		
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
		
		// 2.4.2 Set the filter Predicate to null on double click.
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
		
		mainApp.showProfileWizardNew();
		
		
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
	

	/**
	 * Returns data as an observable list.
	 * 
	 * @return profileData
	 */
	public ObservableList<ProfileView> getProfileData() {
		return profileData;
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
	
	
	@FXML
	public void refreshAll() {
		ComboBoxDomain.loadDomainData();
		ComboBoxDomain.getDomainData();
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		
		loadProfileViewData();
		

	}
}
