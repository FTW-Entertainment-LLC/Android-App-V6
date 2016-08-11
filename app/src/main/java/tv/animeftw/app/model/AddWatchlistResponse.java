package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

public class AddWatchlistResponse extends BaseResponse {
    @SerializedName("results")
    private WatchListEntity watchListEntity;

    public WatchListEntity getWatchListEntity() {
        return watchListEntity;
    }

    public void setWatchListEntity(WatchListEntity watchListEntity) {
        this.watchListEntity = watchListEntity;
    }
}
