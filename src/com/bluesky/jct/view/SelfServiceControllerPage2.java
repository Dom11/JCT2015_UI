package com.bluesky.jct.view;

import java.util.Date;

import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


/**
 * Page 2 of the self-service portal.
 * Selection of environment(s) and quantity for the profiles to be created.
 * 
 * @author Dominik
 */
public class SelfServiceControllerPage2 {
	@FXML
	private CheckBox labCheckBox;
	@FXML
	private CheckBox devCheckBox;
	@FXML
	private CheckBox sitCheckBox;
	@FXML
	private CheckBox uatCheckBox;
	@FXML
	private CheckBox prdCheckBox;
	@FXML
	private Label labQuantityLabel;
	@FXML
	private Label devQuantityLabel;
	@FXML
	private Label sitQuantityLabel;
	@FXML
	private Label uatQuantityLabel;
	@FXML
	private Label prdQuantityLabel;
	@FXML
	private Button labPlus;
	@FXML
	private Button devPlus;
	@FXML
	private Button sitPlus;
	@FXML
	private Button uatPlus;
	@FXML
	private Button prdPlus;
	
	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	
	private ObservableList<Profile> profileData = FXCollections.observableArrayList();

	private Profile tempProfile;
  	private int environmentId;
   	private int hostId;	
   	private int jbarId;   	
   	private int jiraId;   	
	private int prefixId;   	
	private int domainId;   	
	private String profileDescription;
   	private String profileDnsName;
   	private String profileComponent;
	private int jvmId;   
	private boolean profileStatus;
   	private String createdBy;
   	private Date rpmGenerationDate;
   	private Date packageSentDate;
   	private int version;

