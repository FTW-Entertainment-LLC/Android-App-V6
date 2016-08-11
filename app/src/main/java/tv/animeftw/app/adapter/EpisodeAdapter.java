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

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.animeftw.app.R;
import tv.animeftw.app.events.EpisodeClickEvent;
import tv.animeftw.app.events.SeriesClickEvent;
import tv.animeftw.app.model.EpisodeEntity;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.SeriesViewHolder> {

    private ArrayList<EpisodeEntity> episodeEntities;
    private DisplayImageOptions options;

    public EpisodeAdapter(ArrayList<EpisodeEntity> episodeEntities) {
        this.episodeEntities = episodeEntities;

        Log.e("Tag","EpisodeEntity Size "+ episodeEntities.size());

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.video_holder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episode_row, parent, false);
        return new SeriesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SeriesViewHolder holder, final int position) {


        final EpisodeEntity item = episodeEntities.get(position);

        holder.tvTitle.setText(item.getEpisodeName());
        holder.tvRating.setText("Rating: "+item.getAverageRating());
        holder.tvEpisodeNum.setText("EP. "+item.getEpisodeNumber());


        if (item.getImage() != null) {
            ImageLoader.getInstance().displayImage(item.getImage(), holder.posterImage, options);
        } else {
            //TODO show default poster if poster is not available
            //holder.posterImage.setImageResource(R.drawable.default_poster);
        }

    }

    @Override
    public int getItemCount() {
        return episodeEntities.size();
    }

    /**
     * View holder for  Latest updates
     */
    class SeriesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvEpisodeNum)
        TextView tvEpisodeNum;
        @Bind(R.id.tvRating)
        TextView tvRating;
        @Bind(R.id.posterImage)
        ImageView posterImage;

        public SeriesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   EventBus.getDefault().post(new EpisodeClickEvent(episodeEntities.get(getAdapterPosition())));
                }
            });
        }
    }

}