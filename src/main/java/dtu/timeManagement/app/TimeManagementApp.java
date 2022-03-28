package dtu.timeManagement.app;

import java.util.ArrayList;

public class TimeManagementApp {
    private final ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(String initials) {
        return users.stream().filter(u -> u.getInitial().equals(initials)).findAny().orElse(null);

    }
}
