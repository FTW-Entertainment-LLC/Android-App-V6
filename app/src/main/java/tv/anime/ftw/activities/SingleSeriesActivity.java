package tv.anime.ftw.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import tv.anime.ftw.R;
import tv.anime.ftw.adapter.SeriesPagerAdapter;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.events.EpisodeClickEvent;
import tv.anime.ftw.events.PlayEpisodeRequestEvent;
import tv.anime.ftw.events.SeriesLoadEvent;
import tv.anime.ftw.events.WatchListClickEvent;
import tv.anime.ftw.model.AddWatchlistResponse;
import tv.anime.ftw.model.BaseObjectResponse;
import tv.anime.ftw.model.MediaItem;
import tv.anime.ftw.model.SeriesEntity;
import tv.anime.ftw.model.realm.CategoryData;
import tv.anime.ftw.ui.NonSwipeableViewPager;
import tv.anime.ftw.ui.ResizableImageView;
import tv.anime.ftw.utils.CategoryParser;

/**
 * Created by darshanz on 4/23/16.
 */
public class SingleSeriesActivity extends BaseActivity {

    @Bind(R.id.appbar)
    Toolbar mToolbar;

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    @Bind(R.id.banner)
    ResizableImageView mBannerImage;

    @Bind(R.id.title)
    TextView mTvTitle;

    @Bind(R.id.tvRomaji)
    TextView mTvRomaji;

    @Bind(R.id.tvKanji)
    TextView mTvKanji;

    @Bind(R.id.thumb)
    ImageView mIvThumb;

    @Bind(R.id.genre)
    TextView mTvGenre;

    @Bind(R.id.textYear)
    TextView mTvYear;

    @Bind(R.id.textStar)
    TextView mTvStar;

    @Bind(R.id.hqIcon)
    ImageView mIvHqIcon;

    @Bind(R.id.imageRating)
    ImageView imgRating;

    @Bind(R.id.imagePlay)
    ImageView imagePlay;

    @Bind(R.id.viewPager)
    NonSwipeableViewPager mViewPager;

    @Bind(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.moreIcon)
    ImageView mMoreIcon;

    private int seriesId;
    private String seriesDescription;
    private String seriesName;
    private DisplayImageOptions options;

