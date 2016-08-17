package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/22/16.
 */
public class SeriesResponse extends BaseResponse {
    @SerializedName("total-series")
    private  int totalSeries;
     @SerializedName("results")
    private ArrayList<SeriesEntity> seriesList;

    public int getTotalSeries() {
        return totalSeries;
    }

    public void setTotalSeries(int totalSeries) {
        this.totalSeries = totalSeries;
    }



    public ArrayList<SeriesEntity> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(ArrayList<SeriesEntity> seriesList) {
        this.seriesList = seriesList;
    }
}
