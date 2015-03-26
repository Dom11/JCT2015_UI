package com.bluesky.jct.view;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.bluesky.jct.model.ProfileView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;



public class StatisticsController {
	
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private PieChart pieChart;
    
    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    
    
    /**
     * Constructor
     */
    public StatisticsController() {
    	super();
    }
    
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Get an array with the English month names.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convert it to a list and add it to our ObservableList of months.
        monthNames.addAll(Arrays.asList(months));

        // Assign the month names as categories for the horizontal axis.
        xAxis.setCategories(monthNames);
    }
    
    
    /**
     * Sets the profiles to show the statistics for.
     * 
     * @param profile
     */
  	public void setProfileData(List<ProfileView> profiles) {
        // Count the number of profiles having their creation in a specific month.
        int[] monthCounter = new int[12];
        for (ProfileView p : profiles) {
        	SimpleDateFormat format = new SimpleDateFormat("M");
        	int month = Integer.parseInt(format.format(p.getPackageSentDate())) - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data object for each month. Add it to the series.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }
        barChart.getData().add(series);
        
        
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Dominik", 5),
                new PieChart.Data("Hans", 1),
                new PieChart.Data("Peter", 3));
        pieChart.getData().addAll(pieChartData);
    }
  	
}