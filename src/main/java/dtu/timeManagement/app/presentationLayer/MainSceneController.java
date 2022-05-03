package dtu.timeManagement.app.presentationLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dtu.timeManagement.app.Activity;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.Project;
import dtu.timeManagement.app.TimeManagementApp;
import dtu.timeManagement.app.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainSceneController {

    @FXML
    private Label projectID, projectName, activitySerialNumber,
            activityName, activityExpectedHours;
    @FXML
    private VBox projectVBox, activityVBox, userVBox, userActivityVBox, activityAddedUsersVBox;
    @FXML
    private AnchorPane projectInfoPane, activityInfoPane;
    @FXML
    private TextField userSearchBar;
    private TimeManagementApp timeManagementApp;
    private Project selectedProject;
    private Button selectedProjectBtn;

    private Button selectedActivityBtn;
    private Activity selectedActivity;

    private Button selectedUserBtn;
    private User selectedUser;

    /**
     * This function is called when the controller is initialized
     */
    public void initialize() {
        timeManagementApp = new TimeManagementApp();
        refreshProjects();
        activityInfoPane.setVisible(false);
        refreshUsers();
    }

    /**
     * Creation and handling of project ScrollPane
     */
    public void refreshProjects() {
        projectVBox.getChildren().clear();
        for (Project p : timeManagementApp.getProjects()) {
            Button b = createProjectBtn(p);
            if (p == selectedProject) {
                setSelectedProject(b);
                projectID.setText(p.getID());
                projectName.setText((p.getName() != null) ? p.getName() : "...");
            }
        }
        projectInfoPane.setVisible(!(selectedProject == null));

        //Create new Btn
        Button createBtn = new Button("+");
        createBtn.setId("createTab");
        createBtn.setOnMouseClicked(mouseEvent -> {
            createProject();
        });
        createBtn.setPrefSize(200, 50);
        projectVBox.getChildren().add(createBtn);
    }

    public Button createProjectBtn(Project p) {
        Button projectBtn = new Button((p.getName() == null) ? "Project: " + p.getID() : p.getID() + " - " + p.getName());
        projectBtn.setPrefSize(200, 50);
        projectBtn.setId("defaultTab");

        projectVBox.getChildren().add(projectBtn);

        projectBtn.setOnMouseClicked(mouseEvent -> {
            projectClicked(mouseEvent, p);
        });
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
        if (selectedProjectBtn != null) selectedProjectBtn.setId("defaultTab");
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
            throw new RuntimeException(e);
        }
    }


    /**
     * Creation and handling of activity ScrollPane
     */
    public void refreshActivities(Project p) {
        activityVBox.getChildren().clear();
        if (p != null) {
            for (Activity a : p.getActivities()) {
                Button b = createActivityBtn(a);
                activityVBox.getChildren().add(b);
                if (a == selectedActivity) {
                    setSelectedActivity(b);
                    activitySerialNumber.setText(a.getSerialNumber());
                    activityName.setText((a.getActivityName() != null) ? a.getActivityName() : "...");
                    activityExpectedHours.setText(Integer.toString(a.getExpectedHours()));
                }
            }
            if (selectedActivity != null) refreshActivityAddedUsers(selectedActivity);
            activityInfoPane.setVisible(!(selectedActivity == null));

            //Create new Btn
            Button createBtn = new Button("+");
            createBtn.setId("createTab");
            createBtn.setOnMouseClicked(mouseEvent -> {
                createActivity();
            });
            createBtn.setPrefSize(200, 50);
            activityVBox.getChildren().add(createBtn);

        }
    }

    public Button createActivityBtn(Activity a) {
        Button activityBtn = new Button((a.getActivityName() == null) ? "Activity: " + a.getSerialNumber() : a.getSerialNumber() + " - " + a.getActivityName());
        activityBtn.setPrefSize(200, 50);
        activityBtn.setId("defaultTab");

        activityBtn.setOnMouseClicked(mouseEvent -> {
            activityClicked(mouseEvent, a);
        });
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
        } catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSelectedActivity(Button b) {
        if (selectedActivityBtn != null) selectedActivityBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedActivityBtn = b;
    }

    public void deleteActivity() {
        try {
            timeManagementApp.deleteActivity(selectedProject, selectedActivity);
            selectedActivity = null;
            refreshActivities(selectedProject);
        } catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creation and handling of users added to activity ScrollPane
     */
    public void refreshActivityAddedUsers(Activity a) {
        activityAddedUsersVBox.getChildren().clear();
        for (User u : a.getUsers()) {
            Button b = createActivityUserBtn(u);
            activityAddedUsersVBox.getChildren().add(b);
        }
        if (selectedUser != null) refreshUserActivities(selectedUser);


        //Create "Add user" choicebox.
        ComboBox comboBox = new ComboBox();
        for (User u : timeManagementApp.getUsers()) {
            if (!a.isAssigned(u)) comboBox.getItems().add(u.getInitial());
        }
        comboBox.setId("createTab");
        comboBox.setValue("       +");
        comboBox.setPrefSize(200, 50);
        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                addActivityUser(timeManagementApp.getUser(t1));
            }
        });

        activityAddedUsersVBox.getChildren().add(comboBox);
    }

    public Button createActivityUserBtn(User u) {
        Button userBtn = new Button(u.getInitial());
        userBtn.setPrefSize(200, 50);
        userBtn.setId("defaultTab");

        userBtn.setOnMouseClicked(mouseEvent -> {
            deleteUserClicked(mouseEvent, u);
        });
        return userBtn;
    }

    public void deleteUserClicked(MouseEvent e, User u) {
        timeManagementApp.removeUserFromActivity(u, selectedActivity);
        refreshActivityAddedUsers(selectedActivity);
    }

    public void addActivityUser(User u) {
        timeManagementApp.assignUserToActivity(u, selectedActivity);
        refreshActivityAddedUsers(selectedActivity);
    }


    /**
     * User tab in GUI
     * <p>
     * <p>
     * Creation and handling of User ScrollPane
     */
    public void refreshUsers() {
        userVBox.getChildren().clear();
        for (User u : timeManagementApp.getUsers(userSearchBar.getText())) {
            Button b = createUserBtn(u);
            if (u == selectedUser) {
                setSelectedUser(b);
            }
        }
        //Create new Btn
        Button createBtn = new Button("+");
        createBtn.setId("createTab");
        createBtn.setOnMouseClicked(mouseEvent -> {
            createUser();
        });
        createBtn.setPrefSize(200, 50);
        userVBox.getChildren().add(createBtn);
        if (selectedActivity != null) refreshActivityAddedUsers(selectedActivity);
    }

    public void refreshUserActivities(User u) {
        userActivityVBox.getChildren().clear();
        for (Activity a : u.getActivities()) {
            Button b = createActivityBtn(a);
            userActivityVBox.getChildren().add(b);
            if (a == selectedActivity) {
                setSelectedActivity(b);
            }
        }
    }

    public Button createUserBtn(User u) {
        Button userBtn = new Button(u.getInitial());
        userBtn.setPrefSize(200, 50);
        userBtn.setId("defaultTab");

        //activities.put(ActivityBtn, a);
        userVBox.getChildren().add(userBtn);

        userBtn.setOnMouseClicked(mouseEvent -> {
            userClicked(mouseEvent, u);
        });
        return userBtn;
    }

    public void createUser() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Input");
        dialog.setHeaderText("Enter new user initials");
        dialog.setContentText("Initials:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(initials -> {
            User u = new User(initials);
            try {
                timeManagementApp.addUser(u);
            } catch (OperationNotAllowedException e) {
                throw new RuntimeException(e);
            }
            selectedUser = u;
            refreshUsers();
        });
    }

    public void userClicked(MouseEvent e, User u) {
        setSelectedUser((Button) e.getSource());
        selectedUser = u;
        refreshUsers();
        refreshUserActivities(u);
    }

    public void setSelectedUser(Button b) {
        if (selectedUserBtn != null) selectedUserBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedUserBtn = b;
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
