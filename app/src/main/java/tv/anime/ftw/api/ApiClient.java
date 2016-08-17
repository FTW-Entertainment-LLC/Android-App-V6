package tv.anime.ftw.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by darshanz on 4/19/16.
 */
public class ApiClient {
    /**
     * Returns the Webservice instance for accessing the API
     *
     * @return
     */
    public static WebService getWebService(final String token) {

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addQueryParam(Config.DEV_KEY, "NBy8-N2NL-ryKB-a2UP");
                if(token!=null){
                    request.addQueryParam(Config.TOKEN, token);
                }
            }
        };

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        RestAdapter adapter = new RestAdapter.Builder().setClient(new OkClient(okHttpClient))
                .setEndpoint(Config.BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.NONE).build();


        return adapter.create(WebService.class);
    }



}