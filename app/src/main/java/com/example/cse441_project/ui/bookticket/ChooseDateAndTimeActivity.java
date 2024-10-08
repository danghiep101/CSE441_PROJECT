package com.example.cse441_project.ui.bookticket;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.bookticket.custom_adapter.DateAndTimeAdapter;
import com.example.cse441_project.ui.bookticket.custom_adapter.TimeAndScreenAdapter;
import com.example.cse441_project.ui.bookticket.instance.DateAndTimeItem;
import com.example.cse441_project.ui.bookticket.instance.TimeAndScreen;

import java.util.ArrayList;
import java.util.List;

public class ChooseDateAndTimeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_date_and_time);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_choose_date_and_time), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<DateAndTimeItem> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new DateAndTimeItem("Sun", "03"));
        }

        List<TimeAndScreen> list2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list2.add(new TimeAndScreen("Screen " + (i + 1), "12:00 - 13:45"));
        }

        recyclerView = findViewById(R.id.rcv_date_and_time);
        DateAndTimeAdapter adapter = new DateAndTimeAdapter(list);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = findViewById(R.id.rcv_time_and_screen);
        TimeAndScreenAdapter adapter2 = new TimeAndScreenAdapter(list2);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 3));
    }
}