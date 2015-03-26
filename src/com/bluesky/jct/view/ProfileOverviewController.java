package com.bluesky.jct.view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.bluesky.jct.ComboBoxDomain;
import com.bluesky.jct.ComboBoxEnvironment;
import com.bluesky.jct.ComboBoxHost;
import com.bluesky.jct.ComboBoxJbar;
import com.bluesky.jct.ComboBoxJira;
import com.bluesky.jct.ComboBoxPrefix;
import com.bluesky.jct.Filter;
import com.bluesky.jct.LoginDialog;
import com.bluesky.jct.MainApp;
import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.*;
import com.bluesky.jct.rest.RestClient;
import com.bluesky.jct.util.ExceptionHandling;


/**
 * This is the controller for the overview page.
 * 
 * @author Dominik
 */
public class ProfileOverviewController extends Filter {
	
	@FXML
	private TextField searchField;
	@FXML
	private TextField domainFilterCriteria;
	@FXML
	private TextField environmentFilterCriteria;
	@FXML
	private TextField jbarFilterCriteria;
	@FXML
	private TableView<ProfileView> profileTable;
	@FXML
	private TableColumn<ProfileView, String> profileNameColumn;
	@FXML
	private TableColumn<ProfileView, String> profileDescriptionColumn;
	@FXML
	private TableColumn<ProfileView, String> profileHosteNameColumn;
	@FXML
	private TableColumn<ProfileView, Boolean> profileStatusColumn;
	@FXML
	private ComboBox<MyBookmark> bookmarkComboBox;
	@FXML
	private ComboBox<Domain> domainComboBox;
	@FXML
	private ComboBox<Environment> environmentComboBox;
	@FXML
	private ComboBox<Jbar> jbarComboBox;
	
	final ContextMenu contextMenu = new ContextMenu();
	@FXML
	private MenuItem editProfile;
	@FXML
	private MenuItem cloneProfile;	
	@FXML
	private MenuItem deleteProfile;
	@FXML
	private Button selfServicePortal;

	private static ObservableList<ProfileView> profileData = FXCollections.observableArrayList();
	private ObservableList<Domain> domainData = FXCollections.observableArrayList();
	private ObservableList<Environment> environmentData = FXCollections.observableArrayList();
	private ObservableList<Jbar> jbarData = FXCollections.observableArrayList();
	
	private int selectedIndex;
	private static Profile selectedProfile = null;
	private MainApp mainApp;

	
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public ProfileOverviewController() {	
		super();
		
		// fills observableLists with information
		loadProfileViewData();
		// retrieves ComboBox information from DB
		ComboBoxDomain.loadDomainData();
		ComboBoxEnvironment.loadEnvironmentData();
		ComboBoxJbar.loadJbarData();
		ComboBoxPrefix.loadPrefixData();
		ComboBoxHost.loadHostData();
		ComboBoxJbar.loadJbarData();
		ComboBoxJira.loadJiraData();		
		// fills the ComboBoxes with information
		domainData = ComboBoxDomain.getDomainData();
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
		profileStatusColumn.setCellValueFactory(cellData -> cellData.getValue().profileStatusProperty());
		
		// hide filter textFields
		setTextFieldVisibility(false);
		
		// set admin functions based on userType
		deleteProfile.setDisable(LoginDialog.getDisabledType());
		
		// set comboBox items
		domainComboBox.setItems(domainData);
		environmentComboBox.setItems(environmentData);
		jbarComboBox.setItems(jbarData);
		bookmarkComboBox.setItems(getMyBookmarkData());
		
		// fill comboBox with information 
		ComboBoxDomain.iniDomainCombobox(domainComboBox);
		ComboBoxEnvironment.iniEnvironmentCombobox(environmentComboBox);
		ComboBoxJbar.iniJbarCombobox(jbarComboBox);
		
		// Define rendering of the list of values in ComboBox drop down.
		bookmarkComboBox.setCellFactory((comboBox) -> {
			return new ListCell<MyBookmark>() {
				@Override
				protected void updateItem(MyBookmark item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(item.getName());
					}
				}
			};
		});

		// provide toolTip for tableView
		Tooltip t = new Tooltip("double click for more details");
		Tooltip.install(profileTable, t);

		// provide the filterd informtion to tableView
		showFilterdList();
		
		// filters based on selected Bookmark.
		bookmarkComboBox.setOnAction((event) -> {
			setFilterBookmark(bookmarkComboBox.getSelectionModel().getSelectedItem());
			searchField.setText(bookmarkComboBox.getSelectionModel().getSelectedItem().getSearchText());
			
			domainFilterCriteria.setText(bookmarkComboBox.getSelectionModel().getSelectedItem().getDomainName());
			environmentFilterCriteria.setText(bookmarkComboBox.getSelectionModel().getSelectedItem().getEnvironmentName());
			jbarFilterCriteria.setText(bookmarkComboBox.getSelectionModel().getSelectedItem().getJbarName());
			setTextFieldVisibility(true);

			showFilterdList();
		});		
		
