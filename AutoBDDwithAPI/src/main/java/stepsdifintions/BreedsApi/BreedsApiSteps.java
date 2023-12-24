package stepsdifintions.BreedsApi;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import common.ApiUtils;
import common.JsonUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BreedsApiSteps {
	String url, requestBodyFilePath;
	HttpResponse<String> response;
	JsonUtils jsonUtils = new JsonUtils();
	ApiUtils apiUtils = new ApiUtils();

	@Given("I have url and Method and request body of cat api")
	public void i_have_url_and_method_and_request_body_of_cat_api(DataTable givenTable) {
		List<Map<String, String>> list = givenTable.asMaps(String.class, String.class);
		String requestBodyName = "";
		for (Map<String, String> m : list) {
			url = m.get("URL");
			requestBodyName = m.get("RequestBodyName");
		}

		requestBodyFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + requestBodyName;
	}


	@When("I sent cat request")
	public void i_sent_cat_request() {
		String requestBody = jsonUtils.readJsonFile(requestBodyFilePath);
		response = apiUtils.sendPostRequest(url, requestBody);
	}

	@Then("I check {int} of cat api correctly")
	public void i_check_of_cat_api_correctly(int expectedStatusCode) {
		assertEquals(response.statusCode(), expectedStatusCode);
	}
	// Case Vaidate

	@When("I sent cat request with validation data with {string} and {string}")
	public void i_sent_cat_request_with_validation_data(String fieldName, String value) {
		String requestBody= createRequestBody(requestBodyFilePath,fieldName, value);
		response = apiUtils.sendPostRequest(url, requestBody);
	}

	@Then("I check {int} and {string} of cat api correctly")
	public void i_check_and_must_be_a_string_of_cat_api_correctly(int expectedStatusCode, String expectedErrorMessage) {
		assertEquals(response.statusCode(), expectedStatusCode);
		assertEquals(response.body().toString(), expectedErrorMessage);
	}

	public String createRequestBody(String jsonBodySourceFilePath, String fieldName, String value) {
		String requestBody = "";
		
		File sourceFile = new File(jsonBodySourceFilePath);
		String requestBodyName = sourceFile.getName();

		String jsonBodyDictinationFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\"
				+ requestBodyName.replace(".json", "Copy.json");
		File dictinationFile = new File(jsonBodyDictinationFilePath);
		jsonUtils.copyJsonFile(sourceFile, dictinationFile);

		// đổi giá trị //đọc content file
		requestBody = jsonUtils.changeValueByFieldName(dictinationFile, fieldName, value);
		return requestBody;
	}
}
