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
        for (Activity a : user.getActivities()) {
            if (Objects.equals(a.getProject().getProjectLeader(), user)) {
                a.getProject().removeProjectLeader();
            }
            a.removeUser(user);
        }
        users.remove(user);
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
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        return project.createActivity();
    }

    public void deleteActivity(Project project, Activity activity) throws OperationNotAllowedException {
        // TODO : MIKKEL KIG PÅ DET HER IGEN...
//        assert project != null && project.getActivities().contains(activity);
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        if (!project.getActivities().contains(activity)) {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        project.deleteActivity(activity);
        assert (!project.getActivities().contains(activity));
    }

    public void assignUserToActivity(User user, Activity activity) throws OperationNotAllowedException {
        // Pre-conditions
        // assert user != null && activity != null && !activity.isAssigned(user);

        // Implicit pre-conditions
        if (user == null) {
            throw new OperationNotAllowedException("User does not exist");
        }
        if (activity == null) {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        if (activity.isAssigned(user)) {
            throw new OperationNotAllowedException("User is already assigned to this activity");
        }
        // Pre-condition
        assert !user.getActivities().contains(activity);

        // Assign user to activity, and add activity to user
        activity.assignUser(user);
        user.addActivity(activity);

        // Post-conditions
        assert user.getActivities().contains(activity);
        assert activity.isAssigned(user);
    }


    public void removeUserFromActivity(User user, Activity activity) {
        user.removeActivity(activity);
        try {
            activity.removeUser(user);
        } catch (OperationNotAllowedException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProjectLeader(Project project) throws OperationNotAllowedException {
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        project.removeProjectLeader();
    }
}
