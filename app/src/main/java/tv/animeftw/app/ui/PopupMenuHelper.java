package tv.animeftw.app.ui;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import tv.animeftw.app.R;
import tv.animeftw.app.events.PopupMenuItemClickedEvent;
import tv.animeftw.app.utils.AppSettings;

public class PopupMenuHelper implements PopupMenu.OnMenuItemClickListener {
    public static final int SORT_TYPE_NAME = 0;
    public static final int SORT_TYPE_YEAR = 1;
    public static final int SORT_TYPE_DATE_ADDED = 2;
    public static final int SORT_TYPE_RATEING = 3;

    public static final int VIEW_TYPE_FAN_ART = 0;
    public static final int VIEW_TYPE_WALL = 1;
    public static final int VIEW_TYPE_DETAILS = 2;

    private final PopupMenu popupMenu;
    private final AppSettings appSettings;
    private final int menu;

    public PopupMenuHelper(@MenuRes int menu, View anchorView) {
        this.menu = menu;
        Context context = anchorView.getContext();
        popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());
        appSettings = new AppSettings(context);
        if (menu == R.menu.menu_movies_sort_type) {
            popupMenu.getMenu().findItem(R.id.withResumePoint).setChecked(appSettings.withResumePoint());
            popupMenu.getMenu().findItem(R.id.hideWatched).setChecked(appSettings.hideWatched());
            popupMenu.getMenu().findItem(menuItemForSortType(appSettings.getMoviesSortType())).setChecked(true);
        } else if (menu == R.menu.menu_movies_view_type) {
            popupMenu.getMenu().findItem(menuItemForViewType(appSettings.getMoviesViewType())).setChecked(true);
        }
    }

    public void show() {
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (menu == R.menu.menu_movies_sort_type) {
            switch (item.getItemId()) {
                case R.id.withResumePoint:
                    appSettings.toggleWithResumePoint();
                    break;
                case R.id.hideWatched:
                    appSettings.toggleHideWatched();
                    break;
                default:
                    appSettings.setMoviesSortType(sortTypeForMenuItem(item.getItemId()));
                    break;
            }
        } else if (menu == R.menu.menu_movies_view_type) {
            appSettings.setMoviesViewType(viewTypeForMenuItem(item.getItemId()));
        }
        EventBus.getDefault().post(new PopupMenuItemClickedEvent(item.getItemId(), menu));
        return true;
    }

    public static int viewTypeForMenuItem(int itemId) {
        switch (itemId) {
            case R.id.detail:
                return VIEW_TYPE_DETAILS;
            case R.id.fanArt:
                return VIEW_TYPE_FAN_ART;
            default:
                return VIEW_TYPE_WALL;
        }
    }

    public static int sortTypeForMenuItem(int itemId) {
        switch (itemId) {
            case R.id.year:
                return SORT_TYPE_YEAR;
            case R.id.rating:
                return SORT_TYPE_RATEING;
            case R.id.date_added:
                return SORT_TYPE_DATE_ADDED;
            default:
                return SORT_TYPE_NAME;
        }
    }

    public static int menuItemForViewType(int viewType) {
        switch (viewType) {
            case VIEW_TYPE_DETAILS:
                return R.id.detail;
            case VIEW_TYPE_FAN_ART:
                return R.id.fanArt;
            default:
                return R.id.wall;
        }
    }

    public static int menuItemForSortType(int sortType) {
        switch (sortType) {
            case SORT_TYPE_YEAR:
                return R.id.year;
            case SORT_TYPE_RATEING:
                return R.id.rating;
            case SORT_TYPE_DATE_ADDED:
                return R.id.date_added;
            default:
                return R.id.name;
        }
    }


}
