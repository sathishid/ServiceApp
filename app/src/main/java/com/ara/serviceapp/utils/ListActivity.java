package com.ara.serviceapp.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ara.serviceapp.R;
import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.models.Truck;

import java.util.List;

import static com.ara.serviceapp.utils.Helper.CUSTOMER_EXTRA;
import static com.ara.serviceapp.utils.Helper.CUSTOMER_SELECT;
import static com.ara.serviceapp.utils.Helper.REQUEST_CODE;
import static com.ara.serviceapp.utils.Helper.TRUCK_EXTRA;
import static com.ara.serviceapp.utils.Helper.TRUCK_SELECT;
import static com.ara.serviceapp.utils.Helper.showSnackBar;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private int requestCode;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.list_view);

        Intent intent = getIntent();
        requestCode = intent.getIntExtra(REQUEST_CODE, -1);
        String customer = intent.getStringExtra(CUSTOMER_EXTRA);
        listView.setOnItemClickListener(this);

        switch (requestCode) {
            case CUSTOMER_SELECT:
                List<Customer> customerList = AppLogic.getAppLogic().getCustomers();
                if (customerList == null) {
                    showSnackBar(listView, R.string.something_went_wrong);
                    return;
                }
                ArrayAdapter<Customer> arrayAdapter = new ArrayAdapter<Customer>(this,
                        android.R.layout.simple_list_item_1, customerList);
                listView.setAdapter(arrayAdapter);
                break;
            case TRUCK_SELECT:
                Customer customerObj = Customer.fromGson(customer);
                ArrayAdapter<Truck> truckArrayAdapter = new ArrayAdapter<Truck>(this,
                        android.R.layout.simple_list_item_1, customerObj.getTruckList());
                listView.setAdapter(truckArrayAdapter);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (requestCode) {
            case CUSTOMER_SELECT:
                Customer customer = (Customer) parent.getItemAtPosition(position);
                AppLogic.getAppLogic().getTrucks(customer.getId());
                String json = customer.toJson();
                Intent intent = new Intent();
                intent.putExtra(CUSTOMER_EXTRA, json);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case TRUCK_SELECT:
                Truck truck = (Truck) parent.getItemAtPosition(position);
                String truckJson = truck.toJson();
                Intent truckIntent = new Intent();
                truckIntent.putExtra(TRUCK_EXTRA, truckJson);
                setResult(RESULT_OK, truckIntent);
                finish();
                break;
        }
    }
}
