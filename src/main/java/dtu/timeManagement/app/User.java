package dtu.timeManagement.app;

import java.util.LinkedList;

public class User {
    private String initial;
    private String name;

    public User(String initial, String name) {
        this.initial = initial;
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
