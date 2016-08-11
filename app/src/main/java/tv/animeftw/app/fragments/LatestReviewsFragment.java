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

import java.util.ArrayList;

import butterknife.Bind;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.activities.BaseActivity;
import tv.animeftw.app.adapter.RecentUpdateAdapter;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.model.NewsItem;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.SeriesResponse;

/**
 * Loads Series List on MainActivity when "Series" menu is selected from Navigation Drawer.
 * @see tv.animeftw.app.activities.MainActivity
 *
 * Created by darshanz on 4/20/16.
 */
public class LatestReviewsFragment extends Fragment {



    public LatestReviewsFragment() {
        // Required empty public constructor
    }

    public static LatestReviewsFragment newInstance() {
        LatestReviewsFragment fragment = new LatestReviewsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_latest_reviews, container, false);


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
