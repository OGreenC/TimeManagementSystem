package dtu.timeManagement.app.presentationLayer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import dtu.timeManagement.app.domains.Activity;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.domains.Project;
import dtu.timeManagement.app.domains.TimeManagementApp;
import dtu.timeManagement.app.domains.User;
import dtu.timeManagement.app.domains.timeRegistration.RegistrationInstance;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import static java.lang.Integer.MAX_VALUE;

/**
 * @author Generelt alle med meget crossover i alle metoder,
 * men hovedansvarlig Oliver Grønborg Christensen (s204479)
 */
public class MainSceneController {

    @FXML
    private Label projectID, projectName, activitySerialNumber, projectLeader,
            activityName, activityExpectedHours, userActivityProjectID,
            userActivityProjectName, userActivitySerialNumber, userActivityName,
            userActivityExpectedHours, activityStartDate, activityFinishDate,
            projectFinishDate, projectStartDate, activityStartDateInfo, activityFinishDateInfo,
            projectStartDateInfo, projectFinishDateInfo;

    @FXML
    private VBox projectVBox, activityVBox, userVBox, userActivityVBox,
            activityAddedUsersVBox, registeredTimeOverview;
    @FXML
    private AnchorPane projectInfoPane, activityInfoPane, userActivityInfoPane, userRegistrationOverview;

    @FXML
    private DatePicker dateField, registerTimeOverviewDate,
            startDatePicker, finishDatePicker,
            projectStartDatePicker, projectFinishDatePicker;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private TextField userSearchBar;
    private TimeManagementApp timeManagementApp;
    private Project selectedProject;
    private Button selectedProjectBtn;

    private Button selectedActivityBtn;
    private Activity selectedActivity;

    private Button selectedUserActivityBtn;
    private Activity selectedUserActivity;

    private Button selectedUserBtn;
    private User selectedUser;

    /**
     * This function is called when the controller is initialized
     */
    public void initialize() {
        timeManagementApp = new TimeManagementApp();

        //Initializing data
        try {
            InitialData.initializeData(timeManagementApp);
        } catch (OperationNotAllowedException e) {
            showAlert(e.getMessage());
        }

        refreshProjects();
        activityInfoPane.setVisible(false);
        userActivityInfoPane.setVisible(false);
        refreshUsers();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24,1);
        hourSpinner.setValueFactory(valueFactory);

        registerTimeOverviewDate.setValue(java.time.LocalDate.now());
        dateField.setValue(java.time.LocalDate.now());

