#Author: Pham Xuan
@CatApi
Feature: Cat Api Main Test

  @MainCase
  Scenario Outline: Check response whe send request successfully
    Given I have url and Method and request body of cat api
      | URL                                | RequestBodyName                |
      | https://api.thecatapi.com/v1/votes | CatApi\\CatApiRequestBody.json |
    When I sent cat request
    Then I check <StatusCode> of cat api correctly

    Examples: 
      | RequestBodyName                | StatusCode |
      | CatApi//CatApiRequestBody.json |        201 |
