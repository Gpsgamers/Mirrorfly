@caller
Feature: Caller : login the application

  @register
  Scenario: Caller : login the application
    Given Caller : Navigate to register page
    When Caller : Enter the number on the number field
    And Caller : click on the register button
    Then Caller : page should be navigating to the home page