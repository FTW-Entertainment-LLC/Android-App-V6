package tv.animeftw.app.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

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
import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.SeriesResponse;

/**
 * Loads Series List on MainActivity when "Top 100" menu is selected from Navigation Drawer.
 * @see tv.animeftw.app.activities.MainActivity
 *
 * Created by darshanz on 4/20/16.
 */
public class TopHundredFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

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

    public TopHundredFragment() {
        // Required empty public constructor
    }

    public static TopHundredFragment newInstance() {
        TopHundredFragment fragment = new TopHundredFragment();
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
        View view =  inflater.inflate(R.layout.fragment_series, container, false);

        ButterKnife.bind(this, view);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        seriesRecyclerView.setLayoutManager(linearLayoutManager);

        mSeriesAdapter = new SeriesAdapter(seriesEntities, ((BaseActivity)getActivity()).realm);
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


    private void getSeries(){
        ((BaseActivity)getActivity()).webService.getTopSeries(Config.ACTION_TOP_SERIES, totalCount, start, new RestCallback<SeriesResponse>() {
            @Override
            public void failure(RestError restError) {
                if(swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);


                spinKitView.setVisibility(View.GONE);

                Log.e("TAG",""+restError.getStrMessage());
                if(restError.getCode() == 0){
                    Snackbar.make(seriesRecyclerView, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void success(SeriesResponse seriesResponse, Response response) {
                if(swipyRefreshLayout.isRefreshing())
                    swipyRefreshLayout.setRefreshing(false);


                spinKitView.setVisibility(View.GONE);

                if(seriesResponse!=null){
                    if(seriesResponse.getSeriesList()!=null){
                        seriesEntities.clear();
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
                    if(swipyRefreshLayout.isRefreshing()) {
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
}
