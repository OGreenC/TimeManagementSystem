Feature: Search for a user

  Background: There are users in the system
    Given these users with initials are registered in the system
      | hubu | hyle | oli |

  Scenario: Search for a specific user in the system
    When I search for the text "hubu"
    Then I find the users with initials
      | hubu |

  Scenario: Search for a user in the system
    When I search for the text "h"
    Then I find the users with initials
      | hubu | hyle |

  Scenario: Search for a non-existing user in the system
    When I search for the text "unkn"
    Then I find the users with initials
      | |