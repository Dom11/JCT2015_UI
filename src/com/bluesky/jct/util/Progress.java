package com.bluesky.jct.util;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Progress {

//	private static Float[] values = new Float[] { -1.0f, 0f, 0.6f, 1.0f };
	private static Float[] values = new Float[] { -1.0f };	
	private static Label[] labels = new Label[values.length];
//	private static ProgressBar[] pbs = new ProgressBar[values.length];
	private static ProgressIndicator[] pins = new ProgressIndicator[values.length];
	private static HBox hbs[] = new HBox[values.length];

	
	public static void showProgressDialog() {

		Stage stage = new Stage();
		Group root = new Group();
		Scene scene = new Scene(root, 300, 250);
		stage.setScene(scene);
		stage.setTitle("Progress Controls");

		for (int i = 0; i < values.length; i++) {
			final Label label = labels[i] = new Label();
			label.setText("progress:" + values[i]);

//			final ProgressBar pb = pbs[i] = new ProgressBar();
//			pb.setProgress(values[i]);

			final ProgressIndicator pin = pins[i] = new ProgressIndicator();
			pin.setProgress(values[i]);
			final HBox hb = hbs[i] = new HBox();
			hb.setSpacing(5);
			hb.setAlignment(Pos.CENTER);
			hb.getChildren().addAll(label,  pin);
		}

		final VBox vb = new VBox();
		vb.setSpacing(5);
		vb.getChildren().addAll(hbs);
		scene.setRoot(vb);
		stage.show();
		
		
		// check http://java-buddy.blogspot.ch/2014/02/update-javafx-ui-in-scheduled-task-of.html
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stage.close();
	}

}
