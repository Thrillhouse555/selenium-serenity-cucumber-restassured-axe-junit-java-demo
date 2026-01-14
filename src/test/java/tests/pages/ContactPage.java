package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

public class ContactPage extends PageObject {

    @FindBy(id = "contactForm")
    private WebElementFacade contactForm;

    @FindBy(id = "name")
    private WebElementFacade nameField;

    @FindBy(id = "email")
    private WebElementFacade emailField;

    @FindBy(id = "message")
    private WebElementFacade messageField;

    @FindBy(css = "#contactForm button[type='submit']")
    private WebElementFacade submitButton;

    @FindBy(id = "contactResult")
    private WebElementFacade resultMessage;

    public void openPage() {
        getDriver().get(System.getProperty("webdriver.base.url", "https://georgeansell.co.uk") + "/contact.html");
    }

    public boolean isContactFormDisplayed() {
        return contactForm.isCurrentlyVisible();
    }

    public boolean isNameFieldVisible() {
        return nameField.isCurrentlyVisible();
    }

    public boolean isEmailFieldVisible() {
        return emailField.isCurrentlyVisible();
    }

    public boolean isMessageFieldVisible() {
        return messageField.isCurrentlyVisible();
    }

    public boolean isSubmitButtonVisible() {
        return submitButton.isCurrentlyVisible();
    }

    public void enterName(String name) {
        nameField.type(name);
    }

    public void enterEmail(String email) {
        emailField.type(email);
    }

    public void enterMessage(String message) {
        messageField.type(message);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public boolean isSuccessMessageDisplayed() {
        return resultMessage.isVisible() && 
               resultMessage.getText().contains("Message sent") ||
               resultMessage.getCssValue("color").contains("green");
    }

    public boolean areValidationErrorsDisplayed() {
        // Check for HTML5 validation or custom error messages
        return !nameField.getAttribute("validationMessage").isEmpty() ||
               !emailField.getAttribute("validationMessage").isEmpty() ||
               !messageField.getAttribute("validationMessage").isEmpty();
    }
}
