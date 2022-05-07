Feature: Delete activity
  Description: Delete an activity from a project
  Actor: User

  Scenario: Successfully delete an existing activity from a project
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And there is an activity in the project with serial "0001"
    When the user deletes the activity with serial "0001"
    Then the activity with serial "0001" is deleted

  Scenario: Attempt to delete a non-existing activity from a project
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    When the user deletes the activity with serial "0001"
    Then the error message "Activity does not exist" is given

  Scenario: Attempt to delete an activity from a non-existing project
    Given there is a user in the system
    And a project is registered in the system
    And an activity is added to the project
    And there is an activity in the project with serial "0001"
    And the project is deleted
    When the user deletes the activity with serial "0001"
    Then the error message "Project does not exist" is given

