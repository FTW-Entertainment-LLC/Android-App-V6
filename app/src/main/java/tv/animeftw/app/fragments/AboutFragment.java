package tv.animeftw.app.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.animeftw.app.BuildConfig;
import tv.animeftw.app.R;

/**
 * Loads About app   when "About" menu is selected from Navigation Drawer.
 * @see tv.animeftw.app.activities.MainActivity
 *
 * Created by darshanz on 4/20/16.
 */
public class AboutFragment extends Fragment {


    @Bind(R.id.version)
    TextView tvVersion;

    @Bind(R.id.about_content)
    TextView tvAboutContent;

    @Bind(R.id.linkFeedback)
    TextView tvLinkFeedback;

    @Bind(R.id.rateApp)
    TextView tvRateApp;

    @Bind(R.id.homepage)
    TextView tvHomepage;

    @Bind(R.id.copyright)
    TextView tvCopyright;


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
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
        View view =  inflater.inflate(R.layout.fragment_about, container, false);

        ButterKnife.bind(this, view);

       // Typeface tf = FontCache.get("Quadranta-Bold-OTF.otf", getActivity());

        tvVersion.setTextColor(Color.WHITE);
        tvVersion.setText(String.format(getResources().getString(R.string.version_x), BuildConfig.VERSION_NAME));

        tvAboutContent.setTextColor(Color.WHITE);
        tvRateApp.setTextColor(getResources().getColor(R.color.orange));
        tvLinkFeedback.setTextColor(getResources().getColor(R.color.orange));
        tvHomepage.setTextColor(getResources().getColor(R.color.orange));

        tvRateApp.setPaintFlags(tvRateApp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLinkFeedback.setPaintFlags(tvLinkFeedback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvHomepage.setPaintFlags(tvHomepage.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
