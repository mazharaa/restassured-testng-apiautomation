package restassured;

import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;

public class BaseTest {
    protected final String BASEURL = "https://whitesmokehouse.com/webhook/";
    protected static String token;
    protected static int objectId;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = BASEURL;
    }
}
