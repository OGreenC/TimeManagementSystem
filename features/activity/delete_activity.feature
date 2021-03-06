# Author: Mikkel Allermand (s214953)

Feature: Delete activity
  Description: Delete an activity from a project
  Actor: User

  Scenario: Successfully delete an existing activity from a project
    Given no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And there is an activity in the project with serial "0001"
    When the user deletes the activity with serial "0001"
    Then the activity with serial "0001" is deleted

  Scenario: Attempt to delete a non-existing activity from a project
    Given no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    When the user deletes the activity with serial "0001"
    Then the error message "Activity does not exist" is given
