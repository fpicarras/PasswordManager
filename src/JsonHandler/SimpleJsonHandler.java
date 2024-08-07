package JsonHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SimpleJsonHandler implements JsonHandler{
    private static final String version = "0.0.1a";

    @Override
    public JSONObject getJSON(String content) {
        if(content == null) return this.generate();
        try{
            JSONObject json = new JSONObject(content);
            if(!json.getString("version").equals(version)) json = this.update(json);
            return json;
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(JSONObject content) {
        content.put("lastAccess", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()));
        return content.toString(4);
    }

    @Override
    public JSONObject update(JSONObject content) {
        System.out.println("Updating from " + content.getString("version") + " to " + version + "...");
        return content;
    }

    @Override
    public JSONObject generate(){
        System.out.println("Generating new content...");
        JSONObject newContent = new JSONObject();

        newContent.put("version", version);
        newContent.put("lastAccess", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()));

        JSONObject example = new JSONObject("""
                {
                    "title": "Docinhos da Maria",
                    "description": "Bakery account",
                    "username": "fpicarras",
                    "email": "fpicarras@fakemail.com",
                    "secret": "password123",
                    "date": "21:00 07/08/2024"
                }""");
        newContent.put("content", (new JSONArray()).put(example));

        return newContent;
    }
}
