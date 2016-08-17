package tv.anime.ftw.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tv.anime.ftw.R;
import tv.anime.ftw.adapter.CountryListAdapter;
import tv.anime.ftw.events.SelectCountryEvent;

/**
 * Created by darshanz on 05/30/15.
 */
public class CountryListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    @Bind(R.id.countryList)
    RecyclerView mRecyclerView;

    @Bind(R.id.appbar)
    Toolbar appBar;

    private ArrayList<String> countryList = new ArrayList<>(); //Used for the adapter

    private ArrayList<String> allCountries = new ArrayList<String>(); //Used for search filtering

    private CountryListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        ButterKnife.bind(this);

        setSupportActionBar(appBar);
        getSupportActionBar().setTitle(getResources().getString(R.string.select_country));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadCountriesTask().execute();
    }

    class LoadCountriesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            String[] countries = getResources().getStringArray(R.array.countries_array);
            for (int i = 0; i < countries.length; i++) {
                countryList.add(countries[i]);
            }
            allCountries.addAll(countryList); //keep separate list for search
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter = new CountryListAdapter(countryList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(CountryListActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_country, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(CountryListActivity.this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<String> filteredModelList = filter(query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    /**
     * Filter the country list based on the search query
     *
     * @param query
     * @return
     */
    private List<String> filter(String query) {
        query = query.toLowerCase();
        final List<String> filteredModelList = new ArrayList<>();

        for (String country : allCountries) {
            final String text = country.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(country);
            }
        }
        return filteredModelList;
    }


    @Subscribe
    public void onCountrySelected(SelectCountryEvent selectCountryEvent) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("countryName", selectCountryEvent.getCountry());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

}
