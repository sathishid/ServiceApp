package com.ara.serviceapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ara.serviceapp.DatePickerActivity;
import com.ara.serviceapp.R;
import com.ara.serviceapp.models.Customer;
import com.ara.serviceapp.models.ServiceDetail;
import com.ara.serviceapp.models.ServiceRequestModel;
import com.ara.serviceapp.models.Truck;
import com.ara.serviceapp.models.User;
import com.ara.serviceapp.utils.AppLogic;
import com.ara.serviceapp.utils.Helper;
import com.ara.serviceapp.utils.ListActivity;
import com.ara.serviceapp.utils.MultiSelectListActivity;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ara.serviceapp.utils.Helper.CUSTOMER_EXTRA;
import static com.ara.serviceapp.utils.Helper.CUSTOMER_SELECT;
import static com.ara.serviceapp.utils.Helper.DATE_EXTRA;
import static com.ara.serviceapp.utils.Helper.DATE_REQUEST_CODE;
import static com.ara.serviceapp.utils.Helper.EMPLOYEE_SELECT;
import static com.ara.serviceapp.utils.Helper.REQUEST_CODE;
import static com.ara.serviceapp.utils.Helper.TRUCK_EXTRA;
import static com.ara.serviceapp.utils.Helper.TRUCK_SELECT;
import static com.ara.serviceapp.utils.Helper.USER_LIST_EXTRA;
import static com.ara.serviceapp.utils.Helper.dateToString;
import static com.ara.serviceapp.utils.Helper.showSnackBar;

public class ServiceFragment extends Fragment implements View.OnClickListener {

    ServiceDetail serviceDetial;
    LinearLayout mDateLayout;
    LinearLayout mCustomerLayout;
    LinearLayout mTruckNoLayout;
    LinearLayout mEmployeeLayout;
    ProgressBar progressBar;
    RelativeLayout layoutView;

    TextView mDateView;
    TextView mCustomerView;
    TextView mTruckNo;
    TextView mEmployeesView;
    EditText mLocationEdit;
    EditText mNatureOfServiceEdit;
    EditText mSpareReplacedEdit;
    EditText mSpareRequiredEdit;
    RadioButton mPendingRadio;
    RadioButton mProgressRadio;
    RadioButton mCompletedRadio;

