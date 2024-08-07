package DataEngine;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Entry {
    private String title;
    private String description;
    private String username;
    private String email;
    private String secret;
    private String date;

    public Entry(JSONObject entry){
        title = entry.getString("title");
        description = entry.getString("description");
        username = entry.getString("username");
        email = entry.getString("email");
        secret = entry.getString("secret");
        date = entry.getString("date");
    }

    private void updateAccessDate(){
        date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
    }

    public String toString(){
        return "Title: " + title + "\nDescription: " + description + "\n\tUsername: " + username +
                "\n\tEmail: " + email + "\n\tSecret: " + secret + "\nLast Modified: " + date + "\n";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.updateAccessDate();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.updateAccessDate();
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.updateAccessDate();
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.updateAccessDate();
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.updateAccessDate();
        this.secret = secret;
    }

    public String getDate() {
        return date;
    }
}
