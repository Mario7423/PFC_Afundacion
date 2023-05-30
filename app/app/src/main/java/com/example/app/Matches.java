package com.example.app;

import org.json.JSONException;
import org.json.JSONObject;

public class Matches {

    private String home, visiting, date;

    public Matches(JSONObject object) throws JSONException {

        home = object.getString("home");
        visiting = object.getString("visiting");
        date = object.getString("date");

    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getVisiting() {
        return visiting;
    }

    public void setVisiting(String visiting) {
        this.visiting = visiting;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
