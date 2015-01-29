package pl.tajchert.exceptionwear.sample;

import android.app.Application;

import pl.tajchert.exceptionwear.ExceptionDataListenerService;


public class MyCustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionDataListenerService.setHandler(new CustomHandlerThrow());
    }
}
