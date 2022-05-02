package dtu.timeManagement.app.presentationLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dtu.timeManagement.app.Activity;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.Project;
import dtu.timeManagement.app.TimeManagementApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainSceneController {

    @FXML
    private Label projectID, projectName, activitySerialNumber,
            activityName, activityExpectedHours;
    @FXML
    private VBox projectVBox, activityVBox;
    @FXML
    private AnchorPane projectInfoPane, activityInfoPane;
    private TimeManagementApp timeManagementApp;
    private Project selectedProject;
    private Button selectedProjectBtn;
    private Button selectedActivityBtn;
    private Activity selectedActivity;

    /**
     * This function is called when the controller is initialized
     */
    public void initialize() {
        timeManagementApp = new TimeManagementApp();
        refreshProjects();
        activityInfoPane.setVisible(false);
    }

    /**
     * Creation and handling of project ScrollPane
     */
    public void refreshProjects() {
        projectVBox.getChildren().clear();
        for (Project p : timeManagementApp.getProjects()) {
            Button b = createProjectBtn(p);
            if(p == selectedProject) {
                setSelectedProject(b);
                projectID.setText(p.getID());
                projectName.setText((p.getName() != null) ? p.getName() : "...");
            }
        }
        projectInfoPane.setVisible(!(selectedProject == null));

        //Create new Btn
        Button createBtn = new Button("+");
        createBtn.setId("createTab");
        createBtn.setOnMouseClicked(mouseEvent -> { createProject(); });
        createBtn.setPrefSize(200, 50);
        projectVBox.getChildren().add(createBtn);
    }

    public Button createProjectBtn(Project p) {
        Button projectBtn = new Button((p.getName() == null) ? "Project: " + p.getID() : p.getID() + " - " + p.getName());
        projectBtn.setPrefSize(200, 50);
        projectBtn.setId("defaultTab");

        projectVBox.getChildren().add(projectBtn);

        projectBtn.setOnMouseClicked(mouseEvent -> { projectClicked(mouseEvent, p); });
        return projectBtn;
    }

    public void projectClicked(MouseEvent e, Project p) {
        setSelectedProject((Button) e.getSource());
        selectedProject = p;
        refreshProjects();
        refreshActivities(selectedProject);
    }

    public void createProject() {
        selectedProject = timeManagementApp.createProject();
        refreshProjects();
        refreshActivities(selectedProject);
    }

    public void setSelectedProject(Button b) {
        if(selectedProjectBtn != null) selectedProjectBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedProjectBtn = b;
        selectedActivity = null;
    }

    public void deleteProject() {
        try {
            timeManagementApp.deleteProject(selectedProject);
            selectedProject = null;
            refreshProjects();
            refreshActivities(null);
        } catch (OperationNotAllowedException e) {
            // TODO : Handle exception by showing some error to the user
            System.out.println("TODO : HANDLE ERROR - PROJECT HAS ALREADY BEEN DELETED");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creation and handling of activity ScrollPane
     */
    public void refreshActivities(Project p) {
        activityVBox.getChildren().clear();
        if(p != null) {
            for (Activity a : p.getActivities()) {
                Button b = createActivityBtn(a);
                if(a == selectedActivity) {
                    setSelectedActivity(b);
                    activitySerialNumber.setText(a.getSerialNumber());
                    activityName.setText((a.getActivityName() != null) ? a.getActivityName() : "...");
                    activityExpectedHours.setText(Integer.toString(a.getExpectedHours()));
                }
            }
            activityInfoPane.setVisible(!(selectedActivity == null));

            //Create new Btn
            Button createBtn = new Button("+");
            createBtn.setId("createTab");
            createBtn.setOnMouseClicked(mouseEvent -> { createActivity(); });
            createBtn.setPrefSize(200, 50);
            activityVBox.getChildren().add(createBtn);

        }
    }
    public Button createActivityBtn(Activity a) {
        Button activityBtn = new Button((a.getActivityName() == null) ? "Activity: " + a.getSerialNumber() : a.getSerialNumber() + " - " + a.getActivityName());
        activityBtn.setPrefSize(200, 50);
        activityBtn.setId("defaultTab");


        //activities.put(ActivityBtn, a);
        activityVBox.getChildren().add(activityBtn);

        activityBtn.setOnMouseClicked(mouseEvent -> { activityClicked(mouseEvent, a); });
        return activityBtn;
    }

    public void activityClicked(MouseEvent e, Activity a) {
        setSelectedActivity((Button) e.getSource());
        selectedActivity = a;
        refreshActivities(selectedProject);
    }

    public void createActivity() {
        try {
            Activity a = timeManagementApp.createActivity(selectedProject);
            selectedActivity = a;
            refreshActivities(selectedProject);
        }  catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedActivity(Button b) {
        if(selectedActivityBtn != null) selectedActivityBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedActivityBtn = b;
    }

    public void deleteActivity() {
        try {
            timeManagementApp.deleteActivity(selectedProject, selectedActivity);
            refreshActivities(selectedProject);
        } catch (OperationNotAllowedException e) {
            // TODO : Handle exception by showing some error to the user
            System.out.println("TODO : HANDLE ERROR - PROJECT HAS ALREADY BEEN DELETED");
            throw new RuntimeException(e);
        }
    }



    /**
     * Edit Buttons
     */
    public void editProjectName() {
        TextInputDialog dialog = new TextInputDialog((selectedProject.getName() == null) ? "" : selectedProject.getName());

        dialog.setTitle("Input");
        dialog.setHeaderText("Enter Project Name");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            selectedProject.setName(name);
            refreshProjects();
        });
    }
    public void editActivityName() {
        TextInputDialog dialog = new TextInputDialog((selectedActivity.getActivityName() == null) ? "" : selectedActivity.getActivityName());

        dialog.setTitle("Input");
        dialog.setHeaderText("Enter Activity Name");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            selectedActivity.setActivityName(name);
            refreshActivities(selectedProject);
        });
    }
    public void editActivityExpectedHours() {
        TextInputDialog dialog = new TextInputDialog(Integer.toString(selectedActivity.getExpectedHours()));

        dialog.setTitle("Input");
        dialog.setHeaderText("Enter Expected Hours");
        dialog.setContentText("Hours:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(hours -> {
            selectedActivity.setExpectedHours(Integer.parseInt(hours));
            refreshActivities(selectedProject);
        });
    }
}