    private SeriesEntity seriesEntity;
    private SeriesPagerAdapter mSeriesPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_series);

        ButterKnife.bind(this);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        setSupportActionBar(mToolbar);
        seriesId = getIntent().getIntExtra("SERIES_ID_EXTRA", 0);
        seriesName = getIntent().getStringExtra("SERIES_NAME_EXTRA");
        seriesDescription = getIntent().getStringExtra("SERIES_DESCRIPTION_EXTRA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle(seriesName);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        mTabLayout.addTab(mTabLayout.newTab().setText("Summary"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Episodes"));
        mTabLayout.addTab(mTabLayout.newTab().setText("My WatchList"));

        mSeriesPagerAdapter = new SeriesPagerAdapter(getSupportFragmentManager(), seriesDescription, seriesId, seriesName);
        mViewPager.setAdapter(mSeriesPagerAdapter);
        mViewPager.setEnabled(false);
        mTabLayout.setupWithViewPager(mViewPager);

        webService.getSingeSeries(Config.ACTION_DISPLAY_SINGLE_SERIES, seriesId, new RestCallback<BaseObjectResponse>() {
            @Override
            public void failure(RestError restError) {
                if (restError.getCode() == 0) {
                    Snackbar.make(mToolbar, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(BaseObjectResponse baseObjectResponse, Response response) {

                if (baseObjectResponse.getStatus() == 405) {
                    logout();
                    finish();
                } else {
                    //TODO DIsplay Information
                    seriesEntity = new Gson().fromJson(baseObjectResponse.getResponse(), SeriesEntity.class);

                    if (seriesEntity != null) {
                        mSeriesPagerAdapter.setSummary(seriesDescription);

                        EventBus.getDefault().post(new SeriesLoadEvent(seriesEntity));

                        ArrayList<CategoryData> categoryDatas = CategoryParser.parseCategory(seriesEntity.getCategory(), realm);
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0; i < categoryDatas.size(); i++) {
                            builder.append(categoryDatas.get(i).getName());
                            if (i < categoryDatas.size() - 1) {
                                builder.append(", ");
                            }
                        }

                        mTvGenre.setText(builder.toString());
                        mTvTitle.setText(seriesEntity.getFullSeriesName());
                        mTvRomaji.setText(seriesEntity.getRomaji());
                        mTvKanji.setText(seriesEntity.getKanji());
                        mTvGenre.setSelected(true);
                        mTvTitle.setSelected(true);
                        mTvRomaji.setSelected(true);
                        mTvKanji.setSelected(true);
                        mTvStar.setText("" + seriesEntity.getReviewsAverageStars());
                        mTvYear.setText("2016");
                        setOverflowItem(seriesEntity);

                        if (seriesEntity.getImage() != null) {
                            ImageLoader.getInstance().displayImage(seriesEntity.getImage(), mBannerImage, options);
                            ImageLoader.getInstance().displayImage(seriesEntity.getImage(), mIvThumb, options);
                            ImageLoader.getInstance().displayImage(seriesEntity.getRatingLink(), imgRating, options);
                        }
                    }
                }
            }
        });

    }


    private void setOverflowItem(final SeriesEntity seriesEntity) {
        mMoreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SingleSeriesActivity.this, mMoreIcon);
                popupMenu.getMenu().add(seriesEntity.getWatchlist() == 0 ? "Add to watchlist" : "Added to watchlist");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (seriesEntity.getWatchlist() == 0) {
                            final ProgressDialog progressDialog = ProgressDialog.show(SingleSeriesActivity.this,
                                    "Adding watchlist", "Please wait while process completes.");
                            webService.addWatchList(Config.ACTION_ADD_WATCHLIST, seriesId, new RestCallback<AddWatchlistResponse>() {
                                @Override
                                public void success(AddWatchlistResponse watchListResponse, Response response) {
                                    progressDialog.cancel();
                                    if (watchListResponse != null && watchListResponse.getWatchListEntity() != null) {
                                        mMoreIcon.setVisibility(View.GONE);
                                        Snackbar.make(mMoreIcon, "Successfully added to watchlist.", Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        Snackbar.make(mMoreIcon, "Error adding to watchlist.", Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void failure(RestError restError) {
                                    progressDialog.cancel();
                                    Snackbar.make(mMoreIcon, "Error adding to watchlist.", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        mMoreIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Subscribe
    public void onWatchListClicked(WatchListClickEvent watchListClickEvent) {
        Intent intent = new Intent(SingleSeriesActivity.this, WatchlistItemActivity.class);
        intent.putExtra("WATCH_LIST_ENTITY", watchListClickEvent.asString());
        startActivity(intent);
    }

    @Subscribe
    public void onEpisodePlayRequested(EpisodeClickEvent episodeClickEvent) {
        /*
        Intent intent = new Intent(SingleSeriesActivity.this, VideoPlayerActivity.class);
        intent.putExtra("EPISODE_ID_EXTRA", episodeClickEvent.getEpisode().getId());
        intent.putExtra("EPISODE_TITLE_EXTRA", episodeClickEvent.getEpisode().getEpisodeName());
        intent.putExtra("EPISODE_VID_URL_EXTRA", episodeClickEvent.getEpisode().getVideo());
        intent.putExtra("EPISODE_VID_POSITION_EXTRA", episodeClickEvent.getEpisode().getVideoPosition());
        startActivity(intent);
        */

        MediaItem item = new MediaItem();
        item.setTitle(episodeClickEvent.getEpisode().getEpisodeName());
        item.setVideoPosition(episodeClickEvent.getEpisode().getVideoPosition());
        item.setEpisodeId(episodeClickEvent.getEpisode().getId());
        item.setUrl(episodeClickEvent.getEpisode().getVideo());
        item.addImage(episodeClickEvent.getEpisode().getImage());
        Intent intent = new Intent(SingleSeriesActivity.this, LocalPlayerActivity.class);
        intent.putExtra("media", item.toBundle());
        intent.putExtra("shouldStart", false);
        startActivity(intent);
    }


    @OnClick(R.id.imagePlay)
    void onPlayed() {
//        TODO play start
        EventBus.getDefault().post(new PlayEpisodeRequestEvent());
    }



}
