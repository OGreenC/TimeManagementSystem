package dtu.timeManagement.app.presentationLayer;

import java.util.HashMap;
import java.util.Map;

import dtu.timeManagement.app.Activity;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.Project;
import dtu.timeManagement.app.TimeManagementApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainSceneController {

    @FXML
    private Label testLabel;
    @FXML
    private VBox projectVBox, activityVBox;
    private final Map<Button, Project> projects = new HashMap<>();
    private final Map<Button, Activity> activities = new HashMap<>();
    private TimeManagementApp timeManagementApp;
    private Project project;
    private Button selectedProjectTab;
    private Button selectedActivityTab;
    private Activity activity;

    /**
     * This function is called when the controller is initialized
     */
    public void initialize() {
        System.out.println("Initialized");
        timeManagementApp = new TimeManagementApp();
    }

    // Temporary button controls
    public void createProjectClick() {
        testLabel.setText("Project should now be created");
        createProject();
    }

    public void deleteProjectClick() {
        testLabel.setText("Project should now be deleted");
        deleteProject();
    }

    public void createActivityClick() {
        testLabel.setText("Activity should now be created");
        createActivity();
    }

    public void deleteActivityClick() {
        testLabel.setText("Activity should now be deleted");
        deleteActivity();
    }

    // Creating a new project tab
    public void createProject() {
        // Create project elements
        this.project = timeManagementApp.createProject();
        Button projectTab = new Button("Project " + this.project.getID());
        projectTab.setPrefSize(150, 50);

        // Add project objects to system and UI.
        projects.put(projectTab, this.project);
        projectVBox.getChildren().add(projectTab);

        // Update UI
        showProjectActivities();

        // Handle clicking on the project Tab
        projectTab.setOnMouseClicked(mouseEvent -> {
            this.selectedProjectTab = (Button) mouseEvent.getSource();
            this.project = projects.get(selectedProjectTab);
            showProjectActivities();

            System.out.println("project with ID " + this.project.getID() + " was clicked");
        });

    }

    /**
     * Updates the UI for the activities in the selected
     */
    private void showProjectActivities() {
        if (project == null) {
            return;
        }
        activityVBox.getChildren().setAll(project.getActivityTabs());
    }

    // Deleting a project from the system
    public void deleteProject() {
        try {
            timeManagementApp.deleteProject(this.project);
            projectVBox.getChildren().remove(selectedProjectTab);
            activityVBox.getChildren().clear();
            projects.remove(selectedProjectTab);
            selectedActivityTab = null;
            selectedProjectTab = null;
        } catch (OperationNotAllowedException e) {
            // TODO : Handle exception by showing some error to the user
            System.out.println("TODO : HANDLE ERROR - PROJECT HAS ALREADY BEEN DELETED");
			throw new RuntimeException(e);
        }
        System.out.println("Deleted project with ID " + this.project.getID());
    }

    // Create a new activity in the system
    public void createActivity() {
        try {
            // Create activity elements
            this.activity = timeManagementApp.createActivity(project);
            Button activityTab = new Button("Activity " + activity.getSerialNumber());
            activityTab.setPrefSize(150, 50);

            // Add activity objects to system and UI
            activityVBox.getChildren().add(activityTab);
            project.addActivityTab(activityTab);
            activities.put(activityTab, activity);

            activityTab.setOnMouseClicked(mouseEvent -> {
                this.selectedActivityTab = (Button) mouseEvent.getSource();
                this.activity = activities.get(selectedActivityTab);
                if (activity == null) {
                    return;
                }
                showActivityInformation();

                System.out.println("Activity with serial " + this.activity.getSerialNumber() + " was clicked");
            });

        } catch (OperationNotAllowedException e) {
            // TODO : Handle exception
            System.out.println("TODO : HANDLE ERROR - createActivity()");
            throw new RuntimeException(e);
        }
    }

    // TODO : SHOW ACTIVITY INFORMATION
    private void showActivityInformation() {
        System.out.println("TODO");
    }

    // Deleting an activity from the system
    public void deleteActivity() {
        if (selectedActivityTab == null || activity == null || project == null) {
            // We could also throw an Exception here which might be better
            return;
        }

        try {
            timeManagementApp.deleteActivity(project, activity);
            activityVBox.getChildren().remove(selectedActivityTab);
            project.removeActivityTab(selectedActivityTab);
            activities.remove(selectedActivityTab);
            selectedActivityTab = null;
            activity = null;
        } catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }

    }
}
