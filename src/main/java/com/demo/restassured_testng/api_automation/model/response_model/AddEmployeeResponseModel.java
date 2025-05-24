package com.demo.restassured_testng.api_automation.model.response_model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddEmployeeResponseModel {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password_hash")
    private String passwordHash;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("department")
    private String department;

    @JsonProperty("title")
    private String title;
    
    @JsonProperty("create_at")
    private String createAt;

    @JsonProperty("update_at")
    private String updateAt;
}
