Feature: Create Project
    Description: Create a new project
    Actor: User

Scenario: Successfully create a new project
    Given the year is "2022"
    And no projects have been created
    When a project is registered in the system
    Then there is a project with ID "220001"
    And there are no activities in the project


