package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class WatchListResponse extends BaseResponse {
    private int total;
    private int sort;
    @SerializedName("results")
    private ArrayList<WatchListEntity> watchListItems;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public ArrayList<WatchListEntity> getWatchListItems() {
        return watchListItems;
    }

    public void setWatchListItems(ArrayList<WatchListEntity> watchListItems) {
        this.watchListItems = watchListItems;
    }
}
