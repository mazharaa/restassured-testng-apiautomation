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
    private final String CPU_MODEL = "Apple M1";
    private final String HARD_DISK_SIZE = "256 GB";
    private final String CAPACITY = "8 CPU";
    private final String SCREEN_SIZE = "13 inch";
    private final String COLOR = "SPace Grey";

    @Test(priority = 1)
    public void addObjectTest() {
        // Mapping the data and request body
        Map<String, Object> objectData = new HashMap<>();
        objectData.put("year", YEAR);
        objectData.put("price", PRICE);
        objectData.put("cpu_model", CPU_MODEL);
        objectData.put("hard_disk_size", HARD_DISK_SIZE);
        objectData.put("capacity", CAPACITY);
        objectData.put("screen_size", SCREEN_SIZE);
        objectData.put("color", COLOR);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", NAME);
        requestBody.put("data", objectData);

        System.out.println("addObjectTest starting...");

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

        int id = (int) firstObject.get("id");
        objectId = id;

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
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, NAME);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, PRICE);
        Assert.assertEquals(cpuModel, CPU_MODEL);
        Assert.assertEquals(hardDiskSize, HARD_DISK_SIZE);
        Assert.assertEquals(capacity, CAPACITY);
        Assert.assertEquals(screenSize, SCREEN_SIZE);
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 2)
    public void getListAllObjectTest() {
        System.out.println("getListAllObjectTest starting...");

        // Get List All object request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("api/objects");

        List<Map<String, Object>> responseList = actRes.jsonPath().getList("");

        System.out.println(actRes.prettyPrint());

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertFalse(responseList.isEmpty(), "Object list should not be empty");
    }

    @Test(priority = 3)
    public void listOfObjectsByIdTest() {
        System.out.println("listOfObjectsByIdTest starting...");

        // Get List of object by ID request
        Response actRes = RestAssured
            .given()
            .queryParam("id", objectId)
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("api/objectslistId");

        System.out.println(actRes.prettyPrint());

        List<Map<String, Object>> responseList = actRes.jsonPath().getList("");
        Map<String, Object> firstObject = responseList.get(0);

        int id = (int) firstObject.get("id");
        String name = String.valueOf(firstObject.get("name"));
        
        Map<String, Object> data = (Map<String, Object>) firstObject.get("data");
        String year = String.valueOf(data.get("year"));
        // Price type data is different from addObject response body(?)
        String price = String.valueOf(data.get("price"));
        String cpuModel = String.valueOf(data.get("cpu_model"));
        String hardDiskSize = String.valueOf(data.get("hard_disk_size"));

        // Capacity type data is different from addObject response body(?)
        int capacity = (int) data.get("capacity");

        // Screen size type data is different from addObject response body(?)
        int screenSize = (int) data.get("screen_size");

        String color = String.valueOf(data.get("color"));

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, NAME);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, String.valueOf(PRICE));
        Assert.assertEquals(cpuModel, CPU_MODEL);
        Assert.assertEquals(hardDiskSize, HARD_DISK_SIZE);
        Assert.assertEquals(capacity, Integer.parseInt(CAPACITY.replaceAll("[^0-9]", "").trim()));
        Assert.assertEquals(screenSize, Integer.parseInt(SCREEN_SIZE.replaceAll("[^0-9]", "").trim()));
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 4)
    public void getSingleObjectTest() {
        System.out.println("getSingleObjectTest starting...");

        // Get single object request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + objectId);

        System.out.println(actRes.prettyPrint());

        int id = actRes.jsonPath().getInt("id");
        String name = String.valueOf(actRes.jsonPath().getString("name"));

        Map<String, Object> data = actRes.jsonPath().getMap("data");
        String year = String.valueOf(data.get("year"));
        // Price type data is different from addObject response body(?)
        String price = String.valueOf(data.get("price"));
        String cpuModel = String.valueOf(data.get("cpu_model"));
        String hardDiskSize = String.valueOf(data.get("hard_disk_size"));

        // Capacity type data is different from addObject response body(?)
        int capacity = (int) data.get("capacity");

        // Screen size type data is different from addObject response body(?)
        int screenSize = (int) data.get("screen_size");

        String color = String.valueOf(data.get("color"));

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, NAME);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, String.valueOf(PRICE));
        Assert.assertEquals(cpuModel, CPU_MODEL);
        Assert.assertEquals(hardDiskSize, HARD_DISK_SIZE);
        Assert.assertEquals(capacity, Integer.parseInt(CAPACITY.replaceAll("[^0-9]", "").trim()));
        Assert.assertEquals(screenSize, Integer.parseInt(SCREEN_SIZE.replaceAll("[^0-9]", "").trim()));
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 5)
    public void updateObjectTest() {
        System.out.println("updateObjectTest staring...");

        // Update name
        String updateName = "Apple MacBook Air M1 azhar update";

        // Mapping the data and request body
        Map<String, Object> objectData = new HashMap<>();
        objectData.put("year", YEAR);
        objectData.put("price", PRICE);
        objectData.put("cpu_model", CPU_MODEL);
        objectData.put("hard_disk_size", HARD_DISK_SIZE);
        objectData.put("capacity", CAPACITY);
        objectData.put("screen_size", SCREEN_SIZE);
        objectData.put("color", COLOR);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", updateName);
        requestBody.put("data", objectData);

        // Get List of object by ID request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .log().all()
            .when()
            .put("37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + objectId);

        System.out.println(actRes.prettyPrint());

        List<Map<String, Object>> responseList = actRes.jsonPath().getList("");
        Map<String, Object> firstObject = responseList.get(0);

        int id = (int) firstObject.get("id");
        String name = String.valueOf(firstObject.get("name"));
        
        Map<String, Object> data = (Map<String, Object>) firstObject.get("data");
        String year = String.valueOf(data.get("year"));
        int price = (int) data.get("price");

        // Key is different from CPU model key in addObject response body
        String cpuModel = String.valueOf(data.get("CPU model"));

        // Key is different from Hard disk size key in addObject response body
        String hardDiskSize = String.valueOf(data.get("Hard disk size"));

        String capacity = String.valueOf(data.get("capacity"));
        String screenSize = String.valueOf(data.get("screen_size"));
        String color = String.valueOf(data.get("color"));

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, updateName);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, PRICE);
        Assert.assertEquals(cpuModel, CPU_MODEL);
        Assert.assertEquals(hardDiskSize, HARD_DISK_SIZE);
        Assert.assertEquals(capacity, CAPACITY);
        Assert.assertEquals(screenSize, SCREEN_SIZE);
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 6)
    public void partiallyUpdateObjectTest() {
        System.out.println("partiallyUpdateObjectTest staring...");

        // Update name, price, and CPU Model
        String updateName = "Apple MacBook Air M1 azhar partially update";
        int updatePrice = 999;
        String updateCpuModel = "Apple M1 partially update";

        // Mapping the request body data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", updateName);
        requestBody.put("price", updatePrice);
        requestBody.put("cpu_model", updateCpuModel);

        // Get List of object by ID request
        Response actRes = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .log().all()
            .when()
            .patch("39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/" + objectId);

        System.out.println(actRes.prettyPrint());

        int id = (int) actRes.jsonPath().getInt("id");
        String name = actRes.jsonPath().getString("name");
        
        Map<String, Object> data = actRes.jsonPath().getMap("data");
        String year = String.valueOf(data.get("year"));
        String price = String.valueOf(data.get("price"));
        String cpuModel = String.valueOf(data.get("cpu_model"));
        String hardDiskSize = String.valueOf(data.get("hard_disk_size"));
        String capacity = String.valueOf(data.get("capacity"));
        String screenSize = String.valueOf(data.get("screen_size"));
        String color = String.valueOf(data.get("color"));

        Assert.assertEquals(actRes.statusCode(), 200);
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertEquals(name, updateName);
        Assert.assertEquals(year, String.valueOf(YEAR));
        Assert.assertEquals(price, String.valueOf(updatePrice));
        Assert.assertEquals(cpuModel, updateCpuModel);
        Assert.assertEquals(hardDiskSize, HARD_DISK_SIZE);
        Assert.assertEquals(capacity, CAPACITY);
        Assert.assertEquals(screenSize, SCREEN_SIZE);
        Assert.assertEquals(color, COLOR);
    }

    @Test(priority = 7)
    public void deleteObjectTest() {
        System.out.println("deleteObjectTest starting...");
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
