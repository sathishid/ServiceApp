package com.ara.serviceapp.utils;

import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.models.Employee;
import com.ara.serviceapp.models.ServiceRequestModel;
import com.ara.serviceapp.models.Truck;
import com.ara.serviceapp.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface AppService {
    @GET("app.php")
    Call<User> validateUser(@Query("action") String action, @Query("username") String loginId,
                            @Query("password") String password);

    @GET("app.php")
    Call<List<Customer>> listCustomers(@Query("action") String action, @Query("id") String empId);

    @GET("app.php")
    Call<ResponseBody> addServiceDetail(@Query("action") String action, @QueryMap Map<String, String> serviceDetail);

    @GET("app.php")
    Call<ResponseBody> addAttendanceDetail(@Query("action") String action, @QueryMap Map<String, String> attendance);

    @GET("app.php")
    Call<List<User>> listUsers(@Query("action") String action);

    @GET("app.php")
    Call<List<Employee>> listEmployees(@Query("action") String action);


    @POST("app.php")
    Call<ResponseBody> postServiceDetail(@Query("action") String action, @Body ServiceRequestModel serviceRequestModel);


    @GET("app.php")
    Call<List<Truck>> listTrucks(@Query("action") String action, @Query("id") int empId);
}


