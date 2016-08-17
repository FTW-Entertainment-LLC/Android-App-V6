package tv.anime.ftw.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.events.WatchListClickEvent;
import tv.anime.ftw.model.WatchListEntity;
import tv.anime.ftw.utils.DateHelper;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder> {

    private ArrayList<WatchListEntity> seriesEntities;
    private DisplayImageOptions options;

    public WatchListAdapter(ArrayList<WatchListEntity> seriesEntities) {
        this.seriesEntities = seriesEntities;

        Log.e("Tag", "WatchList size " + seriesEntities.size());

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    @Override
    public WatchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watchlist_row, parent, false);
        return new WatchListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(WatchListViewHolder holder, final int position) {


        final WatchListEntity item = seriesEntities.get(position);


        holder.tvTitle.setText("" + item.getFullSeriesName());
        Log.e("TAG", "long " + item.getUpdated() + " Date" + DateHelper.getMDYFromat(item.getUpdated()*1000));

        holder.tvUpdated.setText("Update: " + DateHelper.getMDYFromat(item.getUpdated()*1000));

    }

    @Override
    public int getItemCount() {
        return seriesEntities.size();
    }

    /**
     * View holder for  Latest updates
     */
    class WatchListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvUpdated)
        TextView tvUpdated;

        public WatchListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new WatchListClickEvent(seriesEntities.get(getAdapterPosition())));
                }
            });

        }
    }

}