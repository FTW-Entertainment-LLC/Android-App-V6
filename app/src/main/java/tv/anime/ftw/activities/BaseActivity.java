package tv.anime.ftw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.realm.Realm;
import retrofit.client.Response;
import tv.anime.ftw.AnimeFtwTvApp;
import tv.anime.ftw.activities.accounts.LoginActivity;
import tv.anime.ftw.api.ApiClient;
import tv.anime.ftw.api.Config;
import tv.anime.ftw.api.RestCallback;
import tv.anime.ftw.api.RestError;
import tv.anime.ftw.api.WebService;
import tv.anime.ftw.model.BaseResponse;
import tv.anime.ftw.model.UserEntity;
import tv.anime.ftw.utils.AppSettings;

/**
 * Created by darshanz on 4/20/16.
 */
public class BaseActivity extends AppCompatActivity {

    public WebService webService;
    public UserEntity currentUser;
    public AppSettings settings;
    public Realm realm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //settings instance for all the activities extending Base Activity, for accessing prefs
        settings = new AppSettings(this);

        //realm instance to be used by all the activities
        if (realm == null)
            realm = ((AnimeFtwTvApp) getApplication()).getRealmInstance();


        //Current user and current session
        if (currentUser == null) {
            if (settings != null) {
                currentUser = settings.getCurrentUser();
            }
        }

        //Web service instance for accessing the api
        if (webService == null) {
            webService = ApiClient.getWebService(settings.getUserToken());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    /**
     * logout
     */
    public void logout(){
        webService.user(Config.ACTION_LOGOUT, null, null, null, null, null, new RestCallback<BaseResponse>() {
            @Override
            public void failure(RestError restError) {
                if (restError.getCode() == 0) {
                    Toast.makeText(BaseActivity.this , "Failed to logout", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(BaseResponse baseResponse, Response response) {

                if(baseResponse!=null){
                    Toast.makeText(BaseActivity.this, ""+baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    ((AnimeFtwTvApp)getApplication()).logout();
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });


    }
}
