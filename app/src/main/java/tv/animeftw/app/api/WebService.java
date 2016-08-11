package tv.animeftw.app.api;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import tv.animeftw.app.model.AddWatchlistResponse;
import tv.animeftw.app.model.AppSettingsResponse;
import tv.animeftw.app.model.BaseObjectResponse;
import tv.animeftw.app.model.BaseResponse;
import tv.animeftw.app.model.CategoryResponse;
import tv.animeftw.app.model.CommentResponse;
import tv.animeftw.app.model.DeviceKeyResponse;
import tv.animeftw.app.model.EpisodeEntity;
import tv.animeftw.app.model.EpisodeResponse;
import tv.animeftw.app.model.MovieResponse;
import tv.animeftw.app.model.NewsResponse;
import tv.animeftw.app.model.ReviewResponse;
import tv.animeftw.app.model.SearchResponse;
import tv.animeftw.app.model.SeriesResponse;
import tv.animeftw.app.model.UserProfileResponse;
import tv.animeftw.app.model.WatchListResponse;

/**
 * Api Service
 * Created by darshanz on 4/19/16.
 */
public interface WebService {

    /**
     * Used for register, login and logout
     * <p/>
     * for login send username and password with action=login
     * for register send all the fields.
     * for logout send action=logout only
     *
     * @param registerAction action for registration Config.ACTION_REGISTER or Config.ACTION_LOGIN
     * @param username       required for both login and register
     * @param password       required for both login and register
     * @param email          register only
     * @param birthday       register only date of birth in  'MMDDYYYY' format
     * @param remember       persist token for uto 4 weeks
     * @param restCallback   callback with BaseResponse (in case of login if 'status' is 200 check 'message' is a token)
     */
    @GET(Config.ENDPOINT)
    void user(@Query("action") String registerAction,
              @Query("username") String username,
              @Query("password") String password,
              @Query("email") String email,
              @Query("birthday") String birthday,
              @Query("remember") Boolean remember,
              RestCallback<BaseResponse> restCallback);

    /**
     * Used for register, login and logout
     * <p/>
     * for login send username and password with action=login
     * for register send all the fields.
     * for logout send action=logout only
     *
     * @param registerAction action for registration Config.ACTION_REGISTER or Config.ACTION_LOGIN
     * @param username       required for both login and register
     * @param password       required for both login and register
     */
    @GET(Config.ENDPOINT)
    Observable<BaseResponse> login(@Query("action") String registerAction,
                                   @Query("username") String username,
                                   @Query("password") String password);

    @GET(Config.ENDPOINT)
    Observable<DeviceKeyResponse> generateDeviceKey(@Query(Config.TOKEN) String token, @Query("action") String action);

    @GET(Config.ENDPOINT)
    Observable<BaseResponse> validateDevice(@Query("action") String action, @Query("key") String key, @Query("token") String token);


    /**
     * @param action
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void displayProfile(@Query("action") String action,
                        RestCallback<UserProfileResponse> restCallback);


    /**
     * @param action
     * @param count
     * @param start
     * @param latest
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getSeries(@Query("action") String action,
                   @Query("count") int count,
                   @Query("start") int start,
                   @Query("latest") boolean latest,
                   RestCallback<SeriesResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param id           REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getSingeSeries(@Query("action") String action,
                        @Query("id") int id,
                        RestCallback<BaseObjectResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getLatestNews(@Query("action") String action,
                       RestCallback<NewsResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param count
     * @param start
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getTopSeries(@Query("action") String action,
                      @Query("count") int count,
                      @Query("start") int start,
                      RestCallback<SeriesResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param count
     * @param start
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getRandomSeries(@Query("action") String action,
                         @Query("count") int count,
                         @Query("start") int start,
                         RestCallback<SeriesResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param count
     * @param start
     * @param seriesId     REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getReviewsForSeries(@Query("action") String action,
                             @Query("count") int count,
                             @Query("start") int start,
                             @Query("id") String seriesId,
                             RestCallback<ReviewResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param seriesId     REQUIRED
     * @param review       REQUIRED
     * @param stars        REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void addReview(@Query("action") String action,
                   @Query("id") int seriesId,
                   @Query("review") String review,
                   @Query("stars") int stars,
                   RestCallback<BaseResponse> restCallback);

    /**
     * @param action       REQUIRED
     * @param seriesId     REQUIRED
     * @param start
     * @param count
     * @param isLatest
     * @param timeframe    used with latest, supply minutes(m), hours(h) or seconds(s)
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getEpisodes(@Query("action") String action,
                     @Query("id") int seriesId,
                     @Query("start") int start,
                     @Query("count") int count,
                     @Query("latest") boolean isLatest,
                     @Query("timeframe") String timeframe,
                     RestCallback<EpisodeResponse> restCallback);


    /**
     * @param action       REQUIRED
     * @param start
     * @param count
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getMovies(@Query("action") String action,
                   @Query("start") int start,
                   @Query("count") int count,
                   RestCallback<MovieResponse> restCallback);


    /**
     * @param action       REQUIRED
     * @param episodeId    REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getSingleEpisode(
            @Query("action") String action,
            @Query("id") int episodeId,
            RestCallback<EpisodeEntity> restCallback);


    /**
     * @param action          REQUIRED
     * @param commentTargetId REQUIRED id of episode or user
     * @param commentTarget   REQUIRED CommentTarget
     * @param page
     * @param count
     * @param restCallback    REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getComments(
            @Query("action") String action,
            @Query("id") int commentTargetId,
            @Query("type") String commentTarget,
            @Query("page") int page,
            @Query("count") int count,
            RestCallback<CommentResponse> restCallback);


    /**
     * @param action       REQUIRED
     * @param targetId     REQUIRED
     * @param comment      REQUIRED
     * @param targetType   default is 0 for episode, 1 for user
     * @param spoiler      Spoiler.YES or Spoiler.NO default is NO
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void addComment(
            @Query("action") String action,
            @Query("id") int targetId,
            @Query("comment") String comment,
            @Query("type") int targetType,
            @Query("spoiler") int spoiler,
            RestCallback<BaseResponse> restCallback
    );

    /**
     * @param action          REQUIRED
     * @param timeInSeconds   REQUIRED
     * @param lengthInSeconds REQUIRED
     * @param restCallback    REQUIRED
     */
    @GET(Config.ENDPOINT)
    void recordPlaybackLocation(@Query("action") String action, @Query("time") int timeInSeconds,
                                @Query("length") int lengthInSeconds,
                                RestCallback<BaseResponse> restCallback);

