package cucumber.definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class EmployeeDefinition {
    public static Response response;
    public static String token;

    @Given("The base url in this feature is {string}")
    public void set_base_url(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    @Given("Make sure token is in local storage")
    public void assert_token_in_variable() {
        assert EmployeeDefinition.token != null : "Token is null";
    }

    @When("Send a http {string} request to {string} with body:")
    public void send_request_http(String method, String endPoint, String body) {
        response = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", EmployeeDefinition.token != null ? "Bearer " + EmployeeDefinition.token : "")
            .body(body)
            .when()
            .request(method, endPoint);
    }

    @Then("The response status must be {int}")
    public void send_request_http(int statusCode) {
        assert response.statusCode() == statusCode : "Error, due to actual status code is " + response.statusCode();
    }

    @And("The response schema should be match with schema {string}")
    public void schema_validation(String schemaPath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @And("Save token from the response to local storage")
    public void save_token() {
        EmployeeDefinition.token = response.jsonPath().getString("[0].token");
    }

    @And("The response's {string} should not be null")
    public void reponse_null_validation(String property) {
        assert response.jsonPath().getString("[0]." + property) != null : property + " is null";
    }

    @And("The response's {string} should be {string}")
    public void response_value_property_validation(String property, String value) {
        assert response.jsonPath().getString("[0]." + property).equals(value) : property +" is not mathc with " + value;
    }

    // Only for test data is ready to test
    @And("The response should not be empty")
    public void response_empty_validation() {
        assert response.jsonPath().getList("$").size() > 0 : "Response is empty" ;
    }

    // Only for test data is ready to test
    @And("Full name should not be null")
    public void full_name_get_all_validation() {
        int employeeTotal = response.jsonPath().getList("$").size();

        for (int i = 0; i < employeeTotal; i++) {
            assert response.jsonPath().getString("[" + i + "].full_name") != null : "Full name is null";
        }
    }

    // Only for test data is ready to test
    @And("Search result should not be empty and contain {string}")
    public void search_result_validation(String query) {
        int resultsTotal = response.jsonPath().getList("result").size();

        for (int i = 0; i < resultsTotal; i++) {
            assert response.jsonPath().getString("[0].result[" + i + "].full_name") != null : "Full name is null";
            assert response.jsonPath().getString("[0].result[" + i + "].department") != null : "Department is null";
            assert response.jsonPath().getString("[0].result[" + i + "].title") != null : "Title is null";

            String arrString[] = response.jsonPath().getString("[0].result[" + i + "].full_name").split(" ", 2);
            assert arrString[0].equals(query) : "Full name is not match with query";
        }
    }

    @And("Search results contain {string}")
    public void searc_resul_contain_validation(String query) {

    }
}