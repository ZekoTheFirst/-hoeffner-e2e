Feature: Register customer

  Background: I register a new customer
    Given I load the register page
    And I accept cookies on the page
    And I see the gender dropdown menu
    And I see the first name input field
    And I see the last name input field
    And I see the email address input field
    And I see the password input field
    And I see the repeat password input field

  Scenario Outline: using non valid input fields
    When I select "<gender>" in the gender dropdown menu
    And I enter "<first name>" in the first name input field
    And I enter "<last name>" in the last name input field
    And I enter "<email>" in the email input field
    And I enter "<password>" in the password input field
    And I enter "<repeat password>" in the repeat password input field
    And I click the Further button
    Then the following "<warnings>" is presented to the customer

    Examples:
      | gender       | first name | last name | email                    | password  | repeat password | warnings                       |
      | Frau         | Sune       | Hjort     | sune.hjort@hotmail.com   | pass      | pass            | password-error                 |
      | Herr         | Klas       | Hjort     | klas.hjort@hotmail.com   | pA8#      | pA8#            | password-error                 |
      | Keine Angabe | Pär        | Karlsson  | par.karlsson@hotmail.com | pA8#      | pA8¤            | password-error,password2-error |
      | Frau         | Linda      | Wester    | linda.wester@hotmail.com | pA8#lkz=e | pA8#lkz=r       | password2-error                |
      | Frau         |            | Wester    | linda.wester@hotmail.com | pA8#lkz=e | pA8#lkz=e       | firstName-error                |
      | Frau         | Linda      |           | linda.wester@hotmail.com | pA8#lkz=e | pA8#lkz=e       | lastName-error                 |
      | Frau         | Linda      | Wester    | linda.westerhotmail.com  | pA8#lkz=e | pA8#lkz=e       | email-error                    |
      |              | Linda      | Wester    | linda.wester@hotmail.com | pA8#lkz=e | pA8#lkz=e       | salutation-error               |

  Scenario: without accepting terms and conditions and privacy policy
    When I select "Herr" in the gender dropdown menu
    And I enter "Johan" in the first name input field
    And I enter "Svensson" in the last name input field
    And I enter "johan.svensson@hotmail.com" in the email input field
    And I enter "pA8#lkz=e" in the password input field
    And I enter "pA8#lkz=e" in the repeat password input field
    And I dont select to agree with terms and conditions
    And I click the Further button
    Then I am promted to accept the terms and conditions

  Scenario: valid input fields and accepting terms and conditions and privacy policy
    When I select "Frau" in the gender dropdown menu
    And I enter a random firstname in the first name input field
    And I enter a random lastname in the last name input field
    And I enter a random email address in the email input field
    And I enter "pA8#lkz=e" in the password input field
    And I enter "pA8#lkz=e" in the repeat password input field
    And I select to agree with terms and conditions
    And I click the Further button
    And I am redirected to hoeffner.de
    And I can see the user account icon
    And I click the user account icon
    Then the welcome message contains the newly created user first and lastname

  Scenario: create a user that already exists
  //Testscenario remains to be done, dont want to create a lot of fake users the Hoeffner database.