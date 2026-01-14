package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(id = "email")
    private WebElementFacade emailField;

    @FindBy(id = "password")
    private WebElementFacade passwordField;

    @FindBy(id = "submitBtn")
    private WebElementFacade submitButton;

    @FindBy(id = "emailError")
    private WebElementFacade emailError;

    @FindBy(id = "passwordError")
    private WebElementFacade passwordError;

    @FindBy(id = "formMessage")
    private WebElementFacade formMessage;

    public void openPage() {
        open();
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.type(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.type(password);
    }

    public void clickSignIn() {
        submitButton.click();
    }

    public boolean isEmailFieldVisible() {
        return emailField.isCurrentlyVisible();
    }

    public boolean isPasswordFieldVisible() {
        return passwordField.isCurrentlyVisible();
    }

    public boolean isSubmitButtonVisible() {
        return submitButton.isCurrentlyVisible();
    }

    public String getEmailErrorText() {
        return emailError.getText();
    }

    public String getFormMessageText() {
        return formMessage.getText();
    }

    public boolean isEmailErrorDisplayed() {
        return emailError.isCurrentlyVisible() && !emailError.getText().isEmpty();
    }

    public boolean isFormMessageDisplayed() {
        return formMessage.isCurrentlyVisible() && !formMessage.getText().isEmpty();
    }
}