    Button mSaveButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);
        //Fetch Details from server in background.
        AppLogic.getAppLogic().getCustomers();
        AppLogic.getAppLogic().getUsers();

        serviceDetial = new ServiceDetail();

        serviceDetial.setStatus(Helper.ServiceType.PENDING);
        serviceDetial.setDate(dateToString(Calendar.getInstance()));
        progressBar = (ProgressBar) rootView.findViewById(R.id.service_progressBar);
        layoutView = (RelativeLayout) rootView.findViewById(R.id.service_root_element_layout);

        mDateView = (TextView) rootView.findViewById(R.id.service_date);
        mDateView.setText(serviceDetial.getDate());
        mCustomerView = (TextView) rootView.findViewById(R.id.service_customer);
        mTruckNo = (TextView) rootView.findViewById(R.id.service_truck);
        mEmployeesView = (TextView) rootView.findViewById(R.id.service_employees);

        mLocationEdit = (EditText) rootView.findViewById(R.id.service_location);
        mNatureOfServiceEdit = (EditText) rootView.findViewById(R.id.service_nature);
        mSpareReplacedEdit = (EditText) rootView.findViewById(R.id.service_spare_replace);
        mSpareRequiredEdit = (EditText) rootView.findViewById(R.id.service_spare_required);

        mPendingRadio = (RadioButton) rootView.findViewById(R.id.service_pending);
        mPendingRadio.setOnClickListener(this);

        mProgressRadio = (RadioButton) rootView.findViewById(R.id.service_progress);
        mProgressRadio.setOnClickListener(this);

        mCompletedRadio = (RadioButton) rootView.findViewById(R.id.service_completed);
        mCompletedRadio.setOnClickListener(this);

        mSaveButton = (Button) rootView.findViewById(R.id.service_save);
        mSaveButton.setOnClickListener(this);

        mDateLayout = (LinearLayout) rootView.findViewById(R.id.service_date_layout);
        mDateLayout.setOnClickListener(this);

        mCustomerLayout = (LinearLayout) rootView.findViewById(R.id.service_customer_layout);
        mCustomerLayout.setOnClickListener(this);

        mTruckNoLayout = (LinearLayout) rootView.findViewById(R.id.service_truck_layout);
        mTruckNoLayout.setOnClickListener(this);

        mEmployeeLayout = (LinearLayout) rootView.findViewById(R.id.service_employee_layout);
        mEmployeeLayout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.service_pending:
                serviceDetial.setStatus(Helper.ServiceType.PENDING);
                break;
            case R.id.service_completed:
                serviceDetial.setStatus(Helper.ServiceType.COMPLETED);
                break;
            case R.id.service_progress:
                serviceDetial.setStatus(Helper.ServiceType.IN_PROGRESS);
                break;
            case R.id.service_save:
                saveDetails();
                break;
            case R.id.service_date_layout:
                intent = new Intent(getContext(), DatePickerActivity.class);
                intent.putExtra(DATE_EXTRA, serviceDetial.getDate());
                startActivityForResult(intent, DATE_REQUEST_CODE);
                break;
            case R.id.service_customer_layout:
                intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra(REQUEST_CODE, CUSTOMER_SELECT);

                startActivityForResult(intent, CUSTOMER_SELECT);
                break;
            case R.id.service_truck_layout:
                if (serviceDetial.getCustomer() == null) {
                    showSnackBar(mCustomerLayout, R.string.choose_customer);
                    return;
                }
                List<Truck> truckList = AppLogic.getAppLogic().getTrucks(serviceDetial.getCustomer().getId());
                serviceDetial.getCustomer().setTruckList(truckList);
                intent = new Intent(getContext(), ListActivity.class);
                intent.putExtra(REQUEST_CODE, TRUCK_SELECT);
                intent.putExtra(CUSTOMER_EXTRA, serviceDetial.getCustomer().toJson());
                startActivityForResult(intent, TRUCK_SELECT);
                break;
            case R.id.service_employee_layout:
                intent = new Intent(getContext(), MultiSelectListActivity.class);
                List<User> users = serviceDetial.getUsers();
                if (users != null && users.size() > 0) {
                    intent.putExtra(USER_LIST_EXTRA, User.toJson(users));
                } else {
                    intent.putExtra(USER_LIST_EXTRA, "");
                }

                startActivityForResult(intent, EMPLOYEE_SELECT);
                break;
        }
    }

    private void saveDetails() {
        if (hasError()) {
            return;
        }
        serviceDetial.setLogedUser(AppLogic.getAppLogic().CurrentUser);
        serviceDetial.getCustomer().setTruckList(null);

        ServiceRequestModel serviceRequestModel = ServiceRequestModel.fromServiceDetail(serviceDetial);


        Call<ResponseBody> call = AppLogic.getAppLogic().getAppService().postServiceDetail("serviceAdd", serviceRequestModel);
        showProgress(true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    showProgress(false);
                    showSnackBar(mCustomerLayout, "Service Saved...");
                    clearAll();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showProgress(false);
                showSnackBar(layoutView, t.getMessage());
            }
        });

    }

    private void clearAll() {
        serviceDetial = new ServiceDetail();
        mCustomerView.setText(R.string.sample_company_name);
        mTruckNo.setText(R.string.sample_truck_no);

        mLocationEdit.setText("");
        mNatureOfServiceEdit.setText("");
        mSpareReplacedEdit.setText("");
        mSpareRequiredEdit.setText("");
        mPendingRadio.toggle();
    }

    private boolean hasError() {

        serviceDetial.setLocation(mLocationEdit.getText().toString());
        serviceDetial.setNatureOfService(mNatureOfServiceEdit.getText().toString());
        serviceDetial.setRequiredSpare(mSpareRequiredEdit.getText().toString());
        serviceDetial.setSpareReplace(mSpareReplacedEdit.getText().toString());
        if (serviceDetial.getCustomer() == null) {
            showSnackBar(mCustomerLayout, "Select Customer");
            return true;
        }
        if (serviceDetial.getTruck() == null) {
            showSnackBar(mCustomerLayout, "Select Truck");
            return true;
        }
        if (serviceDetial.getLocation().isEmpty()) {
            mLocationEdit.setError("Location should not be empty");
            return true;
        }
        if (serviceDetial.getNatureOfService().isEmpty()) {
            mNatureOfServiceEdit.setError("Nature of service should not be empty");
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        String json;
        switch (requestCode) {
            case DATE_REQUEST_CODE:
                String date = data.getStringExtra(DATE_EXTRA);
                serviceDetial.setDate(date);
                mDateView.setText(date);

                break;
            case CUSTOMER_SELECT:
                json = data.getStringExtra(CUSTOMER_EXTRA);
                Customer customer = Customer.fromGson(json);
                customer.setTruckList(AppLogic.getAppLogic().getTrucks(customer.getId()));
                serviceDetial.setCustomer(customer);
                mCustomerView.setText(customer.getCustomerName());
                break;
            case TRUCK_SELECT:
                json = data.getStringExtra(TRUCK_EXTRA);
                Truck truck = Truck.fromGson(json);
                serviceDetial.setTruck(truck);
                mTruckNo.setText(truck.getTruckNo());
                break;
            case EMPLOYEE_SELECT:
                json = data.getStringExtra(USER_LIST_EXTRA);
                serviceDetial.setUsers(User.fromJsonArray(json));
                List<User> users = serviceDetial.getUsers();
                if (users != null) {
                    String strUsers = "";
                    int count = 0;
                    for (User user : users) {
                        strUsers = strUsers + user.getEmpNo() + ",";
                        count++;
                        if (count >= 3)
                            break;

                    }
                    count = users.size() - 2;
                    if (count > 0)
                        strUsers = strUsers + "...(+" + count + ")";
                    mEmployeesView.setText(strUsers);
                }
                break;

        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
            layoutView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            layoutView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
