package e2e;

import java.util.List;

import org.testng.annotations.Test;

import com.demo.restassured_testng.api_automation.model.EmployeeSearchInfo;
import com.demo.restassured_testng.api_automation.model.response_model.DeleteEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.GetAllEmployeeResponse;
import com.demo.restassured_testng.api_automation.model.response_model.SearchEmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class DeleteEmployeeTest {
    @Test(dependsOnGroups = "UpdateEmployee", groups = "DeleteEmployee")
    public void deleteEmployee() throws Exception{
        System.out.println("deleteEmployee test starting...");

        Response res = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + StaticVar.token)
            .log().all()
            .when()
            .delete("employee/delete");

        System.out.println(res.asPrettyString());

        assert res.getStatusCode() == 200 : "Delete employee status code must be 200";

        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("delete_employee_schema.json"));

        ObjectMapper objectMapper = new ObjectMapper();

        List<DeleteEmployeeResponse> deleteEmployeeResponses = objectMapper.readValue(res.body().asString(),
        new TypeReference<List<DeleteEmployeeResponse>>() {
        });

        assert deleteEmployeeResponses.size() > 0 : "Data is empty";
        assert deleteEmployeeResponses.get(0).getSuccess() != null : "Success is null";
    }

    @Test(dependsOnMethods = "deleteEmployee", groups = "DeleteEmployee")
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

    @Test(dependsOnMethods = "getAllEmployee", groups = "DeleteEmployee")
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

        List<EmployeeSearchInfo> searchResults = searchEmployeeResponses.get(0).getResult();

        for (EmployeeSearchInfo searchResult : searchResults) {
            assert searchResult.getFullName() != null : "Full name should not be null";
            assert searchResult.getDepartment() != null : "Department should not be null";
            assert searchResult.getTitle() != null : "Title should not be null";

            assert searchResult.getFullName().split(" ", 2)[0].equals(query) : "Full name is not match with query";
        }
    }
}
