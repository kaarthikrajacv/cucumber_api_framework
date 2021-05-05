
Feature: Weather API automation Testing

  Scenario: GET CITY API and TEST RESULT
    Given I hit weather API with valid data
    When I hit a GET API
    Then Validate API response
  	

    