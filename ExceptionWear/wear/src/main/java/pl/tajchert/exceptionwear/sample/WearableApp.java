package pl.tajchert.exceptionwear.sample;

import android.app.Application;

import pl.tajchert.exceptionwear.ExceptionWearApp;


public class WearableApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ExceptionWearApp.initialize(this);
    }
}
