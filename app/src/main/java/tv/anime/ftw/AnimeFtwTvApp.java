package tv.anime.ftw;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tv.anime.ftw.R;
import tv.anime.ftw.model.realm.CategoryData;
import tv.anime.ftw.utils.AppSettings;
import tv.anime.ftw.utils.RealmDataMigration;

/**
 * Created by darshanz on 4/19/16.
 */
public class AnimeFtwTvApp extends Application {

    private static RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();


        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_ad_unit_id));


        initImageLoader(this);


        realmConfiguration = new RealmConfiguration.Builder(this)
                .name("default1")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .migration(new RealmDataMigration())
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

    }



    /**
     * Get Realm Instance
     *
     * @return Realm Configuration
     */
    public Realm getRealmInstance() {

        //

        if (Realm.getDefaultInstance().getConfiguration() != null) {
            return Realm.getDefaultInstance();
        } else {

            realmConfiguration = new RealmConfiguration.Builder(this)
                    .name("default1")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .migration(new RealmDataMigration())
                    .build();

            Realm.setDefaultConfiguration(realmConfiguration);

            return Realm.getDefaultInstance();
        }
    }


    /**
      * Clear the realm objects and preferences when logged out.
     */
    public void logout() {

        Realm realm = getRealmInstance();
        realm.beginTransaction();
        realm.clear(CategoryData.class);
        //TODO more realm Objects

        realm.commitTransaction();
        realm.close();

        AppSettings settings = new AppSettings(this);
        settings.clearSettings();

    }



    /**
     * Method for Initializing Universal ImageLoader
     *
     * @param context
     */
    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoader.getInstance().init(config.build());

    }

}
