package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 4/24/16.
 */
public class WatchListEntity {

    private int id;
    @SerializedName("date")
    private long createdAt;
    @SerializedName("update")
    private long updated;
    @SerializedName("sid")
    private int seriesId;
    private int status;
    private int email;
    @SerializedName("current_episode")
    private int currentep;
    private int tracker;
    @SerializedName("tracker_latest")
    private int trackerLatest;
    private String fullSeriesName;
    private String comment;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getCurrentep() {
        return currentep;
    }

    public void setCurrentep(int currentep) {
        this.currentep = currentep;
    }

    public int getTracker() {
        return tracker;
    }

    public void setTracker(int tracker) {
        this.tracker = tracker;
    }

    public int getTrackerLatest() {
        return trackerLatest;
    }

    public void setTrackerLatest(int trackerLatest) {
        this.trackerLatest = trackerLatest;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getFullSeriesName() {
        return fullSeriesName;
    }

    public void setFullSeriesName(String fullSeriesName) {
        this.fullSeriesName = fullSeriesName;
    }

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
