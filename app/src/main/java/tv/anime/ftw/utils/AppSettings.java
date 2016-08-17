package tv.anime.ftw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import tv.anime.ftw.model.AdSetting;
import tv.anime.ftw.model.UserEntity;
import tv.anime.ftw.ui.PopupMenuHelper;

/**
 * Created by darshanz on 4/19/16.
 */
public class AppSettings {

    private static String USER_ENTITY_JSON = "user_entity_json";
    private static String USER_TOKEN = "user_token";
    private static final String MOVIES_VIEW_TYPE = "movies_view_type";
    public static final String MOVIE_SORT_TYPE = "sort_type";
    public static final String MOVIE_HIDE_WATCHED = "hide_watched";
    public static final String MOVIE_WITH_RESUME_POINT = "with_resume_point";

    private SharedPreferences prefs;

    public AppSettings(Context context) {
        prefs = context.getSharedPreferences("anime_ftw_tv_settings", Context.MODE_PRIVATE);
    }


    public UserEntity getCurrentUser() {
        String userJson = prefs.getString(USER_ENTITY_JSON, null);
        UserEntity userEntity = new Gson().fromJson(userJson, UserEntity.class);
        return userEntity;
    }


    public void saveCurrentUser(UserEntity userEntity) {
        prefs.edit().putString(USER_ENTITY_JSON, new Gson().toJson(userEntity)).commit();
    }


    public void saveToken(String token) {
        prefs.edit().putString(USER_TOKEN, token).apply();
    }


    public String getUserToken() {
        return prefs.getString(USER_TOKEN, null);
    }


    public void setMoviesViewType(int viewType) {
        prefs.edit().putInt(MOVIES_VIEW_TYPE, viewType).apply();
    }

    public int getMoviesViewType() {
        return prefs.getInt(MOVIES_VIEW_TYPE, PopupMenuHelper.VIEW_TYPE_WALL);
    }

    public void setMoviesSortType(int sortType) {
        prefs.edit().putInt(MOVIE_SORT_TYPE, sortType).apply();
    }

    public int getMoviesSortType() {
        return prefs.getInt(MOVIE_SORT_TYPE, PopupMenuHelper.SORT_TYPE_NAME);
    }

    public void toggleHideWatched() {
        prefs.edit().putBoolean(MOVIE_HIDE_WATCHED, !hideWatched()).apply();
    }

    public boolean hideWatched() {
        return prefs.getBoolean(MOVIE_HIDE_WATCHED, false);
    }

    public void toggleWithResumePoint() {
        prefs.edit().putBoolean(MOVIE_WITH_RESUME_POINT, !withResumePoint()).apply();
    }

    public boolean withResumePoint() {
        return prefs.getBoolean(MOVIE_WITH_RESUME_POINT, false);
    }



    public void saveAd(AdSetting adSetting){
        prefs.edit().putString("ad_unit_id", adSetting.getUnitId()).commit();
        prefs.edit().putString("ad_format", adSetting.getFormat()).commit();
        prefs.edit().putString("ad_enabled", adSetting.getEnabled()).commit();
    }


    public AdSetting getAd(){
        AdSetting adSetting = new AdSetting();
        adSetting.setUnitId(prefs.getString("ad_unit_id", null));
        adSetting.setFormat(prefs.getString("ad_format", null));
        adSetting.setEnabled(prefs.getString("ad_enabled", null));
        return  adSetting;
    }

    public void clearAd(){
        prefs.edit().remove("ad_unit_id").commit();
        prefs.edit().remove("ad_format").commit();
        prefs.edit().remove("ad_enabled").commit();
    }

    /**
     * Clear settings required when logged out
     */
    public void clearSettings() {
        prefs.edit().clear().commit();
    }
}
