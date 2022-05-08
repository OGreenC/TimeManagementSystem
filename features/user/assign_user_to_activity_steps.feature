# Author: Oliver Tobias Siggaard (s204450)

Feature: Assign user to activity
  Description: Assigning user(s) to an activity
  Actor: User

  Scenario: Assign a user to an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the activity with serial "0001" in the project with ID "220001" has the user with initials "ABC" assigned
    And there is 1 user added to the activity

  Scenario: Assign a non-existing user to an activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the error message "User does not exist" is given

  Scenario: Assign a user to a non-existing activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And there is a user with the initials "ABC"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the error message "Activity does not exist" is given

  Scenario: Assign a user to an activity that is already assigned to the activity
    Given there is a user with the initials "ABC"
    And a project is registered in the system
    And an activity is added to the project
    And the user with initials "ABC" is assigned to this activity
    When the user with initials "ABC" is assigned to the activity
    Then the error message "User is already assigned to this activity" is given