package uk.georgeansell.tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import uk.georgeansell.tests.accessibility.AccessibilityHelper;
import uk.georgeansell.tests.pages.ContactPage;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactPageSteps {

    @Steps
    ContactPage contactPage;

    @Given("I am on the contact page")
    public void i_am_on_the_contact_page() {
        contactPage.openPage();
    }

    @Then("I should be on the contact page")
    public void i_should_be_on_the_contact_page() {
        assertThat(contactPage.isContactFormDisplayed()).isTrue();
    }

    @Then("I should see the contact form")
    public void i_should_see_the_contact_form() {
        assertThat(contactPage.isContactFormDisplayed()).isTrue();
    }

    @Then("the name field should be visible")
    public void the_name_field_should_be_visible() {
        assertThat(contactPage.isNameFieldVisible()).isTrue();
    }

    @Then("the email field should be visible")
    public void the_email_field_should_be_visible() {
        assertThat(contactPage.isEmailFieldVisible()).isTrue();
    }

    @Then("the message field should be visible")
    public void the_message_field_should_be_visible() {
        assertThat(contactPage.isMessageFieldVisible()).isTrue();
    }

    @Then("the submit button should be visible")
    public void the_submit_button_should_be_visible() {
        assertThat(contactPage.isSubmitButtonVisible()).isTrue();
    }

    @When("I enter {string} in the name field")
    public void i_enter_in_the_name_field(String name) {
        contactPage.enterName(name);
    }

    @When("I enter {string} in the email field")
    public void i_enter_in_the_email_field(String email) {
        contactPage.enterEmail(email);
    }

    @When("I enter {string} in the message field")
    public void i_enter_in_the_message_field(String message) {
        contactPage.enterMessage(message);
    }

    @When("I click the submit button")
    public void i_click_the_submit_button() {
        contactPage.clickSubmit();
    }

    @Then("I should see a success message")
    public void i_should_see_a_success_message() {
        contactPage.waitForTextToAppear("Message sent", 5000);
        assertThat(contactPage.isSuccessMessageDisplayed()).isTrue();
    }

    @Then("the form should show validation errors")
    public void the_form_should_show_validation_errors() {
        assertThat(contactPage.areValidationErrorsDisplayed()).isTrue();
    }

    // Accessibility Steps

    @Then("the contact page should have no accessibility violations")
    public void the_contact_page_should_have_no_accessibility_violations() {
        AccessibilityHelper.assertNoAccessibilityViolations(contactPage.getDriver());
    }

    @Then("the contact page should have no critical accessibility violations")
    public void the_contact_page_should_have_no_critical_accessibility_violations() {
        AccessibilityHelper.assertNoSeriousAccessibilityViolations(contactPage.getDriver());
    }
}
