package pl.tajchert.exceptionwear.sample;

import android.app.Application;

import pl.tajchert.exceptionwear.ExceptionWear;


public class WearableApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionWear.initialize(this);
    }
}
