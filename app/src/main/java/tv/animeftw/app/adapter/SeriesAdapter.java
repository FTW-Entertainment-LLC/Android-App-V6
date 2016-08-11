package tv.animeftw.app.adapter;

import android.databinding.ViewDataBinding;
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
import io.realm.Realm;
import tv.animeftw.app.BR;
import tv.animeftw.app.R;
import tv.animeftw.app.databinding.ListItemSeriesTypeDetailBinding;
import tv.animeftw.app.databinding.ListItemSeriesTypeFanArtBinding;
import tv.animeftw.app.databinding.ListItemSeriesTypeWallBinding;
import tv.animeftw.app.events.SeriesClickEvent;
import tv.animeftw.app.model.SeriesEntity;
import tv.animeftw.app.model.realm.CategoryData;
import tv.animeftw.app.ui.PopupMenuHelper;
import tv.animeftw.app.utils.CategoryParser;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {

    private ArrayList<SeriesEntity> seriesEntities;
    private DisplayImageOptions options;
    private Realm realm;
    private int type;


    public SeriesAdapter(ArrayList<SeriesEntity> seriesEntities, Realm realm) {
        this.seriesEntities = seriesEntities;
        this.realm = realm;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.video_holder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case PopupMenuHelper.VIEW_TYPE_FAN_ART:
                viewDataBinding = ListItemSeriesTypeFanArtBinding.inflate(inflater, parent, false);
                break;
            case PopupMenuHelper.VIEW_TYPE_WALL:
                viewDataBinding = ListItemSeriesTypeWallBinding.inflate(inflater, parent, false);
                break;
            default:
                viewDataBinding = ListItemSeriesTypeDetailBinding.inflate(inflater, parent, false);
                break;
        }
        return new SeriesViewHolder(viewDataBinding);
    }


    @Override
    public void onBindViewHolder(SeriesViewHolder holder, final int position) {
        final SeriesEntity item = seriesEntities.get(position);
        if (type != R.id.wall) {
            ArrayList<CategoryData> categoryDatas = CategoryParser.parseCategory(item.getCategory(), realm);
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < categoryDatas.size(); i++) {
                builder.append(categoryDatas.get(i).getName());
                if (i < categoryDatas.size() - 1) {
                    builder.append(", ");
                }
            }
            holder.binding.setVariable(BR.genere, builder.toString());
        }
        holder.binding.setVariable(BR.series, item);
    }

    @Override
    public int getItemCount() {
        return seriesEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    /**
     * View holder for  Latest updates
     */
    class SeriesViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public SeriesViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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