package com.ara.serviceapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    @SerializedName("customer_id")
    private int id;

    @SerializedName("customer_no")
    private String customerNo;

    @SerializedName("customer_name")
    private String customerName;

    private String mobileNo;
    private String contactPerson;
    private List<Truck> truckList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<Truck> getTruckList() {
        if (truckList == null) {
            truckList = new ArrayList<>(3);
        }
        return truckList;
    }

    public void setTruckList(List<Truck> truckList) {
        this.truckList = truckList;
    }

    public void addTruck(Truck truck) {
        getTruckList().add(truck);
    }

    public Customer(int id, String customerNo, String customerName) {
        this.id = id;
        this.customerNo = customerNo;
        this.customerName = customerName;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return customerName + " - " + customerNo;
    }

    public static Customer fromGson(String message) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(message, Customer.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
