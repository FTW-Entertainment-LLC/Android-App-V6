package tv.animeftw.app.events;

import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.SeriesEntity;

/**
 * Created by darshanz on 4/22/16.
 */
public class NewsClickEvent {
    private String topicLink;

    public NewsClickEvent(String topicLink) {
        this.topicLink = topicLink;
    }


    public String getLink() {
        return topicLink;
    }
}
