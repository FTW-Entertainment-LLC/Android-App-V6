package tv.animeftw.app.events;

import tv.animeftw.app.utils.Screens;

/**
 * Created by darshanz on 4/20/16.
 */
public class DrawerMenuEvent {
    private int screen = Screens.SCREEN_MOVIES;

    public DrawerMenuEvent(int screen) {
        this.screen = screen;
    }

    public int getScreen() {
        return screen;
    }
}
