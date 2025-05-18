package restassured;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;

public class BaseTest {
    protected final String BASEURL = "https://whitesmokehouse.com/webhook/";
    protected String token;

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = BASEURL;
    }
}
