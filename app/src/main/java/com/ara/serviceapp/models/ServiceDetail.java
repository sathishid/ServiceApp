package com.ara.serviceapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ServiceDetail {

    private String date;
    private Customer customer;
    private Truck truck;
    private String location;
    private String natureOfService;
    private String spareReplace;
    private String spareReject;
    private String requiredSpare;
    private int status;
    private List<User> users;
    private User logedUser;

    public User getLogedUser() {
        return logedUser;
    }

    public void setLogedUser(User logedUser) {
        this.logedUser = logedUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNatureOfService() {
        return natureOfService;
    }

    public void setNatureOfService(String natureOfService) {
        this.natureOfService = natureOfService;
    }

    public String getSpareReject() {
        return spareReject;
    }

    public void setSpareReject(String spareReject) {
        this.spareReject = spareReject;
    }

    public String getSpareReplace() {
        return spareReplace;
    }

    public void setSpareReplace(String spareReplace) {
        this.spareReplace = spareReplace;
    }

    public String getRequiredSpare() {
        return requiredSpare;
    }

    public void setRequiredSpare(String requiredSpare) {
        this.requiredSpare = requiredSpare;
    }

    public static ServiceDetail fromGson(String message) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(message, ServiceDetail.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
