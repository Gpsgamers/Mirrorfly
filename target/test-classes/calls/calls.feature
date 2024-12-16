@calls
Feature: calls feature testing

  @call_connection
  Scenario: Caller from call log and attend the call
    When caller : click on the call log btn from the recent chat screen
    Then call log screen is displayed
    When click on the floating call icon
    Then audio call option is displayed
    When click on the audio call icon
    Then contacts screen is displayed
    When select any of the contacts and click on the call now button
    Then call is triggered to receiver
    When Receiver attend the call
    Then the call is connected to both caller and receiver

  @call_reconnection
  Scenario: Caller goes to reconnection and comes back to online in 30 second
    When caller goes to offline
    Then caller goes to reconnection state
    When caller connects the call after 30 second
    Then caller:reconnection text is removed for both caller and receiver

  @call_reconnection
  Scenario: Receiver goes to reconnection and comes back to online in 30 second
    When receiver goes to offline
    Then receiver goes to reconnection state
    When receiver connects the call after 30 second
    Then receiver:reconnection text is removed for both caller and receiver

  @call_reconnection
  Scenario: Both caller and receiver disconnected when caller disconnected in reconnection timeout
    When caller goes to offline for 1 min
    Then caller is disconnected for both caller and receiver
