package com.demo.restassured_testng.api_automation.model.response_model;

import java.util.List;

import com.demo.restassured_testng.api_automation.model.EmployeeSearchInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchEmployeeResponse {
    @JsonProperty("query")
    private String query;

    @JsonProperty("result")
    private List<EmployeeSearchInfo> result;
}
