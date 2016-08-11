package tv.animeftw.app.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.animeftw.app.R;
import tv.animeftw.app.events.SeriesClickEvent;
import tv.animeftw.app.events.WatchListClickEvent;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.WatchListEntity;
import tv.animeftw.app.model.realm.CategoryData;
import tv.animeftw.app.utils.CategoryParser;
import tv.animeftw.app.utils.DateHelper;

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