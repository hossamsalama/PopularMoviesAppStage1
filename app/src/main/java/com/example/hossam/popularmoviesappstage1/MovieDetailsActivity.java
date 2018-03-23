package com.example.hossam.popularmoviesappstage1;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intentThatStartedThisActivity = getIntent();
        movie = intentThatStartedThisActivity.getParcelableExtra("MovieDetails");
        String movieUrl = movie.getURL();
        String title = movie.getTitle();
        String releaseDate = movie.getReleaseDate();
        String voteAverage = movie.getVoteAverage();
        String plotSynopsis = movie.getOverview();

        ImageView moviePoster = findViewById(R.id.movie_poster);
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieReleaseDate = findViewById(R.id.release_date);
        TextView movieVoteAverage = findViewById(R.id.vote_average);
        TextView overview = findViewById(R.id.plot_synopsis);

        Picasso.with(this).load(movieUrl).into(moviePoster);
        movieTitle.setText(title);
        movieReleaseDate.setText(releaseDate);
        movieVoteAverage.setText(voteAverage);
        overview.setText(plotSynopsis);
    }


}

