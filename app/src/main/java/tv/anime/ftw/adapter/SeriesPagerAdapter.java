package tv.anime.ftw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tv.anime.ftw.fragments.EpisodeFragment;
import tv.anime.ftw.fragments.SeriesWatchListFragment;
import tv.anime.ftw.fragments.SummaryFragment;

/**
 * Created by darshanz on 4/21/16.
 */
public class SeriesPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = {"Summary", "Episodes", "My WatchList"};
    private String summary = "";
    private int seriesId;
    private String seriesName;

    public SeriesPagerAdapter(FragmentManager fm, String summary, int seriesId, String seriesName) {
        super(fm);
        this.summary = summary;
        this.seriesId = seriesId;
        this.seriesName = seriesName;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SummaryFragment.newInstance(summary, 0, 0);// send 0 intially will be refreshed when event detail is loaded
            case 1:
                return EpisodeFragment.newInstance(seriesId);
            case 2:
                return SeriesWatchListFragment.newInstance(seriesId, seriesName);
            default:
                return SummaryFragment.newInstance(summary, 0, 0);
        }
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
