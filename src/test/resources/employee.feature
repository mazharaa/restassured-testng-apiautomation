Feature: Employee API

Background:
    Given The base url in this feature is "https://whitesmokehouse.com/webhook"

Scenario:
    When Send a http "POST" request to "/employee/add" with body:
        """
        {
            "email": "testazhar7@test.com",
            "password": "test",
            "full_name": "test name",
            "department": "IT",
            "title": "QA"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "add_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "testazhar7@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test name"
    And The response's "department" should be "IT"
    And The response's "title" should be "QA"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario:
    When Send a http "POST" request to "/employee/login" with body:
        """
        {
            "email": "testazhar7@test.com",
            "password": "test"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "employee_login_schema.json"
    And The response's "token" should not be null
    And Save token from the response to local storage

Scenario:
    Given Make sure token is in local storage
    When Send a http "GET" request to "/employee/get" with body:
        """
        {} 
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "testazhar7@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test name"
    And The response's "department" should be "IT"
    And The response's "title" should be "QA"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario:
    Given Make sure token is in local storage
    When Send a http "PUT" request to "/employee/update" with body:
        """
        {
            "email": "testazhar7update@test.com",
            "password": "testupdate",
            "full_name": "test name update",
            "department": "IT Update",
            "title": "QA Update"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "update_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "testazhar7update@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test name update"
    And The response's "department" should be "IT Update"
    And The response's "title" should be "QA Update"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario:
    Given Make sure token is in local storage
    When Send a http "GET" request to "/employee/get" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "testazhar7update@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test name update"
    And The response's "department" should be "IT Update"
    And The response's "title" should be "QA Update"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario:
    When Send a http "GET" request to "/employee/get_all" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_all_employee_schema.json"
    And Full name should not be null

Scenario:
    When Send a http "GET" request to "/41a9698d-d8b0-42df-9ddc-89c0a1a1aa79/employee/search/Azhar" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "search_employee_schema.json"
    And Search result should not be empty and contain "Azhar"
    

Scenario:
    Given Make sure token is in local storage
    When Send a http "DELETE" request to "/employee/delete" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "delete_employee_schema.json"