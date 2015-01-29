package pl.tajchert.exceptionwear;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ExceptionWearApp {
    private static final String TAG = "ExceptionWearApp";
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
            Log.d(TAG, "uncaughtException :" + ex.getMessage());
            // Pass the exception to a Service which will send the data upstream to your Smartphone/Tablet
            Intent errorIntent = new Intent(mContext, ExceptionService.class);
            errorIntent.putExtra("exception", ex);
            mContext.startService(errorIntent);

            // Let the default UncaughtExceptionHandler take it from here
            mDefaultUEH.uncaughtException(thread, ex);
        }
    };
}
