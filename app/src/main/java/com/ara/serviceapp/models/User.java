package com.ara.serviceapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private int userId;
    private String userName;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User fromJson(String message) {
        try {
            JSONArray jsonArray = new JSONArray(message);
            User user = new User();
            JSONObject reader = jsonArray.getJSONObject(0);
            user.userId = Integer.parseInt(reader.getString("userid"));
            return user;
        } catch (JSONException jsonException) {
            return null;
        }
    }

    public static User fromGson(String message) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(message, User.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

}
