package tv.animeftw.app.events;

import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.utils.Screens;

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
