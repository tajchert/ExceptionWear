package pl.tajchert.exceptionwear;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.wearable.DataMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pl.tajchert.exceptionwear.wear.SendByteArrayToNode;
import pl.tajchert.exceptionwear.wear.WearExceptionTools;


public class ExceptionService extends IntentService {
    private static final String EXTRA_EXCEPTION = WearExceptionTools.EXCEPTION_WEAR_TAG + "/EXTRA_EXCEPTION";

    private ByteArrayOutputStream bos;
    private ObjectOutputStream oos;

    public ExceptionService() {
        super(WearExceptionTools.EXCEPTION_WEAR_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            new SendByteArrayToNode(createExceptionInformation(intent).toByteArray(), ExceptionService.this).start();
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException exx) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException exx) {
                // ignore close exception
            }
        }
    }

    private DataMap createExceptionInformation(Intent intent){

        bos = new ByteArrayOutputStream();
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(intent.getSerializableExtra(EXTRA_EXCEPTION));
        } catch (IOException e) {
            Log.e(WearExceptionTools.EXCEPTION_WEAR_TAG, "createExceptionInformation error while getting exception information.");
        }

        byte[] exceptionData = bos.toByteArray();
        DataMap dataMap = new DataMap();

        // Add a bit of information on the Wear Device to pass a long with the exception
        dataMap.putString("board", Build.BOARD);
        dataMap.putString("fingerprint", Build.FINGERPRINT);
        dataMap.putString("model", Build.MODEL);
        dataMap.putString("manufacturer", Build.MANUFACTURER);
        dataMap.putString("product", Build.PRODUCT);
        dataMap.putString("api_level", Integer.toString(Build.VERSION.SDK_INT));

        dataMap.putByteArray("exception", exceptionData);

        return dataMap;
    }

    public static void reportException(Context context, Throwable ex) {
        Intent errorIntent = new Intent(context, ExceptionService.class);
        errorIntent.putExtra(EXTRA_EXCEPTION, ex);
        context.startService(errorIntent);
    }
}