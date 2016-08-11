package tv.animeftw.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.client.Response;
import tv.animeftw.app.AnimeFtwTvApp;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.activities.accounts.LoginActivity;
import tv.animeftw.app.adapter.DrawerAdapter;
import tv.animeftw.app.adapter.NewsAdapter;
import tv.animeftw.app.adapter.RecentUpdateAdapter;
import tv.animeftw.app.adapter.SettingsAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.events.MoreMovieEvent;
import tv.animeftw.app.events.SettingsEvent;
import tv.animeftw.app.model.BaseResponse;
import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.NewsResponse;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.SeriesResponse;
import tv.animeftw.app.utils.AppSettings;
import tv.animeftw.app.utils.DrawerItemHelper;
import tv.animeftw.app.utils.SettingsAction;

/**
 *
 * Created by darshanz on 4/20/16.
 */
public class SettingsFragment extends Fragment {

    @Bind(R.id.settingsRecyclerView)
    RecyclerView settingsRecyclerView;



    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        settingsRecyclerView.setAdapter(new SettingsAdapter(DrawerItemHelper.getSettingsItem(getActivity())));

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


}
