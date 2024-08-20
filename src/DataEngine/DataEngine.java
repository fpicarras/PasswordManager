package DataEngine;

import Crypt.EncryptAlgorithm;
import JsonHandler.JsonHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataEngine {
    private final JsonHandler j;
    private final EncryptAlgorithm c;

    private String key = null;
    private JSONObject data;
    private  ArrayList<Entry> entries = null;

    private String filename = "pmdata.txt";

    public DataEngine(JsonHandler j, EncryptAlgorithm c){
        this.j = j;
        this.c = c;
    }

    public DataEngine(JsonHandler j, EncryptAlgorithm c, String filename){
        this.j = j;
        this.c = c;
        this.filename = filename;
    }

    public boolean open(){
        data = j.getJSON(c.decrypt(this.readFile(filename), key));
        if(data == null) return false;
        entries = getEntries(data);
        //System.out.println("Opening data : " + entries);
        return true;
    }

    public void save(){
        if(entries == null) return;

        data.put("content", getContent(entries));
        this.writeFile(filename, c.encrypt(j.getString(data), key));
    }

    public ArrayList<Entry> getEntries(){
        return entries;
    }

    private ArrayList<Entry> getEntries(JSONObject content){
        ArrayList<Entry> entries = new ArrayList<>();
        for(Object entry : content.getJSONArray("content")){
            entries.add(new Entry((JSONObject) entry));
        }
        return entries;
    }

    public void deleteEntries(){
        entries = null;
    }

    private JSONArray getContent(ArrayList<Entry> entries){
        JSONArray content = new JSONArray();
        for(Entry entry : entries){
            JSONObject entryJson = new JSONObject();
            entryJson.put("title", entry.getTitle());
            entryJson.put("description", entry.getDescription());
            entryJson.put("username", entry.getUsername());
            entryJson.put("email", entry.getEmail());
            entryJson.put("secret", entry.getSecret());
            entryJson.put("date", entry.getDate());
            content.put(entryJson);
        }
        return content;
    }

    //Check if file exists
    public boolean fileExists(){
        return Files.exists(Paths.get(filename));
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    //Generate a 16 character key
    public String generatePassword(){
        return "WIP";
    }

    public String getDefaultMail(){
        try {
            return data.getString("defaultMail");
        } catch (Exception e){
            return "";
        }
    }

    public void setDefaultMail(String mail){
        data.put("defaultMail", mail);
    }

    //Generate an email
    public String generateEmail(){
        return "WIP";
    }

    protected void writeFile(String filePath, String str) {
        List<String> lines = List.of(str);
        try {
            Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
