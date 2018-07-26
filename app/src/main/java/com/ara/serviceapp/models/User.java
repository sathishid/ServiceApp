package com.ara.serviceapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class User {
    @SerializedName(value = "userid", alternate = {"employee_id"})
    private int userId;
    @SerializedName(value = "username", alternate = {"employee_name"})
    private String userName;
    @SerializedName("name")
    private String name;
    private String password;
    @SerializedName("employee_no")
    private String empNo;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public User(int userId, String userName, String empNo) {
        this.userId = userId;
        this.userName = userName;
        this.empNo = empNo;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

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
            JSONObject reader = new JSONObject(message);
            User user = new User();

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

    @Override
    public String toString() {
        return userName + "-" + empNo;
    }

    public static String toJson(List<User> userList) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(userList);
    }

    public static List<User> fromJsonArray(String message) {
        Gson gson = new GsonBuilder().create();
        User[] users = gson.fromJson(message, User[].class);
        return Arrays.asList(users);
    }
}
