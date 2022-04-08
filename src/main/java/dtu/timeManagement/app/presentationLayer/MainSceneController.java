package dtu.timeManagement.app.presentationLayer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainSceneController {
	
	@FXML
	private Button createProjectButton;
	@FXML
	private Label testLabel;
	
	public void createProjectClick() {
		testLabel.setText("Project should now be created");
	}
}
