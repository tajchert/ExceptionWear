package pl.tajchert.exceptionwear.sample;

import android.app.Application;

import pl.tajchert.exceptionwear.ExceptionDataListenerService;


public class MyCustomApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CustomHandlerMail customHandlerMail = new CustomHandlerMail(this, "cool@email.com", "Error on Wear device");
        ExceptionDataListenerService.setHandler(customHandlerMail);
        //ExceptionDataListenerService.setHandler(new CustomHandlerCrashlytics());
        //ExceptionDataListenerService.setHandler(new CustomHandlerLog());
    }
}
