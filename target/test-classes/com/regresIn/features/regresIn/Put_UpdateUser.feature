Feature: update user

  Background:
    Given url regresIn
    * def createNewUserResponseBody = read('classpath:com/regresIn/data/regresIn/createNewUserRequestBody.json')
    * def fakeName = dataGenerator.getRandomName
    * createNewUserResponseBody.name = fakeName
    * print createNewUserResponseBody
    * call read('classpath:com/regresIn/data/regresIn/UserInit.feature')

    Scenario: user can update a user
      And path "/api/users/2"
      And header Authorization = customerToken
      And request createNewUserResponseBody
      When method POST
      Then status 201
      Then match response.name contains createNewUserResponseBody.name
