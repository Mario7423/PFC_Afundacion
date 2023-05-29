package com.example.app.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class New implements Serializable {

    private String title, image, date, text;

    public New(JSONObject object) throws JSONException {

        this.title = object.getString("title");
        this.image = object.getString("image");
        this.date = object.getString("date");
        this.text = object.getString("text");

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
