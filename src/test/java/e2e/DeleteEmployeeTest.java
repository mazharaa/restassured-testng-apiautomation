package e2e;

import java.util.List;

import org.testng.annotations.Test;

import com.demo.restassured_testng.api_automation.model.response_model.DeleteEmployeeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class DeleteEmployeeTest {
    @Test(dependsOnGroups = "UpdateEmployee")
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
}
