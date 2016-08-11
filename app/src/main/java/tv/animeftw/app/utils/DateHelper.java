package tv.animeftw.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by darshanz on 5/2/16.
 */
public class DateHelper {
    public final static String toDisplayDate(Date date) {
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a");
        return displayDateFormat.format(date);
    }


    public static String apiDateToPickerDate(String apiDate) {
        Date date = new Date();
        SimpleDateFormat datformat = new SimpleDateFormat("mm/dd/yyyy");
        try {
            date = datformat.parse(apiDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat widgetDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return widgetDateFormat.format(date);
    }


    public static String pickerDateToApiDate(String pickerDate) {
        Date date = new Date();
        SimpleDateFormat widgetDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = widgetDateFormat.parse(pickerDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat datformat = new SimpleDateFormat("MM/dd/yyyy");
        return datformat.format(date);
    }


    //Apparently Signup accepts differnet format
    public static String apiDateToSignupDate(String apiDate) {
        Date date = new Date();
        SimpleDateFormat apiDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = apiDateFormat.parse(apiDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat signUpDateFormat = new SimpleDateFormat("MMddyyyy");
        return signUpDateFormat.format(date);
    }


    public static String getMDYFromat(long longDate) {
        Date date = new Date(longDate);
        SimpleDateFormat datformat = new SimpleDateFormat("MM/dd/yyyy");
        return datformat.format(date);
    }
}