		// Apply filter based on searchField.
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			setFilterSearchText(newValue);
			showFilterdList();
		});	
		
		// Apply filter based on Domain comboBox selection.
		domainComboBox.setOnAction((event) -> {
			setFilterDomainName(domainComboBox.getSelectionModel().getSelectedItem().getName());
			showFilterdList();
		});
		
		// Apply filter based on Environment comboBox selection.
		environmentComboBox.setOnAction((event) -> {
			setFilterEnvironmentName(environmentComboBox.getSelectionModel().getSelectedItem().getName());
			showFilterdList();
		});
		
		// Apply filter based on Jbar comboBox selection.	
		jbarComboBox.setOnAction((event) -> {
			setFilterJbarName(jbarComboBox.getSelectionModel().getSelectedItem().getName());
			showFilterdList();
		});
		
		// reset of searchField filter to null on ESC.
		searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					searchField.setText(null);
					setFilterSearchText(null);
					showFilterdList();
				}
			}
		});
		
		// reset of Domain filter.
		domainComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					domainComboBox.setValue(null);			
					setFilterDomainName(null);
					showFilterdList();
				}
			}
		});
		
		// reset of Environment filter.
		environmentComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					environmentComboBox.setValue(null);
					setFilterEnvironmentName(null);
					showFilterdList();
				}
			}
		});
		
		// reset of Jbar filter.
		jbarComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					jbarComboBox.setValue(null);
					setFilterJbarName(null);
					showFilterdList();
				}
			}
		});
		
		// reset of Jbar filter.
		bookmarkComboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					bookmarkComboBox.setValue(null);
					searchField.setText(null);
					domainComboBox.setValue(null);
					environmentComboBox.setValue(null);
					jbarComboBox.setValue(null);
					setFilterBookmark(null);
					setTextFieldVisibility(false);
					showFilterdList();
				}
			}
		});		

		// opens the Edit dialog on double click or shows a contextMenu on right click
		profileTable.addEventHandler(MouseEvent.ANY, (MouseEvent event) -> {
			if (event.getClickCount() == 1) {
				setSelectedProfile();				
			} else if (event.getClickCount() >1) {
				handleViewProfile();
			} else if (event.getButton() == MouseButton.SECONDARY || event.isControlDown()) {
				contextMenu.show(profileTable, event.getScreenX(), event.getScreenY());
			} else {
				contextMenu.hide();
			}
		});
	}
	

	/**
	 * Replaces the current tableView with the filtered list.
	 */
	private void showFilterdList() {
		// 4. Bind the SortedList comparator to the TableView comparator. Otherwise, sorting the TableView would have no effect.
		getFilteredList().comparatorProperty().bind(profileTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		profileTable.setItems(getFilteredList());
		
		// Custom rendering of the table cell.
		profileStatusColumn.setCellFactory(column -> {
			return new TableCell<ProfileView, Boolean>() {
				
				@Override
				public void updateItem(Boolean item, boolean empty) {
					super.updateItem(item, empty);
					
					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Style true/false with a different color.
						if (item == true) {
					        Rectangle rect = new Rectangle(11, 11, 11, 11);
					        rect.setFill(Color.GREEN);
					        rect.setStroke(Color.LIGHTGREY);
					        rect.setStrokeWidth(1);
							setGraphic(rect);							
							setAlignment(Pos.CENTER);
						} else {
					        Rectangle rect = new Rectangle(11, 11, 11, 11);
					        rect.setFill(Color.RED);
					        rect.setStroke(Color.LIGHTGREY);
					        rect.setStrokeWidth(1);
					        setGraphic(rect);
							setAlignment(Pos.CENTER);
						}
					}
				}
			};
		});
	}
	
	
	/**
	 * Shows or hides the textFields with the values from the Bookmarks.
	 * 
	 * @param visibilityTextField
	 */
	private void setTextFieldVisibility(boolean visibilityTextField) {
		domainFilterCriteria.setVisible(visibilityTextField);
		environmentFilterCriteria.setVisible(visibilityTextField);
		jbarFilterCriteria.setVisible(visibilityTextField);
	}
	
	
	@FXML
	private void handleViewProfile() {	
		mainApp.showProfileEditDialog(getSelectedProfile());
	}
	
	
	@FXML
	private void handleCloneProfile() {
    	ProfileFunctions.setTempProfile(selectedProfile);
		mainApp.showProfileWizard();
	}
	
	
	@FXML
	public void handleDeleteProfile() {
		ProfileFunctions.deleteProfile(getSelectedProfile());
	}
	
	
	@FXML
	public void handleSaveBookmark() {
		saveMyBookmark();		
	}
	
	
	@FXML
	public void handleSelfServicePortal() {
		mainApp.showSelfServicePortal();
	}
	
	
	/**
	 * This method refreshes the displayed profiles on the GUI table. 
	 */
	public static void loadProfileViewData() {
		List<ProfileView> profileView = RestClient.findAllProfiles();
		profileData.clear();

		try {
			for (ProfileView profiles : profileView) {
				profileData.add(profiles);
			}
		} catch (Exception e) {
			String headerText = "Load Profiles";
			String contentText = "Please contact System Administrator";
			ExceptionHandling.handleError(headerText, contentText);
		}
	}
	

	/**
	 * Returns data as an observable list.
	 * 
	 * @return profileData
	 */
	public static ObservableList<ProfileView> getProfileData() {
		return profileData;
	}

	
	/**
	 * Retrieves the Profile Object from the DB of the current selected line item in the GUI table.
	 * The Profile will be written to static variable selectedProfile.
	 */
	private void setSelectedProfile() {
		selectedIndex = profileTable.getSelectionModel().getSelectedItem().getProfileId();
		selectedProfile = RestClient.findProfile(selectedIndex);
	}
	
	
	/**
	 * Returns the Profile object of the current selected line item in the GUI table.
	 * 
	 * @return selectedProfile
	 */
	public static Profile getSelectedProfile() {
		return selectedProfile;
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