package com.example.arce.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Toast;

import com.example.arce.flixster.model.Config;
import com.example.arce.flixster.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MovieListAct extends AppCompatActivity {
    //constants
    public final static String API_BASE_URL="https://api.themoviedb.org/3";
// parameter name for API key
    public final static String API_KEY_PARAM = "api_key";
//TAG for Loggin calls
    public final  static String TAG= "MovieListAct";

    AsyncHttpClient client;
    //list of movies
    ArrayList<Movie> movies;
    //recycler view
    RecyclerView rvMovies;
    // adapter wired to the recycler view
    MovieAdapter adapter;
    //Config
    Config conf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // in Activity#onCreate
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        //initializes
        client = new AsyncHttpClient();
        //initializes arraylist
        movies= new ArrayList<>();
        //initializas the adapter -- movies array connect a layout manager and the adapter
        adapter = new MovieAdapter(movies);


        //check how this lines works
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        //get config
        getConfiguration();

    }

    //get list of movies
    public void getNowPlaying(){
        //complete URL
        String url = API_BASE_URL + "/movie/now_playing";
        //request param
        RequestParams params= new RequestParams();
        params.put(API_KEY_PARAM,getString(R.string.api_key));
        //execute a get
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results= response.getJSONArray("results");
                    for(int i=0; i<results.length();i++){
                        Movie movie=new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        //notify adapter a row was added
                        adapter.notifyItemInserted(movies.size()-1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    errorLog("Error while parsing response", e, true);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                errorLog("Failed getting data from /nowplaying", throwable, true);
            }
        });
    }


    //get the config from the API

    private void getConfiguration(){
        //complete URL
        String url = API_BASE_URL + "/configuration";
        //request param
        RequestParams params= new RequestParams();
        params.put(API_KEY_PARAM,getString(R.string.api_key));
        //execute a get
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    conf = new Config(response);
                    Log.i(TAG, String.format("configuration loaded with %s BaseURL and %s PosterSize", conf.getImgBaseUrl(), conf.getSize()));
                    //Pass this config to movie adaptor
                    // es por que se necesita desplegar correctamente en pantalla
                    adapter.setConfig(conf);

                    //gt the list of movies currently playing
                    //called here to ensure confgiuration is done before adding the movie list
                    getNowPlaying();
                }

                catch (JSONException e) {
                    errorLog("Error while parsing", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                errorLog("Failed configuration",throwable,true);
            }
        });

    }

    //error handler
    public void errorLog(String messg, Throwable error, boolean alertUser){
        Log.e(TAG, messg,error);

        if (alertUser)
            Toast.makeText(getApplicationContext(),messg,Toast.LENGTH_LONG).show();
    }
}
