Feature: Create Project
    Description: Create a new project
    Actor: User

Scenario: Successfully create a new project
    Given the year is "2022"
    When a project is created
    Then there is a project with ID "220001"
    And there are no activities in the project

Scenario: Successfully set project name
    Given a project is registered in the system
    When the project is renamed to "niceProject"
    Then the project has the name "niceProject"

Scenario: Successfully set startDate
    Given a project is registered in the system
    When the startDate is set to year 2022 month 4 date 20
    Then the startDate is year 2022 month 4 date 20