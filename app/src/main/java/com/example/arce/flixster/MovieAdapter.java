package com.example.arce.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arce.flixster.model.Config;
import com.example.arce.flixster.model.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //list of movies
    ArrayList<Movie> movies;
    Config config;
    Context context;
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item
        View movieView = inflater.inflate(R.layout.item_movie, parent,false);
        //return a new viewholderrgtrfkclccucvjkcjkbgdiuiuretneut
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the movie data at the specified position
        Movie movie = movies.get(position);

        //set the movie information
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //set image
        String imgUrl = config.getImgUrl(config.getSize(),movie.getPoster());

        GlideApp.with(context)
                .load(imgUrl)
                .transform(new RoundedCornersTransformation(15, 0))
               // .into(ivImg);
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.ivPosterImage);

    }

    //returns the total of items in the List
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create a viewHold as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivPoster);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);

        }
    }

}

