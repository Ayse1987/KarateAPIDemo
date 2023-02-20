Feature: user list
  Background:
    Given url regresIn

    Scenario: user should be able to get user list
      And path "/api/users"
      And param page = "2"
      When method GET
      Then status 200

