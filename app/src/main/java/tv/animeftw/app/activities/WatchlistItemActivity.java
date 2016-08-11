package tv.animeftw.app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit.client.Response;
import tv.animeftw.app.R;
import tv.animeftw.app.api.Config;
import tv.animeftw.app.api.RestCallback;
import tv.animeftw.app.api.RestError;
import tv.animeftw.app.model.BaseResponse;
import tv.animeftw.app.model.WatchListEntity;
import tv.animeftw.app.utils.DataBindingUtils;
import tv.animeftw.app.utils.DateHelper;

public class WatchlistItemActivity extends BaseActivity {

    private WatchListEntity watchListEntity;

    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Bind(R.id.tvAdded)
    TextView tvAdded;

    @Bind(R.id.tvLastUpdated)
    TextView tvLastUpdated;

    @Bind(R.id.checkEmailUpdates)
    CheckBox checkEmailUpdates;

    @Bind(R.id.ivImage)
    ImageView imageView;

    @Bind(R.id.spTrackerOrManual)
    Spinner trackerOrManual;

    @Bind(R.id.spEntryStatus)
    Spinner entryStatus;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.etCurrentEpisodeId)
    EditText etCurrentEpisode;

    @Bind(R.id.etComment)
    EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist_item);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        watchListEntity = new Gson().fromJson(getIntent().getStringExtra("WATCH_LIST_ENTITY"), WatchListEntity.class);

        tvTitle.setText(watchListEntity.getFullSeriesName());
        tvAdded.setText("Added: " + DateHelper.getMDYFromat(watchListEntity.getCreatedAt() * 1000));
        tvLastUpdated.setText("Last Updated: " + DateHelper.getMDYFromat(watchListEntity.getUpdated() * 1000));
        checkEmailUpdates.setChecked(watchListEntity.getEmail() == 0 ? false : true);
        checkEmailUpdates.setTag(watchListEntity.getEmail());

        checkEmailUpdates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int emailUpdates = ((Integer)checkEmailUpdates.getTag());
                emailUpdates = emailUpdates == 0 ? 1 : 0;
                checkEmailUpdates.setTag(emailUpdates);
            }
        });


        DataBindingUtils.setImageUrl(imageView, watchListEntity.getImage());

        ArrayAdapter<CharSequence> trackerOrManualAdapter = ArrayAdapter.createFromResource(this,
                R.array.tracker_or_manual_array, android.R.layout.simple_spinner_item);
        trackerOrManualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackerOrManual.setAdapter(trackerOrManualAdapter);

        trackerOrManual.setSelection(watchListEntity.getTracker());
        ArrayAdapter<CharSequence> entryStatusAdapter = ArrayAdapter.createFromResource(this,
                R.array.entry_status, android.R.layout.simple_spinner_item);
        entryStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        entryStatus.setAdapter(entryStatusAdapter);
        entryStatus.setSelection(watchListEntity.getStatus());

        etCurrentEpisode.setText(watchListEntity.getCurrentep() + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackerOrManual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enableExtra(position == 0);
                String currentEpisode = "";
                if(watchListEntity.getCurrentep()>0) {
                    currentEpisode = watchListEntity.getCurrentep() + "";
                }
                etCurrentEpisode.setText(position == 0 ? currentEpisode :"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void enableExtra(boolean enable) {
        etCurrentEpisode.setClickable(enable);
        etCurrentEpisode.setFocusableInTouchMode(enable);
        etCurrentEpisode.setFocusable(enable);
        etCurrentEpisode.setEnabled(enable);
    }


    @OnClick({R.id.ivImage, R.id.tvAdded, R.id.tvTitle, R.id.tvLastUpdated})
    void onTitleClicked() {
        Intent intent = new Intent(this, SingleSeriesActivity.class);
        intent.putExtra("SERIES_ID_EXTRA", watchListEntity.getSeriesId());
        intent.putExtra("SERIES_NAME_EXTRA", watchListEntity.getFullSeriesName());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private int getSelectionPositionOf(String item) {
        String[] statusArray = getResources().getStringArray(R.array.entry_status);
        for (int i = 0; i < statusArray.length; i++) {
            if (statusArray[i].equals(item)) {
                return i;
            }
        }
        return 0;
    }


    @OnClick(R.id.btnUpdate)
    void update() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Updating", "Please wait while update completes");

        int id = watchListEntity.getId();
        int status = entryStatus.getSelectedItemPosition();
        int email = ((Integer) checkEmailUpdates.getTag());
        int currentEpisode = 0;
        try {
            currentEpisode = Integer.parseInt(etCurrentEpisode.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String comment = etComment.getText().toString().trim();
        int trackerStatus = trackerOrManual.getSelectedItemPosition();
        int trackerLatest = watchListEntity.getTrackerLatest();


        webService.editWatchList(Config.ACTION_EDIT_WATCHLIST, id, status, email, currentEpisode, trackerStatus, trackerLatest, comment, new RestCallback<BaseResponse>() {
            @Override
            public void failure(RestError restError) {
                progressDialog.dismiss();
                Toast.makeText(WatchlistItemActivity.this, "Error updating item.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(BaseResponse baseResponse, Response response) {
                progressDialog.dismiss();
                if (baseResponse != null && baseResponse.getStatus() == 200) {
                    Toast.makeText(WatchlistItemActivity.this, "Item update successful.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WatchlistItemActivity.this, "Error updating item.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
