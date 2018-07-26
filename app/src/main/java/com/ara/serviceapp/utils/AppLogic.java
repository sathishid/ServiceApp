package com.ara.serviceapp.utils;

import android.util.Log;

import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.models.Truck;
import com.ara.serviceapp.models.User;

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
    private static List<User> users;

    private AppLogic() {
        retrofit = new Retrofit.Builder()
                .baseUrl(REST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        appService = retrofit.create(AppService.class);
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

    public List<User> getUsers() {
        if (users == null || users.size() == 0) {
            appService.listUsers("employee")
                    .enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            users = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            users = null;
                            Log.d("Get Users", t.getMessage());
                        }
                    });
        }
        return users;
    }


    public static List<Truck> trucks;

    public List<Truck> getTrucks(int companyId) {
        if (trucks == null || trucks.size() == 0) {
            appService.listTrucks("truck", companyId)
                    .enqueue(new Callback<List<Truck>>() {
                        @Override
                        public void onResponse(Call<List<Truck>> call, Response<List<Truck>> response) {
                            trucks = response.body();
                        }

                        @Override
                        public void onFailure(Call<List<Truck>> call, Throwable t) {
                            users = null;
                            Log.d("Get Users", t.getMessage());
                        }
                    });
        }
        return trucks;
    }


}
