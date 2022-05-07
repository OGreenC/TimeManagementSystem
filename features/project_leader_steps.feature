Feature: Edit Project Leader Properties
  Description: Edit project leader properties
  Actor: User

  Background: Setup project and user
    Given a project is registered in the system
    And there is a user with the initials "hubu"
    And the project does not have a project leader assigned

  Scenario: Successfully add project leader to project
    When the user "hubu" is assigned as project leader to the project
    Then the project has the project leader "hubu" assigned

  Scenario: Successfully replace project leader
    Given the user "hubu" is assigned as project leader to the project
    And the project has the project leader "hubu" assigned
    When there is a user with the initials "test"
    And the user "test" is assigned as project leader to the project
    Then the project has the project leader "test" assigned

  Scenario: Successfully remove project leader
    Given the user "hubu" is assigned as project leader to the project
    And the project has the project leader "hubu" assigned
    When the project leader is removed from the project
    Then the project has no project leader

  Scenario: Remove a non-existing project leader from project
    Given the user "hubu" is assigned as project leader to the project
    And the project has the project leader "hubu" assigned
    When the project leader is removed from the project
    And the project leader is removed from the project
    Then the error message "Project leader does not exist" is given