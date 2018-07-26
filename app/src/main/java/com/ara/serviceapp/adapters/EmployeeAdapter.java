package com.ara.serviceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.ara.serviceapp.models.User;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<User> {

    List<User> userList;


    public EmployeeAdapter(Context context, List<User> userList) {
        super(context, android.R.layout.simple_list_item_multiple_choice, userList);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getUserId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice,
                    parent, false);
        }
        // Lookup view for data population
        CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(android.R.id.text1);
        checkedTextView.setText(user.toString());

        checkedTextView.setChecked(user.isChecked());
        return convertView;
    }
}
