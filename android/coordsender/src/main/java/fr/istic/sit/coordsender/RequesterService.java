package fr.istic.sit.coordsender;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Pair;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RequesterService extends Service {

    public static final int MSG_ZONE = 1;
    public static final int MSG_POINT = 2;
    public static final int MSG_IMG = 3;
    public static final int MSG_AGENTS = 4;

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Coordinates coordinates;
            switch (msg.what) {
                case MSG_POINT:
                    //send point to the server
                    coordinates = msg.getData().getParcelable("coord");
                    new RequestTask(MSG_POINT, coordinates, msg.replyTo).execute();
                    break;
                case MSG_ZONE:
                    //send zone to the server
                    coordinates = msg.getData().getParcelable("coord");
                    new RequestTask(MSG_ZONE, coordinates, msg.replyTo).execute();
                    break;
                case MSG_IMG:
                    //ask server for images
                    new RequestTask(MSG_IMG, msg.replyTo).execute();
                    break;
                case MSG_AGENTS:
                    //ask server for agents
                    new RequestTask(MSG_AGENTS, msg.replyTo).execute();
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());
}
