package tv.anime.ftw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.adapter.SettingsAdapter;
import tv.anime.ftw.utils.DrawerItemHelper;

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
