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
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.SeriesWatchListAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.WatchListUpdateEvent;
import tv.animeftw.app.model.AddWatchlistResponse;
import tv.animeftw.app.model.BaseResponse;
import tv.animeftw.app.model.WatchListEntity;
import tv.animeftw.app.model.WatchListResponse;

/**
 * Loads Series List on MainActivity when "WatchList" menu is selected from Navigation Drawer.
 * @see tv.animeftw.app.activities.MainActivity
 *
 * Created by darshanz on 4/20/16.
 */
public class SeriesWatchListFragment extends Fragment {

    private static final String ARG_PARAM1 = "seriesId";
    private static final String ARG_PARAM2 = "seriesName";

    private int seriesId;
    private String seriesName;


    @Bind(R.id.watchList)
    RecyclerView watchlistRecyclerView;

    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;

    private ArrayList<WatchListEntity> watchListEntities = new ArrayList<>();
    private SeriesWatchListAdapter mSeriesAdapter;

    public SeriesWatchListFragment() {
        // Required empty public constructor
    }

    public static SeriesWatchListFragment newInstance(int seriesId, String seriesName) {
        SeriesWatchListFragment fragment = new SeriesWatchListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, seriesId);
        args.putString(ARG_PARAM2, seriesName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            seriesId = getArguments().getInt(ARG_PARAM1);
            seriesName = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_series_watchlist, container, false);
        ButterKnife.bind(this, view);

        WatchListEntity watchListEntity = new WatchListEntity();
        watchListEntity.setFullSeriesName(seriesName);
        watchListEntity.setSeriesId(seriesId);
        watchListEntities.clear();
        watchListEntities.add(watchListEntity); // jsut create empty watchlist in case there is none
        watchlistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSeriesAdapter = new SeriesWatchListAdapter(watchListEntities);
        watchlistRecyclerView.setAdapter(mSeriesAdapter);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getWatchList();
    }

    private void getWatchList(){
        Integer sid = null;
        if(seriesId != 0)
            sid =  seriesId;
        spinKitView.setVisibility(View.VISIBLE);
        ((BaseActivity)getActivity()).webService.getWatchlist(Config.ACTION_DISPLAY_WATCHLIST,
                null,
                sid, null,
                new RestCallback<WatchListResponse>() {
                    @Override
                    public void failure(RestError restError) {
                        spinKitView.setVisibility(View.GONE);
                        if(restError.getCode() == 0){
                            Snackbar.make(watchlistRecyclerView, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void success(WatchListResponse watchListResponse, Response response) {
                        spinKitView.setVisibility(View.GONE);
                        if(watchListResponse!=null) {
                            if (watchListResponse.getWatchListItems() != null) {
                                if(isAdded()) {
                                    if (watchListEntities.size()>0) {
                                        watchListEntities.clear();
                                        watchListEntities.addAll(watchListResponse.getWatchListItems());
                                        mSeriesAdapter.notifyDataSetChanged();
                                    }
                                }

                            }
                        }

                    }
                });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }



    @Subscribe
    public void onWatchListUpdateRequested(final WatchListUpdateEvent watchListUpdateEvent){


          if(watchListUpdateEvent.isNew()){
              spinKitView.setVisibility(View.VISIBLE);
              ((BaseActivity)getActivity()).webService.addWatchList(Config.ACTION_ADD_WATCHLIST, seriesId, new RestCallback<AddWatchlistResponse>() {
                  @Override
                  public void success(AddWatchlistResponse watchListResponse, Response response) {
                      spinKitView.setVisibility(View.GONE);
                      if (watchListResponse != null && watchListResponse.getWatchListEntity() != null) {
                          Snackbar.make(watchlistRecyclerView, "Successfully added to watchlist.", Snackbar.LENGTH_SHORT).show();


                          spinKitView.setVisibility(View.VISIBLE);
                          ((BaseActivity)getActivity()).webService.editWatchList(Config.ACTION_EDIT_WATCHLIST,
                                  watchListResponse.getWatchListEntity().getId(),
                                  watchListUpdateEvent.getStatus(),
                                  watchListUpdateEvent.getEmail(),
                                  watchListUpdateEvent.getCurrentEpisode(),
                                  watchListUpdateEvent.getTrackerStatus(),
                                  watchListUpdateEvent.getTrackerLatest(),
                                  watchListUpdateEvent.getComment(), new RestCallback<BaseResponse>() {
                                      @Override
                                      public void failure(RestError restError) {
                                          getWatchList();

                                          spinKitView.setVisibility(View.GONE);
                                          Toast.makeText(getActivity(), "Error updating item.", Toast.LENGTH_SHORT).show();
                                      }

                                      @Override
                                      public void success(BaseResponse baseResponse, Response response) {
                                          getWatchList();

                                          spinKitView.setVisibility(View.GONE);

                                          if (baseResponse != null && baseResponse.getStatus() == 200) {
                                              Toast.makeText(getActivity(), "Item update successful.", Toast.LENGTH_SHORT).show();
                                          } else {
                                              Toast.makeText(getActivity(), "Error updating item.", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  });


                      } else {
                          Snackbar.make(watchlistRecyclerView, "Error adding to watchlist.", Snackbar.LENGTH_SHORT).show();
                      }
                  }

                  @Override
                  public void failure(RestError restError) {
                      spinKitView.setVisibility(View.GONE);
                      Snackbar.make(watchlistRecyclerView, "Error adding to watchlist.", Snackbar.LENGTH_SHORT).show();
                  }
              });



          }else{
              spinKitView.setVisibility(View.VISIBLE);
              ((BaseActivity)getActivity()).webService.editWatchList(Config.ACTION_EDIT_WATCHLIST,
                      watchListUpdateEvent.getId(),
                      watchListUpdateEvent.getStatus(),
                      watchListUpdateEvent.getEmail(),
                      watchListUpdateEvent.getCurrentEpisode(),
                      watchListUpdateEvent.getTrackerStatus(),
                      watchListUpdateEvent.getTrackerLatest(),
                      watchListUpdateEvent.getComment(), new RestCallback<BaseResponse>() {
                          @Override
                          public void failure(RestError restError) {
                              spinKitView.setVisibility(View.GONE);
                              Toast.makeText(getActivity(), "Error updating item.", Toast.LENGTH_SHORT).show();
                          }

                          @Override
                          public void success(BaseResponse baseResponse, Response response) {
                              spinKitView.setVisibility(View.GONE);
                              if (baseResponse != null && baseResponse.getStatus() == 200) {
                                  Toast.makeText(getActivity(), "Item update successful.", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(getActivity(), "Error updating item.", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
          }

    }



    private int getSelectionPositionOf(String item) {
        String[] statusArray = getResources().getStringArray(R.array.entry_status);
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i].equals(item)) {
                return i;
            }
        }
        return 0;
    }


}