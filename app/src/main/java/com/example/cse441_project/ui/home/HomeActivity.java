package com.example.cse441_project.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cse441_project.R;
import com.example.cse441_project.databinding.ActivityHomeBinding;
import com.example.cse441_project.ui.home.comingsoon.ComingSoonFragment;
import com.example.cse441_project.ui.home.nowplaying.NowPlayingFragment;
import com.example.cse441_project.ui.profileoverlay.ProfileActivity;
import com.example.cse441_project.ui.profileoverlay.ProfileMenuActivity;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnNowplaying.setOnClickListener(v -> {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, new NowPlayingFragment())
                        .commit();
        });
        binding.btnComingSoon.setOnClickListener(v->{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ComingSoonFragment())
                    .commit();
        });
        binding.cardViewAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileMenuActivity.class);
            startActivity(intent);
        });

    }
}