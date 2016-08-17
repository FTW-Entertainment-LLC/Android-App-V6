package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/23/16.
 */
public class NewsResponse extends BaseResponse {

    @SerializedName("results")
    private ArrayList<NewsItem> newsItems;

    public ArrayList<NewsItem> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(ArrayList<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }
}
