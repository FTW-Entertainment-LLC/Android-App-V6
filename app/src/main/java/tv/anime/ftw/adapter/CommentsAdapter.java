package tv.anime.ftw.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.model.CommentEntity;
import tv.anime.ftw.utils.DateHelper;

/**
 * Created by darshanz on 5/2/16.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    private ArrayList<CommentEntity> comments;


    public CommentsAdapter(ArrayList<CommentEntity> comments) {
        this.comments = comments;

    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CommentViewHolder holder, final int position) {


        final CommentEntity item = comments.get(position);

        holder.comment.setText(Html.fromHtml(item.getComment()));
        holder.date.setText(DateHelper.toDisplayDate(item.getDated()));
        holder.author.setText(item.getUser());
        if(item.getSpoiler() == 1){
            holder.spoiler.setVisibility(View.VISIBLE);
        }else{
            holder.spoiler.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    /**
     * View holder for  Latest updates
     */
    class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.comment)
        TextView comment;
        @Bind(R.id.spoiler)
        TextView spoiler;

        public CommentViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
        }
    }

}