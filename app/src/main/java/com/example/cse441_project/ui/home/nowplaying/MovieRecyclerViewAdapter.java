package com.example.cse441_project.ui.home.nowplaying;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_project.data.model.movie.ResultsItem;
import com.example.cse441_project.databinding.ItemShowingMovieBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<ResultsItem> movieList;
    private final OnItemClickListener listener;

    public MovieRecyclerViewAdapter(ArrayList<ResultsItem> movieList, OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShowingMovieBinding binding = ItemShowingMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bindData(movieList.get(position));
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(movieList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovies(List<ResultsItem> newMovies){
        int startPosition = movieList.size();
        movieList.addAll(newMovies);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemShowingMovieBinding binding;
        public ViewHolder(ItemShowingMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindData(ResultsItem resultsItem){
            String postUrl = "https://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath();
            Glide.with(binding.getRoot().getContext()).load(postUrl).into(binding.imgPoster);
            binding.txtTitle.setText(resultsItem.getTitle());

            int voteAverage = Math.round(resultsItem.getVoteAverage());
            int starCount = (int) Math.floor(voteAverage / 1.5);
            StringBuilder stars = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                if (i < starCount) {
                    stars.append("⭐");
                } else {
                    stars.append("☆");
                }
            }
            binding.txtVote.setText(stars.toString());

            binding.txtReleaseDate.setText(resultsItem.getReleaseDate().toString());

            String genres = TextUtils.join(", ", resultsItem.getGenreNames());
            binding.txtGenre.setText(genres);
        }

    }
    public interface OnItemClickListener{
        void onItemClick(ResultsItem movie);
    }

}
