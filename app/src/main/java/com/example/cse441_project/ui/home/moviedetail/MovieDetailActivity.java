package com.example.cse441_project.ui.home.moviedetail;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import com.example.cse441_project.data.model.moviedetail.MovieDetail;

import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;
import com.example.cse441_project.databinding.ActivityMovieDetailBinding;
import com.example.cse441_project.ui.bookticket.ChooseDateAndTimeActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MovieDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding binding;
    private DetailMovieViewModel viewModel;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(DetailMovieViewModel.class);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        id = intent.getIntExtra("MOVIE_ID", -1);
        if(id != -1){
            observeViewModel();
            viewModel.loadMovie(id);
        } else {
            Toast.makeText(this, "movieId is null", Toast.LENGTH_SHORT).show();
        }

    }


    private void observeViewModel() {
        viewModel.movieDetail.observe(this, detailMovie ->{
            if(detailMovie != null){
                bindData(detailMovie);
            }
        });

        viewModel.movieTrailer.observe(this, movieTrailers -> {
            if (movieTrailers != null && !movieTrailers.isEmpty()) {
                MovieTrailerItem firstTrailer = movieTrailers.get(0);
                setupPlayer(firstTrailer);
            } else {
                Toast.makeText(this, "No trailer available", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.error.observe(this, error ->{
            if(error != null){
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void bindData(MovieDetail movieDetail) {
        binding.txtName.setText(movieDetail.getTitle());
        binding.txtVoteAvarage.setText(String.valueOf(movieDetail.getVoteAverage()));
        binding.txtTotalVote.setText(String.valueOf(movieDetail.getVoteCount()));
        binding.txtDate.setText(String.valueOf(movieDetail.getReleaseDate()));
        int age = movieDetail.isAdult() ? 18 : 13;
        binding.txtAge.setText(String.valueOf(age));
        binding.txtDuration.setText(movieDetail.getRuntime() + " min");
        binding.txtDescription.setText(movieDetail.getOverview());
        binding.btnBookTicket.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseDateAndTimeActivity.class);
            startActivity(intent);
        });

    }
    private void setupPlayer(MovieTrailerItem movieTrailerItem){
        YouTubePlayerView youTubePlayerView = binding.youtubePlayerView;
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = movieTrailerItem.getKey();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

}