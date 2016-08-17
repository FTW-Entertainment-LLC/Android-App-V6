package tv.anime.ftw.activities.accounts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Calendar;
import java.util.regex.Matcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import tv.anime.ftw.R;
import tv.anime.ftw.activities.BaseActivity;
import tv.anime.ftw.activities.CountryListActivity;
import tv.anime.ftw.api.ApiClient;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.model.BaseResponse;
import tv.anime.ftw.model.UserEntity;
import tv.anime.ftw.model.UserProfileResponse;
import tv.anime.ftw.utils.DataBindingUtils;
import tv.anime.ftw.utils.DateHelper;

/**
 * Login Activity
 * <p/>
 * Created by darshanz on 4/19/16.
 */
public class ProfileActivity extends BaseActivity {

    public static final int COUNTRY_REQUEST = 300; //Sparta

    @Bind(R.id.btnSave)
    Button btnEdit;

    @Bind(R.id.firstName)
    EditText firstName;

    @Bind(R.id.lastName)
    EditText lastName;

    @Bind(R.id.email)
    EditText email;

    @Bind(R.id.appbar)
    Toolbar toolbar;

    @Bind(R.id.country)
    EditText country;

    @Bind(R.id.gender)
    Spinner gender;

    @Bind(R.id.birthday)
    EditText birthDay;

    @Bind(R.id.userFoto)
    ImageView avatarImage;

    @Bind(R.id.spin_kit)
    SpinKitView spin_kit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchProfile();

        setUpFields();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btnSave)
    void onSaveProfile() {
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String emailText = email.getText().toString();
        String genderText = getResources().getStringArray(R.array.genders)[gender.getSelectedItemPosition()];
        String birthdayText = birthDay.getText().toString();
        String countryText = country.getText().toString();

        if (firstNameText.length() == 0) {
            Snackbar.make(firstName, "Please enter first name", Snackbar.LENGTH_SHORT).show();
        } else if (lastNameText.length() == 0) {
            Snackbar.make(firstName, "Please enter last name", Snackbar.LENGTH_SHORT).show();
        } else if (emailText.length() == 0) {
            Snackbar.make(firstName, "Please enter first name", Snackbar.LENGTH_SHORT).show();
        } else if (!isValidEmail(emailText)) {
            Snackbar.make(firstName, "Please enter email", Snackbar.LENGTH_SHORT).show();
        } else if (gender.getSelectedItemPosition() == 0) {
            Snackbar.make(firstName, "Please select gender", Snackbar.LENGTH_SHORT).show();
        } else if (countryText.length() == 0) {
            Snackbar.make(firstName, "Please select country", Snackbar.LENGTH_SHORT).show();
        } else {
            spin_kit.setVisibility(View.VISIBLE);
            webService.editProfile(Config.ACTION_EDIT_PROFILE, firstNameText, lastNameText, genderText, birthdayText, countryText, new RestCallback<BaseResponse>() {
                @Override
                public void failure(RestError restError) {
                    spin_kit.setVisibility(View.GONE);
                    if (restError.getCode() == 0) {
                        Snackbar.make(btnEdit, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void success(BaseResponse baseResponse, Response response) {
                    fetchProfile();
                }
            });
        }

    }


    /**
     * Fetch Profile
     */
    private void fetchProfile() {
        spin_kit.setVisibility(View.VISIBLE);
        webService = ApiClient.getWebService(settings.getUserToken());
        webService.displayProfile(Config.ACTION_DISPLAY_PROFILE, new RestCallback<UserProfileResponse>() {
            @Override
            public void failure(RestError restError) {
                spin_kit.setVisibility(View.GONE);
                if (restError.getCode() == 0) {
                    Snackbar.make(btnEdit, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(UserProfileResponse userProfileResponse, Response response) {
                spin_kit.setVisibility(View.GONE);
                if (userProfileResponse != null) {
                    if (userProfileResponse.getStatus() == 200) {
                        if (userProfileResponse.getResults() != null) {
                            UserEntity userEntity = userProfileResponse.getResults();
                            settings.saveCurrentUser(userEntity);
                            setUpFields();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();

                        }
                    } else if (userProfileResponse.getStatus() == 405) {
                        logout();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }


    @OnClick(R.id.country)
    void onCountryClicked() {
        startActivityForResult(new Intent(ProfileActivity.this, CountryListActivity.class), COUNTRY_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COUNTRY_REQUEST) {
            if (resultCode == RESULT_OK) {
                country.setText(data.getStringExtra("countryName"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Fill from the settings
     */
    private void setUpFields() {
        UserEntity currentUser = settings.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getFirstname() != null)
                firstName.setText(currentUser.getFirstname());

            if (currentUser.getLastname() != null)
                lastName.setText(currentUser.getLastname());

            if (currentUser.getEmail() != null)
                email.setText(currentUser.getEmail());

            if (currentUser.getGender() != null) {
                if (currentUser.getGender().equalsIgnoreCase("Male")) {
                    gender.setSelection(0);
                } else {
                    gender.setSelection(1);
                }
            }

            if (currentUser.getCountry() != null)
                country.setText(currentUser.getCountry());

            if (currentUser.getBirthday() != null)
                birthDay.setText(currentUser.getBirthday());

            DataBindingUtils.setImageUrl(avatarImage, currentUser.getAvatar());
        }
    }


    /**
     * is Valid Email.
     *
     * @return
     */
    private boolean isValidEmail(String email) {
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }


    @OnClick(R.id.birthday)
    void onClickDob() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int startYear = (year - 13) - 100;
        int toYear = year - 13;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(ProfileActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                birthDay.setText(DateHelper.pickerDateToApiDate(dateDesc));
            }
        }).textConfirm("CONFIRM") //text of confirm button
                .textCancel("CANCEL") //text of cancel button
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(getResources().getColor(R.color.colorBlue))//color of confirm button
                .minYear(startYear) //min year in loop
                .maxYear(toYear) // max year in loop
                .dateChose((toYear - 10) + "-" + (month + 1) + "-" + day) // date chose when init popwindow
                .build();

        pickerPopWin.showPopWin(ProfileActivity.this);

    }

}
