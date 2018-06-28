package com.example.arce.flixster.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    // values from API

    private String title;
    private String overview;
    private String poster; //just the path

    public Movie (JSONObject obj) throws JSONException {
        title = obj.getString("title");
        overview = obj.getString("overview");
        poster=obj.getString("poster_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }
}
