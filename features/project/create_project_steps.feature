# Author: Victor Hyltoft (s214964)

Feature: Create Project
  Description: Create a new project
  Actor: User

  Scenario: Successfully create a new project
    Given the year is "2022"
    And no projects have been created
    When a project is registered in the system
    Then there is a project with ID "220001"
    And there are no activities in the project

  Scenario: Successfully create two projects
    Given the year is "2022"
    And no projects have been created
    When a project is registered in the system
    And a project is registered in the system
    Then there is a project with ID "220001"
    And there are no activities in the project
    And there is a project with ID "220002"
    And there are no activities in the project

  Scenario: Successfully create a new project in another year
    Given the year is "2020"
    And no projects have been created
    When a project is registered in the system
    Then there is a project with ID "200001"
    And there are no activities in the project

  Scenario: Successfully create a new project after some years
    Given the year is "2022"
    And no projects have been created
    When 2 years has passed
    And a project is registered in the system
    Then there is a project with ID "240001"
    And there are no activities in the project


