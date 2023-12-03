package com.krieger.hoeffner.e2e.pages;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import com.krieger.hoeffner.e2e.support.TestConfig;
import com.krieger.hoeffner.e2e.support.WebDriverSupport;
import jdk.jfr.Timespan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class RegisterPage {
    public static final String REGISTER_PATH = "/registrierung";

    private final TestConfig config;
    private final WebDriverSupport support;


    private final By byAcceptCookieButton = By.cssSelector(".consentForm__acceptButton");
    private final By byGenderDropdownMenu = By.id("salutation");
    private final By byFirstNameInputField = By.id("firstName");
    private final By byLastNameInputField = By.id("lastName");
    private final By byEmailAddressInputField = By.id("email");
    private final By byPasswordInputField = By.id("password");
    private final By byRepeatPasswordInputField = By.id("password2");
    private final By byTermsAndConditionsCheckbox = By.xpath("//*[@id=\"register-form\"]/div/div[12]/div/div/span[1]");
    private final By byFurtherButton = By.id("register-submit");
    private final By byUserAccountButton = By.className("headerElement__link--login");
    private final By byAccountWelcomeMessage = By.className("titleHeadline");


    public void load() {
        support.getWebDriver().get(config.getUrl(REGISTER_PATH));
    }

    public void clickAcceptCookieButton() {
        support.getWebDriver().findElement(byAcceptCookieButton).click();
    }
    public WebElement getGenderDropdownMenu() {
        return support.getWebDriver().findElement(byGenderDropdownMenu);
    }
    public WebElement getFirstNameInputField() {
        return support.getWebDriver().findElement(byFirstNameInputField);
    }
    public WebElement getLastNameInputField() {
        return support.getWebDriver().findElement(byLastNameInputField);
    }
    public WebElement getEmailAddressInputField() {
        return support.getWebDriver().findElement(byEmailAddressInputField);
    }
    public WebElement getPasswordInputField() {
        return support.getWebDriver().findElement(byPasswordInputField);
    }
    public WebElement getRepeatPasswordInputField() {
        return support.getWebDriver().findElement(byRepeatPasswordInputField);
    }
    public WebElement getUserAccountButton() {
        return support.getWebDriver().findElement(byUserAccountButton);
    }

    public void selectGender(String gender){
        if(gender != null && !gender.isEmpty()){
            Select genderDropdown = new Select(support.getWebDriver().findElement(byGenderDropdownMenu));
            genderDropdown.selectByVisibleText(gender);
        }
    }
    public void enterFirstName(String firstName){
        support.getWebDriver().findElement(byFirstNameInputField).sendKeys((firstName));
    }

    public void enterLastName(String lastName){
        support.getWebDriver().findElement(byLastNameInputField).sendKeys((lastName));
    }

    public void enterEmail(String email){
        support.getWebDriver().findElement(byEmailAddressInputField).sendKeys((email));
    }

    public void enterPassword(String password){
        support.getWebDriver().findElement(byPasswordInputField).sendKeys((password));
    }

    public void enterRepeatPassword(String repeatPassword){
        support.getWebDriver().findElement(byRepeatPasswordInputField).sendKeys((repeatPassword));
    }

    public boolean getTermsAndConditionsCheckboxState(){
        String classes = support.getWebDriver().findElement(byTermsAndConditionsCheckbox).getAttribute("class");
        return classes.contains(("checkbox__checkbox--checked"));
    }

    public void clickFurtherButton(){
        support.getWebDriver().findElement(byFurtherButton).click();
    }

    public List<WebElement> getAllCheckBoxWarning(){
        return support.getWebDriver().findElements(By.className("accountNew__agbBoxError"));
    }

    public void selectTermsAndConditionsCheckbox(){
        support.getWebDriver().findElement(byTermsAndConditionsCheckbox).click();
    }

    public void waitForRedirect(String url, Integer timeOut) {
        WebDriverWait wait = new WebDriverWait(support.getWebDriver(), timeOut);
        wait.until(ExpectedConditions.urlToBe(url));
    }

    public void clickUserAccountButton(){
        support.getWebDriver().findElement(byUserAccountButton).click();
    }

    public String getWelcomeMessage(){
        return support.getWebDriver().findElement(byAccountWelcomeMessage).getText();
    }

    public List<WebElement> getAllInputFieldWarnings(){
        List<WebElement> warnings = support.getWebDriver().findElements(By.className("formInput__error"));
        return warnings;
    }
}
