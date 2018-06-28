package com.example.arce.flixster.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {
    // values from API

    String title;
    String overview;
    String poster; //just the path
    String backdrop; //just the path

    public Movie(){

    }


    public Movie (JSONObject obj) throws JSONException {
        title = obj.getString("title");
        overview = obj.getString("overview");
        poster=obj.getString("poster_path");
        backdrop=obj.getString("backdrop_path");
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

    public String getBackdrop() {
        return backdrop;
    }
}
