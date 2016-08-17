package tv.anime.ftw.events;

import tv.anime.ftw.utils.SettingsAction;

/**
 * Created by darshanz on 4/20/16.
 */
public class SettingsEvent {
    private int action = SettingsAction.ACTION_LOGOUT;

    public SettingsEvent(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
