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
