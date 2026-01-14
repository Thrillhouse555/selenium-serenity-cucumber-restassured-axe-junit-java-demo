package uk.georgeansell.tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import uk.georgeansell.tests.accessibility.AccessibilityHelper;
import uk.georgeansell.tests.api.ApiClient;
import uk.georgeansell.tests.pages.DashboardPage;
import uk.georgeansell.tests.pages.LoginPage;
import io.github.cdimascio.dotenv.Dotenv;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    @Steps
    LoginPage loginPage;

    @Steps
    DashboardPage dashboardPage;

    private ApiClient apiClient = new ApiClient();
    private Response lastApiResponse;
    private String lastEnteredEmail;
    private String lastEnteredPassword;

    private static final Dotenv dotenv = Dotenv.configure()
            .directory(".")
            .ignoreIfMissing()
            .load();

    private String getTestEmail() {
        String email = dotenv.get("TEST_USER_EMAIL");
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("TEST_USER_EMAIL not configured in .env file");
        }
        return email;
    }

    private String getTestPassword() {
        String password = dotenv.get("TEST_USER_PASSWORD");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("TEST_USER_PASSWORD not configured in .env file");
        }
        return password;
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        loginPage.openUrl("https://georgeansell.co.uk/login.html");
    }

    @When("I enter valid credentials")
    public void i_enter_valid_credentials() {
        lastEnteredEmail = getTestEmail();
        lastEnteredPassword = getTestPassword();
        loginPage.enterEmail(lastEnteredEmail);
        loginPage.enterPassword(lastEnteredPassword);
    }

    @When("I enter {string} in the login email field")
    public void i_enter_in_the_login_email_field(String email) {
        lastEnteredEmail = email;
        loginPage.enterEmail(email);
    }

    @When("I enter {string} in the password field")
    public void i_enter_in_the_password_field(String password) {
        lastEnteredPassword = password;
        loginPage.enterPassword(password);
    }

    @When("I enter valid password")
    public void i_enter_valid_password() {
        lastEnteredPassword = getTestPassword();
        loginPage.enterPassword(lastEnteredPassword);
    }

    @When("I click the sign in button")
    public void i_click_the_sign_in_button() {
        loginPage.clickSignIn();
    }

    @When("I log in with valid credentials")
    public void i_log_in_with_valid_credentials() {
        loginPage.openUrl("https://georgeansell.co.uk/login.html");
        loginPage.enterEmail(getTestEmail());
        loginPage.enterPassword(getTestPassword());
        loginPage.clickSignIn();
        loginPage.waitFor(2).seconds();
    }

    @When("I click the logout link")
    public void i_click_the_logout_link() {
        dashboardPage.clickLogout();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        loginPage.waitFor(3).seconds();
        assertThat(loginPage.getDriver().getCurrentUrl()).contains("/dashboard.html");
    }

    @Then("I should be redirected to the login page")
    public void i_should_be_redirected_to_the_login_page() {
        loginPage.waitFor(2).seconds();
        assertThat(loginPage.getDriver().getCurrentUrl()).contains("/login.html");
    }

    @Then("I should see the welcome message")
    public void i_should_see_the_welcome_message() {
        assertThat(dashboardPage.isWelcomeMessageVisible()).isTrue();
        assertThat(dashboardPage.getWelcomeMessageText()).containsIgnoringCase("welcome");
    }

    @Then("I should see the email field")
    public void i_should_see_the_email_field() {
        assertThat(loginPage.isEmailFieldVisible()).isTrue();
    }

    @Then("I should see the password field")
    public void i_should_see_the_password_field() {
        assertThat(loginPage.isPasswordFieldVisible()).isTrue();
    }

    @Then("I should see the sign in button")
    public void i_should_see_the_sign_in_button() {
        assertThat(loginPage.isSubmitButtonVisible()).isTrue();
    }

    @Then("I should see email validation error")
    public void i_should_see_email_validation_error() {
        loginPage.waitFor(1).seconds();
        assertThat(loginPage.isEmailErrorDisplayed()).isTrue();
    }

    @Then("I should see an authentication error")
    public void i_should_see_an_authentication_error() {
        loginPage.waitFor(2).seconds();
        assertThat(loginPage.isFormMessageDisplayed()).isTrue();
    }

    @Then("I should be on the dashboard page")
    public void i_should_be_on_the_dashboard_page() {
        assertThat(dashboardPage.isOnDashboard()).isTrue();
    }

    @Then("I should see the profile text form")
    public void i_should_see_the_profile_text_form() {
        assertThat(dashboardPage.isProfileTextFormVisible()).isTrue();
    }

    @Then("I should see the links management section")
    public void i_should_see_the_links_management_section() {
        assertThat(dashboardPage.isLinksManagementVisible()).isTrue();
    }

    @Then("I should see the manage links section")
    public void i_should_see_the_manage_links_section() {
        assertThat(dashboardPage.isLinksManagementVisible()).isTrue();
    }

    @Then("the links list should be visible")
    public void the_links_list_should_be_visible() {
        dashboardPage.waitFor(1).seconds();
        assertThat(dashboardPage.isLinksListVisible()).isTrue();
    }

    // API Validation Steps

    @Then("the login API should return status {int}")
    public void the_login_api_should_return_status(int expectedStatus) {
        lastApiResponse = apiClient.login(lastEnteredEmail, lastEnteredPassword);
        assertThat(lastApiResponse.getStatusCode()).isEqualTo(expectedStatus);
    }

    @Then("the profile API should confirm I am authenticated")
    public void the_profile_api_should_confirm_i_am_authenticated() {
        // Extract auth cookie from browser and use it for API call
        String token = loginPage.getDriver().manage().getCookieNamed("token").getValue();
        apiClient.setAuthCookie(token);
        
        Response response = apiClient.getProfile();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("email")).isEqualTo(getTestEmail());
    }

    @Then("the links API should return active links")
    public void the_links_api_should_return_active_links() {
        Response response = apiClient.getLinks();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
    }

    // Accessibility Steps

    @Then("the page should have no accessibility violations")
    public void the_page_should_have_no_accessibility_violations() {
        AccessibilityHelper.assertNoAccessibilityViolations(loginPage.getDriver());
    }

    @Then("the page should have no critical accessibility violations")
    public void the_page_should_have_no_critical_accessibility_violations() {
        AccessibilityHelper.assertNoSeriousAccessibilityViolations(loginPage.getDriver());
    }

    @Then("I should see accessibility test results")
    public void i_should_see_accessibility_test_results() {
        AccessibilityHelper.logAccessibilityResults(loginPage.getDriver());
    }
}
