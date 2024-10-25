package com.example.admincse441project.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincse441project.R;
import com.example.admincse441project.data.model.Management;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvManagementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        List<Management> managementList = new ArrayList<>();
        managementList.add(new Management("Account management"));
        managementList.add(new Management("Discount management"));
        managementList.add(new Management("Seat management"));
        managementList.add(new Management("Movie ticket management"));
        managementList.add(new Management("Showtime management"));

        rcvManagementList = findViewById(R.id.rcv_management_list);
        ManagementListRecyclerViewAdapter adapter = new ManagementListRecyclerViewAdapter(managementList, getSupportFragmentManager());

        rcvManagementList.setAdapter(adapter);
        rcvManagementList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}