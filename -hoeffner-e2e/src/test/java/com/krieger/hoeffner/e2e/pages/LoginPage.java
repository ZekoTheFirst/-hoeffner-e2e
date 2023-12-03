package com.krieger.hoeffner.e2e.pages;

import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.support.TestConfig;
import com.krieger.hoeffner.e2e.support.WebDriverSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class LoginPage {
    public static final String LOGIN_PATH = "/login";

    private final TestConfig config;
    private final WebDriverSupport support;

    private final By byNewsletterInputField = By.id("email");
    private final By bySubmitButton = By.id("newsletterFormSubmitBtn");
    private final By byNewsletterConfirmationText = By.cssSelector(".footerNewsletter__confirmation");
    private final By byAcceptCookieButton = By.cssSelector(".consentForm__acceptButton");

    private final By byForgottenPasswordLink = By.xpath("//a[@class='existingAccount__forgotten']");

    public void load() {
        support.getWebDriver().get(config.getUrl(LOGIN_PATH));
    }

    public void clickAcceptCookieButton() {
        support.getWebDriver().findElement(byAcceptCookieButton).click();
    }

    public WebElement getNewsletterInputField() {
        return support.getWebDriver().findElement(byNewsletterInputField);
    }

    public void setEmail(String email) {
        WebElement element = support.getWebDriver().findElement(byNewsletterInputField);
        element.clear();
        element.sendKeys(email);
    }

    public void clickSendButton() {
        support.getWebDriver().findElement(bySubmitButton).click();
    }

    public WebElement getNewsletterConfirmationTextElement() {

        new FluentWait<>(support.getWebDriver())
                .withTimeout(ofSeconds(5))
                .until(visibilityOfElementLocated(byNewsletterConfirmationText));

        return support.getWebDriver().findElement(byNewsletterConfirmationText);
    }

    public void clickForgottenPasswordLink() {
        support.getWebDriver().findElement(byForgottenPasswordLink).click();
    }

    public void waitForForgottenPasswordLink() {
        new WebDriverWait(support.getWebDriver(), 1).until(ExpectedConditions.elementToBeClickable(byForgottenPasswordLink));
    }

    public String getCurrentUrl() {
        return support.getWebDriver().getCurrentUrl();
    }
}
