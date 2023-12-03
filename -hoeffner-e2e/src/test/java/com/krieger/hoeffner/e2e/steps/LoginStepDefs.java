package com.krieger.hoeffner.e2e.steps;

import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.pages.LoginPage;
import com.krieger.hoeffner.e2e.support.TestConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class LoginStepDefs {
    private final LoginPage loginPage;
    private final TestConfig config;

    @Given("I load the login page")
    public void iLoadTheLoginPage() {
        loginPage.load();
    }

    @And("I see the newsletter subscription input field")
    public void iSeeTheNewsletterSubscriptionInputField() {
        WebElement newsletterInputField = loginPage.getNewsletterInputField();
        assertThat(newsletterInputField.isDisplayed(), equalTo(true));
    }

    @And("I accept cookies on the login page")
    public void iAcceptCookiesOnTheLoginPage() {
        loginPage.clickAcceptCookieButton();
    }

    @And("I can see forgot password link")
    public void iCanSeeForgotPasswordLink() {
        loginPage.waitForForgottenPasswordLink();
    }

    @When("I click on a forgot password link")
    public void iClickOnAForgotPasswordLink() {
        loginPage.clickForgottenPasswordLink();
    }

    @And("I enter the email for {string} in the newsletter subscription input field")
    public void iEnterTheEmailForInTheNewsletterSubscriptionInputField(String userId) {
        loginPage.setEmail(config.email(userId));
    }

    @When("I click on the Send button")
    public void iClickOnTheSendButton() {
        loginPage.clickSendButton();
    }

    @Then("I see a confirmation message that my newsletter subscription is in progress")
    public void iSeeAConfirmationMessageThatMyNewsletterSubscriptionIsInProgress() {
        WebElement newletterConfirmationTextElement = loginPage.getNewsletterConfirmationTextElement();

        assertThat(newletterConfirmationTextElement.getText(), equalTo("Nur noch ein Klick und Sie haben es geschafft!\n" +
                "Bitte bestätigen Sie jetzt Ihre Anmeldung über den Klick auf \"Anmeldung bestätigen\" in der E-Mail, die soeben an Sie versandt wurde."));

    }

    @Then("I am redirected to forgot password form")
    public void iAmRedirectedToForgotPasswordForm() {
        assertThat(loginPage.getCurrentUrl(), equalTo("https://www.hoeffner.de/passwort/vergessen/login"));
    }
}
