package com.example.arce.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arce.flixster.model.Config;
import com.example.arce.flixster.model.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    //list of movies
    ArrayList<Movie> movies;
    Config config;
    Context context;

    public  static final String imgUrl=" ";
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

        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;


        //set image
        String imgUrl = null;

        //if on portrait mode, load image. Else, load backdrop
        if(isPortrait)
            imgUrl = config.getImgUrl(config.getSize(),movie.getPoster());
        else
            imgUrl = config.getImgUrl(config.getBackdropsSize(), movie.getBackdrop());

        //get correct placeholder
        int placeholderID = isPortrait ? R.drawable.placeholder : R.drawable.backdrop_placeholder;
        //get correct image poster or backdrop
        ImageView imageview = isPortrait ?  holder.ivPosterImage : holder.ivBackdrop;

        GlideApp.with(context)
                .load(imgUrl)
                .transform(new RoundedCornersTransformation(15, 0))
                .placeholder(placeholderID)
                .error(placeholderID)
                .into(imageview);

    }

    //returns the total of items in the List
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create a viewHold as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //track view objects
        ImageView ivPosterImage;
        ImageView ivBackdrop;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivPoster);
            ivBackdrop = (ImageView) itemView.findViewById(R.id.ivBackdropImg);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitleD);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION){
                Movie mov = movies.get(pos);
                Intent i = new Intent(context, MovieDetailAct.class);
                // serialize the movie using parceler, use its short name as a key
                i.putExtra(Movie.class.getSimpleName(), Parcels.wrap(mov));
                //
                i.putExtra(imgUrl,config.getImgUrl(config.getBackdropsSize(),mov.getBackdrop()));
                // show the activity
                context.startActivity(i);
            }
        }
    }

}

