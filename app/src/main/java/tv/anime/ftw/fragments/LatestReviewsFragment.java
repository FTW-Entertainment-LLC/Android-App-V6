package tv.anime.ftw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tv.anime.ftw.R;

/**
 * Loads Series List on MainActivity when "Series" menu is selected from Navigation Drawer.
 * @see tv.anime.ftw.activities.MainActivity
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
