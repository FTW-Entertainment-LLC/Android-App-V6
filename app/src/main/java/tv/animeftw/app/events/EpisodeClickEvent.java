package tv.animeftw.app.events;

import tv.animeftw.app.model.EpisodeEntity;
import tv.animeftw.app.model.SeriesEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class EpisodeClickEvent {
    private EpisodeEntity episodeEntity;

    public EpisodeClickEvent(EpisodeEntity episodeEntity) {
        this.episodeEntity = episodeEntity;
    }


    public EpisodeEntity getEpisode() {
        return episodeEntity;
    }
}
