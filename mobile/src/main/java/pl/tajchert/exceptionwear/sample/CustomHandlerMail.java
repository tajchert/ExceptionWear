package pl.tajchert.exceptionwear.sample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataMap;

import pl.tajchert.exceptionwear.ExceptionWearHandler;

/**
 * Sample Handler to show how to handle exception from Wear, it will show an Intent to send mail with error.
 */
public class CustomHandlerMail implements ExceptionWearHandler{
    private static final String TAG = "ErrorHandlerMail";
    private Context context;
    private String emailAddress;
    private String title;

    public CustomHandlerMail(Context context, String emailAddress, String title) {
        this.context = context;
        this.emailAddress = emailAddress;
        this.title = title;
    }

    @Override
    /**
     * Exception handler with sending an email by showing Intent to do so.
     */
    public void handleException(Throwable throwable, DataMap map) {
        if(context == null || emailAddress == null){
            return;
        }

        String emailContent =  "Received exception from Wear" + throwable.getMessage()
                + ", manufacturer: " + map.getString("manufacturer")
                + ", model: " + map.getString("model")
                + ", product: " + map.getString("product")
                + ", fingerprint: " + map.getString("fingerprint")
                + ", stack trace: " + Log.getStackTraceString(throwable);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("message/rfc822");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Exception from Wear");
        sendIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}
