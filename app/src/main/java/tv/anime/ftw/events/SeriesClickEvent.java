package tv.anime.ftw.events;

import tv.anime.ftw.model.SeriesEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class SeriesClickEvent {
    private SeriesEntity seriesEntity;

    public SeriesClickEvent(SeriesEntity seriesEntity) {
        this.seriesEntity = seriesEntity;
    }


    public SeriesEntity shoSeriesDetail() {
        return seriesEntity;
    }
}
