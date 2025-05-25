package e2e;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.restassured_testng.api_automation.model.EmployeeModel;
import com.demo.restassured_testng.api_automation.model.response_model.GetAllEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.GetEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.SearchEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.UpdateEmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UpdateEmployee {
    @BeforeClass(dependsOnGroups = "RegisterEmployee")
    public void testDataSetUp() {
        System.out.println("Setting up test data UpdateEmployee group...");

        String randomString = RandomStringUtils.randomAlphabetic(10);

        StaticVar.employee = new EmployeeModel();
        StaticVar.employee.setEmail("azhartestregisterupdate" +randomString + "@email.com");
        StaticVar.employee.setPassword("testupdate");
        StaticVar.employee.setFullName("Azhar Test Register Update");
        StaticVar.employee.setDepartment("Bussiness");
        StaticVar.employee.setTitle("Operation");
    }

    @Test(dependsOnGroups = "RegisterEmployee", groups = "UpdateEmployee")
    public void updateEmployee() throws Exception{
        System.out.println("updateEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(StaticVar.employee);

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + StaticVar.token)
            .body(body)
            .log().all()
            .put("employee/update");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Update employee status code must be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("update_employee_schema.json"));

        List<UpdateEmployeeResponse> updateEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<UpdateEmployeeResponse>>() { 
        });

        assert updateEmployeeResponses.size() > 0 : "Data is Empty";
        assert updateEmployeeResponses.get(0).getEmail().equals(StaticVar.employee.getEmail()) : "email not expected";
        assert updateEmployeeResponses.get(0).getFullName().equals(StaticVar.employee.getFullName()) : "Full name not expected";
        assert updateEmployeeResponses.get(0).getDepartment().equals(StaticVar.employee.getDepartment()) : "Department not expected";
        assert updateEmployeeResponses.get(0).getTitle().equals(StaticVar.employee.getTitle()) : "Title not expected";
        assert updateEmployeeResponses.get(0).getPasswordHash() != null : "password hash is null";
    }

    @Test(dependsOnMethods = "updateEmployee", groups = "UpdateEmployee")
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

    @Test(dependsOnMethods = "getEmployee", groups = "UpdateEmployee")
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

    @Test(dependsOnMethods = "getEmployee", groups = "UpdateEmployee")
    public void searchEmployee() throws Exception{
        System.out.println("searchEmployee test starting...");

        ObjectMapper objectMapper = new ObjectMapper();

        String arrString[] = StaticVar.employee.getFullName().split(" ", 2);

        String query = arrString[0];

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + StaticVar.token)
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
