package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class EpisodeResponse extends BaseResponse {
    @SerializedName("series-id")
    private int seriesId;
    @SerializedName("total-episodes")
    private int totalEpisodes;

    @SerializedName("results")
    private ArrayList<EpisodeEntity> episodeList;

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    public ArrayList<EpisodeEntity> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<EpisodeEntity> episodeList) {
        this.episodeList = episodeList;
    }
}
