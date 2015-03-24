package com.bluesky.jct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import com.bluesky.jct.model.MyBookmark;
import com.bluesky.jct.model.ProfileView;
import com.bluesky.jct.view.ProfileOverviewController;


/**
 * Filtering based on search Field and ComboBox
 * (basic inputs from Marco Jakob's search Field)
 * 
 * @author Dominik Rey
 */
public class Filter {
	
	private ObservableList<ProfileView> profileData = FXCollections.observableArrayList();
	private ObservableList<MyBookmark> bookmarkData = FXCollections.observableArrayList();
	
	private String filterSearchField = null;
	private String filterDomainComboBox = null;
	private String filterEnvironmentComboBox = null;
	private String filterJbarComboBox = null;

	private int i = 1;
	
	

	
	public SortedList<ProfileView> getFilteredList() {
		
		profileData = ProfileOverviewController.getProfileData();
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<ProfileView> filteredData = new FilteredList<>(profileData, p -> true);

		
		// apply searchField filter
		filteredData.setPredicate(profileView -> {
			// If filter text is empty, display all profiles.
			if (filterSearchField == null || filterSearchField.isEmpty()) {
				return true;
			}
			// Compare ProfileName, Description and HostName of every profile with filter text.
			String lowerCaseFilter = filterSearchField.toLowerCase();
			if (profileView.getProfileName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; // Filter matches profileName
			} else if (profileView.getProfileDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; // Filter matches profileDescription.					
			} else if (profileView.getHostName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
				return true; // Filter matches hostName.
			}
			return false; // Does not match.
		});
		FilteredList<ProfileView> filteredData1 = new FilteredList<>(filteredData, p -> true);

		
		// apply Domain Filter
		filteredData1.setPredicate(profileView -> {
			// If filter text is empty, display all profiles.
			if (filterDomainComboBox == null || filterDomainComboBox.isEmpty())	{
				return true;
			}
			// Compare Environment of every profile with filter text.
			if (profileView.getDomainName().toLowerCase().indexOf(filterDomainComboBox.toLowerCase()) != -1) {
				return true; // Filter matches profileEnvironment
			}
			return false; // Does not match.
		});
		FilteredList<ProfileView> filteredData2 = new FilteredList<>(filteredData1, p -> true);

		
		// apply Environment Filter
		filteredData2.setPredicate(profileView -> {
			// If filter text is empty, display all profiles.
			if (filterEnvironmentComboBox == null || filterEnvironmentComboBox.isEmpty()) {
				return true;
			}
			// Compare Environment of every profile with filter text.
			if (profileView.getEnvironmentName().toLowerCase().indexOf(filterEnvironmentComboBox.toLowerCase()) != -1) {
				return true; // Filter matches profileEnvironment
			}
			return false; // Does not match.
		});
		FilteredList<ProfileView> filteredData3 = new FilteredList<>(filteredData2, p -> true);
		
		
		// apply Jbar Filter
		filteredData3.setPredicate(profileView -> {
			// If filter text is empty, display all profiles.
			if (filterJbarComboBox == null || filterJbarComboBox.isEmpty()) {
				return true;
			}
			// Compare Environment of every profile with filter text.
			if (profileView.getJbarName().toLowerCase().indexOf(filterJbarComboBox.toLowerCase()) != -1) {
				return true; // Filter matches profileEnvironment
			}
			return false; // Does not match.
		});
		FilteredList<ProfileView> filteredData4 = new FilteredList<>(filteredData3, p -> true);
		
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ProfileView> sortedData = new SortedList<>(filteredData4);
		
		return sortedData;
	}
	
	
	// --- Setters for Filter Strings
	
	public void setFilterSearchText(String searchText) {
		this.filterSearchField = searchText;
	}
	
	public void setFilterDomainName(String domainName) {
		this.filterDomainComboBox = domainName;
	}
	
	public void setFilterEnvironmentName(String EnvironmentName) {
		this.filterEnvironmentComboBox = EnvironmentName;
	}
	
	public void setFilterJbarName(String JbarName) {
		this.filterJbarComboBox = JbarName;
	}
	
	
	public void saveMyBookmark() {
		MyBookmark myBookmark = new MyBookmark();

		myBookmark.setName("test" + i);
		myBookmark.setSearchText(filterSearchField);
		myBookmark.setDomainName(filterDomainComboBox);
		myBookmark.setEnvironmentName(filterEnvironmentComboBox);
		myBookmark.setJbarName(filterJbarComboBox);
		
		i++;		
		bookmarkData.add(myBookmark);
	}
	
	
	/**
	 * Returns the bookmarkData as an ObservableList.
	 * 
	 * @return bookmarkData
	 */
	public ObservableList<MyBookmark> getMyBookmarkData() {
		return bookmarkData;
	}
	
}