package tv.anime.ftw.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tv.anime.ftw.R;
import tv.anime.ftw.events.DrawerMenuEvent;
import tv.anime.ftw.events.ProfileOpenEvent;
import tv.anime.ftw.utils.AppSettings;
import tv.anime.ftw.utils.DataBindingUtils;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    public final static int TYPE_HEADER = 0;
    public final static int TYPE_MENU = 1;


    private ArrayList<DrawerItem> drawerMenuList;
    private AppSettings settings;


    public DrawerAdapter(ArrayList<DrawerItem> drawerMenuList, AppSettings settings) {
        this.drawerMenuList = drawerMenuList;
        this.settings = settings;
    }


    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_menu, parent, false);
        }
        return new DrawerViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(DrawerViewHolder holder, final int position) {

        if (position == 0) {
            if (settings.getCurrentUser() != null) {
                holder.userFullName.setText(settings.getCurrentUser().getUsername());
            } else {
                holder.userFullName.setText("");
            }
            holder.userFullName.setTextColor(Color.WHITE);
            DataBindingUtils.setImageUrl(holder.userFoto, settings.getCurrentUser().getAvatar());
            holder.userFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new ProfileOpenEvent(Integer.parseInt(settings.getCurrentUser().getId())));
                }
            });

        } else {
            final DrawerItem item = drawerMenuList.get(position - 1);

            if (!item.isMenu()) {
                holder.icon.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                holder.menuTitleLabel.setVisibility(View.VISIBLE);
                holder.menuTitleLabel.setText(item.getTitle());

            } else {
                holder.icon.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setText(item.getTitle());
                holder.menuTitleLabel.setVisibility(View.GONE);
                holder.subTitle.setText(item.getSubTitle());
                holder.icon.setImageResource(item.getIcon());
                holder.itemRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new DrawerMenuEvent(item.getScreen()));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return drawerMenuList.size() + 1;
    }

    /**
     * View holder for drawer menuitems
     */
    class DrawerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView menuTitleLabel;
        TextView subTitle;
        ImageView icon;
        CircleImageView userFoto;
        TextView userFullName;
        RelativeLayout itemRow;

        public DrawerViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == 0) {
                userFullName = (TextView) itemView.findViewById(R.id.userFullName);
                userFoto = (CircleImageView) itemView.findViewById(R.id.userFoto);
            } else {
                title = (TextView) itemView.findViewById(R.id.menuTitle);
                menuTitleLabel = (TextView) itemView.findViewById(R.id.menuTitleLabel);
                subTitle = (TextView) itemView.findViewById(R.id.subtitle);
                icon = (ImageView) itemView.findViewById(R.id.icon);
                itemRow = (RelativeLayout) itemView.findViewById(R.id.listItem);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_MENU;
    }
}