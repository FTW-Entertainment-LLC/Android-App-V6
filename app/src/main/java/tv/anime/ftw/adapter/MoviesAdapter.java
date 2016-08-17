package tv.anime.ftw.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.realm.Realm;
import tv.anime.ftw.BR;
import tv.anime.ftw.R;
import tv.anime.ftw.databinding.ListItemMovieTypeDetailBinding;
import tv.anime.ftw.databinding.ListItemMovieTypeFanArtBinding;
import tv.anime.ftw.databinding.ListItemMovieTypeWallBinding;
import tv.anime.ftw.events.MovieClickEvent;
import tv.anime.ftw.model.MovieEntity;
import tv.anime.ftw.model.realm.CategoryData;
import tv.anime.ftw.ui.PopupMenuHelper;
import tv.anime.ftw.utils.CategoryParser;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.SeriesViewHolder> {
    private ArrayList<MovieEntity> movieEntities;
    private Realm realm;
    private int type;
    private final LayoutInflater inflater;

    public MoviesAdapter(Context context, ArrayList<MovieEntity> movieEntities, Realm realm) {
        this.movieEntities = movieEntities;
        this.realm = realm;
        this.inflater = LayoutInflater.from(context);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding;
        switch (viewType) {
            case PopupMenuHelper.VIEW_TYPE_FAN_ART:
                viewDataBinding = ListItemMovieTypeFanArtBinding.inflate(inflater, parent, false);
                break;
            case PopupMenuHelper.VIEW_TYPE_WALL:
                viewDataBinding = ListItemMovieTypeWallBinding.inflate(inflater, parent, false);
                break;
            default:
                viewDataBinding = ListItemMovieTypeDetailBinding.inflate(inflater, parent, false);
                break;

        }
        return new SeriesViewHolder(viewDataBinding);
    }


    @Override
    public void onBindViewHolder(SeriesViewHolder holder, final int position) {
        final MovieEntity item = movieEntities.get(position);

        if (type != R.id.wall) {
            ArrayList<CategoryData> categoryDatas = CategoryParser.parseCategory(item.getCategory(), realm);
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < categoryDatas.size(); i++) {
                builder.append(categoryDatas.get(i).getName());
                if (i < categoryDatas.size() - 1) {
                    builder.append(", ");
                }
            }
            holder.binding.setVariable(BR.categories, builder.toString());
        }

        holder.binding.setVariable(BR.movie, item);
    }

    @Override
    public int getItemCount() {
        return movieEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    /**
     * View holder for  Latest updates
     */
    class SeriesViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public SeriesViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new MovieClickEvent(movieEntities.get(getAdapterPosition())));
                }
            });
        }
    }

}