        //Register time overview value change listener
        registerTimeOverviewDate.valueProperty().addListener((ov, oldValue, newValue) -> registerTimeOverviewChanged(newValue));
    }


    /**
     * Project scroll pane
     */
    public void refreshProjects() {
        projectVBox.getChildren().clear();
        for (Project p : timeManagementApp.getProjects()) {
            Button b = createProjectBtn(p);
            if (p == selectedProject) {
                setSelectedProject(b);
                projectID.setText(p.getID());
                projectName.setText((p.getName() != null) ? p.getName() : "...");
                projectLeader.setText((p.getProjectLeader() != null ? p.getProjectLeader().getInitial() : "..."));
                projectStartDate.setText((selectedProject.getStartDate() != null) ? selectedProject.getStartDate().get(Calendar.YEAR) + "-" +
                        selectedProject.getStartDate().get(Calendar.MONTH) + "-" + selectedProject.getStartDate().get(Calendar.DAY_OF_MONTH) : "...");
                projectFinishDate.setText((selectedProject.getEndDate() != null) ? selectedProject.getEndDate().get(Calendar.YEAR) + "-" +
                        selectedProject.getEndDate().get(Calendar.MONTH) + "-" + selectedProject.getEndDate().get(Calendar.DAY_OF_MONTH) : "...");
            }
        }
        projectInfoPane.setVisible(!(selectedProject == null));

        //Create new Btn
        Button createBtn = new Button("+");
        createBtn.setId("createTab");
        createBtn.setOnMouseClicked(mouseEvent -> createProject());
        createBtn.setPrefSize(200, 50);
        projectVBox.getChildren().add(createBtn);
    }

    public Button createProjectBtn(Project p) {
        Button projectBtn = new Button((p.getName() == null) ? "Project: " + p.getID() : p.getID() + " - " + p.getName());
        projectBtn.setPrefSize(200, 50);
        projectBtn.setId("defaultTab");

        projectVBox.getChildren().add(projectBtn);

        projectBtn.setOnMouseClicked(mouseEvent -> projectClicked(mouseEvent, p));
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
            activityInfoPane.setVisible(false);
            refreshProjects();
            refreshActivities(null);
        } catch (OperationNotAllowedException e) {
            showAlert(e.getMessage());
        }
    }


    /**
     * activity ScrollPane
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
                    activityStartDate.setText((a.getStartTime() != null) ? a.getStartTime().get(Calendar.YEAR) +"-"+
                            (a.getStartTime().get(Calendar.MONTH)+1) +"-"+ a.getStartTime().get(Calendar.DAY_OF_MONTH)  : "...");
                    activityFinishDate.setText((a.getEndTime() != null) ? a.getEndTime().get(Calendar.YEAR) +"-"+
                            (a.getEndTime().get(Calendar.MONTH)+1) +"-"+ a.getEndTime().get(Calendar.DAY_OF_MONTH)  : "...");
                } else {
                    if(a.getEndTime() != null && a.hasEnded()) {

                        b.setId("timeEndedBtn");
                    }
                }
            }
            if (selectedActivity != null) refreshActivityAddedUsers(selectedActivity);
            activityInfoPane.setVisible(!(selectedActivity == null));

            //Create new Btn
            Button createBtn = new Button("+");
            createBtn.setId("createTab");
            createBtn.setOnMouseClicked(mouseEvent -> createActivity());
            createBtn.setPrefSize(200, 50);
            activityVBox.getChildren().add(createBtn);

        }
    }

    public Button createActivityBtn(Activity a) {
        Button activityBtn = new Button((a.getActivityName() == null) ? "Activity: " + a.getSerialNumber() : a.getSerialNumber() + " - " + a.getActivityName());
        activityBtn.setPrefSize(200, 50);
        activityBtn.setId("defaultTab");

        activityBtn.setOnMouseClicked(mouseEvent -> activityClicked(mouseEvent, a));
        return activityBtn;
    }

    public void activityClicked(MouseEvent e, Activity a) {
        setSelectedActivity((Button) e.getSource());
        selectedActivity = a;
        refreshActivities(selectedProject);
    }

    public void createActivity() {
        try {
            selectedActivity = timeManagementApp.createActivity(selectedProject);
            refreshActivities(selectedProject);
        } catch (OperationNotAllowedException e) {
            showAlert(e.getMessage());
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
            showAlert(e.getMessage());
        }
    }

    /**
     * Users added to activity ScrollPane
     */
    public void refreshActivityAddedUsers(Activity a) {
        activityAddedUsersVBox.getChildren().clear();
        for (User u : a.getUsers()) {
            Button b = createActivityUserBtn(u);
            activityAddedUsersVBox.getChildren().add(b);
        }
        if (selectedUser != null) refreshUserActivities(selectedUser);


        //Create "Add user" choicebox.
        ComboBox<String> comboBox = new ComboBox<>();
        for (User u : timeManagementApp.getUsers()) {
            if (!a.isAssigned(u)) comboBox.getItems().add(u.getInitial());
        }
        comboBox.setId("createTab");
        comboBox.setValue("       +");
        comboBox.setPrefSize(200, 50);
        comboBox.valueProperty().addListener((ov, t, t1) -> addActivityUser(timeManagementApp.getUser(t1)));

        activityAddedUsersVBox.getChildren().add(comboBox);
    }

    public Button createActivityUserBtn(User u) {
        Button userBtn = new Button(u.getInitial());
        userBtn.setPrefSize(200, 50);
        userBtn.setId("defaultTab");

        userBtn.setOnMouseClicked(mouseEvent -> deleteUserClicked(u));
        return userBtn;
    }

    public void deleteUserClicked(User u) {
        try {
            timeManagementApp.removeUserFromActivity(u, selectedActivity);
        } catch (OperationNotAllowedException err) {
            showAlert(err.getMessage());
        }
        refreshActivityAddedUsers(selectedActivity);
    }

    public void addActivityUser(User u) {
        try {
            timeManagementApp.assignUserToActivity(u, selectedActivity);
        } catch (OperationNotAllowedException e) {
            showAlert(e.getMessage());
        }
        refreshActivityAddedUsers(selectedActivity);
    }

    /**
     * Edit Buttons for project pane
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

    public void editActivityStartDate() {
        LocalDate localDate = startDatePicker.getValue(); //Null if non chosen
        if (localDate != null) {
            try {
                selectedActivity.setStartTime(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                activityStartDate.setText((selectedActivity.getStartTime() != null) ? selectedActivity.getStartTime().get(Calendar.YEAR) + "-" +
                        (selectedActivity.getStartTime().get(Calendar.MONTH)+1) + "-" + selectedActivity.getStartTime().get(Calendar.DAY_OF_MONTH) : "...");
            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
            startDatePicker.setValue(null);
        }
    }

    public void editActivityFinishDate() {
        LocalDate localDate = finishDatePicker.getValue(); //Null if non chosen
        if (localDate != null) {
            try {
                selectedActivity.setEndTime(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                activityFinishDate.setText((selectedActivity.getEndTime() != null) ? selectedActivity.getEndTime().get(Calendar.YEAR) + "-" +
                        (selectedActivity.getEndTime().get(Calendar.MONTH)+1) + "-" + selectedActivity.getEndTime().get(Calendar.DAY_OF_MONTH) : "...");

            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
            finishDatePicker.setValue(null);
            }
    }

    public void editProjectStartDate() {
        LocalDate localDate = projectStartDatePicker.getValue(); //Null if non chosen
        if (localDate != null) {
            try {
                selectedProject.setStartDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                projectStartDate.setText((selectedProject.getStartDate() != null) ? selectedProject.getStartDate().get(Calendar.YEAR) + "-" +
                        (selectedProject.getStartDate().get(Calendar.MONTH)+1) + "-" + selectedProject.getStartDate().get(Calendar.DAY_OF_MONTH) : "...");
            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
            projectStartDatePicker.setValue(null);

        }
    }

    public void editProjectFinishDate() {
        LocalDate localDate = projectFinishDatePicker.getValue(); //Null if non chosen
        if (localDate != null) {
            try {
                selectedProject.setEndDate(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
                projectFinishDate.setText((selectedProject.getEndDate() != null) ? selectedProject.getEndDate().get(Calendar.YEAR) + "-" +
                        (selectedProject.getEndDate().get(Calendar.MONTH)+1) + "-" + selectedProject.getEndDate().get(Calendar.DAY_OF_MONTH) : "...");
            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
            projectFinishDatePicker.setValue(null);

        }
    }
    /*
     *
     * User tab in GUI
     *
     */

    /**
     * User ScrollPane + activities
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
        createBtn.setOnMouseClicked(mouseEvent -> createUser());
        createBtn.setPrefSize(200, 50);
        userVBox.getChildren().add(createBtn);
        if (selectedActivity != null) refreshActivityAddedUsers(selectedActivity);
    }

    public void refreshUserActivities(User u) {
        userActivityVBox.getChildren().clear();
        for (Activity a : u.getActivities()) {
            Button b = createSelectedUserActivityBtn(a);
            userActivityVBox.getChildren().add(b);
            if(a == selectedUserActivity) {
                setSelectedUserActivity(b);
            }
        }
    }

    public Button createUserBtn(User u) {
        Button userBtn = new Button(u.getInitial() + "\n (right click to remove)");
        userBtn.setPrefSize(200, 50);
        userBtn.setId("defaultTab");
        userBtn.setTextAlignment(TextAlignment.CENTER);
        userVBox.getChildren().add(userBtn);

        userBtn.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                try {
                    timeManagementApp.removeUser(u);
                } catch (OperationNotAllowedException e) {
                    showAlert(e.getMessage());
                }
                refreshUsers();
                selectedUserActivity = null;
                selectedUser = null;
                userActivityInfoPane.setVisible(false);
                userRegistrationOverview.setVisible(false);
            } else {
                userClicked(mouseEvent, u);
            }
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
                selectedUser = u;
                refreshUsers();
                refreshUserActivities(u);
                userRegistrationOverview.setVisible(false);
                userActivityInfoPane.setVisible(false);
            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
        });
    }
    public void userClicked(MouseEvent e, User u) {
        setSelectedUser((Button) e.getSource());
        selectedUser = u;
        refreshUsers();
        selectedUserActivity = null;
        userActivityInfoPane.setVisible(false);
        userRegistrationOverview.setVisible(true);
        registerTimeOverviewChanged(registerTimeOverviewDate.getValue());
        refreshUserActivities(u);
    }
    public void setSelectedUser(Button b) {
        if (selectedUserBtn != null) selectedUserBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedUserBtn = b;
    }
    public void setSelectedUserActivity(Button b) {
        if(selectedUserActivityBtn != null) selectedUserActivityBtn.setId("defaultTab");
        b.setId("selectedTab");
        selectedUserActivityBtn = b;
    }
    public Button createSelectedUserActivityBtn(Activity a) {
        Button activityBtn = new Button((a.getActivityName() == null) ? "Activity: " + a.getSerialNumber() : a.getSerialNumber() + " - " + a.getActivityName());
        activityBtn.setPrefSize(200, 50);
        activityBtn.setId("defaultTab");

        activityBtn.setOnMouseClicked(mouseEvent -> selectedUserActivity(mouseEvent, a));
        return activityBtn;
    }
    public void selectedUserActivity(MouseEvent e, Activity a) {
        setSelectedUserActivity((Button) e.getSource());
        selectedUserActivity = a;
        refreshUserActivities(selectedUser);

        userActivityProjectID.setText(a.getProject().getID());
        userActivityProjectName.setText(a.getProject().getName());
        userActivitySerialNumber.setText(a.getSerialNumber());
        userActivityName.setText(a.getActivityName());
        userActivityExpectedHours.setText(Integer.toString(a.getExpectedHours()));
        userActivityInfoPane.setVisible(true);
        userRegistrationOverview.setVisible(false);
        activityStartDateInfo.setText((a.getStartTime() != null) ? "Start date: "+ a.getStartTime().get(Calendar.YEAR) +"-"+
                (a.getStartTime().get(Calendar.MONTH)+1) +"-"+ a.getStartTime().get(Calendar.DAY_OF_MONTH)  : "Start date:");
        activityFinishDateInfo.setText((a.getEndTime() != null) ? "End date: "+ a.getEndTime().get(Calendar.YEAR) +"-"+
                (a.getEndTime().get(Calendar.MONTH)+1) +"-"+ a.getEndTime().get(Calendar.DAY_OF_MONTH)  : "End date:");
        projectStartDateInfo.setText((a.getProject().getStartDate() != null) ? "Start date: "+ a.getProject().getStartDate().get(Calendar.YEAR) +"-"+
                (a.getProject().getStartDate().get(Calendar.MONTH)+1) +"-"+ a.getProject().getStartDate().get(Calendar.DAY_OF_MONTH)  : "Start date:");
        projectFinishDateInfo.setText((a.getProject().getEndDate() != null) ? "End date: "+ a.getProject().getEndDate().get(Calendar.YEAR) +"-"+
                (a.getProject().getEndDate().get(Calendar.MONTH)+1) +"-"+ a.getProject().getEndDate().get(Calendar.DAY_OF_MONTH)  : "End date:");
    }

    /**
     * Button for registering time
     */
    public void registerTimeClicked() {
        LocalDate localDate = dateField.getValue(); //Null if non chosen
        Calendar calendarDate = new GregorianCalendar.Builder()
                .setDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).build();

        int hours = hourSpinner.getValue();

        try {
            selectedUser.registerTime(calendarDate, hours, selectedUserActivity.getProject().getID(), selectedUserActivity.getSerialNumber());
        } catch (OperationNotAllowedException e) {
            showAlert(e.getMessage());
        }
        //System.out.println(selectedUser.getTimeRegistrationDay(calendarDate).getRegistrationUnit(selectedUserActivity.getProject().getID(), selectedUserActivity.getSerialNumber()).getHours());
    }

    public void registerTimeOverviewChanged(LocalDate localDate) {
        registeredTimeOverview.getChildren().clear();
        Calendar calendarDate = new GregorianCalendar.Builder()
                .setDate(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).build();
        if(selectedUser.getTimeRegistrationDay(calendarDate) != null) {
            for (RegistrationInstance ru : selectedUser.getTimeRegistrationDay(calendarDate).getRegistrationUnits()) {
                Project p = timeManagementApp.getProject(ru.getProjectID());
                if(p == null) continue;
                Activity a = p.getActivity(ru.getActivitySerial());
                String projectInfo = ((p.getName() != null) ? p.getName() : p.getID());
                String activityInfo = ((a != null && a.getActivityName() != null) ? a.getActivityName() : ru.getActivitySerial());
                String l1 = projectInfo + "  " + activityInfo;
                String l2 = "Hours registered: " + ru.getHours();


                Button timeBtn = new Button(l1 + "\n" + l2 + "\n" + "Click to delete entry");
                timeBtn.setPrefHeight(75);
                timeBtn.setMaxWidth(MAX_VALUE);
                timeBtn.setId("defaultTab");
                timeBtn.setTextAlignment(TextAlignment.CENTER);

                //activities.put(ActivityBtn, a);
                registeredTimeOverview.getChildren().add(timeBtn);

                timeBtn.setOnMouseClicked(mouseEvent -> removeTimeRegistration(calendarDate,ru));
            }
        }
    }
    public void removeTimeRegistration(Calendar date, RegistrationInstance registrationInstance) {
        selectedUser.getTimeRegistrationDay(date).removeRegistrationUnit(registrationInstance.getProjectID(), registrationInstance.getActivitySerial());
        registerTimeOverviewChanged(registerTimeOverviewDate.getValue());
    }

    /**
     * Edit project leader in project pane
     * Victor Hyltoft
     */
    public void editProjectLeader() {
        String remove = "REMOVE";

        List<String> initials = timeManagementApp.getUsers().stream().map(User::getInitial).sorted().collect(Collectors.toList());
        initials.add(0, remove);

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Select user", initials);
        dialog.setTitle("Select a project leader");
        dialog.setHeaderText("Select a project leader");
        dialog.setContentText("User:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            try {
                if (name.equals(remove)) {
                    timeManagementApp.removeProjectLeader(selectedProject);
                    projectLeader.setText("...");
                    return;
                }
                //
                timeManagementApp.setProjectLeader(selectedProject, timeManagementApp.getUser(name));
                projectLeader.setText(name);
            } catch (OperationNotAllowedException e) {
                showAlert(e.getMessage());
            }
        });
    }

    public static void showAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.NONE, errorMessage, ButtonType.CLOSE);
        alert.show();
    }
}
