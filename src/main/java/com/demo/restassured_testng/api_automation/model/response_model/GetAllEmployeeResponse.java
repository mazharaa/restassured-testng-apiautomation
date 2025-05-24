package com.demo.restassured_testng.api_automation.model.response_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetAllEmployeeResponse {
    @JsonProperty("full_name")
    private String fullName;
}
