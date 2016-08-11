package tv.animeftw.app.model;

import java.util.ArrayList;

/**
 * Created by darshanz on 8/9/16.
 */
public class AppSettingsResponse  {

    private int status;
    private String message;
    private String enabled;
    private String total;
    private ArrayList<AdSetting> ads;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<AdSetting> getAds() {
        return ads;
    }

    public void setAds(ArrayList<AdSetting> ads) {
        this.ads = ads;
    }
}
