# Author: Oliver Tobias Siggaard (s204450)

Feature: Remove user from activity
  Description: Remove a user from an activity
  Actor: User

  Scenario: Remove a user from an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user is removed from the activity
    Then the activity with serial "0001" in the project with ID "220001" does not have the user with initials "ABC" assigned