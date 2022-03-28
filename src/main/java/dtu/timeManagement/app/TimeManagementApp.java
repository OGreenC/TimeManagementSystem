package dtu.timeManagement.app;

import java.util.ArrayList;

public class TimeManagementApp {
    private final ArrayList<User> users = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();

    public void createProject() {
        projects.add(new Project());
    }

    public void addUser(User user) throws OperationNotAllowedException {
        if (getUser(user.getInitial())!=null) {
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
        return projects.stream().filter(p -> p.getID() == ID).findAny().orElse(null);
    }
}