package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 4/23/16.
 */
public class NewsItem {

    private String poster;
    private String posterId;
    @SerializedName("poster-avatar")
    private String posterAvatar;
    @SerializedName("topic-id")
    private String topicId;
    @SerializedName("topic-title")
    private String topicTitle;

    @SerializedName("forum-id")
    private String forumId;

    private long date;
    private String body;
    @SerializedName("topic-link")
    private String topicLink;


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getPosterAvatar() {
        return posterAvatar;
    }

    public void setPosterAvatar(String posterAvatar) {
        this.posterAvatar = posterAvatar;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTopicLink() {
        return topicLink;
    }

    public void setTopicLink(String topicLink) {
        this.topicLink = topicLink;
    }
}
