Feature: Send a message for alerting

  Scenario Outline: Send a message successfully
    Given I have my <message>
    And I have the <destination> of my message
    When I send the message
    Then The message is sent correctly

    Examples:
      | message         | destination                      |
      | This is my test | #provider-alerts-temp-supply-ops |