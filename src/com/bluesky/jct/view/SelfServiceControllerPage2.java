package com.bluesky.jct.view;

import com.bluesky.jct.ProfileFunctions;
import com.bluesky.jct.model.Profile;
import com.bluesky.jct.util.ExceptionHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


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
	private TextField labQuantityField;
	@FXML
	private TextField devQuantityField;
	@FXML
	private TextField sitQuantityField;
	@FXML
	private TextField uatQuantityField;
	@FXML
	private TextField prdQuantityField;

	@FXML
	private Button nextButton;
	@FXML
	private Button backButton;
	
	private ObservableList<Profile> profileData = FXCollections.observableArrayList();
	private Profile tempProfile;
	
	private int labQuantity = 0;
	private int devQuantity = 0;
	private int sitQuantity = 0;
	private int uatQuantity = 0;
	private int prdQuantity = 0;
	
	private String labQuantityText = "";
	private String devQuantityText = "";
	private String sitQuantityText = "";
	private String uatQuantityText = "";
	private String prdQuantityText = "";
	
			
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
		tempProfile = ProfileFunctions.getTempProfile();
		
		// setting initial quality inputs
		labQuantityField.setText(getLabQuantity());
		devQuantityField.setText(getDevQuantity());
		sitQuantityField.setText(getSitQuantity());
		uatQuantityField.setText(getUatQuantity());
		prdQuantityField.setText(getPrdQuantity());
		
		// listener for quality inputs
		labQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			setLabQuantity(newValue);
		});
		devQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			setDevQuantity(newValue);
		});
		sitQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			setSitQuantity(newValue);
		});
		uatQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			setUatQuantity(newValue);
		});
		prdQuantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			setPrdQuantity(newValue);
		});
	}
	
	
	/**
	 * User clicks on Next button.
	 * Profiles will be created and stored in a list.
	 */
    @FXML
    private void handleNext() {
    	
    	int totalQuantity = labQuantity + devQuantity + sitQuantity + uatQuantity + prdQuantity;
    	
    	if (totalQuantity == 0) {
        	String headerText = "Quantity Input";
        	String contentText = "No quantities have been entered!";
        	ExceptionHandling.handleWarning(headerText, contentText);
    	}
    	
    	if (checkIsQuantityEntered() == false) {
        	String headerText = "Quantity / CheckBox Input";
        	String contentText = "Either a quantity on a checked environment is missing\n"
        					   + "or a checkBox is unchecked that shows a quantity!";
        	ExceptionHandling.handleWarning(headerText, contentText);
    	} else {
    		createProfiles();
    	}
    	ProfileFunctions.setTempProfiles(profileData);
      	
	   	// load next page of wizard
   		SelfServiceController.increasePageCounter();
    	
		
        for(int i = 0; i < profileData.size(); i++) {
            System.out.println(profileData.get(i).getProfileDescription()
            		+ ", " + profileData.get(i).getPrefixId()
            		+ ", " + profileData.get(i).getJbarId()
            		+ ", " + profileData.get(i).getEnvironmentId());
        }
		
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
    	
    	if (labQuantity > 0) {
        	int labIndex = 1;    		
    		do {
    			tempProfile.setEnvironmentId(5);
    			tempProfile.setPrefixId(labIndex);
    			profileData.add(tempProfile);
    			labIndex++;
	    	} while (labIndex <= labQuantity);
    	}
    	if (devQuantity > 0) {
        	int devIndex = 1;    		
    		do {
    			tempProfile.setEnvironmentId(1);
    			tempProfile.setPrefixId(devIndex);
    			profileData.add(tempProfile);
    			devIndex++;
	    	} while (devIndex <= devQuantity);
    	}
    	if (sitQuantity > 0) {
        	int sitIndex = 1;    		
    		do {
    			tempProfile.setEnvironmentId(3);
    			tempProfile.setPrefixId(sitIndex);
    			profileData.add(tempProfile);
    			sitIndex++;
	    	} while (sitIndex <= sitQuantity);
    	}
    	if (uatQuantity > 0) {
        	int uatIndex = 1;    		
    		do {
    			tempProfile.setEnvironmentId(2);
    			tempProfile.setPrefixId(uatIndex);
    			profileData.add(tempProfile);
    			uatIndex++;
	    	} while (uatIndex <= uatQuantity);
    	}
       	if (prdQuantity > 0) {
        	int prdIndex = 1;    		
    		do {
    			tempProfile.setEnvironmentId(4);
    			tempProfile.setPrefixId(prdIndex);
    			profileData.add(tempProfile);
    			prdIndex++;
	    	} while (prdIndex <= prdQuantity);
    	}
    }
    

    /**
     * Checks whether both quantity and checkBox are set or not.
     * In case only one input is available it returns false.
     * 
     * @return quantitiesAvailable
     */
    private boolean checkIsQuantityEntered() {   	
    	boolean quantitiesAvailable;
    	if (((labCheckBox.isSelected() == true) && (labQuantity != 0) == true) ||
    			((devCheckBox.isSelected() == true) && (devQuantity != 0) == true) ||
    			((sitCheckBox.isSelected() == true) && (sitQuantity != 0) == true) ||
    			((uatCheckBox.isSelected() == true) && (uatQuantity != 0) == true) ||
    			((prdCheckBox.isSelected() == true) && (prdQuantity != 0) == true) ) {
//TODO remove those two lines if everything works. This is the working x-check in two steps.   			
// (devbCheckBox.isSelected() == false) && (devQuantity != 0) == false ||
// (devbCheckBox.isSelected() == true) && (devQuantity > 0) == true 
    		quantitiesAvailable = true;
    	} else {
    		quantitiesAvailable = false;
    	}
    	return quantitiesAvailable;
    }
    
    
    // --- Getter and Setter for the quantities
    
    private String getLabQuantity() {
    	if (labQuantity == 0) {
    		labQuantityText = "";
    	}
    	labQuantityText = Integer.toString(labQuantity);
    	return labQuantityText; 
    }
    
    private int setLabQuantity(String labQuantityInput) {
    	
    	String headerText = "Quantity Input";
    	String contentText = "Quantity must be an integer between 0 and 5!";
    	
     	if (labQuantityField.getText() == null || labQuantityField.getText().length() == 0) {
    		labQuantity = 0; 
        } else {
            // try to parse the quantity into an int.
            try {
            	labQuantity = Integer.parseInt(labQuantityField.getText());
            } catch (NumberFormatException e) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
            if(labQuantity > 5) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
        }
    	return labQuantity; 
    }
    
    private String getDevQuantity() {
    	if (devQuantity == 0) {
    		devQuantityText = "";
    	}
    	devQuantityText = Integer.toString(devQuantity);
    	return devQuantityText; 
    }
    
    private int setDevQuantity(String devQuantityInput) {
    	
    	String headerText = "Quantity Input";
    	String contentText = "Quantity must be an integer between 0 and 5!";
    	
     	if (devQuantityField.getText() == null || devQuantityField.getText().length() == 0) {
    		devQuantity = 0; 
        } else {
            // try to parse the quantity into an int.
            try {
            	devQuantity = Integer.parseInt(devQuantityField.getText());
            } catch (NumberFormatException e) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
            if(devQuantity > 5) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
        }
    	return devQuantity; 
    }
    
    private String getSitQuantity() {
    	if (sitQuantity == 0) {
    		sitQuantityText = "";
    	}
    	sitQuantityText = Integer.toString(sitQuantity);
    	return sitQuantityText; 
    }
    
    private int setSitQuantity(String sitQuantityInput) {
    	
    	String headerText = "Quantity Input";
    	String contentText = "Quantity must be an integer between 0 and 5!";
    	
     	if (sitQuantityField.getText() == null || sitQuantityField.getText().length() == 0) {
    		sitQuantity = 0; 
        } else {
            // try to parse the quantity into an int.
            try {
            	sitQuantity = Integer.parseInt(sitQuantityField.getText());
            } catch (NumberFormatException e) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
            if(sitQuantity > 5) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
        }
    	return sitQuantity; 
    }
    
    private String getUatQuantity() {
    	if (uatQuantity == 0) {
    		uatQuantityText = "";
    	}
    	uatQuantityText = Integer.toString(uatQuantity);
    	return uatQuantityText; 
    }
    
    private int setUatQuantity(String uatQuantityInput) {
    	
    	String headerText = "Quantity Input";
    	String contentText = "Quantity must be an integer between 0 and 5!";
    	
     	if (uatQuantityField.getText() == null || uatQuantityField.getText().length() == 0) {
    		uatQuantity = 0; 
        } else {
            // try to parse the quantity into an int.
            try {
            	uatQuantity = Integer.parseInt(uatQuantityField.getText());
            } catch (NumberFormatException e) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
            if(uatQuantity > 5) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
        }
    	return uatQuantity; 
    }
    
    private String getPrdQuantity() {
    	if (prdQuantity == 0) {
    		prdQuantityText = "";
    	}
    	prdQuantityText = Integer.toString(prdQuantity);
    	return prdQuantityText; 
    }
    
    private int setPrdQuantity(String prdQuantityInput) {
    	
    	String headerText = "Quantity Input";
    	String contentText = "Quantity must be an integer between 0 and 5!";
    	
     	if (prdQuantityField.getText() == null || prdQuantityField.getText().length() == 0) {
    		prdQuantity = 0; 
        } else {
            // try to parse the quantity into an int.
            try {
            	prdQuantity = Integer.parseInt(prdQuantityField.getText());
            } catch (NumberFormatException e) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
            if(prdQuantity > 5) {
            	ExceptionHandling.handleWarning(headerText, contentText);
            }
        }
    	return prdQuantity; 
    }

}