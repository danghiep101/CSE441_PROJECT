package com.example.admincse441project.ui.home;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admincse441project.R;
import com.example.admincse441project.ui.ticketmanagement.AddTicketFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        if (savedInstanceState == null) {
//            // Gọi FragmentA làm fragment mặc định
//            getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_add_ticket, new AddTicketFragment()) // R.id.fragment_container là ID của FrameLayout
//                .commit();
//        }
    }
}