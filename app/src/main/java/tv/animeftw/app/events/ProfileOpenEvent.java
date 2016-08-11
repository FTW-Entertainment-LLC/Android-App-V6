package tv.animeftw.app.events;

import tv.animeftw.app.utils.Screens;

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
