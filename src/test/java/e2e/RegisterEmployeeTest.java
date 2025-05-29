package e2e;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.demo.restassured_testng.api_automation.model.EmployeeModel;
import com.demo.restassured_testng.api_automation.model.response_model.AddEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.EmployeeLoginResponse;
import com.demo.restassured_testng.api_automation.model.response_model.GetAllEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.GetEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.SearchEmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class RegisterEmployeeTest {
    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Suite E2E running...");

        RestAssured.baseURI = StaticVar.BASE_URL;
    }

    @BeforeClass
    public void testDataSetUp() {
        System.out.println("Setting up test data RegisterEmployee group...");

        String randomString = RandomStringUtils.randomAlphabetic(10);

        StaticVar.employee = new EmployeeModel();
        StaticVar.employee.setEmail("azhartestregister" +randomString + "@email.com");
        StaticVar.employee.setPassword("test");
        StaticVar.employee.setFullName("Azhar Test Register");
        StaticVar.employee.setDepartment("Technology");
        StaticVar.employee.setTitle("QA");
    }

    @Test(groups = "RegisterEmployee")
    public void addEmployee() throws Exception{
        System.out.println("addEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(StaticVar.employee);

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .body(body)
            .log().all()
            .when()
            .post("employee/add");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Add Employee status code must be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("add_employee_schema.json"));

        List<AddEmployeeResponse> addEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<AddEmployeeResponse>>() { 
        });

        assert addEmployeeResponses.size() > 0 : "Data is Empty";
        assert addEmployeeResponses.get(0).getEmail().equals(StaticVar.employee.getEmail()) : "email not expected";
        assert addEmployeeResponses.get(0).getFullName().equals(StaticVar.employee.getFullName()) : "Full name not expected";
        assert addEmployeeResponses.get(0).getDepartment().equals(StaticVar.employee.getDepartment()) : "Department not expected";
        assert addEmployeeResponses.get(0).getTitle().equals(StaticVar.employee.getTitle()) : "Title not expected";
        assert addEmployeeResponses.get(0).getPasswordHash() != null : "password hash is null";
    }

    @Test(dependsOnMethods = "addEmployee", groups = "RegisterEmployee")
    public void loginEmployee() throws Exception {
        System.out.println("loginEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(StaticVar.employee);

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .body(body)
            .log().all()
            .when()
            .post("employee/login");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Login status code should be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("employee_login_schema.json"));

        List<EmployeeLoginResponse> employeeLoginResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<EmployeeLoginResponse>>() {
        });

        StaticVar.token = employeeLoginResponses.get(0).getToken();

        assert employeeLoginResponses.size() > 0 : "Data is empty";
        assert StaticVar.token != null : "Token is null";
    }

    @Test(dependsOnMethods = "loginEmployee", groups = "RegisterEmployee")
    public void getEmployee() throws Exception {
        System.out.println("getEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + StaticVar.token)
            .log().all()
            .when()
            .get("employee/get");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Get employee status code should be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get_employee_schema.json"));

        List<GetEmployeeResponse> getEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<GetEmployeeResponse>>() { 
        });

        assert getEmployeeResponses.size() > 0 : "Data is Empty";
        assert getEmployeeResponses.get(0).getEmail().equals(StaticVar.employee.getEmail()) : "email not expected";
        assert getEmployeeResponses.get(0).getFullName().equals(StaticVar.employee.getFullName()) : "Full name not expected";
        assert getEmployeeResponses.get(0).getDepartment().equals(StaticVar.employee.getDepartment()) : "Department not expected";
        assert getEmployeeResponses.get(0).getTitle().equals(StaticVar.employee.getTitle()) : "Title not expected";
        assert getEmployeeResponses.get(0).getPasswordHash() != null : "password hash is null";
    }

    @Test(dependsOnMethods = "getEmployee", groups = "RegisterEmployee")
    public void getAllEmployee() throws Exception {
        System.out.println("getAllEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .log().all()
            .when()
            .get("employee/get_all");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Get all employee status code should be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("get_all_employee_schema.json"));

        List<GetAllEmployeeResponse> getAllEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<GetAllEmployeeResponse>>() { 
        });

        assert getAllEmployeeResponses.size() > 0 : "Data is Empty";
        
        for (GetAllEmployeeResponse getAllEmployeeResponse : getAllEmployeeResponses) {
            assert getAllEmployeeResponse.getFullName() != null : "Full name shoul not be null";
        }
    }

    @Test(dependsOnMethods = "getAllEmployee", groups = "RegisterEmployee")
    public void searchEmployee() throws Exception{
        System.out.println("searchEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();

        String arrString[] = StaticVar.employee.getFullName().split(" ", 2);

        String query = arrString[0];

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .log().all()
            .when()
            .get("41a9698d-d8b0-42df-9ddc-89c0a1a1aa79/employee/search/" + query);

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Search employee status code should be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("search_employee_schema.json"));

        List<SearchEmployeeResponse> searchEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<SearchEmployeeResponse>>() { 
        });

        assert searchEmployeeResponses.size() > 0 : "Data is Empty";
        assert searchEmployeeResponses.get(0).getQuery().equals(query) : "query not expected";
        assert searchEmployeeResponses.get(0).getResult().size() > 0 : "Search not found";
    }
}
