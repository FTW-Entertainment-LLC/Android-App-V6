package tv.anime.ftw.events;

/**
 * Created by darshanz on 6/23/16.
 */
public class WatchListUpdateEvent {
    private int id;
    private int status;
    private int email;
    private int currentEpisode;
    private String comment;
    private int trackerStatus;
    private int trackerLatest;
    private boolean isNew;

    public WatchListUpdateEvent(int id, int status, int email, int currentEpisode, String comment, int trackerStatus, int trackerLatest, boolean isNew) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.currentEpisode = currentEpisode;
        this.comment = comment;
        this.trackerStatus = trackerStatus;
        this.trackerLatest = trackerLatest;
        this.isNew = isNew;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getEmail() {
        return email;
    }

    public int getCurrentEpisode() {
        return currentEpisode;
    }

    public String getComment() {
        return comment;
    }

    public int getTrackerStatus() {
        return trackerStatus;
    }

    public int getTrackerLatest() {
        return trackerLatest;
    }

    public boolean isNew() {
        return isNew;
    }
}
