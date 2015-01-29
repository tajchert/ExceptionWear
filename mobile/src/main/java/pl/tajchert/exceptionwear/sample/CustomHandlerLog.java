package pl.tajchert.exceptionwear.sample;

import android.util.Log;

import com.google.android.gms.wearable.DataMap;

import pl.tajchert.exceptionwear.ExceptionWearHandler;

/**
 * Sample Handler to show how to handle exception from Wear, it will only print an error.
 */
public class CustomHandlerLog implements ExceptionWearHandler{
    private static final String TAG = "ErrorHandlerLog";

    @Override
    /**
     * Exception handler with only showing in logcat.
     */
    public void handleException(Throwable throwable, DataMap map) {
        Log.e(TAG, "Received exception from Wear" + throwable.getMessage()
                + ", manufacturer: " + map.getString("manufacturer")
                + ", model: " + map.getString("model")
                + ", product: " + map.getString("product")
                + ", fingerprint: " + map.getString("fingerprint")
                + ", stack trace: " + Log.getStackTraceString(throwable));
    }
}
