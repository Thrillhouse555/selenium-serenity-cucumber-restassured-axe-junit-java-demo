Feature: Login and Dashboard functionality
  As a registered user
  I want to log in to the dashboard
  So that I can view and verify dashboard content without modifying data

  Background:
    Given I am on the login page

  @smoke @login @api
  Scenario: Successful login
    When I enter valid credentials
    And I click the sign in button
    Then I should be redirected to the dashboard
    And I should see the welcome message
    And the login API should return status 200
    And the profile API should confirm I am authenticated

  @login
  Scenario: Login page elements are displayed
    Then I should see the email field
    And I should see the password field
    And I should see the sign in button
    And the page should have no critical accessibility violations

  @login
  Scenario: Login with invalid email format
    When I enter "invalid-email" in the login email field
    And I enter valid password
    And I click the sign in button
    Then I should see email validation error

  @login @api
  Scenario: Login with incorrect credentials
    When I enter "wrong@example.com" in the login email field
    And I enter "wrongpassword" in the password field
    And I click the sign in button
    Then I should see an authentication error
    And the login API should return status 401

  @smoke @dashboard @api
  Scenario: View dashboard after login
    When I log in with valid credentials
    Then I should be on the dashboard page
    And I should see the profile text form
    And I should see the links management section
    And the links API should return active links
    And the page should have no critical accessibility violations

  @dashboard
  Scenario: Dashboard displays existing links (read-only)
    When I log in with valid credentials
    Then I should be on the dashboard page
    And I should see the manage links section
    And the links list should be visible

  @dashboard
  Scenario: Logout from dashboard
    When I log in with valid credentials
    And I click the logout link
    Then I should be redirected to the login page
