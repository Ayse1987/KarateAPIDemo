Feature: user credentials
  Scenario:
    * def customerLogin = read('classpath:com/regresIn/data/regresIn/CustomerLogin.json')
    * def loginCall = call read('classpath:com/regresIn/utilities/tokenGeneration.feature@login') customerLogin

    * def customerToken = loginCall.token
    * def inActiveCustomerToken = 'ertifodl5967fnfkdkif78'
    * def expiredCustomerToken = 'lkhjjsjsjkkshdfcb0c9ty'
