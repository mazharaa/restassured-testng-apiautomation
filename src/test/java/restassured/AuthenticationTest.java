package restassured;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthenticationTest extends BaseTest{
    private final String EMAIL = "azhartest1@email.com";
    private final String FULLNAME = "Azhar Test1 Name";
    private final String PASSWORD = "p@ssw0rd";
    private final String DEPARTMENT = "Technology";
    private final String PHONENUMBER = "081234567890";

    @Test
    public void registerTest() {
        // Mapping the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", EMAIL);
        requestBody.put("full_name", FULLNAME);
        requestBody.put("password", PASSWORD);
        requestBody.put("department", DEPARTMENT);
        requestBody.put("phone_number", PHONENUMBER);

        System.out.println("registerTest starting...");
        
        // Register request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .body(requestBody)
            .log().all()
            .when()
            .post("api/register");

        String id = actRes.jsonPath().getString("id");
        String email = actRes.jsonPath().getString("email");
        String fullName = actRes.jsonPath().getString("full_name");
        String department = actRes.jsonPath().getString("department");
        String phoneNumber = actRes.jsonPath().getString("phone_number");
        String createAt = actRes.jsonPath().getString("create_at");
        String updateAt = actRes.jsonPath().getString("update_at");

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(email, EMAIL, "Email should match request");
        Assert.assertEquals(fullName, FULLNAME, "Full Name should match request");
        Assert.assertEquals(department, DEPARTMENT, "Department should match request");
        Assert.assertEquals(phoneNumber, PHONENUMBER, "Phone Number should match request");
        Assert.assertNotNull(createAt, "Create At should not be null");
        Assert.assertNotNull(updateAt, "Update At should not be null");

        System.out.println(actRes.prettyPrint());
    }
}
