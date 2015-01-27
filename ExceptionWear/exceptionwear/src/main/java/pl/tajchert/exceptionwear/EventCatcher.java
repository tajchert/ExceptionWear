package pl.tajchert.exceptionwear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class EventCatcher extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //TODO handle exceptions on phone side
    }
}
