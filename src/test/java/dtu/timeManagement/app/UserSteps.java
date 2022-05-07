package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private final UserHelper userHelper;
    private final ProjectHelper projectHelper;
    private final ActivityHelper activityHelper;
    private final ErrorMessageHolder errorMessageHolder;
    private List<User> users = new ArrayList<>();

    public UserSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ProjectHelper projectHelper, ActivityHelper activityHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.projectHelper = projectHelper;
        this.activityHelper = activityHelper;
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) {
        userHelper.setUser(new User(initials));
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("there is a user in the system")
    public void thereIsAUserInTheSystem() {
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @When("the user is added to the system")
    public void theUserIsAddedToTheSystem() {
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("there is a user in the system with the initials {string}")
    public void thereIsAUserInTheSystemWithTheInitials(String initials) {
        assertEquals(timeManagementApp.getUser(initials).getInitial(), initials.toLowerCase(Locale.ROOT));
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String errorMessage) {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());
    }

    /**
     * Assign user to activity
     * - Includes assigning a non-existing user and assigning a user twice
     */
    @Given("an activity with serial {string} is added to the project with ID {string}")
    public void an_activity_with_serial_is_added_to_the_project_with_id(String serialNumber, String ID) {
        if (projectHelper.getProject().getID().equals(ID)) {
            projectHelper.getProject().createActivity();
            activityHelper.setActivity(projectHelper.getProject().getActivity(serialNumber));
            assertEquals(activityHelper.getActivity().getSerialNumber(), serialNumber);
        }
    }

    @When("the user with initials {string} is assigned to the activity with serial {string}")
    public void the_user_with_initials_is_assigned_to_the_activity_with_serial(String initials, String serial) {
        try {
            projectHelper.getProject().getActivity(serial).assignUser(timeManagementApp.getUser(initials));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with serial {string} in the project with ID {string} has the user with initials {string} assigned")
    public void the_activity_with_serial_in_the_project_with_id_has_the_user_with_initials_assigned(String serialNumber, String ID, String initials) {
        if (projectHelper.getProject().getID().equals(ID)) {
            if (activityHelper.getActivity().getSerialNumber().equals(serialNumber)) {
                assertTrue(activityHelper.getActivity().isAssigned(timeManagementApp.getUser(initials)));
            }
        }
    }

    @Given("the user with initials {string} is assigned to this activity")
    public void the_user_with_initials_is_assigned_to_this_activity(String initials) throws OperationNotAllowedException {
        activityHelper.getActivity().assignUser(timeManagementApp.getUser(initials));
    }

    @When("the user with initials {string} is assigned to the activity")
    public void the_user_with_initials_is_assigned_to_the_activity(String initials) {
        try {
            activityHelper.getActivity().assignUser(timeManagementApp.getUser(initials));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    /**
     * Remove user from an activity
     * - Includes removing a non-existing user
     */
    @When("the user with initials {string} is removed from the activity with serial {string}")
    public void the_user_with_initials_is_removed_from_the_activity_with_serial(String initials, String serial) {
        try {
            if (activityHelper.getActivity().getSerialNumber().equals(serial)) {
                activityHelper.getActivity().removeUser(timeManagementApp.getUser(initials));
            }
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with serial {string} in the project with ID {string} does not have the user with initials {string} assigned")
    public void the_activity_with_serial_in_the_project_with_id_does_not_have_the_user_with_initials_assigned(String serial, String ID, String initials) {
        if (projectHelper.getProject().getID().equals(ID) && activityHelper.getActivity().getSerialNumber().equals(serial)) {
            activityHelper.getActivity().isAssigned(timeManagementApp.getUser(initials));
        }
    }

    @Given("these users with initials are registered in the system")
    public void these_users_with_initials_are_registered_in_the_system(List<List<String>> userDatatable) throws OperationNotAllowedException {
        List<String> userInitials = userDatatable.get(0);
        for (String userInitial : userInitials) {
            timeManagementApp.addUser(new User(userInitial));
        }
    }
    @When("I search for the text {string}")
    public void i_search_for_the_text(String searchQuery) {
        users = timeManagementApp.getUsers(searchQuery);
    }

    @Then("I find the users with initials")
    public void i_find_the_users_with_initials(List<List<String>> userDatatable) {
        // Get userInitials into a list and remove null-values
        List<String> userInitials = userDatatable.get(0).stream().filter(Objects::nonNull).collect(Collectors.toList());
        // Check both lists are equal in size
        assertEquals(userInitials.size(), users.size());
        // Gets the user field's initials and compares with the test initials
        assertTrue(userInitials.containsAll(users.stream().map(User::getInitial).collect(Collectors.toList())));
    }





    /**
     * Remove a user from the system
     */
    @Given("the user is added to the activity")
    public void the_user_is_added_to_the_activity() {
        timeManagementApp.assignUserToActivity(userHelper.getUser(), activityHelper.getActivity());
    }

    @Given("the user is set as project leader of the project")
    public void the_user_is_set_as_project_leader_of_the_project() throws OperationNotAllowedException {
        projectHelper.getProject().setProjectLeader(userHelper.getUser());
    }

    @When("the user is removed from the system")
    public void the_user_is_removed_from_the_system() {
        timeManagementApp.removeUser(userHelper.getUser());
    }

    @Then("the user is not in the system")
    public void the_user_is_not_in_the_system() {
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }

    @Then("no project has the user as project leader")
    public void no_project_has_the_user_as_project_leader() {
        boolean projectLeaderFound = false;

        for (Project p : timeManagementApp.getProjects()) {
            if (p.getProjectLeader() != null) {
                if (p.getProjectLeader().equals(userHelper.getUser())) {
                    projectLeaderFound = true;
                    break;
                }
            }
        }
        assertFalse(projectLeaderFound);
    }

    @Then("no activity has the user assigned to it")
    public void no_activity_has_the_user_assigned_to_it() {
        for (Project p : timeManagementApp.getProjects()) {
            for (Activity a : p.getActivities()) {
                assertFalse(a.isAssigned(userHelper.getUser()));
            }
        }
    }
}
