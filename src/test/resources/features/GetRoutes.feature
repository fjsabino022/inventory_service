Feature: Get routes

  Scenario: Get information from routes
    Given I am connected with google cloud
    When I want to get the routes information
    Then Routes are returned