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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.RecentUpdateAdapter;
import tv.animeftw.app.adapter.WatchListAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.model.WatchListEntity;
import tv.animeftw.app.model.WatchListResponse;

/**
 * Loads Series List on MainActivity when "WatchList" menu is selected from Navigation Drawer.
 * @see tv.animeftw.app.activities.MainActivity
 *
 * Created by darshanz on 4/20/16.
 */
public class WatchListFragment extends Fragment {

    private static final String ARG_PARAM1 = "seriesId";

    //TODO implement pagination loading 20 items at a time
    private int totalCount = 20;
    private int currentPage = 0;
    private int seriesId;

    @Bind(R.id.watchList)
    RecyclerView watchlistRecyclerView;


    private ArrayList<WatchListEntity> watchListEntities = new ArrayList<>();
    private WatchListAdapter mSeriesAdapter;

    public WatchListFragment() {
        // Required empty public constructor
    }

    public static WatchListFragment newInstance(int seriesId) {
        WatchListFragment fragment = new WatchListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, seriesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seriesId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_watchlist, container, false);
        ButterKnife.bind(this, view);

        watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSeriesAdapter = new WatchListAdapter(watchListEntities);
        watchlistRecyclerView.setAdapter(mSeriesAdapter);
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
    public void onResume() {
        super.onResume();
        getSeries(1);
    }

    private void getSeries(int page){

        Integer sid = null;
        if(seriesId != 0)
            sid =  seriesId;

        ((BaseActivity)getActivity()).webService.getWatchlist(Config.ACTION_DISPLAY_WATCHLIST,
                null,
                sid, null,
                new RestCallback<WatchListResponse>() {
                    @Override
                    public void failure(RestError restError) {
                        if(restError.getCode() == 0){
                            Snackbar.make(watchlistRecyclerView, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void success(WatchListResponse watchListResponse, Response response) {
                        if(watchListResponse!=null) {
                            if (watchListResponse.getWatchListItems() != null) {
                                watchListEntities.clear();
                                watchListEntities.addAll(watchListResponse.getWatchListItems());
                                mSeriesAdapter.notifyDataSetChanged();
                                currentPage++;
                            }
                        }

                    }
                });
    }

}