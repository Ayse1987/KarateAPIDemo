Feature: Login user with valid email and valid password

  Background:
    * header Content-Type = 'application/json'
    * url regresIn

  @login
  Scenario: Given valid username and password, one should get an authorization token
    * path "api/register"
    And request
    """
    {
    "email": "#(email)",
    "password": "#(password)"
}
    """
    When method POST
    Then status 200
    * def token = response.token

