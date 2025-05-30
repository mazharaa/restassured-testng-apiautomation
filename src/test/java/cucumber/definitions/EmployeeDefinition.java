package cucumber.definitions;

import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import service.EmployeeService;

public class EmployeeDefinition {
    public static Response response;
    public static String token;
    private final EmployeeService employeeService;
    private final TestContext testContext;

    public EmployeeDefinition() {
        this.employeeService = new EmployeeService();
        this.testContext = new TestContext();
    }

    // @Given("The base url in this feature is {string}")
    // public void set_base_url(String baseUrl) {
    //     RestAssured.baseURI = baseUrl;
    // }

    @Given("Make sure token is in local storage")
    public void assert_token_in_variable() {
        assert EmployeeDefinition.token != null : "Token is null";
    }

    @When("Send a http {string} request to {string} with body:")
    public void send_request_http(String method, String endPoint, String body) {
        response = employeeService.sendRequest(method, endPoint, body, token);
        testContext.setResponse(response);
    }

    @Then("The response status must be {int}")
    public void send_request_http(int statusCode) {
        assert testContext.getResponse().statusCode() == statusCode : "Expected status code: " + statusCode + ", but got: " + response.statusCode();
    }

    @And("The response schema should be match with schema {string}")
    public void schema_validation(String schemaPath) {
        testContext.getResponse().then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    @And("Save token from the response to local storage")
    public void save_token() {
        EmployeeDefinition.token = testContext.getResponse().jsonPath().getString("[0].token");
    }

    @And("The response's {string} should not be null")
    public void assert_reponse_null(String property) {
        assert testContext.getResponse().jsonPath().getString("[0]." + property) != null : property + " is null";
    }

    @And("The response's {string} should be {string}")
    public void assert_response_value(String property, String expectedValue) {
        String actualValue = testContext.getResponse().jsonPath().getString("[0]." + property);
        assert actualValue.equals(expectedValue) : property + " expected: " + expectedValue + ", but got: " + actualValue;
    }

    // Only for test data is ready to test
    @And("The response should not be empty")
    public void assert_response_empty() {
        int size = testContext.getResponse().jsonPath().getList("$").size();
        assert size > 0 : "Response is empty" ;
    }

    // Only for test data is ready to test
    @And("Full name should not be null")
    public void assert_get_all_full_name() {
        int employeeTotal = testContext.getResponse().jsonPath().getList("$").size();

        for (int i = 0; i < employeeTotal; i++) {
            String fullName = response.jsonPath().getString("[" + i + "].full_name");
            assert fullName != null : "Full name is null at index " + i;
        }
    }

    // Only for test data is ready to test
    @And("Search result should not be empty and contain {string}")
    public void assert_search_result(String query) {
        int resultsTotal = testContext.getResponse().jsonPath().getList("result").size();

        for (int i = 0; i < resultsTotal; i++) {
            String fullName = testContext.getResponse().jsonPath().getString("[0].result[" + i + "].full_name");
            String department = testContext.getResponse().jsonPath().getString("[0].result[" + i + "].department");
            String title = testContext.getResponse().jsonPath().getString("[0].result[" + i + "].title");

            assert fullName != null : "Full name is null";
            assert department != null : "Department is null";
            assert title != null : "Title is null";

            assert fullName.contains(query) : "Full name is not match with query";
        }
    }
}