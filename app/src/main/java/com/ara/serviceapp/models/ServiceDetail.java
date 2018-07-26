package com.ara.serviceapp.models;

import com.ara.serviceapp.utils.AppLogic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDetail {

    private String date;
    private Customer customer;
    private Truck truck;
    private String location;
    private String natureOfService;
    private String spareReplace;
    private String requiredSpare;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public Map<String, String> toHashMap() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("date", date);
        queryMap.put("employee_id", AppLogic.getAppLogic().CurrentUser.getUserId() + "");
        queryMap.put("customer_id", customer.getId() + "");
        queryMap.put("truck_id", truck.getId() + "");
        queryMap.put("location", location);
        queryMap.put("nature_of_service", natureOfService);
        queryMap.put("required_spare", requiredSpare);
        queryMap.put("spare_replace", spareReplace);
        queryMap.put("status", status);
        return queryMap;
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
