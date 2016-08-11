package tv.animeftw.app.api;

import com.google.gson.annotations.SerializedName;

/**
 * Custom Error for handling Connection problem
 */
public class RestError
{
  @SerializedName("code")
  private int code;

  @SerializedName("error_message")
  private String strMessage;

  public RestError(String strMessage)
  {
      this.strMessage = strMessage;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getStrMessage() {
    return strMessage;
  }

  public void setStrMessage(String strMessage) {
    this.strMessage = strMessage;
  }
}