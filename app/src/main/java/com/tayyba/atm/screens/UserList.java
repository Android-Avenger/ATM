package com.tayyba.atm.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tayyba.atm.R;
import com.tayyba.atm.UserListAdapter;
import com.tayyba.atm.db.ATMDb;

public class UserList extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        ATMDb myDB = new ATMDb(this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserListAdapter adapter = new UserListAdapter(myDB.getAllUsers(),UserList.this);
        recyclerView.setAdapter(adapter);


    }
}