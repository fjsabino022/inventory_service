Feature: Send a message for alerting

  Scenario: Send a message successfully
    Given I am an user
    And I have my <message>
    And I have the <destination> of my message
    When I send the message
    Then The message is sent correctly

