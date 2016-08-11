package tv.animeftw.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.RecentUpdateAdapter;
import tv.animeftw.app.adapter.SeriesAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.PopupMenuItemClickedEvent;
import tv.animeftw.app.model.MovieEntity;
import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.SeriesResponse;
import tv.animeftw.app.ui.PopupMenuHelper;
import tv.animeftw.app.utils.AppSettings;

/**
 * Loads Series List on MainActivity when "Series" menu is selected from Navigation Drawer.
 *
 * @see tv.animeftw.app.activities.MainActivity
 * <p/>
 * Created by darshanz on 4/20/16.
 */
public class SeriesFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    //TODO implement pagination loading 20 items at a time
    private int totalCount = 20;
    private int start = 0;

    @Bind(R.id.seriesRecyclerView)
    RecyclerView seriesRecyclerView;

    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;

    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;

    LinearLayoutManager linearLayoutManager;


    private ArrayList<SeriesEntity> seriesEntities = new ArrayList<>();

    private SeriesAdapter mSeriesAdapter;
    private AppSettings appSettings;

    public SeriesFragment() {
        // Required empty public constructor
    }

    public static SeriesFragment newInstance() {
        SeriesFragment fragment = new SeriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_series, container, false);

        ButterKnife.bind(this, view);

        appSettings = ((BaseActivity) getActivity()).settings;
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mSeriesAdapter = new SeriesAdapter(seriesEntities, ((BaseActivity) getActivity()).realm);
        setSeriesItemView();
        seriesRecyclerView.setAdapter(mSeriesAdapter);
        swipyRefreshLayout.setOnRefreshListener(this);
        getSeries();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movies, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewType:
                new PopupMenuHelper(R.menu.menu_movies_view_type, getActivity().findViewById(item.getItemId())).show();
                break;
            case R.id.sort:
                new PopupMenuHelper(R.menu.menu_movies_sort_type, getActivity().findViewById(item.getItemId())).show();
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }


    @Subscribe
    public void popumMenuItemClickedEvent(PopupMenuItemClickedEvent event) {
        if (event.getMenuId() == R.menu.menu_movies_view_type) {
            setSeriesItemView();
            mSeriesAdapter.notifyDataSetChanged();
        } else if (event.getMenuId() == R.menu.menu_movies_sort_type) {
            sortSeriesItems(seriesEntities);
            mSeriesAdapter.notifyDataSetChanged();
        }
    }

    private void setSeriesItemView() {
        int movieItemViewType = new AppSettings(getActivity()).getMoviesViewType();
        mSeriesAdapter.setType(movieItemViewType);
        if (movieItemViewType == PopupMenuHelper.VIEW_TYPE_WALL) {
            seriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            seriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void getSeries() {
        ((BaseActivity) getActivity()).webService.getSeries(Config.ACTION_DISPLAY_SERIES, totalCount, start, true, new RestCallback<SeriesResponse>() {
            @Override
            public void failure(RestError restError) {

                if (swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);


                spinKitView.setVisibility(View.GONE);
                Log.e("TAG", "" + restError.getStrMessage());
                if (restError.getCode() == 0) {
                    Snackbar.make(seriesRecyclerView, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(SeriesResponse seriesResponse, Response response) {

                if (swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);

                spinKitView.setVisibility(View.GONE);
                if (seriesResponse != null) {
                    if (seriesResponse.getSeriesList() != null) {
                        seriesEntities.clear();
                        sortSeriesItems(seriesResponse.getSeriesList());
                        seriesEntities.addAll(seriesResponse.getSeriesList());
                        mSeriesAdapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(0);
                    }
                }

            }
        });
    }


    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        switch (direction) {
            case TOP:
                if (start > 0) {
                    start = start - totalCount;
                    getSeries();
                } else {
                    if (swipyRefreshLayout.isRefreshing()) {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                    Snackbar.make(seriesRecyclerView, "This is the first page.", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case BOTTOM:
                start = start + totalCount;
                getSeries();
                break;

        }
    }

    private void sortSeriesItems(List<SeriesEntity> series) {
        Comparator<SeriesEntity> comparator = null;
        int sortType = appSettings.getMoviesSortType();
        if (sortType == PopupMenuHelper.SORT_TYPE_YEAR || sortType == PopupMenuHelper.SORT_TYPE_DATE_ADDED)
            sortType = R.id.name;
        switch (sortType) {
            case PopupMenuHelper.SORT_TYPE_RATEING:
                comparator = new Comparator<SeriesEntity>() {
                    @Override
                    public int compare(SeriesEntity lhs, SeriesEntity rhs) {
                        return lhs.getRating().compareTo(rhs.getRating());
                    }
                };
                break;
            case PopupMenuHelper.SORT_TYPE_YEAR:
                comparator = new Comparator<SeriesEntity>() {
                    @Override
                    public int compare(SeriesEntity lhs, SeriesEntity rhs) {
                        return 0;
                    }
                };
                break;
            case PopupMenuHelper.SORT_TYPE_DATE_ADDED:
                comparator = new Comparator<SeriesEntity>() {
                    @Override
                    public int compare(SeriesEntity lhs, SeriesEntity rhs) {
                        return 0;
                    }
                };
                break;
            default:
                comparator = new Comparator<SeriesEntity>() {

                    @Override
                    public int compare(SeriesEntity lhs, SeriesEntity rhs) {
                        if ((!TextUtils.isEmpty(lhs.getFullSeriesName())) && !TextUtils.isEmpty(rhs.getFullSeriesName()))
                            return lhs.getFullSeriesName().compareTo(rhs.getFullSeriesName());
                        return 0;
                    }
                };
                break;
        }
        Collections.sort(series, comparator);
    }
}
