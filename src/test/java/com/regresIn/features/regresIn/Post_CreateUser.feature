Feature: create user

  Background:
    Given url regresIn
    * def createNewUserResponseBody = read('classpath:com/regresIn/data/regresIn/createNewUserRequestBody.json')
    #* def fakeName = dataGenerator.getRandomName
    #* createNewUserResponseBody.name = fakeName
    * print createNewUserResponseBody
    * call read('classpath:com/regresIn/data/regresIn/UserInit.feature')

  @Positive
  Scenario: user should be able to create a new user with valid credentials
    And path "/api/users"
    And header Authorization = customerToken
    And request createNewUserResponseBody
    When method POST
    Then status 201
    Then match response.name contains createNewUserResponseBody.name

  @Negative
  Scenario Outline: user can't create a new user with <invalid> customerToken
    And path "/api/users"
    And header Authorization = <customerToken>
    And request createNewUserResponseBody
    When method POST
    Then status 201
    Then match response.name contains createNewUserResponseBody.name
    Examples:
      | invalid | customerToken         |
      | invalid | inActiveCustomerToken |
      | invalid | expiredCustomerToken  |


  @Negative
  Scenario Outline: user should not be able to create user list with <invalid> name
    And path "/api/users"
    And header Authorization = customerToken
    * createNewUserResponseBody.name = <newName>
    And request createNewUserResponseBody
    When method POST
    Then status 201
    Then match response.name contains createNewUserResponseBody.name
    Examples:
      | invalid                  | newName                                                                                                      |
      | empty                    | ''                                                                                                           |
      | numbers as string        | '1234'                                                                                                       |
      | integer                  | 1234                                                                                                         |
      | longer than 35 character | 'agagagaggggggggggggggggggggggaggggggaiakkkkkkkkkkkkkkkkkkkkkkkkkkkalllllllllllllllllllllllllllakkkkkkkkkkk' |
      |                          | ',.;@'                                                                                                       |


  @Negative
  Scenario Outline: user should be able to create user with <invalid> job
    And path "/api/users"
    And header Authorization = customerToken
    * createNewUserResponseBody.name = <newName>
    And request createNewUserResponseBody
    When method POST
    Then status 201
    Then match response.name contains createNewUserResponseBody.name
    Examples:
      | invalid                  | newName                                                                                                      |
      | empty                    | ''                                                                                                           |
      | numbers as string        | '1234'                                                                                                       |
      | integer                  | 1234                                                                                                         |
      | longer than 35 character | 'agagagaggggggggggggggggggggggaggggggaiakkkkkkkkkkkkkkkkkkkkkkkkkkkalllllllllllllllllllllllllllakkkkkkkkkkk' |
      | special characters       | ',.;@'                                                                                                       |
