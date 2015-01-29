package pl.tajchert.exceptionwear;

import android.util.Log;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ExceptionDataListenerService extends WearableListenerService {
    private static final String TAG = "WearDataListenerService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if(messageEvent.getPath().equals("/exceptionwear/wear_error")) {
            DataMap map = DataMap.fromByteArray(messageEvent.getData());
            readException(map);
        }
    }
    private void readException(DataMap map) {
        ByteArrayInputStream bis = new ByteArrayInputStream(map.getByteArray("exception"));
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            Throwable ex = (Throwable) ois.readObject();
            Log.e(TAG, "Error from Wear" + ex.getMessage()
                    + ", manufacturer: " + map.getString("manufacturer")
                    + ", model: " + map.getString("model")
                    + ", product: " + map.getString("product")
                    + ", board: " + map.getString("board")
                    + ", fingerprint: " + map.getString("fingerprint")
                    + ", stack trace: " + Log.getStackTraceString(ex));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}