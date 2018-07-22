package com.ara.serviceapp.utils;

import android.util.Log;

import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.models.Truck;
import com.ara.serviceapp.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ara.serviceapp.utils.Helper.REST_URL;

public class AppLogic {
    public User CurrentUser;
    private Retrofit retrofit;
    private AppService appService;
    private List<Customer> customers;
    private static AppLogic appLogic;

    private AppLogic() {
        retrofit = new Retrofit.Builder()
                .baseUrl(REST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        appService = retrofit.create(AppService.class);
        customers = new ArrayList<>();
        Customer customer = new Customer(1, "EMP001", "E1");
        customer.addTruck(new Truck(11, "TN11"));
        customer.addTruck(new Truck(12, "TN12"));
        customers.add(customer);

        customer = new Customer(2, "EMP002", "E2");
        customer.addTruck(new Truck(21, "TN21"));
        customer.addTruck(new Truck(22, "TN22"));
        customers.add(customer);

        customer = new Customer(3, "EMP003", "E3");
        customer.addTruck(new Truck(31, "TN31"));
        customer.addTruck(new Truck(32, "TN32"));
        customers.add(customer);

    }

    public AppService getAppService() {
        return appService;

    }

    public static AppLogic getAppLogic() {
        if (appLogic == null) {
            appLogic = new AppLogic();
        }
        return appLogic;

    }

    public List<Customer> getCustomers() {
        if (customers == null || customers.size() == 0) {
            appService.listCustomers("customer", CurrentUser.getUserId() + "")
                    .enqueue(new Callback<List<Customer>>() {
                        @Override
                        public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                            customers = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<Customer>> call, Throwable t) {
                            customers = null;
                            Log.d("Get Customer", t.getMessage());
                        }
                    });
        }
        return customers;
    }
}
