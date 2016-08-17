package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class SearchResponse extends BaseResponse {
    @SerializedName("numrows")
    private int numRows;

    @SerializedName("results")
    private ArrayList<SearchResultEntity> searchResultList;

    public ArrayList<SearchResultEntity> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(ArrayList<SearchResultEntity> searchResultList) {
        this.searchResultList = searchResultList;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }
}
