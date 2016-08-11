package tv.animeftw.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.NewsAdapter;
import tv.animeftw.app.adapter.RecentUpdateAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.MoreMovieEvent;
import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.NewsResponse;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.SeriesResponse;

/**
 * Home fragment is the one that loads first when app is opened.
 * Loads on Oncreate of the MainActivity.
 * <p/>
 * Created by darshanz on 4/20/16.
 */
public class HomeFragment extends Fragment {

    @Bind(R.id.recentRecyclerView)
    RecyclerView recyclerViewRecent;
    @Bind(R.id.newsRecyclerView)
    RecyclerView recyclerNews;
    @Bind(R.id.btnMore)
    Button btnMore;
    @Bind(R.id.recentLabel)
    TextView recentLabel;
    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;


    private ArrayList<SeriesEntity> seriesEntities;
    private ArrayList<NewsItem> newsItems;
    private RecentUpdateAdapter mUpdateAdapter;
    private NewsAdapter mNewsAdapter;
    private boolean loadingDone = false;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        seriesEntities = new ArrayList<>();
        newsItems = new ArrayList<>();

        recyclerViewRecent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mUpdateAdapter = new RecentUpdateAdapter(seriesEntities);
        recyclerViewRecent.setAdapter(mUpdateAdapter);


        recyclerNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsAdapter = new NewsAdapter(newsItems);
        recyclerNews.setAdapter(mNewsAdapter);


        recentLabel.setVisibility(View.INVISIBLE);
        btnMore.setVisibility(View.INVISIBLE);
        spinKitView.setVisibility(View.VISIBLE);

        //Load Recent Series
        getLatestSeries();
        getLatestNews();

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MoreMovieEvent());
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * Get latest Series from webservice
     */
    private void getLatestSeries() {
        ((BaseActivity) getActivity()).webService.getSeries(Config.ACTION_DISPLAY_SERIES, 10, 0, true, new RestCallback<SeriesResponse>() {
            @Override
            public void failure(RestError restError) {

                if (loadingDone) {
                    spinKitView.setVisibility(View.GONE);
                }
                loadingDone = true;

                if (restError.getCode() == 0) {
                    Snackbar.make(recyclerViewRecent, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(SeriesResponse seriesResponse, Response response) {
                if (seriesResponse.getStatus() == 200) {


                    if (loadingDone) {
                        spinKitView.setVisibility(View.GONE);
                    }
                    loadingDone = true;


                    recentLabel.setVisibility(View.VISIBLE);
                    btnMore.setVisibility(View.VISIBLE);


                    if (seriesResponse.getSeriesList() != null) {
                        seriesEntities.clear();
                        seriesEntities.addAll(seriesResponse.getSeriesList());
                        mUpdateAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }


    private void getLatestNews() {
        ((BaseActivity) getActivity()).webService.getLatestNews(Config.ACTION_LATEST_NES, new RestCallback<NewsResponse>() {
            @Override
            public void failure(RestError restError) {

                if (loadingDone) {
                    spinKitView.setVisibility(View.GONE);
                }
                loadingDone = true;

                if (restError.getCode() == 0) {
                    Snackbar.make(recyclerViewRecent, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(NewsResponse newsResponse, Response response) {

                if (loadingDone) {
                    spinKitView.setVisibility(View.GONE);
                }
                loadingDone = true;

                if (newsResponse.getStatus() == 200) {
                    if (newsResponse.getNewsItems() != null) {
                        newsItems.clear();
                        newsItems.addAll(newsResponse.getNewsItems());
                        mNewsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