    /**
     * @param action          REQUIRED
     * @param timeInSeconds   REQUIRED
     * @param lengthInSeconds REQUIRED
     */
    @GET(Config.ENDPOINT)
    Observable<BaseResponse> recordPlaybackLocation(@Query("action") String action,
                                                    @Query("id") int id,
                                                    @Query("time") int timeInSeconds,
                                                    @Query("length") int lengthInSeconds);

    /**
     * @param action       REQUIRED
     * @param episodeId    REQUIRED
     * @param stars        REQUIRED  1-5
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void rateEpisode(@Query("action") String action,
                     @Query("id") int episodeId,
                     @Query("star") int stars,
                     RestCallback<BaseResponse> restCallback);


    /**
     * @param action      REQUIRED
     * @param queryString REQUIRD
     * @param start
     * @param count
     */
    @GET(Config.ENDPOINT)
    Observable<SearchResponse> search(@Query("action") String action,
                                      @Query("for") String queryString,
                                      @Query("start") int start,
                                      @Query("count") int count);


    //TODO needs confirmation there is no information being sent
    @GET(Config.ENDPOINT)
    void editProfile(@Query("action") String action,
                     @Query("firstname") String firstName,
                     @Query("lastname") String lastName,
                     @Query("gender") String gender,
                     @Query("birthday") String birthday,
                     @Query("country") String country,
                     RestCallback<BaseResponse> restCallback);


    /**
     * @param action       REQUIRED
     * @param start
     * @param count
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getCategories(@Query("action") String action,
                       @Query("start") Integer start,
                       @Query("count") Integer count,
                       RestCallback<CategoryResponse> restCallback);

    /**
     * @param action
     * @param id
     * @param seriesId
     * @param sort         column0|asc, column1|desc
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void getWatchlist(
            @Query("action") String action,
            @Query("id") String id,
            @Query("sid") Integer seriesId,
            @Query("sort") Integer sort,
            RestCallback<WatchListResponse> restCallback
    );

    /**
     * @param action       REQUIRED
     * @param id           REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void addWatchList(
            @Query("action") String action,
            @Query("id") int id,
            RestCallback<AddWatchlistResponse> restCallback
    );


    /**
     * @param action           REQUIRED
     * @param id               REQUIRED
     * @param statusId
     * @param email
     * @param currentEpisodeId
     * @param trackerStatus    TrackerStats.ENABLED | .DISABLED
     * @param trackerLatest
     * @param comment
     * @param restCallback     REQUIRED
     */
    @GET(Config.ENDPOINT)
    void editWatchList(
            @Query("action") String action,
            @Query("id") int id,
            @Query("status") int statusId,
            @Query("email") int email,
            @Query("current_episode") int currentEpisodeId,
            @Query("tracker") int trackerStatus,
            @Query("tracker_latest") int trackerLatest,
            @Query("comment") String comment,
            RestCallback<BaseResponse> restCallback

    );

    /**
     * @param action       REQUIRED
     * @param seriesId     REQUIRED
     * @param restCallback REQUIRED
     */
    @GET(Config.ENDPOINT)
    void deleteWatchList(
            @Query("action") String action,
            @Query("id") String seriesId,
            RestCallback<BaseResponse> restCallback
    );




    @GET(Config.ENDPOINT)
    void getAppSettings(
            @Query("action") String action,
             RestCallback<AppSettingsResponse> restCallback
    );
}


