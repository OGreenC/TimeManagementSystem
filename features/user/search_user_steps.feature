# Author: Victor Hyltoft (s214964)

Feature: Search for a user

  Background: There are users in the system
    Given these users with initials are registered in the system
      | HUBU | HYLE | OLI |

  Scenario: Search for a specific user in the system
    When I search for the text "HUBU"
    Then I find the users with initials
      | HUBU |

  Scenario: Search for a user in the system
    When I search for the text "H"
    Then I find the users with initials
      | HUBU | HYLE |

  Scenario: Search for a user in the system with lower case search
    When I search for the text "h"
    Then I find the users with initials
      | HUBU | HYLE |

  Scenario: Search for a non-existing user in the system
    When I search for the text "UNKN"
    Then I find the users with initials
      | |