package e2e;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.demo.restassured_testng.api_automation.model.EmployeeModel;
import com.demo.restassured_testng.api_automation.model.response_model.AddEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.EmployeeLoginResponse;
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
}
