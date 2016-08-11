package tv.animeftw.app.model.realm;

import io.realm.Realm;
import tv.animeftw.app.model.CategoryEntity;

/**
 * Created by darshanz on 5/2/16.
 */
public class RealmMapper {
    /**
     * From Realm
     * @param data
     * @param realm
     * @return
     */
    public static CategoryEntity fromRealm(CategoryData data, Realm realm) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(data.getId());
        entity.setName(data.getName());
        entity.setDescription(data.getDescription());
        return entity;

    }


    /**
     * toRealm
     * @param entity
     * @param realm
     * @return
     */
    public static CategoryData toRealm(CategoryEntity entity, Realm realm) {
        CategoryData categoryData = realm.where(CategoryData.class).equalTo(CategoryData.ID, entity.getId()).findFirst();
        if (categoryData == null) {
            categoryData = realm.createObject(CategoryData.class);
        }
        categoryData.setId(entity.getId());
        categoryData.setDescription(entity.getDescription());
        categoryData.setName(entity.getName());
        return categoryData;
    }
}
