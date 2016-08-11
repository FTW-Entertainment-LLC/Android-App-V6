package tv.animeftw.app.events;

import tv.animeftw.app.model.MovieEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class MovieClickEvent {
    private MovieEntity mEntity;

    public MovieClickEvent(MovieEntity entity) {
        this.mEntity = entity;
    }


    public MovieEntity getMovie() {
        return mEntity;
    }
}
