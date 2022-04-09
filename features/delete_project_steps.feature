Feature: Delete Project
    Description: Delete a project
    Actor: User

Scenario: Successfully delete an empty project
    Given a project is registered in the system
    And there are no activities in the project
    When the project is deleted
    Then the project does not exist

Scenario: Successfully delete a project with activities
    Given a project is registered in the system
    And an activity is registered to the project
    And an activity is registered to the project
    And the project has activities
    When the project is deleted
    Then the project does not exist
    And the activities does not exist

Scenario: Delete a non-existing project
    Given a project is registered in the system
    When the project is deleted
    And the project is deleted
    Then the error message "Project does not exists" is given