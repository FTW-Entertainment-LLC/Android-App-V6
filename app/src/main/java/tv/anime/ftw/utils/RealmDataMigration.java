package tv.anime.ftw.utils;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Migration Helper for realm database
   */
public class RealmDataMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm dynamicRealm, long l, long l1) {
        //DO Migration here
        //This is not required now.
        //Once the app is released and during update if any of the Realm models get changed, write migration code here
    }
}