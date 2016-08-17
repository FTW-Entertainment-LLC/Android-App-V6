package tv.anime.ftw.activities.accounts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Calendar;
import java.util.regex.Matcher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
import rx.Subscription;
import tv.anime.ftw.R;
import tv.anime.ftw.activities.BaseActivity;
import tv.anime.ftw.activities.MainActivity;
import tv.anime.ftw.api.ApiClient;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.model.BaseResponse;
import tv.anime.ftw.model.UserEntity;
import tv.anime.ftw.model.UserProfileResponse;
import tv.anime.ftw.utils.DateHelper;

/**
 * Singup Activity
 * <p/>
 * Created by darshanz on 4/19/16.
 */
public class SignupActivity extends BaseActivity {


    @Bind(R.id.editUserName)
    EditText mEtUserName;

    @Bind(R.id.editPassword)
    EditText mEtPassword;

    @Bind(R.id.editPasswordConfirm)
    EditText mEditPasswordConfirm;

    @Bind(R.id.editEmail)
    EditText mEtEmail;

    @Bind(R.id.editDateOfBirth)
    TextView mTextDOB;

    @Bind(R.id.btnRegister)
    Button mBtnRegister;

    @Bind(R.id.linkLogin)
    TextView mTvLogin;
    private Subscription subscription;

    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        spinKitView.setVisibility(View.GONE);

        mTvLogin.setPaintFlags(mTvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }


    @OnClick(R.id.editDateOfBirth)
    void onClickDob() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int startYear = (year - 13) - 100;
        int toYear = year - 13;
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(SignupActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                mTextDOB.setText(DateHelper.pickerDateToApiDate(dateDesc));
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

        pickerPopWin.showPopWin(SignupActivity.this);

    }


    @OnClick(R.id.btnRegister)
    void register() {
        if (mEtUserName.getText().toString().length() == 0) {
            Toast.makeText(SignupActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (mEtPassword.getText().toString().length() == 0) {
            Toast.makeText(SignupActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else if (mEtPassword.getText().toString().length() < 6) {
            Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        } else if (!mEtPassword.getText().toString().equals(mEditPasswordConfirm.getText().toString())) {
            Toast.makeText(SignupActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(mEtEmail.getText().toString())) {
            Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (mTextDOB.getText().length() == 0) {
            Toast.makeText(SignupActivity.this, "Please enter date of birth", Toast.LENGTH_SHORT).show();
        } else {
            webService.user(Config.ACTION_REGISTER, mEtUserName.getText().toString(),
                    mEtPassword.getText().toString(),
                    mEtEmail.getText().toString(), DateHelper.apiDateToSignupDate(mTextDOB.getText().toString()), null, new RestCallback<BaseResponse>() {
                        @Override
                        public void failure(RestError restError) {
                            spinKitView.setVisibility(View.GONE);
                            if (restError.getCode() == 0) {
                                Snackbar.make(mEtUserName, "Failed to connect, please check internet connection", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void success(BaseResponse baseResponse, Response response) {
                            spinKitView.setVisibility(View.GONE);
                            if (baseResponse.getStatus() == 200) {
                                Toast.makeText(SignupActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                login(mEtUserName.getText().toString(), mEtPassword.getText().toString());
                            } else {
                                Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    @OnClick(R.id.linkLogin)
    void showLogin() {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
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


    /**
     * Login after register
     */
    private void login(String username, String password) {
        webService.user(Config.ACTION_LOGIN, username, password, null, null, true, new RestCallback<BaseResponse>() {
            @Override
            public void failure(RestError restError) {
                if (restError.getCode() == 0) {
                    Snackbar.make(mEtUserName, "Failed to connect, please check internet connection", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(BaseResponse baseResponse, Response response) {
                if (baseResponse != null) {
                    if (baseResponse.getStatus() == 200) {
                        if (baseResponse.getMessage() != null) {
                            settings.saveToken(baseResponse.getMessage());
                            fetchProfile();
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }
                    }else if(baseResponse.getStatus() == 403){
                        Toast.makeText(SignupActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                    }
                }
            }
        });
    }


    /**
     * Fetch Profile
     */
    private void fetchProfile() {
        webService = ApiClient.getWebService(settings.getUserToken());
        webService.displayProfile(Config.ACTION_DISPLAY_PROFILE, new RestCallback<UserProfileResponse>() {
            @Override
            public void failure(RestError restError) {
                if (restError.getCode() == 0) {
                    Snackbar.make(mEtUserName, "Failed to connect, please check internet connection", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(UserProfileResponse userProfileResponse, Response response) {
                if (userProfileResponse != null) {
                    if (userProfileResponse.getStatus() == 200) {
                        if (userProfileResponse.getResults() != null) {
                            UserEntity userEntity = userProfileResponse.getResults();
                            settings.saveCurrentUser(userEntity);
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
