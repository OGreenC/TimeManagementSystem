Feature: Edit Project Properties
    Description: Edit project properties
    Actor: User

Scenario: Successfully set project name
    Given a project is registered in the system
    When the project is renamed to "niceProject"
    Then the project has the name "niceProject"

Scenario: Successfully set startDate
    Given a project is registered in the system
    When the startDate is set to year 2022 month 4 date 20
    Then the startDate is year 2022 month 4 date 20

Scenario: Successfully add project leader to project
    Given a project is registered in the system
    And there is a user with the initials "hubu"
    And the project does not have a project leader assigned
    When the project leader "hubu" is assigned to the project
    Then the project has the project leader "hubu" assigned

Scenario: Unsuccessfully assigning another project leader
    Given a project is registered in the system
    And there is a user with the initials "hubu"
    And the project does not have a project leader assigned
    When the project leader "hubu" is assigned to the project
    And the project leader "hubu" is assigned to the project
    Then the error message "Only one project leader can be assigned per project" is given

Scenario: Successfully remove project leader
    Given a project is registered in the system
    And there is a user with the initials "hubu"
    And the project does not have a project leader assigned
    And the project leader "hubu" is assigned to the project
    And the project has the project leader "hubu" assigned
    When the project leader is removed from the project
    Then the project has no project leader

Scenario: Remove a non-existing project leader from project
    Given a project is registered in the system
    And there is a user with the initials "hubu"
    And the project does not have a project leader assigned
    And the project leader "hubu" is assigned to the project
    And the project has the project leader "hubu" assigned
    When the project leader is removed from the project
    And the project leader is removed from the project
    Then the error message "Project leader does not exist" is given
