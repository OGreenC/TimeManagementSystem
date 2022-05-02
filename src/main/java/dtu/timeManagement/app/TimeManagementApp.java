package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeManagementApp {
    private final List<User> users = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private DateServer dateServer = new DateServer();

    /**
     * @return the created project
     */
    public Project createProject() {
        Project p = new Project(dateServer.getDate());
        projects.add(p);
        return p;
    }

    public void addUser(User user) throws OperationNotAllowedException {
        if (getUser(user.getInitial()) != null) {
            throw new OperationNotAllowedException("The user with the given initials is already in the system");
        }
        users.add(user);
    }

    public User getUser(String initials) {
        return users.stream().filter(u -> u.getInitial().equals(initials)).findAny().orElse(null);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getProject(String ID) {
        return projects.stream().filter(p -> p.getID().equals(ID)).findAny().orElse(null);
    }

    public boolean deleteProject(Project p) throws OperationNotAllowedException {
        if (this.projects.remove(p)) {
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
        project.setProjectLeader(user);
    }

    public Activity createActivity(Project project) throws OperationNotAllowedException {
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        return project.createActivity();
    }

    public void deleteActivity(Project project, Activity activity) throws OperationNotAllowedException {
        if (project == null) {
            throw new OperationNotAllowedException("Project does not exist");
        }
        if (activity == null) {
            throw new OperationNotAllowedException("Activity does not exist");
        }
        project.deleteActivity(activity);
    }

    public List<User> getUsers(String searchText) {
        return users.stream().filter(user -> user.match(searchText)).collect(Collectors.toList());
    }
}
