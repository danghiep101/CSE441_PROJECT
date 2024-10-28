package com.example.cse441_project.ui.home.moviedetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.data.model.moviedetail.MovieDetail;
import com.example.cse441_project.data.model.movietrailer.MovieTrailerItem;
import com.example.cse441_project.databinding.ActivityMovieDetailBinding;
import com.example.cse441_project.ui.bookticket.showscreen.ChooseDateAndTimeActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MovieCinemaDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding binding;
    private DetailMovieViewModel viewModel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DetailMovieViewModel.class);
        Intent intent = getIntent();
        id = intent.getIntExtra("MOVIE_ID", -1);
        String source = intent.getStringExtra("SOURCE");



        if (id != -1) {
            observeViewModel();
            viewModel.loadMovie(id);
            binding.btnBookTicket.setVisibility("NowPlayingMovieFragment".equals(source) ? View.VISIBLE : View.GONE);
        } else {
            Toast.makeText(this, "Movie is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeViewModel() {
        viewModel.movieDetail.observe(this, detailMovie -> {
            if (detailMovie != null) {
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

        viewModel.error.observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindData(MovieDetail movieDetail) {
        binding.txtName.setText(movieDetail.getTitle());
        binding.txtVoteAvarage.setText("Vote average: " + movieDetail.getVoteAverage());
        binding.txtTotalVote.setText(movieDetail.getVoteCount() + " votes");
        binding.txtDate.setText(movieDetail.getReleaseDate());
        binding.txtAge.setText(movieDetail.isAdult() ? "18" : "16");
        binding.txtDuration.setText(movieDetail.getRuntime() + " min");
        binding.txtDescription.setText(movieDetail.getOverview());
        binding.btnBookTicket.setOnClickListener(v -> {
            Log.d("MovieCinemaDetailActivity", "Received Movie ID: " + id);
            Intent intent = new Intent(this, ChooseDateAndTimeActivity.class);
            intent.putExtra("MOVIE_ID", id);
            intent.putExtra("MOVIE_NAME", movieDetail.getTitle());
            startActivity(intent);
        });
    }

    private void setupPlayer(MovieTrailerItem movieTrailerItem) {
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
}
