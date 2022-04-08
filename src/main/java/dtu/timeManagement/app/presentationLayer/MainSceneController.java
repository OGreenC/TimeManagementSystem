package dtu.timeManagement.app.presentationLayer;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainSceneController {

	@FXML
	private Label testLabel;
	@FXML
	private VBox projectVBox, activityVBox;
	
	private ArrayList<Button> projectButtonArr = new ArrayList<Button>();
	private ArrayList<Button> activityButtonArr = new ArrayList<Button>();
	
	// Temporary button controls
	public void createProjectClick() {
		testLabel.setText("Project should now be created");
		createNewProject();
	}
	
	public void deleteProjectClick() {
		testLabel.setText("Project should now be deleted");
		deleteProject();
	}
	
	public void createActivityClick() {
		testLabel.setText("Activity should now be created");
		createNewActivity();
	}
	
	public void deleteActivityClick() {
		testLabel.setText("Activity should now be deleted");
		deleteActivity();
	}
	
	// Creating a new project tab
	public void createNewProject() {
		int projectNum = projectButtonArr.size() + 1;
		
		Button newProjectTab = new Button("Project " + projectNum);
		newProjectTab.setPrefSize(150, 50);
		projectButtonArr.add(newProjectTab);
		
		projectVBox.getChildren().add(newProjectTab);
	}
	
	// Deleting an existing project tab
	public void deleteProject() {
		if (projectButtonArr.size() > 0) {
			int lastElement = projectButtonArr.size() - 1;
			projectButtonArr.remove(lastElement);
			projectVBox.getChildren().remove(lastElement);
		}
	}
	
	// Creating a new activity tab
	public void createNewActivity() {
		int activityNum = activityButtonArr.size() + 1;
		
		Button newActivityTab = new Button("Activity " + activityNum);
		newActivityTab.setPrefSize(150, 50);
		activityButtonArr.add(newActivityTab);
		
		activityVBox.getChildren().add(newActivityTab);
	}
	
	// Deleting an existing activity tab
	public void deleteActivity() {
		if (activityButtonArr.size() > 0) {
			int lastElement = activityButtonArr.size() - 1;
			activityButtonArr.remove(lastElement);
			activityVBox.getChildren().remove(lastElement);
		}
	}
}
