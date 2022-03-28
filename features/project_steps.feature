Feature: Create Project
    Description: Create a new project
    Actor: User

Scenario: Successfully create a new project
    Given the year is "2022"
    When a project is created
    Then there is a project with ID "220001"
    And there are no activities in the project

