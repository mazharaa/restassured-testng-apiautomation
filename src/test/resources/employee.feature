Feature: Employee API

Background: Employee CRUD API Testing

Scenario: Register employee
    When Send a http "POST" request to "/employee/add" with body:
        """
        {
            "email": "azhartest7@test.com",
            "password": "test",
            "full_name": "test azhar",
            "department": "IT",
            "title": "QA"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "add_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "azhartest7@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test azhar"
    And The response's "department" should be "IT"
    And The response's "title" should be "QA"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario: Login employee
    When Send a http "POST" request to "/employee/login" with body:
        """
        {
            "email": "azhartest7@test.com",
            "password": "test"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "employee_login_schema.json"
    And The response's "token" should not be null
    And Save token from the response to local storage

Scenario: Get employee data
    Given Make sure token is in local storage
    When Send a http "GET" request to "/employee/get" with body:
        """
        {} 
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "azhartest7@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test azhar"
    And The response's "department" should be "IT"
    And The response's "title" should be "QA"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario: Update employee data
    Given Make sure token is in local storage
    When Send a http "PUT" request to "/employee/update" with body:
        """
        {
            "email": "azhartest7update@test.com",
            "password": "testupdate",
            "full_name": "test azhar update",
            "department": "IT Update",
            "title": "QA Update"
        }
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "update_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "azhartest7update@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test azhar update"
    And The response's "department" should be "IT Update"
    And The response's "title" should be "QA Update"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario: Get employee data after update employee data
    Given Make sure token is in local storage
    When Send a http "GET" request to "/employee/get" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_employee_schema.json"
    And The response's "id" should not be null
    And The response's "email" should be "azhartest7update@test.com"
    And The response's "password_hash" should not be null
    And The response's "full_name" should be "test azhar update"
    And The response's "department" should be "IT Update"
    And The response's "title" should be "QA Update"
    And The response's "create_at" should not be null
    And The response's "update_at" should not be null

Scenario: Get all employee data
    When Send a http "GET" request to "/employee/get_all" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "get_all_employee_schema.json"
    And Full name should not be null

Scenario: Search employee
    When Send a http "GET" request to "/41a9698d-d8b0-42df-9ddc-89c0a1a1aa79/employee/search/azhar" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "search_employee_schema.json"
    And Search result should not be empty and contain "azhar"
    

Scenario: Delete employee
    Given Make sure token is in local storage
    When Send a http "DELETE" request to "/employee/delete" with body:
        """
        {}
        """

    Then The response status must be 200
    And The response should not be empty
    And The response schema should be match with schema "delete_employee_schema.json"