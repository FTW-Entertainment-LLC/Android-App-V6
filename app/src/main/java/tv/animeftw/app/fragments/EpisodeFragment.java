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

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.EpisodeAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.EpisodeClickEvent;
import tv.animeftw.app.events.PlayEpisodeRequestEvent;
import tv.animeftw.app.model.EpisodeEntity;
import tv.animeftw.app.model.EpisodeResponse;
import tv.animeftw.app.ui.SimpleDividerItemDecoration;

/**
 * Displays the list of all the episode withing the serries
 * <p>
 * Episode Fragment Loads within the SingleSeriesActivity
 *
 * @see tv.animeftw.app.activities.SingleSeriesActivity
 * <p>
 * <p>
 * Created by darshanz on 4/20/16.
 */
public class EpisodeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private int start = 0;
    private int current = 0;

    private int seriesId;
    private ArrayList<EpisodeEntity> episodeList = new ArrayList<>();

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;

    private EpisodeAdapter mAdapter;


    public EpisodeFragment() {
        // Required empty public constructor
    }

    public static EpisodeFragment newInstance(int param1) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
        View view = inflater.inflate(R.layout.fragment_episodes, container, false);

        ButterKnife.bind(this, view);

        swipyRefreshLayout.setEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        getEpisodes();
        mAdapter = new EpisodeAdapter(episodeList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void getEpisodes() {
        ((BaseActivity) getActivity()).webService.getEpisodes(Config.ACTION_DISPLAY_EPISODES,
                seriesId,
                start,
                Config.EPISODES_PER_PAGE,
                false,
                null,
                new RestCallback<EpisodeResponse>() {
                    @Override
                    public void failure(RestError restError) {
                        swipyRefreshLayout.setRefreshing(false);
                        if (restError.getCode() == 0) {
                            Toast.makeText(getActivity(),
                                    getActivity().getResources().getString(R.string.failed_to_connect_msg),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void success(EpisodeResponse episodeResponse, Response response) {
                        swipyRefreshLayout.setRefreshing(false);
                        if (episodeResponse != null) {
                            if (episodeResponse.getEpisodeList() != null) {
                                episodeList.clear();
                                episodeList.addAll(episodeResponse.getEpisodeList());
                                mAdapter.notifyDataSetChanged();
                                current += Config.EPISODES_PER_PAGE;
                                if (episodeResponse.getTotalEpisodes() > current) {
                                    swipyRefreshLayout.setEnabled(true);
                                    swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                                        @Override
                                        public void onRefresh(SwipyRefreshLayoutDirection direction) {
                                            if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                                                loadMoreEpisodes();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
    }

    private void loadMoreEpisodes() {
        ((BaseActivity) getActivity()).webService.getEpisodes(Config.ACTION_DISPLAY_EPISODES,
                seriesId,
                current,
                Config.EPISODES_PER_PAGE,
                false,
                null,
                new RestCallback<EpisodeResponse>() {
                    @Override
                    public void failure(RestError restError) {
                        if (isResumed()) {
                            swipyRefreshLayout.setRefreshing(false);
                            if (restError.getCode() == 0) {
                                Toast.makeText(getActivity(),
                                        getActivity().getResources().getString(R.string.failed_to_connect_msg),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void success(EpisodeResponse episodeResponse, Response response) {
                        if (isResumed()) {
                            swipyRefreshLayout.setRefreshing(false);
                            if (episodeResponse != null) {
                                if (episodeResponse.getEpisodeList() != null) {
                                    final int currentItems = episodeList.size();
                                    episodeList.addAll(episodeResponse.getEpisodeList());
                                    mAdapter.notifyItemRangeInserted(currentItems, episodeList.size() - 1);
                                    current += Config.EPISODES_PER_PAGE;
                                    if (episodeResponse.getTotalEpisodes() < current) {
                                        swipyRefreshLayout.setEnabled(false);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @Subscribe
    public void onPlayEpisodeRequestEvent(PlayEpisodeRequestEvent event) {
        if (!episodeList.isEmpty()) {
            EventBus.getDefault().post(new EpisodeClickEvent(episodeList.get(0)));
        } else {
            Snackbar.make(mRecyclerView, "Episodes not loaded.", Snackbar.LENGTH_SHORT).show();
        }
    }
}
