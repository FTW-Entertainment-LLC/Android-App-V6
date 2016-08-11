package tv.animeftw.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by darshanz on 8/9/16.
 */
public class AdSetting {

    private String id;
    @SerializedName("unit-name")
    private String unitName;
    @SerializedName("unit-id")
    private String unitId;
    private String format;
    private String enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