	private int labQuantity = 0;
	private int devQuantity = 0;
	private int sitQuantity = 0;
	private int uatQuantity = 0;
	private int prdQuantity = 0;

			
	/**
	 * Constructor
	 * The constructor is called before the initialize() method.
	 */
	public SelfServiceControllerPage2() {
		super();
	}
	
	
	/**
	 * Initializes the controller class.
	 * This method is actually called after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
		// hide plus buttons
		labPlus.setVisible(false);
		devPlus.setVisible(false);
		sitPlus.setVisible(false);
		uatPlus.setVisible(false);
		prdPlus.setVisible(false);
		
		// set tempProfile information and load parameters
		tempProfile = ProfileFunctions.getTempProfile();
		environmentId = tempProfile.getEnvironmentId();
		hostId = tempProfile.getHostId();
		jbarId = tempProfile.getJbarId();
	   	jiraId = tempProfile.getJiraId();
		prefixId = tempProfile.getPrefixId();
		domainId = tempProfile.getDomainId();
		profileDescription = tempProfile.getProfileDescription();
	   	profileDnsName = tempProfile.getProfileDnsName();
		profileComponent = tempProfile.getProfileComponent();
		jvmId = tempProfile.getJvmId();
		profileStatus = tempProfile.getProfileStatus();
		createdBy = tempProfile.getCreatedBy();	
		rpmGenerationDate = tempProfile.getRpmGenerationDate();
		packageSentDate = tempProfile.getPackageSentDate();
		version = tempProfile.getVersion();
		
		// handle checkBox events.
		labCheckBox.setOnAction((event) -> {
			final String newText = labCheckBox.isSelected() ? "1":"0";
			labQuantity =  labCheckBox.isSelected() ? 1:0;
			labQuantityLabel.setText(newText);
			labPlus.setVisible(labCheckBox.isSelected());
			
		});
		devCheckBox.setOnAction((event) -> {
			final String newText = devCheckBox.isSelected() ? "1":"0";
			devQuantity =  devCheckBox.isSelected() ? 1:0;
			devQuantityLabel.setText(newText);
			devPlus.setVisible(devCheckBox.isSelected());
			
		});
		sitCheckBox.setOnAction((event) -> {
			final String newText = sitCheckBox.isSelected() ? "1":"0";
			sitQuantity =  sitCheckBox.isSelected() ? 1:0;
			sitQuantityLabel.setText(newText);
			sitPlus.setVisible(sitCheckBox.isSelected());
			
		});
		uatCheckBox.setOnAction((event) -> {
			final String newText = uatCheckBox.isSelected() ? "1":"0";
			uatQuantity =  uatCheckBox.isSelected() ? 1:0;
			uatQuantityLabel.setText(newText);
			uatPlus.setVisible(uatCheckBox.isSelected());
			
		});
		prdCheckBox.setOnAction((event) -> {
			final String newText = prdCheckBox.isSelected() ? "1":"0";
			prdQuantity =  prdCheckBox.isSelected() ? 1:0;
			prdQuantityLabel.setText(newText);
			prdPlus.setVisible(prdCheckBox.isSelected());
			
		});
		
		// handle plus button events.
		labPlus.setOnAction((event) -> {
			if (labQuantity < 5) {
				labQuantity++;
				labQuantityLabel.setText(Integer.toString(labQuantity));
			} 
		}); 
		devPlus.setOnAction((event) -> {
			if (devQuantity < 5) {
				devQuantity++;
				devQuantityLabel.setText(Integer.toString(devQuantity));
			} 
		}); 
		sitPlus.setOnAction((event) -> {
			if (sitQuantity < 5) {
				sitQuantity++;
				sitQuantityLabel.setText(Integer.toString(sitQuantity));
			} 
		}); 
		uatPlus.setOnAction((event) -> {
			if (uatQuantity < 5) {
				uatQuantity++;
				uatQuantityLabel.setText(Integer.toString(uatQuantity));
			} 
		}); 
		prdPlus.setOnAction((event) -> {
			if (prdQuantity < 5) {
				prdQuantity++;
				prdQuantityLabel.setText(Integer.toString(prdQuantity));
			} 
		}); 
	}
	
	
	/**
	 * User clicks on Next button.
	 * Profiles will be created and stored in a list.
	 */
    @FXML
    private void handleNext() {
    	
		createProfiles();
    	ProfileFunctions.setTempProfiles(profileData);
      	
	   	// load next page of wizard
   		SelfServiceController.increasePageCounter();
		
		String headerText = "Order Progress";
		String contentText = "Profiles have been temporarily created";
		ExceptionHandling.handleInformation(headerText, contentText);
    }
    	
    
    @FXML
    private void handleBack() {
    	SelfServiceController.decreasePageCounter();
    }
    
    
    private void createProfiles() {
    	
    	profileData.clear();
    	
   		for (int labIndex = 1; labIndex <= labQuantity; labIndex++) {
   			Profile tempProfileNew = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
           			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
   			tempProfileNew.setEnvironmentId(5);
   			tempProfileNew.setPrefixId(labIndex);
   			profileData.add(tempProfileNew);
   		}
   		for (int devIndex = 1; devIndex <= devQuantity; devIndex++) {
   			Profile tempProfileNew = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
           			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
   			tempProfileNew.setEnvironmentId(1);
   			tempProfileNew.setPrefixId(devIndex);
   			profileData.add(tempProfileNew);
   		}
   		for (int sitIndex = 1; sitIndex <= sitQuantity; sitIndex++) {
   			Profile tempProfileNew = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
           			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
   			tempProfileNew.setEnvironmentId(3);
   			tempProfileNew.setPrefixId(sitIndex);
   			profileData.add(tempProfileNew);
   		}
   		for (int uatIndex = 1; uatIndex <= uatQuantity; uatIndex++) {
    		Profile tempProfileNew = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
           			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);
    		tempProfileNew.setEnvironmentId(2);
    		tempProfileNew.setPrefixId(uatIndex);
    		profileData.add(tempProfileNew);
    	}
    	for (int prdIndex = 1; prdIndex <= prdQuantity; prdIndex++) {
    		Profile tempProfileNew = new Profile(environmentId, hostId, jbarId, jiraId, prefixId, domainId, profileDescription, 
           			profileDnsName, profileComponent, jvmId, profileStatus, createdBy, rpmGenerationDate, packageSentDate, version);    			
    		tempProfileNew.setEnvironmentId(4);
    		tempProfileNew.setPrefixId(prdIndex);
    		profileData.add(tempProfileNew);
    	}
    }

}