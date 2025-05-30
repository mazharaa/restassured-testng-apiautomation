package apiengine;

import helper.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Endpoints {
    public Endpoints() {
        RestAssured.baseURI = ConfigManager.getBaseUrl();
    }

    public Response addEmployee(String bodyRequest) {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/employee/add");

        return response;
    }
    
    public Response loginEmployee(String bodyRequest)  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/employee/login");
        
        return response;
    }

    public Response getEmployee(String token)  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("/employee/get");

        return response;
    }

    public Response getAllEmployee()  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .log().all()
            .when()
            .get("/employee/get_all");

        return response;
    }

    public Response searchEmployee(String query)  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .log().all()
            .when()
            .get("/41a9698d-d8b0-42df-9ddc-89c0a1a1aa79/employee/search/" + query);

        return response;
    }

    public Response updateEmployee(String bodyRequest, String token)  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .body(bodyRequest)
            .log().all()
            .put("employee/update");

        return response;
    }

    public Response deleteEmployee(String token)  {
        Response response = RestAssured
            .given()
            .contentType("application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .delete("employee/delete");

        return response;
    }
}