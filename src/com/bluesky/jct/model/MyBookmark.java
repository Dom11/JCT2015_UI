package com.bluesky.jct.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model class for a Bookmark object
 * 
 * @author Dominik
 */
public class MyBookmark {
	
	private StringProperty bookmarkName = new SimpleStringProperty(null);
	private StringProperty searchText = new SimpleStringProperty(null);
	private StringProperty environmentName = new SimpleStringProperty(null);
	private StringProperty domainName = new SimpleStringProperty(null);
	private StringProperty jbarName = new SimpleStringProperty(null);
	
	
	/**
	 * Default constructor.
	 */
	public MyBookmark() {
		this(null, null, null, null, null);
	}

	
	/**
	 * Constructor
	 * 
	 * @param bookmarkName
	 * @param searchText
	 * @param environmentId
	 * @param domainId
	 * @param jbarId
	 */
	public MyBookmark(String bookmarkName, String searchText, String environmentName, String domainName, String jbarName) {
		super();
		this.bookmarkName = new SimpleStringProperty(bookmarkName);
		this.searchText = new SimpleStringProperty(searchText);
		this.environmentName = new SimpleStringProperty(environmentName);
		this.domainName = new SimpleStringProperty(domainName);
		this.jbarName = new SimpleStringProperty(jbarName);
	}	
	
	
	// --- Getter and Setter
	
	public String getName() {
		return bookmarkName.get();
	}
	
	public void setName(String bookmarkName) {
		this.bookmarkName.set(bookmarkName);
	}

	public StringProperty nameProperty() {
		return bookmarkName;
	}
	
	public String getSearchText() {
		return searchText.get();
	}
	
	public void setSearchText(String searchText) {
		this.searchText.set(searchText);
	}

	public StringProperty searchTextProperty() {
		return searchText;
	}
	
	public String getEnvironmentName() {
		return environmentName.get();
	}
	
	public void setEnvironmentName(String environmentName) {
		this.environmentName.set(environmentName);
	}
	
	public StringProperty environmentNameProperty() {
		return environmentName;
	}
	
	public String getDomainName() {
		return domainName.get();
	}
	
	public void setDomainName(String domainName) {
		this.domainName.set(domainName);
	}
	
	public StringProperty domainNameProperty() {
		return domainName;
	}
	
	public String getJbarName() {
		return jbarName.get();
	}
	
	public void setJbarName(String jbarName) {
		this.jbarName.set(jbarName);
	}
	
	public StringProperty jbarNameProperty() {
		return jbarName;
	}

}