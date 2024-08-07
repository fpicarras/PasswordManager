package JsonHandler;

import org.json.JSONObject;

public interface JsonHandler {
    JSONObject getJSON(String content);

    String getString(JSONObject content);

    JSONObject update(JSONObject content);

    JSONObject generate();
}
