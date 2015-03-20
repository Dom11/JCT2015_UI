package com.bluesky.jct.model;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Jira implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private IntegerProperty jiraId = new SimpleIntegerProperty();
	private StringProperty jiraProjectKey = new SimpleStringProperty();

	
	/**
	 * Constructor
	 * 
	 * @param jbarId, jbarName
	 */
	public Jira(int jiraId, String jiraProjectKey) {
		super();
		this.jiraId = new SimpleIntegerProperty(jiraId);
		this.jiraProjectKey = new SimpleStringProperty(jiraProjectKey);
	}
	
	public int getId() {
		return jiraId.get();
	}

	public IntegerProperty idProperty() {
		return jiraId;
	}
	
	public String getName() {
		return jiraProjectKey.get();
	}

	public void setName(String jiraProjectKey) {
		this.jiraProjectKey.set(jiraProjectKey);
	}

	public StringProperty nameProperty() {
		return jiraProjectKey;
	}
}
