package pl.tajchert.exceptionwear;


import com.google.android.gms.wearable.DataMap;

public interface ExceptionWearHandler {
    public void handleException(Throwable throwable, DataMap dataMap);
}
