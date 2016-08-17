package tv.anime.ftw.activities.accounts;

import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;
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

/**
 * Login Activity
 * <p/>
 * Created by darshanz on 4/19/16.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.editUserName)
    EditText mEtUserName;

    @Bind(R.id.editPassword)
    EditText mEtPassword;

    @Bind(R.id.btnLogin)
    Button mBtnLogin;

    @Bind(R.id.linkRegister)
    TextView mTvSignup;

    @Bind(R.id.spin_kit)
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        spinKitView.setVisibility(View.GONE);

        mTvSignup.setPaintFlags(mTvSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mEtPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    onLogin();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /**
     * Validate and process login
     */
    @OnClick(R.id.btnLogin)
    void onLogin() {

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        if (mEtUserName.getText().toString().length() == 0) {
            Toast.makeText(LoginActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
        } else if (mEtPassword.getText().toString().length() == 0) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            login(mEtUserName.getText().toString(), mEtPassword.getText().toString());
        }
    }


    @OnClick(R.id.linkRegister)
    void showRegister() {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }


    /**
     * Login after register
     */
    private void login(String username, String password) {
        spinKitView.setVisibility(View.VISIBLE);

        webService.user(Config.ACTION_LOGIN, username, password, null, null, true, new RestCallback<BaseResponse>() {
            @Override
            public void failure(RestError restError) {

                if (restError.getCode() == 0) {
                    Snackbar.make(mEtUserName, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(BaseResponse baseResponse, Response response) {
                spinKitView.setVisibility(View.GONE);
                if (baseResponse != null) {
                    if (baseResponse.getStatus() == 200) {
                        if (baseResponse.getMessage() != null) {
                            settings.saveToken(baseResponse.getMessage());
                            fetchProfile();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(baseResponse.getMessage()!=null) {
                            Toast.makeText(LoginActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed. Please check your password.", Toast.LENGTH_SHORT).show();
                        }
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
                spinKitView.setVisibility(View.GONE);
                if (restError.getCode() == 0) {
                    Snackbar.make(mEtUserName, getResources().getString(R.string.failed_to_connect_msg), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(UserProfileResponse userProfileResponse, Response response) {
                spinKitView.setVisibility(View.GONE);
                if (userProfileResponse != null) {
                    if (userProfileResponse.getStatus() == 200) {
                        if (userProfileResponse.getResults() != null) {
                            UserEntity userEntity = userProfileResponse.getResults();
                            settings.saveCurrentUser(userEntity);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

}
