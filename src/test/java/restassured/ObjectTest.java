package restassured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ObjectTest extends BaseTest{
    private final String NAME = "Apple MacBook Air M1 azhar";
    private final int YEAR = 2020;
    private final int PRICE = 1199;
    private final String CPUMODEL = "Apple M1";
    private final String HARDDISKSIZE = "256 GB";
    private final String CAPACITY = "8 CPU";
    private final String SCREENSIZE = "13 inch";
    private final String COLOR = "SPace Grey";

    @Test(priority = 1)
    public void addObjectTest() {
        // Mapping the data and request body
        Map<String, Object> objectData = new HashMap<>();
        objectData.put("year", YEAR);
        objectData.put("price", PRICE);
        objectData.put("cpu_model", CPUMODEL);
        objectData.put("hard_disk_size", HARDDISKSIZE);
        objectData.put("capacity", CAPACITY);
        objectData.put("screen_size", SCREENSIZE);
        objectData.put("color", COLOR);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", NAME);
        requestBody.put("data", objectData);

        // Add object request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .log().all()
            .when()
            .post("api/objects");

        System.out.println(actRes.prettyPrint());

        List<Map<String, Object>> responseList = actRes.jsonPath().getList("");
        Map<String, Object> firstObject = responseList.get(0);

        objectId = (int) firstObject.get("id");
        String name = String.valueOf(firstObject.get("name"));

        
        Map<String, Object> data = (Map<String, Object>) firstObject.get("data");
        String year = String.valueOf(data.get("year"));
        int price = (int) data.get("price");
        String cpuModel = String.valueOf(data.get("cpu_model"));
        String hardDiskSize = String.valueOf(data.get("hard_disk_size"));
        String capacity = String.valueOf(data.get("capacity"));
        String screenSize = String.valueOf(data.get("screen_size"));
        String color = String.valueOf(data.get("color"));

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(objectId, "ID should not be null");
        Assert.assertEquals(name, NAME);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, PRICE);
        Assert.assertEquals(cpuModel, CPUMODEL);
        Assert.assertEquals(hardDiskSize, HARDDISKSIZE);
        Assert.assertEquals(capacity, CAPACITY);
        Assert.assertEquals(screenSize, SCREENSIZE);
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 2)
    public void deleteObjectTest() {
        // Delete object request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .delete("d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/"+objectId);

        System.out.println(actRes.prettyPrint());

        String status = actRes.jsonPath().getString("status");
        String message = actRes.jsonPath().getString("message");

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(status, "status should not be null");
        Assert.assertNotNull(message, "message should not be null");
        Assert.assertEquals(status, "deleted");
        Assert.assertEquals(message, "Object with id = "+objectId+", has been deleted.");
    }
}
