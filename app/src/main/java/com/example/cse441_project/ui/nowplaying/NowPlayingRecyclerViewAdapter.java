package com.example.cse441_project.ui.nowplaying;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cse441_project.data.model.ResultsItem;
import com.example.cse441_project.databinding.ItemNowplayingMovieBinding;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingRecyclerViewAdapter extends RecyclerView.Adapter<NowPlayingRecyclerViewAdapter.ViewHolder> {
    private List<ResultsItem> movieList;

    public NowPlayingRecyclerViewAdapter(List<ResultsItem> movieList) {
        this.movieList = new ArrayList<>(movieList);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNowplayingMovieBinding binding = ItemNowplayingMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNowplayingMovieBinding binding;

        public ViewHolder(ItemNowplayingMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ResultsItem resultsItem) {
            binding.textName.setText(resultsItem.getTitle());
            String imageUrl = "https://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath();
            Glide.with(binding.getRoot().getContext())
                    .load(imageUrl)
                    .into(binding.imageView);

        }
    }

    public void addMovie(List<ResultsItem> newMovies) {
        movieList.clear();
        movieList.addAll(newMovies);
        notifyDataSetChanged();
    }
}
