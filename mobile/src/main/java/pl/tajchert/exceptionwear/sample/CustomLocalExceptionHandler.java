package pl.tajchert.exceptionwear.sample;

import android.util.Log;

import com.google.android.gms.wearable.DataMap;

import pl.tajchert.exceptionwear.ExceptionWearHandler;


public class CustomLocalExceptionHandler implements ExceptionWearHandler{
    private static final String TAG = "CustomLocalException";

    @Override
    public void handleException(Throwable throwable, DataMap map) {
        Log.e(TAG, "Error from Wear" + throwable.getMessage()
                + ", manufacturer: " + map.getString("manufacturer")
                + ", model: " + map.getString("model")
                + ", product: " + map.getString("product")
                + ", fingerprint: " + map.getString("fingerprint")
                + ", stack trace: " + Log.getStackTraceString(throwable));
    }
}
