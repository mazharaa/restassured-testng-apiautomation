package context;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class TestContext {
    private Response response;
    private static final Map<String, Object> data = new HashMap<>();

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setData(String key, Object value) {
        data.put(key, value);
    }

    public <T> T getData(String key, Class<T> type) {
        return type.cast(data.get(key));
    }
}
