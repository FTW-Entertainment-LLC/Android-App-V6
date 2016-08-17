package tv.anime.ftw.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.events.WatchListClickEvent;
import tv.anime.ftw.events.WatchListUpdateEvent;
import tv.anime.ftw.model.WatchListEntity;
import tv.anime.ftw.utils.DateHelper;

public class SeriesWatchListAdapter extends RecyclerView.Adapter<SeriesWatchListAdapter.WatchListViewHolder> {

    private ArrayList<WatchListEntity> seriesEntities;
    private boolean isNew = false;
    public SeriesWatchListAdapter(ArrayList<WatchListEntity> seriesEntities) {
        this.seriesEntities = seriesEntities;

    }


    @Override
    public WatchListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_detail_item, parent, false);
        return new WatchListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final WatchListViewHolder holder, final int position) {


        final WatchListEntity watchListEntity = seriesEntities.get(position);

        holder.tvTitle.setText(watchListEntity.getFullSeriesName());

        if(watchListEntity.getCreatedAt() == 0){
            isNew = true;
            holder.tvAdded.setVisibility(View.GONE);
            holder.tvLastUpdated.setVisibility(View.GONE);
            holder.btnUpdate.setText("add to My Watchlist");
        }else{
            isNew = false;
            holder.tvAdded.setVisibility(View.VISIBLE);
            holder.tvLastUpdated.setVisibility(View.VISIBLE);
            holder.tvAdded.setText("Added: " + DateHelper.getMDYFromat(watchListEntity.getCreatedAt() * 1000));
            holder.tvLastUpdated.setText("Last Updated: " + DateHelper.getMDYFromat(watchListEntity.getUpdated() * 1000));
            holder.btnUpdate.setText("Update");
        }

        holder.checkEmailUpdates.setChecked(watchListEntity.getEmail() == 0 ? false : true);
        holder.checkEmailUpdates.setTag(watchListEntity.getEmail());
        holder.checkEmailUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int emailUpdates = ((Integer)holder.checkEmailUpdates.getTag());
                emailUpdates = emailUpdates == 0 ? 1 : 0;
                holder.checkEmailUpdates.setTag(emailUpdates);
            }
        });


        ArrayAdapter<CharSequence> trackerOrManualAdapter = ArrayAdapter.createFromResource(holder.trackerOrManual.getContext(),
                R.array.tracker_or_manual_array, android.R.layout.simple_spinner_item);
        trackerOrManualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.trackerOrManual.setAdapter(trackerOrManualAdapter);


        ArrayAdapter<CharSequence> entryStatusAdapter = ArrayAdapter.createFromResource(holder.trackerOrManual.getContext(),
                R.array.entry_status, android.R.layout.simple_spinner_item);
        entryStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.entryStatus.setAdapter(entryStatusAdapter);
        holder.entryStatus.setSelection(watchListEntity.getStatus());

        holder.etCurrentEpisode.setText(watchListEntity.getCurrentep() + "");


        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = watchListEntity.getId();
                int status = holder.entryStatus.getSelectedItemPosition();
                int email = ((Integer) holder.checkEmailUpdates.getTag());
                int currentEpisode = 0;
                try {
                    currentEpisode = Integer.parseInt(holder.etCurrentEpisode.getText().toString().trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String comment = holder.etComment.getText().toString().trim();
                int trackerStatus = holder.trackerOrManual.getSelectedItemPosition();
                int trackerLatest = watchListEntity.getTrackerLatest();


                EventBus.getDefault().post(new WatchListUpdateEvent(id, status, email, currentEpisode, comment, trackerStatus, trackerLatest, isNew));
            }
        });




       holder.trackerOrManual.setSelection(watchListEntity.getTracker());

        holder.trackerOrManual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                holder.etCurrentEpisode.setClickable(position == 0);
                holder.etCurrentEpisode.setFocusableInTouchMode(position == 0);
                holder.etCurrentEpisode.setFocusable(position == 0);
                holder.etCurrentEpisode.setEnabled(position == 0);
                String currentEpisode = "";
                if(watchListEntity.getCurrentep()>0) {
                    currentEpisode = watchListEntity.getCurrentep() + "";
                }
                holder.etCurrentEpisode.setText(position == 0 ?currentEpisode :"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        @Bind(R.id.tvAdded)
        TextView tvAdded;

        @Bind(R.id.tvLastUpdated)
        TextView tvLastUpdated;

        @Bind(R.id.checkEmailUpdates)
        CheckBox checkEmailUpdates;

        @Bind(R.id.spTrackerOrManual)
        Spinner trackerOrManual;

        @Bind(R.id.spEntryStatus)
        Spinner entryStatus;


        @Bind(R.id.etCurrentEpisodeId)
        EditText etCurrentEpisode;

        @Bind(R.id.etComment)
        EditText etComment;

        @Bind(R.id.btnUpdate)
        Button btnUpdate;


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