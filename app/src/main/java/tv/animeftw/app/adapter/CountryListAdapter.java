package tv.animeftw.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.animeftw.app.R;
import tv.animeftw.app.events.SelectCountryEvent;

/**
 * Adapter for recyclerview for showing the list of countries.
 */
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.MyViewHolder> {

    ArrayList<String> mCountryList;

    public CountryListAdapter(ArrayList<String> countryList) {
        mCountryList = countryList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_country_item, viewGroup, false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.countryName.setText(mCountryList.get(i));
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    //ViewHolder
    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.countryName)
        TextView countryName;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new SelectCountryEvent(mCountryList.get(getAdapterPosition())));
                }
            });
        }
    }


    public String removeItem(int position) {
        final String country = mCountryList.remove(position);
        notifyItemRemoved(position);
        return country;
    }

    public void addItem(int position, String country) {
        mCountryList.add(position, country);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final String country = mCountryList.remove(fromPosition);
        mCountryList.add(toPosition, country);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<String> countries) {
        applyAndAnimateRemovals(countries);
        applyAndAnimateAdditions(countries);
        applyAndAnimateMovedItems(countries);
    }


    private void applyAndAnimateRemovals(List<String> newCountries) {
        for (int i = mCountryList.size() - 1; i >= 0; i--) {
            final String country = mCountryList.get(i);
            if (!newCountries.contains(country)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<String> newCountries) {
        for (int i = 0, count = newCountries.size(); i < count; i++) {
            final String country = newCountries.get(i);
            if (!mCountryList.contains(country)) {
                addItem(i, country);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<String> newCountries) {
        for (int toPosition = newCountries.size() - 1; toPosition >= 0; toPosition--) {
            final String country = newCountries.get(toPosition);
            final int fromPosition = mCountryList.indexOf(country);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

}