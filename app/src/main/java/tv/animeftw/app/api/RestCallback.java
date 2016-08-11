package tv.animeftw.app.api;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Custom Callback class
 * Use this instead of the retrofit Calback<T> as this class is wrapper around Callback class for handling error
 *
 * @param <T>
 */
public abstract class RestCallback<T> implements Callback<T> {
    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error) {
        RestError restError = null;
        try {
            restError = (RestError) error.getBodyAs(RestError.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (restError != null) {
            failure(restError);
        } else {
            failure(new RestError(error.getMessage()));
        }

    }
}
