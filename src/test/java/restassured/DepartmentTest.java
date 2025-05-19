package restassured;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DepartmentTest extends BaseTest{
    @Test(dependsOnMethods = {"restassured.ObjectTest.deleteObjectTest"})
    public void getAllDepartmentTest() {
        System.out.println("getAllDepartmentTest starting...");
        // Get all department list request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer "+ token)
            .log().all()
            .when()
            .get("api/department");

        System.out.println(actRes.prettyPrint());
        
        List<Map<String, Object>> responseList = actRes.jsonPath().getList("");

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertFalse(responseList.isEmpty(), "Object list should not be empty");
    }
}
