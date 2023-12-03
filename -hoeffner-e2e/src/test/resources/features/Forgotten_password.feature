Feature: Forgotten password

  Background: I forgot my password
    Given I load the login page
    And I accept cookies on the login page

  Scenario: Remind me my password
    And I can see forgot password link
    When I click on a forgot password link
    Then I am redirected to forgot password form
