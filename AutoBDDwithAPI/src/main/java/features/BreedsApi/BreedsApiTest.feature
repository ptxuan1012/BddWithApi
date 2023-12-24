#Author: Pham Xuan
@BreedsApi
Feature: Breeds Api Main Test

  @MainCase
  Scenario Outline: Check response whe send request successfully
    Given I have url and Method of breeds api
      | URL                                 |
      | https://api.thecatapi.com/v1/breeds |
    When I sent breeds request
    Then I check <StatusCode> of breeds api correctly

    Examples: 
      | StatusCode | NumberOfCats |
      |        200 |           67 |
