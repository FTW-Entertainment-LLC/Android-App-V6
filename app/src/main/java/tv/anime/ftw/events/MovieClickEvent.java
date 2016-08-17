package tv.anime.ftw.events;

import tv.anime.ftw.model.MovieEntity;

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
