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

public class CoordsSenderService extends Service {

    public static final int MSG_ZONE = 1;
    public static final int MSG_POINT = 2;
    private static final String URL = "148.60.14.34:8088";

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
            Coordinates coords = msg.getData().getParcelable("coord");
            switch (msg.what) {
                case MSG_POINT:
                    //send point to the server
                    try {
                        postPoint("geo/geoposition/" + coords.getCoords().get(0).first + "/" + coords.getCoords().get(0).second);
                    } catch (IOException e) {
                        Message _msg = Message.obtain(null, MSG_POINT, e.getMessage());
                        try {
                            msg.replyTo.send(_msg);
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                case MSG_ZONE:
                    //send zone to the server
                    //{ "type": "Polygon",
                    //  "coordinates": [
                    //    [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0] ]
                    //    ]
                    //}
                    try {
                        Message _msg = Message.obtain(null, MSG_ZONE, postZone("geo/zone", coords));
                        try {
                            msg.replyTo.send(_msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        Message _msg = Message.obtain(null, MSG_ZONE, e.getMessage());
                        try {
                            msg.replyTo.send(_msg);
                        } catch (RemoteException e1) {
                            e1.printStackTrace();
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private String formatZone(Coordinates coords) {
        String stringCoords = "{\"type\": \"Polygon\", \"coordinates\":[";
        for(int i = 0; i < coords.getCoords().size() - 1; i++) {
            stringCoords += "[" + coords.getCoords().get(i).first + ", " + coords.getCoords().get(i).second + "],";
        }
        stringCoords += "[" + coords.getCoords().get(coords.getCoords().size() - 1).first + ", " + coords.getCoords().get(coords.getCoords().size() - 1).second + "],";
        stringCoords += "]]}";
        return stringCoords;
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private String postZone(String args, Coordinates coords) throws IOException {
        HttpPost request = new HttpPost(URL + args);
        try {
            request.setEntity(new ByteArrayEntity(
                    formatZone(coords).getBytes("UTF8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return post(request);
    }

    private String postPoint(String args) throws IOException {
        HttpPost request = new HttpPost(URL + args);
        return post(request);
    }
    private String post(HttpPost request) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(request);
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            String responseString = out.toString();
            out.close();
            return responseString;
        } else{
            //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
        }
    }
}
