@receiver
Feature: Receiver : login the application

  @register
  Scenario: Receiver : login the application
    Given Receiver : Navigate to register page
    When Receiver : Enter the number on the number field
    And Receiver : click on the register button
    Then Receiver : page should be navigating to the home page