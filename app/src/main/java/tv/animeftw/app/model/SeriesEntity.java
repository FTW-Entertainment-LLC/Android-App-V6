package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 4/22/16.
 */
public class SeriesEntity {
    private int id;
    private String fullSeriesName;
    private String romaji;
    private String kanji;
    private String synonym;
    private String description;
    private String rating;
    private String ratingLink;
    private int stillRelease;
    @SerializedName("Movies")
    private int movies;
    @SerializedName("moviesonly")
    private int moviesOnly;
    private String noteReason;
    private String category;
    private int  prequelto;
    private int sequelto;
    private int hd;
    private int watchlist;
    private String image;
    @SerializedName("image-320x280")
    private String imageThreeTwenty;
    @SerializedName("total-reviews")
    private int totalReviews;
    @SerializedName("user-reviewed")
    private int userReviewed;
    @SerializedName("reviews-average-stars")
    private float reviewsAverageStars;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullSeriesName() {
        return fullSeriesName;
    }

    public void setFullSeriesName(String fullSeriesName) {
        this.fullSeriesName = fullSeriesName;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingLink() {
        return ratingLink;
    }

    public void setRatingLink(String ratingLink) {
        this.ratingLink = ratingLink;
    }

    public int getStillRelease() {
        return stillRelease;
    }

    public void setStillRelease(int stillRelease) {
        this.stillRelease = stillRelease;
    }

    public int getMovies() {
        return movies;
    }

    public void setMovies(int movies) {
        this.movies = movies;
    }

    public int getMoviesOnly() {
        return moviesOnly;
    }

    public void setMoviesOnly(int moviesOnly) {
        this.moviesOnly = moviesOnly;
    }

    public String getNoteReason() {
        return noteReason;
    }

    public void setNoteReason(String noteReason) {
        this.noteReason = noteReason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrequelto() {
        return prequelto;
    }

    public void setPrequelto(int prequelto) {
        this.prequelto = prequelto;
    }

    public int getSequelto() {
        return sequelto;
    }

    public void setSequelto(int sequelto) {
        this.sequelto = sequelto;
    }

    public int getHd() {
        return hd;
    }

    public void setHd(int hd) {
        this.hd = hd;
    }

    public int getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(int watchlist) {
        this.watchlist = watchlist;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageThreeTwenty() {
        return imageThreeTwenty;
    }

    public void setImageThreeTwenty(String imageThreeTwenty) {
        this.imageThreeTwenty = imageThreeTwenty;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getUserReviewed() {
        return userReviewed;
    }

    public void setUserReviewed(int userReviewed) {
        this.userReviewed = userReviewed;
    }

    public float getReviewsAverageStars() {
        return reviewsAverageStars;
    }

    public void setReviewsAverageStars(float reviewsAverageStars) {
        this.reviewsAverageStars = reviewsAverageStars;
    }
}
