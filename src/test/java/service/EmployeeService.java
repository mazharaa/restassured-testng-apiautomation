package service;

import apiengine.Endpoints;
import io.restassured.response.Response;

public class EmployeeService {
    private final Endpoints endpoints;

    public EmployeeService() {
        this.endpoints = new Endpoints();
    }

    public Response sendRequest(String method, String endpoint, String body, String token) {
        switch (method.toUpperCase()) {
            case "POST":
                return handlePostRequest(endpoint, body);
            case "GET":
                return handleGetRequest(endpoint, token);
            case "PUT":
                return handlePutRequest(endpoint, token, body);
            case "DELETE":
                return handleDeleteRequest(endpoint, token);
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    private Response handlePostRequest(String endpoint, String body) {
        if (endpoint.contains("/employee/add")) {
            return endpoints.addEmployee(body);
        } else if (endpoint.contains("/employee/login")) {
            return endpoints.loginEmployee(body);
        }
        throw new IllegalArgumentException("Unsupported POST endpoint" + endpoint);
    }

    private Response handleGetRequest(String endpoint, String token) {
        if (endpoint.contains("/employee/get_all")) {
            return endpoints.getAllEmployee();
        } else if (endpoint.contains("/employee/get")) {
            return endpoints.getEmployee(token);
        } else if (endpoint.contains("/employee/search")) {
            String query = endpoint.substring(endpoint.lastIndexOf("/") + 1);
            return endpoints.searchEmployee(query);
        }
        throw new IllegalArgumentException("Unsupported GET endpoint" + endpoint);
    }

    private Response handlePutRequest(String endpoint, String token, String body) {
        if (endpoint.contains("/employee/update")) {
            return endpoints.updateEmployee(body, token);
        }
        throw new IllegalArgumentException("Unsupported PUT endpoint" + endpoint);
    } 

    private Response handleDeleteRequest(String endpoint, String token) {
        if (endpoint.contains("/employee/delete")) {
            return endpoints.deleteEmployee(token);
        }
        throw new IllegalArgumentException("Unsupported PUT endpoint" + endpoint);
    } 
}
