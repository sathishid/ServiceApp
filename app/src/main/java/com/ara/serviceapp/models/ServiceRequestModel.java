package com.ara.serviceapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestModel {
    private int customerId;
    @SerializedName("sdate")
    private String date;
    private String location;
    private String natureOfService;
    private String requiredSpare;
    private String spareReplaced;
    private String spareRejected;
    private int status;
    private List<Integer> employees;
    private int loggedUserId;
    private int truckId;

    public String getSpareRejected() {
        return spareRejected;
    }

    public void setSpareRejected(String spareRejected) {
        this.spareRejected = spareRejected;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getRequiredSpare() {
        return requiredSpare;
    }

    public void setRequiredSpare(String requiredSpare) {
        this.requiredSpare = requiredSpare;
    }

    public String getSpareReplaced() {
        return spareReplaced;
    }

    public void setSpareReplaced(String spareReplaced) {
        this.spareReplaced = spareReplaced;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Integer> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Integer> employees) {
        this.employees = employees;
    }

    public int getLoggedUserId() {
        return loggedUserId;
    }

    public void setLoggedUserId(int loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    public static ServiceRequestModel fromServiceDetail(ServiceDetail serviceDetail) {
        ServiceRequestModel serviceRequestModel = new ServiceRequestModel();
        serviceRequestModel.customerId = serviceDetail.getCustomer().getId();
        serviceRequestModel.date = serviceDetail.getDate();
        serviceRequestModel.location = serviceDetail.getLocation();
        serviceRequestModel.natureOfService = serviceDetail.getNatureOfService();
        serviceRequestModel.requiredSpare = serviceDetail.getRequiredSpare();
        serviceRequestModel.spareReplaced = serviceDetail.getSpareReplace();
        serviceRequestModel.status = serviceDetail.getStatus();
        serviceRequestModel.truckId = serviceDetail.getTruck().getId();
        serviceRequestModel.spareRejected = serviceDetail.getSpareReject();
        if (serviceDetail.getUsers() != null) {
            serviceRequestModel.employees = new ArrayList<>(serviceDetail.getUsers().size());
            for (User user : serviceDetail.getUsers()) {
                serviceRequestModel.employees.add(user.getUserId());
            }
        }
        serviceRequestModel.loggedUserId = serviceDetail.getLogedUser().getUserId();
        return serviceRequestModel;
    }
}
