package com.bluesky.jct;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

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
	
	private String filterSearchField;
	private String filterDomainComboBox;
	private String filterEnvironmentComboBox;
	private String filterJbarComboBox;
	private String filterAll = filterSearchField + filterDomainComboBox + filterEnvironmentComboBox + filterJbarComboBox;
	
	

	
	public SortedList<ProfileView> getFilteredList() {
		
		profileData = ProfileOverviewController.getProfileData();
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<ProfileView> filteredData = new FilteredList<>(profileData, p -> true);
		
		//TODO
		// 2. Set the filter Predicate.
		filteredData.setPredicate(profileView -> {
			// If filter text is empty, display all profiles.
			if (filterAll == null || 
					filterSearchField.isEmpty() || 
					filterDomainComboBox.isEmpty() || 
					filterEnvironmentComboBox.isEmpty() || 
					filterJbarComboBox.isEmpty()) {
				return true;
			}
			
            // Compare Domain, Environment and Jbar of every profile
			// with filterSearchField, filterDomainComboBox, filterEnvironmentComboBox and filterJbarComboBox
			if (profileView.getDomainName().toLowerCase().indexOf(filterSearchField.toLowerCase()) != -1 ||
					profileView.getEnvironmentName().toLowerCase().indexOf(filterSearchField.toLowerCase()) != -1 ||
					profileView.getJbarName().toLowerCase().indexOf(filterSearchField.toLowerCase()) != -1 ||
					profileView.getDomainName().toLowerCase().indexOf(filterDomainComboBox.toLowerCase()) != -1 ||
            		profileView.getEnvironmentName().toLowerCase().indexOf(filterEnvironmentComboBox.toLowerCase()) != -1 ||
            		profileView.getJbarName().toLowerCase().indexOf(filterJbarComboBox.toLowerCase()) != -1) {
                return true; // Filter matches Domain and/or Environment and/or Jbar.
            }
            return false; // Does not match.
        });
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ProfileView> sortedData = new SortedList<>(filteredData);
		
		return sortedData;
	}
	
	
	public void setFilterSearchField(String filterSearchField) {
		this.filterSearchField = filterSearchField;
	}
	
	public void setFilterDomainComboBox(String filterDomainComboBox) {
		this.filterDomainComboBox = filterDomainComboBox;
	}
	
	public void setFilterEnvironmentComboBox(String filterEnvironmentComboBox) {
		this.filterEnvironmentComboBox = filterEnvironmentComboBox;
	}
	
	public void setFilterJbarComboBox(String filterJbarComboBox) {
		this.filterJbarComboBox = filterJbarComboBox;
	}
}
