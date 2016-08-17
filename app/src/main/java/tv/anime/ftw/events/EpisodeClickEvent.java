package tv.anime.ftw.events;

import tv.anime.ftw.model.EpisodeEntity;

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
