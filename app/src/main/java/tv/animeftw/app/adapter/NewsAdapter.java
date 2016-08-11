package tv.animeftw.app.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import tv.animeftw.app.R;
import tv.animeftw.app.events.NewsClickEvent;
import tv.animeftw.app.model.NewsItem;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsItem> newsItems;
    private DisplayImageOptions options;


    public NewsAdapter(ArrayList<NewsItem> newsItems) {
        this.newsItems = newsItems;
        Log.e("TAG","<>"+new Gson().toJson(newsItems));

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.video_holder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_latest_news, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {


        final NewsItem item = newsItems.get(position);

        holder.newsTitle.setText(item.getTopicTitle());
        holder.newsBody.setText(Html.fromHtml(item.getBody()));

        if (item.getPosterAvatar() != null) {
            ImageLoader.getInstance().displayImage(item.getPosterAvatar(), holder.posterImage, options);
        } else {
            //TODO show default poster if poster is not available
            //holder.posterImage.setImageResource(R.drawable.default_poster);
        }

    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    /**
     * View holder for  Latest updates
     */
    class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.newsTitle)
        TextView newsTitle;
        @Bind(R.id.imgPosterAvatar)
        CircleImageView posterImage;
        @Bind(R.id.newsBody)
        TextView newsBody;

        public NewsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new NewsClickEvent(newsItems.get(getAdapterPosition()).getTopicLink()));
                }
            });
        }
    }

}