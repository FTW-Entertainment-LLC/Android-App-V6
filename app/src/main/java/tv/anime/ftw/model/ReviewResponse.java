package tv.anime.ftw.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class ReviewResponse extends BaseResponse {
    @SerializedName("results")
    private ArrayList<ReviewEntity> reviewList;

    public ArrayList<ReviewEntity> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<ReviewEntity> reviewList) {
        this.reviewList = reviewList;
    }
}
