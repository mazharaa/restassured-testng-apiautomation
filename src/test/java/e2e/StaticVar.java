package e2e;

import com.demo.restassured_testng.api_automation.model.EmployeeModel;

public class StaticVar {
    public final static String BASE_URL = "https://whitesmokehouse.com/webhook/";

    public static String token;

    public static EmployeeModel employee;
}
