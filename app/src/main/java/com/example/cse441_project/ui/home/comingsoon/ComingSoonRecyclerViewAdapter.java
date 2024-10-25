package com.example.cse441_project.ui.home.comingsoon;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_project.data.model.movie.ResultsItem;
import com.example.cse441_project.databinding.ItemComingSoonMovieBinding;

import java.util.ArrayList;
import java.util.List;

public class ComingSoonRecyclerViewAdapter extends RecyclerView.Adapter<ComingSoonRecyclerViewAdapter.ViewHolder> {
    private final ArrayList<ResultsItem> movieList;
    private final ComingSoonRecyclerViewAdapter.OnItemClickListener listener;

    public ComingSoonRecyclerViewAdapter(ArrayList<ResultsItem> movieList, ComingSoonRecyclerViewAdapter.OnItemClickListener listener) {
        this.movieList = movieList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ComingSoonRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemComingSoonMovieBinding binding = ItemComingSoonMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ComingSoonRecyclerViewAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoonRecyclerViewAdapter.ViewHolder holder, int position) {
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
        private final ItemComingSoonMovieBinding binding;
        public ViewHolder(ItemComingSoonMovieBinding binding) {
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
