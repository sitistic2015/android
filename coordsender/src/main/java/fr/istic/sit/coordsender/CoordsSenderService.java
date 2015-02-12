package fr.istic.sit.coordsender;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

public class CoordsSenderService extends Service {

    static final int MSG_ZONE = 1;
    static final int MSG_POINT = 2;

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


}
