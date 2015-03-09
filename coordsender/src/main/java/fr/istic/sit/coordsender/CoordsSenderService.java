package fr.istic.sit.coordsender;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
            switch (msg.what) {
                case MSG_POINT:
                    //send point to the server
                    Coordinates coord = msg.getData().getParcelable("coord");
                    try {
                        postCoords("geo/geoposition/" + coord.getCoords().get(0).first + "/" + coord.getCoords().get(0).second);
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
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private String postCoords(String args) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(new HttpPost(URL + args));
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
