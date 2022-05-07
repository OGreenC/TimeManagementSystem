# Author: Victor Hyltoft (s214964)

Feature: Edit Project Leader Properties
  Description: Edit project leader properties
  Actor: User

  Background: Setup project and user
    Given a project is registered in the system
    And there is a user with the initials "HUBU"
    And the project does not have a project leader assigned

  Scenario: Successfully add project leader to project
    When the user "HUBU" is assigned as project leader to the project
    Then the project has the project leader "HUBU" assigned

  Scenario: Add non-existing user as project leader to project
    Given the user "HUBU" is removed from the system
    When the user "HUBU" is assigned as project leader to the project
    Then the error message "User does not exist" is given

  Scenario: Add project leader to non-existing project
    Given the project is deleted
    When the user "HUBU" is assigned as project leader to the project
    Then the error message "Project does not exist" is given

  Scenario: Successfully replace project leader
    Given the user "HUBU" is assigned as project leader to the project
    And the project has the project leader "HUBU" assigned
    When there is a user with the initials "TEST"
    And the user "TEST" is assigned as project leader to the project
    Then the project has the project leader "TEST" assigned

  Scenario: Successfully remove project leader
    Given the user "HUBU" is assigned as project leader to the project
    And the project has the project leader "HUBU" assigned
    When the project leader is removed from the project
    Then the project has no project leader

  Scenario: Remove a non-existing project leader from project
    Given the user "HUBU" is assigned as project leader to the project
    And the project has the project leader "HUBU" assigned
    When the project leader is removed from the project
    And the project leader is removed from the project
    Then the error message "Project leader does not exist" is given

  Scenario: Remove a project leader from a non-existing project
    Given the user "HUBU" is assigned as project leader to the project
    And the project has the project leader "HUBU" assigned
    When the project is deleted
    And the project leader is removed from the project
    Then the error message "Project does not exist" is given