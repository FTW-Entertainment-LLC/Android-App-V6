package tv.animeftw.app.events;

/**
 * Created by darshanz on 4/22/16.
 */
public class SelectCountryEvent {
    private String country;

    public SelectCountryEvent(String country) {
        this.country = country;
    }


    public String getCountry() {
        return country;
    }
}
