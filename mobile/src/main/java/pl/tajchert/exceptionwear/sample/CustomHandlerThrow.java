package pl.tajchert.exceptionwear.sample;

import com.google.android.gms.wearable.DataMap;

import pl.tajchert.exceptionwear.ExceptionWearHandler;

/**
 * Sample Handler to show how to handle exception from Wear. Using it will trigger an exception a phone when any will be thrown on Wear.
 */
public class CustomHandlerThrow implements ExceptionWearHandler{

    @Override
    /**
     * Exception handler with throwing it locally (on a phone) so it will be visible to the user, in Google Play, other crash analytics tools.
     */
    public void handleException(Throwable throwable, DataMap map) {
        throw new RuntimeException(throwable);
    }
}
