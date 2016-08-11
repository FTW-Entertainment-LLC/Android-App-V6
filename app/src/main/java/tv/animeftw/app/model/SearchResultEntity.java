package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 4/24/16.
 */
public class SearchResultEntity {
    private int id;
    private int type;
    @SerializedName("seriesname")
    private String seriesName;
    private String fullSeriesName;
    @SerializedName("seoname")
    private String seoName;
    private String ratingLink;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getFullSeriesName() {
        return fullSeriesName;
    }

    public void setFullSeriesName(String fullSeriesName) {
        this.fullSeriesName = fullSeriesName;
    }

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public String getRatingLink() {
        return ratingLink;
    }

    public void setRatingLink(String ratingLink) {
        this.ratingLink = ratingLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
