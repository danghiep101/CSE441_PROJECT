
package com.example.cse441_project.ui.profileoverlay;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cse441_project.R;
import com.example.cse441_project.data.model.ticket.Ticket;
import com.example.cse441_project.ui.profileoverlay.ProfileMenuActivity;
import com.example.cse441_project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyTicketActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_ticket);

        rcv = findViewById(R.id.rcv_my_ticket_list);
        btnBack = findViewById(R.id.btn_back_my_ticket);

        List<Ticket> list = new ArrayList<>();

        if (FirebaseUtils.currentUserId() != null) {
            FirebaseUtils.getTicketByUserId(FirebaseUtils.currentUserId())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    Ticket ticket = document.toObject(Ticket.class);
                                    list.add(ticket);
                                }

                                com.example.cse441_project.ui.profileoverlay.MyTicketAdapter adapter = new com.example.cse441_project.ui.profileoverlay.MyTicketAdapter(list);
                                rcv.setAdapter(adapter);
                                rcv.setLayoutManager(new LinearLayoutManager(MyTicketActivity.this));
                            } else {
                                Toast.makeText(MyTicketActivity.this,
                                        "Failed to load tickets.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            Log.d("Hehe", FirebaseUtils.currentUserId());
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(MyTicketActivity.this, ProfileMenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
