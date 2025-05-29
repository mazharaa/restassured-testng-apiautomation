package com.demo.restassured_testng.api_automation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EmployeeSearchInfo {
    @JsonProperty("full_name")
    private String fullName;
    
    @JsonProperty("department")
    private String department;

    @JsonProperty("title")
    private String title;
}
