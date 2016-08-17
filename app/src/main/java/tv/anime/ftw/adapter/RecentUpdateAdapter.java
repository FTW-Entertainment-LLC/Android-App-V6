package tv.anime.ftw.adapter;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.events.SeriesClickEvent;
import tv.anime.ftw.model.SeriesEntity;

public class RecentUpdateAdapter extends RecyclerView.Adapter<RecentUpdateAdapter.LatestUpdateViewHolder> {

    private ArrayList<SeriesEntity> seriesEntities;
    private DisplayImageOptions options;

    public RecentUpdateAdapter(ArrayList<SeriesEntity> seriesEntities) {
        this.seriesEntities = seriesEntities;

        Log.e("Tag","seriesENityL "+ seriesEntities.size());

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.video_holder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    @Override
    public LatestUpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_update, parent, false);
        return new LatestUpdateViewHolder(view);
    }


    @Override
    public void onBindViewHolder(LatestUpdateViewHolder holder, final int position) {


        final SeriesEntity item = seriesEntities.get(position);

        holder.titleText.setText(item.getFullSeriesName());

        if (item.getImage() != null) {
            ImageLoader.getInstance().displayImage(item.getImage(), holder.posterImage, options);
        } else {
            //TODO show default poster if poster is not available
            //holder.posterImage.setImageResource(R.drawable.default_poster);
        }

    }

    @Override
    public int getItemCount() {
        return seriesEntities.size();
    }

    /**
     * View holder for  Latest updates
     */
    class LatestUpdateViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.titleText)
        TextView titleText;
        @Bind(R.id.posterImage)
        ImageView posterImage;

        public LatestUpdateViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new SeriesClickEvent(seriesEntities.get(getAdapterPosition())));
                }
            });
        }
    }

}