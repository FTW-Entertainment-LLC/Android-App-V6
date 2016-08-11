package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by darshanz on 4/24/16.
 */
public class CategoryResponse extends BaseResponse {
    private String sort;
    @SerializedName("results")
    private ArrayList<CategoryEntity> categoryList;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public ArrayList<CategoryEntity> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CategoryEntity> categoryList) {
        this.categoryList = categoryList;
    }
}
