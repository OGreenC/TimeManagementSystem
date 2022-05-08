package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

public class TimeManagementApp {
    private final List<User> users = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private DateServer dateServer = new DateServer();

    /**
     * Create project and add it to the system
     *
     * @return the created project
     */
    public Project createProject() {
        Project project = new Project(dateServer.getDate());
        projects.add(project);
        return project;
    }

    public void addUser(User user) throws OperationNotAllowedException {
        //Precondition
        assert user != null;

        //Implicit preconditions
        if (getUser(user.getInitial()) != null) {
            throw new OperationNotAllowedException("The user with the given initials is already in the system");
        }
        if (user.getInitial().length() > 4) {
            throw new OperationNotAllowedException("The users initials are too long");
        }
        users.add(user);

        //Postcondition
        assert Objects.equals(getUser(user.getInitial()), user);
    }

    public void removeUser(User user) throws OperationNotAllowedException {
        // Precondition:
        assert user != null;
        // Implicit precondition
        if (!users.contains(user)) {                                                        // 1
            throw new OperationNotAllowedException("User is not in the system");
        }

        for (Activity activity : user.getActivities()) {                                    // 2
            if (Objects.equals(activity.getProject().getProjectLeader(), user)) {           // 3
                // Remove the user as project leader
                activity.getProject().removeProjectLeader();
            }
            // Remove the user from the activity
            activity.removeUser(user);
        }
        // Remove the user from the system
        users.remove(user);

        // Postconditions:
        assert !users.contains(user);
        assert projects.stream().filter(p -> p.getProjectLeader() != null).noneMatch(p -> p.getProjectLeader().equals(user));
        for (Project project : projects) {
            for (Activity activity : project.getActivities()) {
                assert !activity.isAssigned(user);
            }
        }
    }

    public User getUser(String initials) {
        return users.stream().filter(u -> u.getInitial().equals(initials.toUpperCase())).findAny().orElse(null);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<User> getUsers(String searchText) {
        return users.stream().filter(user -> user.match(searchText.toUpperCase())).collect(Collectors.toList());
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getProject(String ID) {
        return projects.stream().filter(p -> p.getID().equals(ID)).findAny().orElse(null);
    }

    public Project getProject(Project project) {
        return projects.stream().filter(p -> p.equals(project)).findAny().orElse(null);
    }

    /**
     * Delete a project
     *
     * @param project declares the project to be deleted
     * @return true if project was found and deleted
     */
    public boolean deleteProject(Project project) throws OperationNotAllowedException {
        if (this.projects.remove(project)) {
            return true;
        }
        throw new OperationNotAllowedException("Project does not exist");
    }

    public void deleteAllProjects() {
        this.projects.clear();
    }

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

    public void setProjectLeader(Project project, User user) throws OperationNotAllowedException {
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        if (user == null) {
            throw new OperationNotAllowedException("User does not exist");
        }
        project.setProjectLeader(user);
    }


    public Activity createActivity(Project project) throws OperationNotAllowedException {
        return project.createActivity();
    }

    public void deleteActivity(Project project, Activity activity) throws OperationNotAllowedException {
        //If statement functions as a pre-condition, exceptions that our tests are reliant on are not thrown if assert is uncommented.
        //Whitebox pre-condition:
        //assert project != null && project.getActivities().contains(activity);
        if (!project.getActivities().contains(activity)) {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        project.deleteActivity(activity);
        //Whitebox post-condition:
        assert (!project.getActivities().contains(activity));
    }

    /**
     * Assign a user to an activity and add the activity to the user.
     * Whitebox test has been implemented for this method
     * Victor Hyltoft (s214964)
     */
    public void assignUserToActivity(User user, Activity activity) throws OperationNotAllowedException {
        // Preconditions using assert
        // assert user != null && activity != null && !activity.isAssigned(user);

        // "Implicit" preconditions
        if (user == null) {
            throw new OperationNotAllowedException("User does not exist");
        }
        if (activity == null) {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        if (activity.isAssigned(user)) {
            throw new OperationNotAllowedException("User is already assigned to this activity");
        }
        // Another precondition
        assert !user.hasActivity(activity);

        // Assign user to activity, and add activity to user
        activity.assignUser(user);
        user.addActivity(activity);

        // Post-conditions
        assert user.hasActivity(activity);
        assert activity.isAssigned(user);
    }


    public void removeUserFromActivity(User user, Activity activity) throws OperationNotAllowedException {
        user.removeActivity(activity);
        activity.removeUser(user);
    }

    public void removeProjectLeader(Project project) throws OperationNotAllowedException {
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        project.removeProjectLeader();
    }

    public void removeAllUsers() throws OperationNotAllowedException {
        users.clear();
    }
}
