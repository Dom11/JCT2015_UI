package com.bluesky.jct.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FXProfile {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty description = new SimpleStringProperty();
	private StringProperty hostName = new SimpleStringProperty();
	private StringProperty instance = new SimpleStringProperty();
	private StringProperty environment = new SimpleStringProperty();
	private StringProperty domain = new SimpleStringProperty();
	private StringProperty jBarName = new SimpleStringProperty();

	/**
	 * Default constructor.
	 */
	public FXProfile() {
		this(0, null, null);
	}

	public FXProfile(int id, String description, String environment) {
		this.id = new SimpleIntegerProperty(id);
		this.description = new SimpleStringProperty(description);
		this.environment = new SimpleStringProperty(environment);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public String getHostName() {
		return hostName.get();
	}

	public void setHostName(String hostName) {
		this.hostName.set(hostName);
	}

	public StringProperty hostNameProperty() {
		return hostName;
	}

	public String getInstance() {
		return instance.get();
	}

	public void setInstance(String instance) {
		this.instance.set(instance);
	}

	public StringProperty instanceProperty() {
		return instance;
	}

	public String getEnvironment() {
		return environment.get();
	}

	public void setEnvironment(String environment) {
		this.environment.set(environment);
	}

	public StringProperty environmentProperty() {
		return environment;
	}

	public String getDomain() {
		return domain.get();
	}

	public void setDomain(String domain) {
		this.domain.set(domain);
	}

	public StringProperty domainProperty() {
		return domain;
	}

	public String getJBarName() {
		return jBarName.get();
	}

	public void setJBarName(String jBarName) {
		this.jBarName.set(jBarName);
	}

	public StringProperty jBarNameProperty() {
		return jBarName;
	}
}