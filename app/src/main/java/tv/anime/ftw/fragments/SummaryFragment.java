package tv.anime.ftw.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.events.SeriesLoadEvent;
import tv.anime.ftw.model.SeriesEntity;


/**
 * Displays summary of the series
 * <p/>
 * Summary Fragment Loads within the SingleSeriesActivity
 *
 * @see tv.anime.ftw.activities.SingleSeriesActivity
 * <p/>
 * Created by darshanz on 4/20/16.
 */
public class SummaryFragment extends Fragment {

    private static final String ARG_PARAM1 = "description";
    private static final String ARG_PARAM2 = "reviews";
    private static final String ARG_PARAM3 = "comments";

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private String summaryText;
    private int commentCount;
    private int reviewCount;
    private SummaryAdapter mAdapter;


    public SummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, int reviewCount, int commentCount) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, reviewCount);
        args.putInt(ARG_PARAM3, commentCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            summaryText = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SummaryAdapter(summaryText, reviewCount, commentCount);
        mRecyclerView.setAdapter(mAdapter);
        return view;
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


    class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

        private String summary;
        private int reviewCount;
        private int commentCount;


        public SummaryAdapter(String summary, int reviewCount, int commentCount) {
            this.summary = summary;
            this.reviewCount = reviewCount;
            this.commentCount = commentCount;

        }

        public void setData(String summary, int reviewCount, int commentCount) {
            this.summary = summary;
            this.reviewCount = reviewCount;
            this.commentCount = commentCount;
        }

        @Override
        public SummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
            return new SummaryViewHolder(view);
        }


        @Override
        public void onBindViewHolder(SummaryViewHolder holder, final int position) {
            switch (position) {
                case 0:
                    holder.summaryText.setVisibility(View.VISIBLE);
                    holder.reviewsLayout.setVisibility(View.GONE);
                    holder.summaryText.setText(summary);
                    break;
                case 1:
                    holder.summaryText.setVisibility(View.GONE);
                    holder.summaryText.setVisibility(View.VISIBLE);
                    holder.icon.setImageResource(R.drawable.ic_folder_latest);
                    holder.menuTitle.setText("Reviews");
                    holder.count.setText("" + reviewCount);
                    break;
                case 2:
                    holder.summaryText.setVisibility(View.GONE);
                    holder.summaryText.setVisibility(View.VISIBLE);
                    holder.icon.setImageResource(R.drawable.ic_message_black_24dp);
                    holder.menuTitle.setText("Comments");
                    holder.count.setText("" + commentCount);
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        /**
         * View holder for  Latest updates
         */
        class SummaryViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.summaryText)
            TextView summaryText;

            @Bind(R.id.reviewsLayout)
            RelativeLayout reviewsLayout;

            @Bind(R.id.icon)
            ImageView icon;

            @Bind(R.id.menuTitle)
            TextView menuTitle;

            @Bind(R.id.count)
            TextView count;

            public SummaryViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }


    @Subscribe
    public void onSeriesLoadEvent(SeriesLoadEvent seriesLoadEvent) {
        SeriesEntity entity = seriesLoadEvent.getSeriesEntity();
        summaryText = entity.getDescription();
        reviewCount = entity.getTotalReviews();
        commentCount = entity.getTotalReviews();
        mAdapter.setData(summaryText, reviewCount, commentCount);
        mAdapter.notifyDataSetChanged();
    }


}
