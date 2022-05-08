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

  Scenario: Succesfully set finishDate
    Given a project is registered in the system
    When the finishDate is set to year 2022 month 4 date 20
    Then the finishDate is year 2022 month 4 date 20

  Scenario: Set startdate after finishdate
    Given a project is registered in the system
    And the finishDate is set to year 2022 month 4 date 20
    When the startDate is set to year 2022 month 5 date 21
    Then the error message "The finish date is before the start date" is given

  Scenario: Set finishdate after Startdate
    Given a project is registered in the system
    And the startDate is set to year 2022 month 5 date 21
    When the finishDate is set to year 2022 month 4 date 20
    Then the error message "The finish date is before the start date" is given