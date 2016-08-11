package tv.animeftw.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tv.animeftw.app.R;
import tv.animeftw.app.events.DrawerMenuEvent;
import tv.animeftw.app.events.SettingsEvent;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {

    private ArrayList<SettingsItem> settingsItemList;

    public SettingsAdapter(ArrayList<SettingsItem> settingsItemList) {
        this.settingsItemList = settingsItemList;
      }


    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_settings, parent, false);
        return new SettingsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SettingsViewHolder holder, final int position) {

            final SettingsItem item = settingsItemList.get(position);

            if(!item.isMenu()){

                holder.icon.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                holder.menuTitleLabel.setVisibility(View.VISIBLE);
                holder.menuTitleLabel.setText(item.getTitle());

            }else {

                holder.icon.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(item.getTitle());
                holder.menuTitleLabel.setVisibility(View.GONE);
                holder.subTitle.setText(item.getSubTitle());
                holder.icon.setImageResource(item.getIcon());
                holder.itemRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new SettingsEvent(item.getAction()));
                    }
                });
                holder.itemRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(position == 0)
                        EventBus.getDefault().post(new SettingsEvent(8412));
                        return true;
                    }
                });

            }

    }

    @Override
    public int getItemCount() {
        return settingsItemList.size();
    }

    /**
     * View holder for drawer menuitems
     */
    class SettingsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView menuTitleLabel;
        TextView subTitle;
        ImageView icon;
        RelativeLayout itemRow;

        public SettingsViewHolder(View itemView) {
            super(itemView);

                title = (TextView) itemView.findViewById(R.id.menuTitle);
                menuTitleLabel = (TextView) itemView.findViewById(R.id.menuTitleLabel);
                subTitle = (TextView) itemView.findViewById(R.id.subtitle);
                icon = (ImageView) itemView.findViewById(R.id.icon);
                itemRow = (RelativeLayout) itemView.findViewById(R.id.listItem);

        }
    }

}