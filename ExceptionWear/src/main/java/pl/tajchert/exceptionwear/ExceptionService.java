package pl.tajchert.exceptionwear;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ExceptionService extends IntentService {
    private static final String TAG = ExceptionService.class.getSimpleName();

    private static final String EXTRA_EXCEPTION = TAG + "/EXTRA_EXCEPTION";

    public ExceptionService() {
        super(TAG);
    }

    private List<String> getNodes(GoogleApiClient mGoogleApiClient) {
        ArrayList<String> results= new ArrayList<String>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node : nodes.getNodes()) {
            results.add(node.getId());
        }
        return results;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        GoogleApiClient mGoogleAppiClient = new GoogleApiClient.Builder(ExceptionService.this)
                .addApi(Wearable.API)
                .build();
        mGoogleAppiClient.blockingConnect();

        List<String> nodes = getNodes(mGoogleAppiClient);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(intent.getSerializableExtra(EXTRA_EXCEPTION));

            byte[] exceptionData = bos.toByteArray();
            DataMap dataMap = new DataMap();

            // Add a bit of information on the Wear Device to pass a long with the exception
            dataMap.putString("board", Build.BOARD);
            dataMap.putString("fingerprint", Build.FINGERPRINT);
            dataMap.putString("model", Build.MODEL);
            dataMap.putString("manufacturer", Build.MANUFACTURER);
            dataMap.putString("product", Build.PRODUCT);

            dataMap.putByteArray("exception", exceptionData);

            // "Fire and forget" send to connected Smartphone/Tablet using the Wearable Message API
            if(nodes != null && nodes.size() > 0){
                Wearable.MessageApi.sendMessage(mGoogleAppiClient, nodes.get(0), "/exceptionwear/wear_error", dataMap.toByteArray());
            }

        } catch (IOException e) {
            e.printStackTrace();
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

    public static void reportException(Context context, Throwable ex) {
        Intent errorIntent = new Intent(context, ExceptionService.class);
        errorIntent.putExtra(EXTRA_EXCEPTION, ex);
        context.startService(errorIntent);
    }
}