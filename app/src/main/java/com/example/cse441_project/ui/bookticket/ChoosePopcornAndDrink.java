package com.example.cse441_project.ui.bookticket;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.ui.bookticket.custom_adapter.PopcornAndDrinkAdapter;
import com.example.cse441_project.ui.bookticket.instance.PopcornAndDrinkItem;

import java.util.ArrayList;
import java.util.List;

public class ChoosePopcornAndDrink extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_popcorn_and_drink);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_switch_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<PopcornAndDrinkItem> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new PopcornAndDrinkItem(R.drawable.ic_launcher_background, "Popcorn and drink name", "price: " + 200.000 + " vnd"));
        }

        recyclerView = findViewById(R.id.rcv_popcorn_and_drink);
        PopcornAndDrinkAdapter adapter = new PopcornAndDrinkAdapter(list);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }
}