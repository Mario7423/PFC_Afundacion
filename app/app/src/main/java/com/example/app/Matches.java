package com.example.app;

import org.json.JSONException;
import org.json.JSONObject;

public class Matches {

    private String home, visiting, date, hour;

    public Matches(JSONObject object) throws JSONException {

        this.home = object.getString("home");
        this.visiting = object.getString("visiting");
        this.date = object.getString("date");
        this.hour = object.getString("hour");

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

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
