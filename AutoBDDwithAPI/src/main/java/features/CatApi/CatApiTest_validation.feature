#Author: XuanPham
@CatApi
Feature: Cat Api Validate Test

 @ValidationCase
  Scenario Outline: Check validation of single field
    Given I have url and Method and request body of cat api
      | URL                                | RequestBodyName                |
      | https://api.thecatapi.com/v1/votes | CatApi\\CatApiRequestBody.json |
    When I sent cat request with validation data with "<FieldName>" and "<Value>"
    Then I check <ExpectedStatusCode> and "<ExpectedMessage>" of cat api correctly

    Examples: 
      | FieldName | Value   | ExpectedStatusCode | ExpectedMessage                       |
      #| image_id  | null    |                400 | \"image_id\" must be a string           |
      #| image_id  | missing |                400 | \"image_id\" is required                |
      | image_id  | \"\"      |                400 | \"image_id\" is not allowed to be empty |
      #| image_id  |       2 |                400 | \"image_id\" must be a string           |
