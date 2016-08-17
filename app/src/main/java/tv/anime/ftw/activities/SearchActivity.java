package tv.anime.ftw.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import retrofit.client.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import tv.anime.ftw.R;
import tv.anime.ftw.adapter.SearchAdapter;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.model.AppSettingsResponse;
import tv.anime.ftw.model.SearchResponse;

public class SearchActivity extends BaseActivity {

    private Subscription subscription;
    private SearchAdapter searchAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSupportActionBar((Toolbar) findViewById(R.id.layout_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        RecyclerView searchList = (RecyclerView) findViewById(R.id.searchList);
        searchList.setLayoutManager(new LinearLayoutManager(this));

        searchAdapter = new SearchAdapter(this);
        searchList.setAdapter(searchAdapter);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        MenuItemCompat.expandActionView(myActionMenuItem);

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }
        searchView.setIconified(false);
        subscription = RxSearchView.queryTextChanges(searchView)
                .skip(1)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        boolean empty = TextUtils.isEmpty(charSequence);
                        if (empty) searchAdapter.setItems(null);
                        return !empty;
                    }
                }).doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }).flatMap(new Func1<CharSequence, Observable<SearchResponse>>() {
                    @Override
                    public Observable<SearchResponse> call(CharSequence query) {
                        return webService.search("search", query.toString(), 0, 10);
                    }
                }).doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(progressBar, "Search could not complete.", Snackbar.LENGTH_SHORT).show();
                    }
                }).retry().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchResponse>() {
                    @Override
                    public void call(SearchResponse searchResponse) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (searchResponse != null) {
                            searchAdapter.setItems(searchResponse.getSearchResultList());
                            if (searchResponse.getStatus() >= 400) {
                                String message = TextUtils.isEmpty(searchResponse.getMessage()) ?
                                        "Search could not complete." : searchResponse.getMessage();
                                Snackbar.make(progressBar, message, Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(progressBar, "Search could not complete.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    private void loadAd(){
// Create the adView
        RelativeLayout adLayout = (RelativeLayout)findViewById(R.id.adView);

        if(settings.getAd() != null){
            if(settings.getAd().getEnabled()!=null){
                AdView adView = new AdView(SearchActivity.this);
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
