package pl.tajchert.exceptionwear;

import android.content.Context;
import android.util.Log;

import pl.tajchert.exceptionwear.wear.WearExceptionTools;


public class ExceptionWear {
    private static Context mContext;
    private static Thread.UncaughtExceptionHandler mDefaultUEH;

    public static void initialize(Context context){
        mContext = context;
        mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mWearUEH);
    }

    private static Thread.UncaughtExceptionHandler mWearUEH = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(final Thread thread, final Throwable ex) {
            if(mContext == null){
                return;
            }
            Log.d(WearExceptionTools.EXCEPTION_WEAR_TAG, "uncaughtException :" + ex.getMessage());
            // Pass the exception to a Service which will send the data upstream to your Smartphone/Tablet
            ExceptionService.reportException(mContext, ex);

            // Let the default UncaughtExceptionHandler take it from here
            mDefaultUEH.uncaughtException(thread, ex);
        }
    };
}
