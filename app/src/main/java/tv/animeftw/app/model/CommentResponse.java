package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class CommentResponse extends BaseResponse {
    @SerializedName("results")
    private ArrayList<CommentEntity> commentList;

    public ArrayList<CommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<CommentEntity> commentList) {
        this.commentList = commentList;
    }
}
