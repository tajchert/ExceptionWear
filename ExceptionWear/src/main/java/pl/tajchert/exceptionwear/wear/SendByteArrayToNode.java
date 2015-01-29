package pl.tajchert.exceptionwear.wear;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;


public class SendByteArrayToNode extends Thread {
    private byte[] objectArray;
    private Context context;

    /**
     * Internal ExceptionWear method, using it outside of library is possible but not supported or tested
     */
    public SendByteArrayToNode(byte[] objArray, Context ctx) {
        objectArray = objArray;
        context = ctx;
    }

    public void run() {
        if ((objectArray.length / 1024) > 100) {
            throw new RuntimeException("Object is too big to push it via Google Play Services");
        }
        GoogleApiClient googleApiClient = SendWearManager.getInstance(context);
        googleApiClient.blockingConnect(WearExceptionTools.CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
        for (Node node : nodes.getNodes()) {
            MessageApi.SendMessageResult result;
            result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), WearExceptionTools.MESSAGE_EXCEPTION_PATH, objectArray).await();

            if (!result.getStatus().isSuccess()) {
                Log.v(WearExceptionTools.EXCEPTION_WEAR_TAG, "ERROR: failed to send Message via Google Play Services");
            }
        }
    }
}