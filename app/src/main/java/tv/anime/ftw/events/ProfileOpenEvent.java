package tv.anime.ftw.events;

/**
 * Created by darshanz on 4/20/16.
 */
public class ProfileOpenEvent {
    private int userId = -1;

    public ProfileOpenEvent(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
