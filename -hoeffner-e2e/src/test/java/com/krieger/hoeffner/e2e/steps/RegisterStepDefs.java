package com.krieger.hoeffner.e2e.steps;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.pages.RegisterPage;
import com.krieger.hoeffner.e2e.support.TestConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class RegisterStepDefs {
    private final RegisterPage registerPage;
    private final TestConfig config;

    //If time, use TestContext instead
    private static String FirstName;
    private static String LastName;

    @Given("I load the register page")
    public void iLoadTheRegisterPage() {
        registerPage.load();
    }

    @And("I accept cookies on the page")
    public void iAcceptCookiesOnThePage() {
        registerPage.clickAcceptCookieButton();
    }

    @And("I see the gender dropdown menu")
    public void iSeeTheGenderDropdownMenu() {
        WebElement genderDropdownMenu = registerPage.getGenderDropdownMenu();
        assertThat(genderDropdownMenu.isDisplayed(), equalTo(true));
    }

    @And("I see the first name input field")
    public void iSeeTheFirstNameInputField() {
        WebElement firstNameInputField = registerPage.getFirstNameInputField();
        assertThat(firstNameInputField.isDisplayed(), equalTo(true));
    }

    @And("I see the last name input field")
    public void iSeeTheLastNameInputField() {
        WebElement lastNameInputField = registerPage.getLastNameInputField();
        assertThat(lastNameInputField.isDisplayed(), equalTo(true));
    }

    @And("I see the email address input field")
    public void iSeeTheEmailAddressInputField() {
        WebElement emailAddressInputField = registerPage.getEmailAddressInputField();
        assertThat(emailAddressInputField.isDisplayed(), equalTo(true));
    }

    @And("I see the password input field")
    public void iSeeThePasswordInputField() {
        WebElement passwordInputField = registerPage.getPasswordInputField();
        assertThat(passwordInputField.isDisplayed(), equalTo(true));
    }

    @And("I see the repeat password input field")
    public void iSeeTheRepeatPasswordInputField() {
        WebElement repeatPasswordInputField = registerPage.getRepeatPasswordInputField();
        assertThat(repeatPasswordInputField.isDisplayed(), equalTo(true));
    }

    @And("I enter {string} in the first name input field")
    public void iEnterInTheFirstNameInputField(String firstName) {
        registerPage.enterFirstName(firstName);
    }

    @And("I enter {string} in the last name input field")
    public void iEnterInTheLastNameInputField(String lastName) {
        registerPage.enterLastName(lastName);
    }

    @And("I enter {string} in the email input field")
    public void iEnterInTheEmailInputField(String email) {
        registerPage.enterEmail(email);
    }

    @And("I enter {string} in the password input field")
    public void iEnterInThePasswordInputField(String password) {
        registerPage.enterPassword(password);
    }

    @And("I enter {string} in the repeat password input field")
    public void iEnterInTheRepeatPasswordInputField(String repeatPassword) {
        registerPage.enterRepeatPassword(repeatPassword);
    }

    @And("I dont select to agree with terms and conditions")
    public void iDontSelectToAgreeWithTermsAndConditions() {
        assertThat(registerPage.getTermsAndConditionsCheckboxState(), equalTo((false)));
    }

    @And("I click the Further button")
    public void iClickTheFurtherButton() {
        registerPage.clickFurtherButton();
    }

    @And("I enter a random firstname in the first name input field")
    public void iEnterARandomFirstnameInTheFirstNameInputField() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        registerPage.enterFirstName(firstName);
        FirstName = firstName;
    }

    @And("I enter a random lastname in the last name input field")
    public void iEnterARandomLastnameInTheLastNameInputField() {
        Faker faker = new Faker();
        String lastName = faker.name().lastName();
        registerPage.enterLastName(lastName);
        LastName = lastName;
    }

    @And("I enter a random email address in the email input field")
    public void iEnterARandomEmailAddressInTheEmailInputField() {
        registerPage.enterEmail(FirstName+ "." + LastName+ "@mailz.com");
    }

    @And("I select to agree with terms and conditions")
    public void iSelectToAgreeWithTermsAndConditions() {
        registerPage.selectTermsAndConditionsCheckbox();
    }

    @And("I am redirected to hoeffner.de")
    public void iAmRedirectedToHoeffnerDe() {
        registerPage.waitForRedirect("https://www.hoeffner.de/", 5);
    }

    @And("I can see the user account icon")
    public void iCanSeeTheUserAccountIcon() {
        WebElement userAccountButton = registerPage.getUserAccountButton();
        assertThat(userAccountButton.isDisplayed(), equalTo(true));
    }

    @And("I click the user account icon")
    public void iClickTheUserAccountIcon() {
        registerPage.clickUserAccountButton();
    }

    @When("I select {string} in the gender dropdown menu")
    public void iSelectInTheGenderDropdownMenu(String gender) {
        registerPage.selectGender(gender);
    }

    @Then("I am promted to accept the terms and conditions")
    public void iAmPromtedToAcceptTheTermsAndConditions() {
        List<WebElement> warnings = registerPage.getAllCheckBoxWarning();
        assertThat(warnings.size(), equalTo(1));
        assertThat(warnings.get(0).getText(), equalTo("Bitte akzeptieren Sie die AGB und die Datenschutzbestimmungen"));
    }

    @Then("the welcome message contains the newly created user first and lastname")
    public void theWelcomeMessageContainsTheNewlyCreatedUserFirstAndLastname() {
        String expectedWelcomeMessage = "Hallo, " + FirstName + " " + LastName;
        assertThat(registerPage.getWelcomeMessage(), equalTo(expectedWelcomeMessage));
    }

    @Then("the following {string} is presented to the customer")
    public void theFollowingIsPresentedToTheCustomer(String expectedWarnings) {
        String[] expectedWarningsArray = expectedWarnings.split(",");

        Map<String, String> allWarningTypesAndText = new HashMap<>();
        allWarningTypesAndText.put("salutation-error", "Bitte geben Sie eine Anrede ein");
        allWarningTypesAndText.put("firstName-error", "Bitte geben Sie Ihren Vornamen ein");
        allWarningTypesAndText.put("lastName-error", "Bitte geben Sie Ihren Nachnamen ein");
        allWarningTypesAndText.put("email-error", "Bitte geben Sie eine gültige E-Mail-Adresse ein");
        allWarningTypesAndText.put("password-error", "Bitte verwenden Sie ein Passwort von mindestes 8 Zeichen mit mindestens einem Kleinbuchstaben, einem Großbuchstaben, einer Zahl und einem Sonderzeichen.");
        allWarningTypesAndText.put("password2-error", "Die Passwörter stimmen nicht überein");

        List<WebElement> warnings = registerPage.getAllInputFieldWarnings();

        assertThat(warnings.size(), equalTo(expectedWarningsArray.length));

        int i = 0;
        for(WebElement item : warnings){
            String warningType = item.getAttribute("id");
            String warningText = item.getText();
            assertThat(warningType, equalTo(expectedWarningsArray[i]));
            assertThat(warningText, equalTo(allWarningTypesAndText.get(expectedWarningsArray[i])));
            i++;
        }
    }
}
