package pl.tajchert.exceptionwear.sample;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.wearable.DataMap;

import pl.tajchert.exceptionwear.ExceptionWearHandler;

/**
 * Sample Handler to show how to handle exception from Wear with use of Crashlytics.
 */
public class CustomHandlerCrashlytics implements ExceptionWearHandler{
    private static final String TAG = "ErrorHandlerCrashlytics";

    @Override
    /**
     * Exception handler with Crashlytics support, also it will be shown in logcat.
     */
    public void handleException(Throwable throwable, DataMap map) {
        //Remember to init Crashlytics in your Application class.
        Crashlytics.setBool("wear_exception", true);
        Crashlytics.setString("board", map.getString("board"));
        Crashlytics.setString("fingerprint", map.getString("fingerprint"));
        Crashlytics.setString("model", map.getString("model"));
        Crashlytics.setString("manufacturer", map.getString("manufacturer"));
        Crashlytics.setString("product", map.getString("product"));
        Crashlytics.setString("api_level", map.getString("api_level"));

        Crashlytics.logException(throwable);

        Log.e(TAG, "Received exception from Wear" + throwable.getMessage()
                + ", manufacturer: " + map.getString("manufacturer")
                + ", model: " + map.getString("model")
                + ", product: " + map.getString("product")
                + ", fingerprint: " + map.getString("fingerprint")
                + ", api_level: " + map.getString("api_level")
                + ", stack trace: " + Log.getStackTraceString(throwable));
    }
}
