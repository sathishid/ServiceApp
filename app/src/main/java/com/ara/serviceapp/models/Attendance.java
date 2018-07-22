package com.ara.serviceapp.models;

import com.ara.serviceapp.utils.AppLogic;

import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private String date;
    private String time;
    private Customer customer;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<String, String> toHashMap() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("date", date);
        queryMap.put("time", date);
        queryMap.put("employee_id", AppLogic.getAppLogic().CurrentUser.getUserId() + "");
        queryMap.put("customer_id", date);
        return queryMap;
    }
}
