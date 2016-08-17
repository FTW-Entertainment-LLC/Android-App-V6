package tv.anime.ftw.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.cast.framework.IntroductoryOverlay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.anime.ftw.R;
import tv.anime.ftw.activities.accounts.LoginActivity;
import tv.anime.ftw.activities.accounts.ProfileActivity;
import tv.anime.ftw.adapter.DrawerAdapter;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.events.DrawerMenuEvent;
import tv.anime.ftw.events.MoreMovieEvent;
import tv.anime.ftw.events.MovieClickEvent;
import tv.anime.ftw.events.NewsClickEvent;
import tv.anime.ftw.events.ProfileOpenEvent;
import tv.anime.ftw.events.SeriesClickEvent;
import tv.anime.ftw.events.SettingsEvent;
import tv.anime.ftw.events.WatchListClickEvent;
import tv.anime.ftw.fragments.AboutFragment;
import tv.anime.ftw.fragments.HomeFragment;
import tv.anime.ftw.fragments.LatestReviewsFragment;
import tv.anime.ftw.fragments.MoviesFragment;
import tv.anime.ftw.fragments.RandomSeriesFragment;
import tv.anime.ftw.fragments.SeriesFragment;
import tv.anime.ftw.fragments.SettingsFragment;
import tv.anime.ftw.fragments.TopHundredFragment;
import tv.anime.ftw.fragments.WatchListFragment;
import tv.anime.ftw.model.AppSettingsResponse;
import tv.anime.ftw.model.CategoryEntity;
import tv.anime.ftw.model.CategoryResponse;
import tv.anime.ftw.model.realm.CategoryData;
import tv.anime.ftw.model.realm.RealmMapper;
import tv.anime.ftw.utils.AppSettings;
import tv.anime.ftw.utils.DrawerItemHelper;
import tv.anime.ftw.utils.Screens;
import tv.anime.ftw.utils.SettingsAction;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.appbar)
    Toolbar mToolbar;
    @Bind(R.id.drawerRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.content)
    RelativeLayout contentLayout;

    private CastContext mCastContext;
    private MenuItem mediaRouteMenuItem;
    private CastStateListener mCastStateListener;
    private IntroductoryOverlay mIntroductoryOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {

            ButterKnife.bind(this);
            setSupportActionBar(mToolbar);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(new DrawerAdapter(DrawerItemHelper.getDraweItems(MainActivity.this), new AppSettings(MainActivity.this)));


            getSupportActionBar().setTitle("AFTW.tv");
            getSupportActionBar().setSubtitle("Center");

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            actionBarDrawerToggle.syncState();

            mDrawerLayout.setScrimColor(Color.TRANSPARENT);
            mDrawerLayout.setDrawerShadow(R.drawable.trans, GravityCompat.START);
            mDrawerLayout.setDrawerElevation(0f);

            mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    contentLayout.setTranslationX(slideOffset * drawerView.getWidth());
                    mDrawerLayout.bringChildToFront(drawerView);
                    mDrawerLayout.requestLayout();
                }

                @Override
                public void onDrawerOpened(View drawerView) {

                }

                @Override
                public void onDrawerClosed(View drawerView) {

                }

                @Override
                public void onDrawerStateChanged(int newState) {

                }
            });


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance())
                    .commit();


            //get categories and save to Realm
            webService.getCategories(Config.ACTION_DISPLAY_CATEGORIES, null, null, new RestCallback<CategoryResponse>() {
                @Override
                public void failure(RestError restError) {
                    //Do Nothing on error
                }

                @Override
                public void success(CategoryResponse categoryResponse, Response response) {
                    if (categoryResponse != null) {
                        if (categoryResponse.getStatus() == 200) {
                            if (categoryResponse.getCategoryList() != null) {
                                for (CategoryEntity categoryEntity : categoryResponse.getCategoryList()) {
                                    realm.beginTransaction();
                                    CategoryData data = RealmMapper.toRealm(categoryEntity, realm);
                                    realm.copyToRealmOrUpdate(data);
                                    realm.commitTransaction();
                                }
                            }
                        } else if (categoryResponse.getStatus() == 405) {
                            logout();
                        }
                    }
                }
            });
        }

        mCastStateListener = new CastStateListener() {
            @Override
            public void onCastStateChanged(int newState) {
                if (newState != CastState.NO_DEVICES_AVAILABLE) {
                    showIntroductoryOverlay();
                }
            }
        };

        mCastContext = CastContext.getSharedInstance(this);



        webService.getAppSettings(Config.ACTION_APP_SETTINGS, new RestCallback<AppSettingsResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("TAG", "Failed to get app settings");
            }

            @Override
            public void success(AppSettingsResponse appSettingsResponse, Response response) {
                if(appSettingsResponse.getTotal() != null){
                    if(Integer.parseInt(appSettingsResponse.getTotal()) > 0){
                        settings.saveAd(appSettingsResponse.getAds().get(0));
                        loadAd();
                    }else{
                        settings.clearAd();
                    }
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        mCastContext.addCastStateListener(mCastStateListener);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mCastContext.removeCastStateListener(mCastStateListener);
        EventBus.getDefault().unregister(this);
        super.onPause();
    }


    @Subscribe
    public void onMessageEvent(DrawerMenuEvent event) {
        switch (event.getScreen()) {
            case Screens.SCREEN_MOVIES:
                setActionbarTitle("AFTW.tv", "Movies");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, MoviesFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_SERIES:
                setActionbarTitle("AFTW.tv", "Series");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, SeriesFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_RANDOM_SERIES:
                setActionbarTitle("AFTW.tv", "Random Series");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, RandomSeriesFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_TOP_100:
                setActionbarTitle("AFTW.tv", "Top 100");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, TopHundredFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_MY_WATCHLIST:
                setActionbarTitle("My Watchlist", "");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, WatchListFragment.newInstance(0))
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_LATEST_REVIEWS:
                setActionbarTitle("Latest Reviews", null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, LatestReviewsFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_SETTINGS:
                setActionbarTitle("Settings", null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
            case Screens.SCREEN_LATEST_COMMENTS:
                setActionbarTitle("About", null);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, LatestReviewsFragment.newInstance())
                        .commit();
                mDrawerLayout.closeDrawer(mRecyclerView);
                break;
        }
    }

    @Subscribe
    public void onMoreMovieEvent(MoreMovieEvent moreMovieEvent) {
        setActionbarTitle("Series", "Center");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, SeriesFragment.newInstance())
                .commit();
    }


    @Subscribe
    public void onProfileRequested(ProfileOpenEvent profileOpenEvent) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        mDrawerLayout.closeDrawer(mRecyclerView);
    }

    @Subscribe
    public void onNewsDetailRequested(NewsClickEvent newsClickEvent) {
        String url = newsClickEvent.getLink();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsClickEvent.getLink()));
        startActivity(browserIntent);

    }


    @Subscribe
    public void onSettingsEvent(SettingsEvent settingsEvent) {
        switch (settingsEvent.getAction()) {
            case SettingsAction.ACTION_LOGOUT:
                logout();
                break;
            case SettingsAction.ACTION_ABOUT:
                setActionbarTitle("About", null);
                getSupportFragmentManager()
                        .beginTransaction().addToBackStack("about")
                        .add(R.id.fragment_container, AboutFragment.newInstance())
                        .commit();
                break;
            case 8412:
                Toast.makeText(MainActivity.this, "Launched Nukes", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Subscribe
    public void onSeriesDetail(SeriesClickEvent seriesClickEvent) {
        Intent intent = new Intent(MainActivity.this, SingleSeriesActivity.class);
        intent.putExtra("SERIES_ID_EXTRA", seriesClickEvent.shoSeriesDetail().getId());
        intent.putExtra("SERIES_NAME_EXTRA", seriesClickEvent.shoSeriesDetail().getFullSeriesName());
        intent.putExtra("SERIES_DESCRIPTION_EXTRA", seriesClickEvent.shoSeriesDetail().getDescription());
        startActivity(intent);
    }

    @Subscribe
    public void onWatchListClicked(WatchListClickEvent watchListClickEvent) {
        Intent intent = new Intent(MainActivity.this, WatchlistItemActivity.class);
        intent.putExtra("WATCH_LIST_ENTITY", watchListClickEvent.asString());
        startActivity(intent);
    }


    @Subscribe
    public void onMovieClicked(MovieClickEvent event) {
        Intent intent = new Intent(MainActivity.this, SingleSeriesActivity.class);
        intent.putExtra("SERIES_ID_EXTRA", event.getMovie().getSeriesId());
        intent.putExtra("SERIES_NAME_EXTRA", event.getMovie().getFullSeriesName());
        intent.putExtra("SERIES_DESCRIPTION_EXTRA", event.getMovie().getDescription());
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.media_route_menu_item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * sets title to actionbar
     *
     * @param title
     */
    private void setActionbarTitle(String title, @Nullable String subtitle) {
        getSupportActionBar().setTitle(title);
        if (subtitle != null) {
            getSupportActionBar().setSubtitle(subtitle);
        } else {
            getSupportActionBar().setSubtitle(null);
        }
    }


    private void showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay.remove();
        }
        if ((mediaRouteMenuItem != null) && mediaRouteMenuItem.isVisible()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mIntroductoryOverlay = new IntroductoryOverlay.Builder(
                            MainActivity.this, mediaRouteMenuItem)
                            .setTitleText("AnimeFTW.tv can also be played on your chromecast. Click on the cast icon to connect to your chromecast.")
                            .setSingleTime()
                            .setOnOverlayDismissedListener(
                                    new IntroductoryOverlay.OnOverlayDismissedListener() {
                                        @Override
                                        public void onOverlayDismissed() {
                                            mIntroductoryOverlay = null;
                                        }
                                    })
                            .build();
                    mIntroductoryOverlay.show();
                }
            });
        }
    }


    private void loadAd(){
// Create the adView
        RelativeLayout adLayout = (RelativeLayout)findViewById(R.id.adView);

        if(settings.getAd() != null){
            if(settings.getAd().getEnabled()!=null){
                AdView adView = new AdView(MainActivity.this);
                if(settings.getAd().getEnabled().equalsIgnoreCase("1")){
                     adView.setAdUnitId(settings.getAd().getUnitId());
                    adView.setAdSize(AdSize.SMART_BANNER);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    adLayout.setVisibility(View.VISIBLE);
                    adView.loadAd(adRequest);
                    adLayout.addView(adView);
                }else{
                    adLayout.setVisibility(View.GONE);
                }
            }else{
                adLayout.setVisibility(View.GONE);
            }
        }else{
            adLayout.setVisibility(View.GONE);
        }

    }


}
