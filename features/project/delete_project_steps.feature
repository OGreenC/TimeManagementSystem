# Author: Victor Hyltoft (s214964)

Feature: Delete Project
  Description: Delete a project
  Actor: User

  Scenario: Successfully delete a project
    Given a project is registered in the system
    When the project is deleted
    Then the project does not exist

  Scenario: Delete a non-existing project
    Given a project is registered in the system
    When the project is deleted
    And the project is deleted
    Then the error message "Project does not exist" is given
