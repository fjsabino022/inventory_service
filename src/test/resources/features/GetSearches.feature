Feature: Get searches done

  Scenario: Get searches from logs
    Given I am authenticated
    When I want to get the logs
    Then Logs are returned