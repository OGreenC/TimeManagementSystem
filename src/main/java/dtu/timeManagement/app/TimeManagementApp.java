package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;

public class TimeManagementApp {
    private final ArrayList<User> users = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();

    public void createProject(Project p) {
        projects.add(p);
    }

    public void addUser(User user) throws OperationNotAllowedException {
        if (getUser(user.getInitial()) != null) {
            throw new OperationNotAllowedException("The user with the given initials is already in the system");
        }
        users.add(user);

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String initials) {
        return users.stream().filter(u -> u.getInitial().equals(initials)).findAny().orElse(null);

    }


    public ArrayList<Project> getProjects() {
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

    public void createActivity(Project project) {
        project.createActivity();
    }

    public void deleteAllProjects() {
        this.projects.clear();
    }
}
