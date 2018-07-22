package com.ara.serviceapp.utils;

import com.ara.serviceapp.models.Customer;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AppService {
    @GET("app.php")
    Call<ResponseBody> validateUser(@Query("action") String action, @Query("username") String loginId,
                                    @Query("password") String password);

    @GET("app.php")
    Call<List<Customer>> listCustomers(@Query("action") String action, @Query("id") String empId);

    @GET("app.php")
    Call<ResponseBody> addServiceDetail(@Query("action") String action, @QueryMap Map<String, String> serviceDetail);

    @GET("app.php")
    Call<ResponseBody> addAttendanceDetail(@Query("action") String action, @QueryMap Map<String, String> attendance);
}
