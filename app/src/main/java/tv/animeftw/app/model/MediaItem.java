package tv.animeftw.app.model;

import android.os.Bundle;

import java.util.ArrayList;

public class MediaItem {

    private String mTitle;
    private String mUrl;
    private String mContentType;
    private int mDuration;
    private int videoPosition;
    private int episodeId;
    private ArrayList<String> mImageList = new ArrayList<String>();

    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "movie-urls";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_CONTENT_TYPE = "content-type";
    public static final String KEY_VIDEO_POSITION = "video-position";
    public static final String KEY_EPISODE_ID = "episode-id";

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {

        return mTitle;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void addImage(String url) {
        mImageList.add(url);
    }

    public int getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(int videoPosition) {
        this.videoPosition = videoPosition;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public void addImage(String url, int index) {
        if (index < mImageList.size()) {
            mImageList.set(index, url);
        }
    }

    public String getImage(int index) {
        if (index < mImageList.size()) {
            return mImageList.get(index);
        }
        return null;
    }

    public boolean hasImage() {
        return !mImageList.isEmpty();
    }

    public ArrayList<String> getImages() {
        return mImageList;
    }

    public Bundle toBundle() {
        Bundle wrapper = new Bundle();
        wrapper.putString(KEY_TITLE, mTitle);
        wrapper.putString(KEY_URL, mUrl);
        wrapper.putStringArrayList(KEY_IMAGES, mImageList);
        wrapper.putInt(KEY_VIDEO_POSITION, videoPosition);
        wrapper.putInt(KEY_EPISODE_ID, episodeId);
        wrapper.putString(KEY_CONTENT_TYPE, "video/mp4");
        return wrapper;
    }

    public static final MediaItem fromBundle(Bundle wrapper) {
        if (null == wrapper) {
            return null;
        }
        MediaItem media = new MediaItem();
        media.setUrl(wrapper.getString(KEY_URL));
        media.setTitle(wrapper.getString(KEY_TITLE));
        media.mImageList.addAll(wrapper.getStringArrayList(KEY_IMAGES));
        media.setContentType(wrapper.getString(KEY_CONTENT_TYPE));
        media.setEpisodeId(wrapper.getInt(KEY_EPISODE_ID));
        media.setVideoPosition(wrapper.getInt(KEY_VIDEO_POSITION));
        return media;
    }


}
