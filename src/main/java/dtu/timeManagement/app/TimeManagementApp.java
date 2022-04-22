package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;

public class TimeManagementApp {
    private final ArrayList<User> users = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private DateServer dateServer = new DateServer();

    /**
     *
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String initials) {
        return users.stream().filter(u -> u.getInitial().equals(initials)).findAny().orElse(null);
    }

    public void removeUser(String initials){
        for (int i = 0; i < users.size(); i++) {
            if (initials.equals(users.get(i))) {
                users.remove(i);
            }
        }
    }

    // Add given employee to given project
    public void assignEmployeeToProject(String initials, Project project) {
        project.assignEmployee(getUser(initials));
    }

    // Check if given employee is in given project, returns boolean value
    public boolean searchProjectForEmployee(String initials, Project project) {
        return project.searchEmployee(getUser(initials));
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

    public void setDateServer(DateServer dateServer) {
        this.dateServer = dateServer;
    }

}
