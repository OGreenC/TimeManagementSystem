# Author: Oliver Tobias Siggaard (s204450)

Feature: Add activity
  Description: Add a new activity to a project
  Actor: User

  Scenario: Successfully add a new activity to an existing project
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    When an activity is added to the project
    Then the activity with the serial "0001" is in the project
    And the project of the activity with serial number "0001" is the selected project