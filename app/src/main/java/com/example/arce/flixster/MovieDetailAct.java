package com.example.arce.flixster;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.arce.flixster.model.Movie;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.arce.flixster.MovieAdapter.imgUrl;

public class MovieDetailAct extends AppCompatActivity {

    // the movie to display
    Movie movie;

    // the view objects
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvDate) TextView tvDate;

    @BindView(R.id.ivVideo) ImageView ivVideo;
    @BindView(R.id.rbVoteAv) RatingBar rbVoteAverage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // in Activity#onCreate
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        setContentView(R.layout.activity_movie_detail);
        // resolve the view objects - Using Butterknife
        ButterKnife.bind(this);

    // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        tvDate.setText(movie.getDate());
        tvOverview.setText(movie.getOverview());
        tvTitle.setText(movie.getTitle());


        GlideApp.with(this)
                .load(getIntent().getStringExtra(imgUrl))
                .transform(new RoundedCornersTransformation(15, 0))
                .placeholder(R.drawable.backdrop_placeholder)
                .error(R.drawable.backdrop_placeholder)
                .into(ivVideo);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}
