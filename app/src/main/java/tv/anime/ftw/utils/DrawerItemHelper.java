package tv.anime.ftw.utils;

import android.content.Context;

import java.util.ArrayList;

import tv.anime.ftw.R;
import tv.anime.ftw.adapter.DrawerItem;
import tv.anime.ftw.adapter.SettingsItem;

/**
 * Created by darshanz on 4/19/16.
 */
public class DrawerItemHelper {

    public static ArrayList<DrawerItem> getDraweItems(Context context){
        ArrayList<DrawerItem> drawerItems = new ArrayList<>();
        String[] topTitles = context.getResources().getStringArray(R.array.top_menu_titles);
        String[] topSubtitles = context.getResources().getStringArray(R.array.top_menu_subtitles);
        String[] bottomMenuItems = context.getResources().getStringArray(R.array.bottom_menu_titles);

        int[] topScreens = {Screens.SCREEN_MOVIES, Screens.SCREEN_SERIES, Screens.SCREEN_RANDOM_SERIES, Screens.SCREEN_TOP_100};
        int[] bottomScreens = {Screens.SCREEN_MY_WATCHLIST, Screens.SCREEN_LATEST_REVIEWS, Screens.SCREEN_LATEST_COMMENTS, Screens.SCREEN_SETTINGS};

        int[] topMenuIcons = {R.drawable.ic_movie, R.drawable.ic_series, R.drawable.ic_random, R.drawable.ic_star_top};
        int[] bottomMenuIcons = {
                R.drawable.ic_watchlist,
                R.drawable.ic_folder_latest,
                R.drawable.ic_message_black_24dp,
                R.drawable.ic_settings_black_24dp};


        for (int i = 0; i < topTitles.length; i++){
            DrawerItem drawerItem = new DrawerItem();
            drawerItem.setIcon(topMenuIcons[i]);
            drawerItem.setTitle(topTitles[i]);
            drawerItem.setSubTitle(topSubtitles[i]);
            drawerItem.setMenu(true);
            drawerItem.setScreen(topScreens[i]);
            drawerItems.add(drawerItem);
        }

        DrawerItem titleItem = new DrawerItem();
        titleItem.setTitle(context.getResources().getString(R.string.my_account));
        titleItem.setMenu(false);
        drawerItems.add(titleItem);

        for (int i = 0; i < bottomMenuItems.length; i++){
            DrawerItem drawerItem = new DrawerItem();
            drawerItem.setIcon(bottomMenuIcons[i]);
            drawerItem.setTitle(bottomMenuItems[i]);
            drawerItem.setScreen(bottomScreens[i]);
            drawerItem.setMenu(true);
            drawerItems.add(drawerItem);
        }


        return drawerItems;
    }


    /**
     * Settings item
     * @param context
     * @return
     */
    public static ArrayList<SettingsItem> getSettingsItem(Context context){
        ArrayList<SettingsItem> settings = new ArrayList<>();

        SettingsItem aboutItem = new SettingsItem();
        aboutItem.setIcon(R.drawable.ic_about);
        aboutItem.setTitle("About");
        aboutItem.setSubTitle("About app");
        aboutItem.setMenu(true);
        aboutItem.setAction(SettingsAction.ACTION_ABOUT);
        settings.add(aboutItem);


        SettingsItem LogoutItem = new SettingsItem();
        LogoutItem.setIcon(R.drawable.ic_person);
        LogoutItem.setTitle("Logout");
        LogoutItem.setSubTitle("Logout from this device");
        LogoutItem.setMenu(true);
        LogoutItem.setAction(SettingsAction.ACTION_LOGOUT);
        settings.add(LogoutItem);



        return settings;
    }
}
