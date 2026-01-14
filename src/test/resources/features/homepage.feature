Feature: Homepage functionality
  As a visitor
  I want to view the homepage
  So that I can see profile information and navigate the site

  @smoke
  Scenario: View homepage
    Given I am on the homepage
    Then I should see the profile name "George Ansell"
    And I should see the profile picture
    And the homepage should have no critical accessibility violations

  @smoke
  Scenario: Navigate to contact page
    Given I am on the homepage
    When I click the contact icon
    Then I should be on the contact page
    And I should see the contact form

  Scenario: View QR code
    Given I am on the homepage
    When I click the profile picture
    Then I should be on the QR code page
    And I should see the QR code image

  Scenario: Verify social links
    Given I am on the homepage
    Then I should see the LinkedIn link
    And I should see the GitHub link
