package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 4/24/16.
 */
public class MovieEntity {
    private int id;
    private String fullSeriesName;
    @SerializedName("sid")
    private int seriesId;
    @SerializedName("epname")
    private String episodeName;
    @SerializedName("epnumber")
    private int episodeNumber;

    @SerializedName("vidheight")
    private int vidHeight;
    @SerializedName("vidweight")
    private int vidWIdth;

    @SerializedName("epprefix")
    private String episodePrefix;

    private String subGroup;
    @SerializedName("videotype")
    private String videoType;

    private String image;
    private String category;


    @SerializedName("image-160x140")
    private String imageOneSixty;
    @SerializedName("image-320x280")
    private String imageThreeTwenty;
    @SerializedName("image-640x560")
    private String imageSixForty;

    private String description;

    private String video;
    private int views;

    private String spriteWidth;
    private String spriteHeight;
    private String spriteTotalWidth;
    private String spriteRate;
    private String spriteCount;

    @SerializedName("total-comments")
    private int totalComments;
    @SerializedName("average-rating")
    private float averageRating;

    @SerializedName("user-rated")
    private int userRated;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getVidHeight() {
        return vidHeight;
    }

    public void setVidHeight(int vidHeight) {
        this.vidHeight = vidHeight;
    }

    public int getVidWIdth() {
        return vidWIdth;
    }

    public void setVidWIdth(int vidWIdth) {
        this.vidWIdth = vidWIdth;
    }

    public String getEpisodePrefix() {
        return episodePrefix;
    }

    public void setEpisodePrefix(String episodePrefix) {
        this.episodePrefix = episodePrefix;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageOneSixty() {
        return imageOneSixty;
    }

    public void setImageOneSixty(String imageOneSixty) {
        this.imageOneSixty = imageOneSixty;
    }

    public String getImageThreeTwenty() {
        return imageThreeTwenty;
    }

    public void setImageThreeTwenty(String imageThreeTwenty) {
        this.imageThreeTwenty = imageThreeTwenty;
    }

    public String getImageSixForty() {
        return imageSixForty;
    }

    public void setImageSixForty(String imageSixForty) {
        this.imageSixForty = imageSixForty;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getFullSeriesName() {
        return fullSeriesName;
    }

    public void setFullSeriesName(String fullSeriesName) {
        this.fullSeriesName = fullSeriesName;
    }

    public String getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(String spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public String getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(String spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    public String getSpriteTotalWidth() {
        return spriteTotalWidth;
    }

    public void setSpriteTotalWidth(String spriteTotalWidth) {
        this.spriteTotalWidth = spriteTotalWidth;
    }

    public String getSpriteRate() {
        return spriteRate;
    }

    public void setSpriteRate(String spriteRate) {
        this.spriteRate = spriteRate;
    }

    public String getSpriteCount() {
        return spriteCount;
    }

    public void setSpriteCount(String spriteCount) {
        this.spriteCount = spriteCount;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getUserRated() {
        return userRated;
    }

    public void setUserRated(int userRated) {
        this.userRated = userRated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
