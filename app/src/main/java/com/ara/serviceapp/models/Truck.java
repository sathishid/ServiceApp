package com.ara.serviceapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Truck {
    @SerializedName("truck_id")
    private int id;
    @SerializedName("truck_no")
    private String truckNo;

    public Truck() {
    }

    public Truck(int id, String truckNo) {
        this.id = id;
        this.truckNo = truckNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTruckNo() {
        return truckNo;
    }

    public void setTruckNo(String truckNo) {
        this.truckNo = truckNo;
    }

    public String toString() {
        return truckNo;
    }

    public static Truck fromGson(String message) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(message, Truck.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
