package tv.anime.ftw.model;

/**
 * Created by darshanz on 4/20/16.
 */
public class UserProfileResponse extends BaseResponse {
    private UserEntity results;

    public UserEntity getResults() {
        return results;
    }

    public void setResults(UserEntity results) {
        this.results = results;
    }
}
