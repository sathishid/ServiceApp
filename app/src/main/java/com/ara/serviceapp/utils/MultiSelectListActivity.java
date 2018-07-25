package com.ara.serviceapp.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.ara.serviceapp.R;
import com.ara.serviceapp.models.User;

import java.util.ArrayList;
import java.util.List;

import static com.ara.serviceapp.utils.Helper.USER_LIST_EXTRA;

public class MultiSelectListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayAdapter<User> mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    ListView listView;
    List<User> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_list);

        Intent intent = getIntent();
        String json = intent.getStringExtra(USER_LIST_EXTRA);
        selectedItems = new ArrayList<User>();
        if (json == null || json.isEmpty())
            selectedItems = new ArrayList<User>();
//        else
//            selectedItems = User.fromJsonArray(json);
        listView = (ListView) findViewById(R.id.search_recycler_view);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);


        List<User> userList = AppLogic.getAppLogic().getUsers();

        if (userList != null) {
            mAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_multiple_choice, userList);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;

        if (checkedTextView.isChecked()) {
            selectedItems.remove(mAdapter.getItem(position));
        } else {
            selectedItems.add(mAdapter.getItem(position));
        }
        checkedTextView.toggle();
    }

    @Override
    public void onBackPressed() {
        if (selectedItems.size() == 0) {
            setResult(RESULT_CANCELED);
            finish();
        }

        Intent intent = new Intent();
        intent.putExtra(USER_LIST_EXTRA, User.toJson(selectedItems));

        setResult(RESULT_OK, intent);
        finish();
    }
}
