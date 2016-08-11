package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class MovieResponse extends BaseResponse {
    @SerializedName("total-movies")
    private int totalMovies;
    @SerializedName("results")
    private ArrayList<MovieEntity> movieList;

    public int getTotalMovies() {
        return totalMovies;
    }

    public void setTotalMovies(int totalMovies) {
        this.totalMovies = totalMovies;
    }

    public ArrayList<MovieEntity> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<MovieEntity> movieList) {
        this.movieList = movieList;
    }
}
