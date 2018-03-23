package com.example.hossam.popularmoviesappstage1;

import android.os.Parcel;
import android.os.Parcelable;

/**
        * Created by hossam on 3/14/2018.
        * Movie class to represent movie data
 */

class Movie implements Parcelable {

    static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    private String mPoster_Path;
    private String mRelease_Date;
    private int mMovieID;
    private String mTitle;
    private Number mPopularity;
    private int mVote;
    private Number mVote_Average;
    private String mURL;
    private String mOverview;

    private String voteAverageString;
    public Movie(String posterPath, int movieID, String title, Number popularity, int votes, Number averageVotes, String url, String releaseDate, String overview){
        mPoster_Path = posterPath;
        mMovieID = movieID;
        mTitle = title;
        mPopularity = popularity;
        mVote = votes;
        mVote_Average = averageVotes;
        mURL = url;
        mRelease_Date = releaseDate;
        mOverview = overview;
        voteAverageString = String.valueOf(mVote_Average);
    }

    public Movie() {
    }

    private Movie(Parcel in) {
        mURL = in.readString();
        mTitle = in.readString();
        mRelease_Date = in.readString();
        voteAverageString = in.readString();
        mOverview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(mURL);
        parcel.writeString(mTitle);
        parcel.writeString(mRelease_Date);
        parcel.writeString(String.valueOf(mVote_Average));
        parcel.writeString(mOverview);
    }

    public String getTitle(){
        return mTitle;
    }

    public Number getPopularity(){
        return mPopularity;
    }

    public int getMovieID(){
        return mMovieID;
    }

    public String getVoteAverage(){
        return voteAverageString;
    }

    public int getVote(){
        return mVote;
    }

    public String getURL(){
        return mURL;
    }

    public String getReleaseDate(){
        return mRelease_Date;
    }

    public String getOverview(){
        return mOverview;
    }

}
