package uk.georgeansell.tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import uk.georgeansell.tests.accessibility.AccessibilityHelper;
import uk.georgeansell.tests.pages.HomePage;
import uk.georgeansell.tests.pages.ContactPage;
import uk.georgeansell.tests.pages.QRCodePage;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageSteps {

    @Steps
    HomePage homePage;

    @Steps
    ContactPage contactPage;

    @Steps
    QRCodePage qrCodePage;

    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {
        homePage.openPage();
    }

    @Then("I should see the profile name {string}")
    public void i_should_see_the_profile_name(String expectedName) {
        String actualName = homePage.getProfileName();
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Then("I should see the profile picture")
    public void i_should_see_the_profile_picture() {
        assertThat(homePage.isProfilePictureDisplayed()).isTrue();
    }

    @When("I click the contact icon")
    public void i_click_the_contact_icon() {
        homePage.clickContactIcon();
    }

    @When("I click the profile picture")
    public void i_click_the_profile_picture() {
        homePage.clickProfilePicture();
    }

    @Then("I should see the LinkedIn link")
    public void i_should_see_the_linkedin_link() {
        assertThat(homePage.isLinkedInLinkDisplayed()).isTrue();
    }

    @Then("I should see the GitHub link")
    public void i_should_see_the_github_link() {
        assertThat(homePage.isGitHubLinkDisplayed()).isTrue();
    }

    @Then("I should be on the QR code page")
    public void i_should_be_on_the_qr_code_page() {
        assertThat(qrCodePage.isOnQRCodePage()).isTrue();
    }

    @Then("I should see the QR code image")
    public void i_should_see_the_qr_code_image() {
        assertThat(qrCodePage.isQRCodeImageDisplayed()).isTrue();
    }

    // Accessibility Steps

    @Then("the homepage should have no accessibility violations")
    public void the_homepage_should_have_no_accessibility_violations() {
        AccessibilityHelper.assertNoAccessibilityViolations(homePage.getDriver());
    }

    @Then("the homepage should have no critical accessibility violations")
    public void the_homepage_should_have_no_critical_accessibility_violations() {
        AccessibilityHelper.assertNoSeriousAccessibilityViolations(homePage.getDriver());
    }
}
