package com.example.hossam.popularmoviesappstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<List<Movie>> {

    MyRecyclerViewAdapter mAdapter;
    private static final int Movies_LOADER_ID = 1;

    public static final String LOG_TAG = MainActivity.class.getName();


    RecyclerView recyclerView;

    /**
     * URL to query the The Movie DB api dataset for movies information
     */
    private static String mMovieRequestUrl;

    private static final String Movies_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?page=1&language=en-US&api_key=";

    private static final String Movies_TOP_RTED_URL = "https://api.themoviedb.org/3/movie/top_rated?page=1&language=en-US&api_key=";

    private static String API_KEY;


    private View circularProgressBar;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Api key from gradle
        API_KEY = BuildConfig.MY_API_KEY;;
        //Initialize the app with popular movies
        mMovieRequestUrl = Movies_POPULAR_URL + API_KEY;

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvMovies);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        recyclerView.setHasFixedSize(true);

        mAdapter = new MyRecyclerViewAdapter(this, new ArrayList<Movie>());
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

        errorMessage = findViewById(R.id.empty_view) ;

        circularProgressBar = findViewById(R.id.loading_spinner);

        if (isOnline()){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(Movies_LOADER_ID, null, this);
        }
        else {
            circularProgressBar.setVisibility(View.GONE);
            errorMessage.setText(R.string.no_internet_connection);
        }

    }


    @Override
    public void onItemClick(View view, int position) {
        Movie currentMovie = mAdapter.getItem(position);
        Intent showMovieDetailsIntent = new Intent(this, MovieDetailsActivity.class);
        showMovieDetailsIntent.putExtra("MovieDetails", currentMovie);
        startActivity(showMovieDetailsIntent);
    }


    //Check Network connection
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_sorter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                updateMovieList(Movies_POPULAR_URL);
                return true;
            case R.id.top_rated:
                updateMovieList(Movies_TOP_RTED_URL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMovieList(String movieRequestUrl) {
        if (isOnline()) {
            mMovieRequestUrl = movieRequestUrl + API_KEY;
            //mAdapter.setMovieData(null);
            getLoaderManager().restartLoader(Movies_LOADER_ID, null, this);
        }
        else {
            circularProgressBar.setVisibility(View.GONE);
            errorMessage.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new ImageLoader(this, mMovieRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        circularProgressBar.setVisibility(View.GONE);
        // Clear the adapter of previous movie data
        mAdapter.setMovieData(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.setMovieData(null);
    }

}

