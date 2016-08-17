package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by darshanz on 4/24/16.
 */
public class CommentEntity {
    private int id;
    private String comment;
    private int spoiler;
    private Date dated;
    private String user;
    @SerializedName("votes-negative")
    private int votesNegative;
    @SerializedName("votes-positive")
    private int votesPositive;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(int spoiler) {
        this.spoiler = spoiler;
    }

    public Date getDated() {
        return dated;
    }

    public void setDated(Date dated) {
        this.dated = dated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getVotesNegative() {
        return votesNegative;
    }

    public void setVotesNegative(int votesNegative) {
        this.votesNegative = votesNegative;
    }

    public int getVotesPositive() {
        return votesPositive;
    }

    public void setVotesPositive(int votesPositive) {
        this.votesPositive = votesPositive;
    }
}
