package tv.animeftw.app.utils;

import java.util.ArrayList;

import io.realm.Realm;
import tv.animeftw.app.model.CategoryEntity;
import tv.animeftw.app.model.realm.CategoryData;

/**
 * Created by darshanz on 5/2/16.
 */
public class CategoryParser {
    public static ArrayList<CategoryData> parseCategory(String catIds, Realm realm) {
        ArrayList<CategoryData> categoryDatas = new ArrayList<>();
        catIds = catIds.trim();
        String[] catIdArray = catIds.split(",");
        for (int i = 0; i < catIdArray.length; i++) {
            String catId = catIdArray[i].trim();
            if(catId.length()!=0) {
                CategoryData categoryData = realm.where(CategoryData.class)
                        .equalTo(CategoryData.ID, Integer.parseInt(catId))
                        .findFirst();
                if (categoryData != null)
                    categoryDatas.add(categoryData);
            }
        }
        return categoryDatas;
    }
}
