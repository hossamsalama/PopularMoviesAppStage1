package com.example.hossam.popularmoviesappstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by hossam on 3/15/2018.
 *
 */

public class ImageLoader extends AsyncTaskLoader<List<Movie>> {
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link ImageLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public ImageLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies.
        List<Movie> movies = QueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}

