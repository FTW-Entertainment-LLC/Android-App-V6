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
import tv.animeftw.app.adapter.MoviesAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.PopupMenuItemClickedEvent;
import tv.animeftw.app.model.MovieEntity;
import tv.animeftw.app.model.MovieResponse;
import tv.animeftw.app.ui.PopupMenuHelper;
import tv.animeftw.app.utils.AppSettings;

/**
 * Loads Series List on MainActivity when "Movies" menu is selected from Navigation Drawer.
 *
 * @see tv.animeftw.app.activities.MainActivity
 * <p/>
 * Created by darshanz on 4/20/16.
 */
public class MoviesFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    //TODO implement pagination loading 20 items at a time
    private int totalCount = 20;
    private int start = 0;

    @Bind(R.id.seriesRecyclerView)
    RecyclerView seriesRecyclerView;

    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;

    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;


    private ArrayList<MovieEntity> movieEntities = new ArrayList<>();
    private MoviesAdapter mMoviesAdapter;
    private AppSettings appSettings;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
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

        mMoviesAdapter = new MoviesAdapter(getActivity(), movieEntities, ((BaseActivity) getActivity()).realm);
        setMovieItemView();
        seriesRecyclerView.setAdapter(mMoviesAdapter);
        swipyRefreshLayout.setOnRefreshListener(this);
        appSettings = ((BaseActivity) getActivity()).settings;

        getMovies();
        return view;
    }

    private void setMovieItemView() {
        int movieItemViewType = new AppSettings(getActivity()).getMoviesViewType();
        mMoviesAdapter.setType(movieItemViewType);
        if (movieItemViewType == PopupMenuHelper.VIEW_TYPE_WALL) {
            seriesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            seriesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
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

    @Subscribe
    public void popumMenuItemClickedEvent(PopupMenuItemClickedEvent event) {
        if (event.getMenuId() == R.menu.menu_movies_view_type) {
            setMovieItemView();
            mMoviesAdapter.notifyDataSetChanged();
        } else if (event.getMenuId() == R.menu.menu_movies_sort_type) {
            sortMovieItems(movieEntities);
            mMoviesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getMovies() {
        spinKitView.setVisibility(View.VISIBLE);
        ((BaseActivity) getActivity()).webService.getMovies(Config.ACTION_DISPLAY_MOVIES, start, totalCount, new RestCallback<MovieResponse>() {
            @Override
            public void failure(RestError restError) {
                spinKitView.setVisibility(View.GONE);
                if (swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);
                Log.e("TAG", "" + restError.getStrMessage());
                if (restError.getCode() == 0) {
                    Snackbar.make(seriesRecyclerView, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(MovieResponse movieResponse, Response response) {
                spinKitView.setVisibility(View.GONE);
                if (swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);

                if (movieResponse != null) {
                    if (movieResponse.getMovieList() != null) {
                        movieEntities.clear();
                        sortMovieItems(movieResponse.getMovieList());
                        movieEntities.addAll(movieResponse.getMovieList());
                        mMoviesAdapter.notifyDataSetChanged();
                        seriesRecyclerView.getLayoutManager().scrollToPosition(0);
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
                    getMovies();
                } else {
                    if (swipyRefreshLayout.isRefreshing()) {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                    Snackbar.make(seriesRecyclerView, "This is the first page.", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case BOTTOM:
                start = start + totalCount;
                getMovies();
                break;

        }
    }


    private void sortMovieItems(List<MovieEntity> movies) {
        Comparator<MovieEntity> comparator = null;
        int sortType = appSettings.getMoviesSortType();
        if (sortType == PopupMenuHelper.SORT_TYPE_YEAR || sortType == PopupMenuHelper.SORT_TYPE_DATE_ADDED)
            sortType = PopupMenuHelper.SORT_TYPE_NAME;
        switch (sortType) {
            case PopupMenuHelper.SORT_TYPE_RATEING:
                comparator = new Comparator<MovieEntity>() {
                    @Override
                    public int compare(MovieEntity lhs, MovieEntity rhs) {
                        return Float.compare(lhs.getAverageRating(), rhs.getAverageRating());
                    }
                };
                break;
            case PopupMenuHelper.SORT_TYPE_YEAR:
                comparator = new Comparator<MovieEntity>() {
                    @Override
                    public int compare(MovieEntity lhs, MovieEntity rhs) {
                        return 0;
                    }
                };
                break;
            case PopupMenuHelper.SORT_TYPE_DATE_ADDED:
                comparator = new Comparator<MovieEntity>() {
                    @Override
                    public int compare(MovieEntity lhs, MovieEntity rhs) {
                        return 0;
                    }
                };
                break;
            default:
                comparator = new Comparator<MovieEntity>() {
                    @Override
                    public int compare(MovieEntity lhs, MovieEntity rhs) {
                        if (!TextUtils.isEmpty(lhs.getFullSeriesName()) && !TextUtils.isEmpty(rhs.getFullSeriesName()))
                            return lhs.getFullSeriesName().compareTo(rhs.getFullSeriesName());
                        return 0;
                    }
                };
                break;
        }
        Collections.sort(movies, comparator);
    }

}
