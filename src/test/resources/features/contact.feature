Feature: Contact form functionality
  As a visitor
  I want to submit a contact form
  So that I can send a message to George

  Background:
    Given I am on the contact page

  @smoke
  Scenario: Contact form is displayed
    Then I should see the contact form
    And the name field should be visible
    And the email field should be visible
    And the message field should be visible
    And the submit button should be visible
    And the contact page should have no critical accessibility violations

#   Scenario: Submit valid contact form
#     When I enter "Test User" in the name field
#     And I enter "test@example.com" in the email field
#     And I enter "This is a test message" in the message field
#     And I click the submit button
#     Then I should see a success message

  Scenario: Submit contact form with missing name
    When I enter "test@example.com" in the email field
    And I enter "This is a test message" in the message field
    And I click the submit button
    Then the form should show validation errors

  Scenario: Submit contact form with invalid email
    When I enter "Test User" in the name field
    And I enter "invalid-email" in the email field
    And I enter "This is a test message" in the message field
    And I click the submit button
    Then the form should show validation errors
