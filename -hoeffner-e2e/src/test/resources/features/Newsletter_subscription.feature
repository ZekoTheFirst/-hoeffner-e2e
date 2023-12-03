Feature: Newsletter subscription

  Background: I subscribe to a newsletter
    Given I load the login page
    And I accept cookies on the login page
    And I see the newsletter subscription input field
    And I enter the email for "user1" in the newsletter subscription input field
    When I click on the Send button

  Scenario: My newsletter subscription is in progress
    Then I see a confirmation message that my newsletter subscription is in progress
