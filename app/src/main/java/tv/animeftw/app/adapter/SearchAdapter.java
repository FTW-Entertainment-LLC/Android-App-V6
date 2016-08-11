package tv.animeftw.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import tv.animeftw.app.activities.SingleSeriesActivity;
import tv.animeftw.app.databinding.ListItemSearchBinding;
import tv.animeftw.app.model.SearchResultEntity;
import tv.animeftw.app.model.realm.CategoryData;
import tv.animeftw.app.utils.CategoryParser;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private final LayoutInflater inflater;

    private List<SearchResultEntity> items;

    public SearchAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHolder(ListItemSearchBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        final SearchResultEntity item = items.get(position);
        holder.binding.setItem(item);
        if (!TextUtils.isEmpty(item.getCategory())) {
            ArrayList<CategoryData> categoryDatas = CategoryParser.parseCategory(item.getCategory(), Realm.getDefaultInstance());
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < categoryDatas.size(); i++) {
                builder.append(categoryDatas.get(i).getName());
                if (i < categoryDatas.size() - 1) {
                    builder.append(", ");
                }
            }
            holder.binding.setCategory(builder.toString());
        } else {
            holder.binding.setCategory("");
        }
        holder.binding.setItem(item);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SingleSeriesActivity.class);
                intent.putExtra("SERIES_ID_EXTRA", item.getId());
                intent.putExtra("SERIES_NAME_EXTRA", item.getFullSeriesName());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        else
            return items.size();
    }

    public void setItems(List<SearchResultEntity> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class SearchHolder extends RecyclerView.ViewHolder {
        private ListItemSearchBinding binding;

        public SearchHolder(ListItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
