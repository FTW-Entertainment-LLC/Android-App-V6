package tv.animeftw.app.events;

import tv.animeftw.app.model.SeriesEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class SeriesLoadEvent {
    private SeriesEntity seriesEntity;

    public SeriesLoadEvent(SeriesEntity seriesEntity) {
        this.seriesEntity = seriesEntity;
    }


    public SeriesEntity getSeriesEntity() {
        return seriesEntity;
    }
}
