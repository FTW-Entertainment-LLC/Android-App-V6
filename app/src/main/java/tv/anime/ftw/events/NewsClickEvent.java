package tv.anime.ftw.events;

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
