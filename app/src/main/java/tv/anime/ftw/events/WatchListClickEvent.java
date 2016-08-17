package tv.anime.ftw.events;

import com.google.gson.Gson;

import tv.anime.ftw.model.WatchListEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class WatchListClickEvent {
    private final WatchListEntity watchListEntity;

    public WatchListClickEvent(WatchListEntity watchListEntity) {
        this.watchListEntity = watchListEntity;
    }

    public String asString() {
        return new Gson().toJson(watchListEntity);
    }

    public int getSeriesId() {
        return watchListEntity.getSeriesId();
    }

    public String getFullSeriesName() {
        return watchListEntity.getFullSeriesName();
    }
}
