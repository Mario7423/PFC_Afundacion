package com.example.app.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Player implements Serializable {

    private String name, nickname, image, position, team, nationality;
    private int age, number;

    public Player(JSONObject object) throws JSONException{  // Método constructor que recibe un JSONObject para formar un nuevo jugador

        this.name = object.getString("name");
        this.age = object.getInt("age");
        this.number = object.getInt("number");
        this.image = object.getString("image");
        this.team = object.getString("team");
        this.nationality = object.getString("nationality");
        this.nickname = object.getString("nickname");
        this.position = object.getString("position");

    }

    public String getName() {
        return name;
    } // Getter & Setter

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
