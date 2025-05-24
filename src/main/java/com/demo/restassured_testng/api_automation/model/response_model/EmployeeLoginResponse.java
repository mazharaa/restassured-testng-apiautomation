package com.demo.restassured_testng.api_automation.model.response_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployeeLoginResponse {
    @JsonProperty("token")
    private String token;
}
