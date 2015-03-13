package fr.istic.sit.selectionzone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;

import java.util.ArrayList;
import java.util.List;

import fr.istic.sit.coordsender.Coordinates;
import fr.istic.sit.coordsender.RequesterService;


public class MainActivity extends MapActivity {

    protected MapView map;
    protected LinearLayout actions;
    protected ScrollView scroller;
    protected List<GeoPoint> polyData = new ArrayList();

    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleClickOnMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doBindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        doUnbindService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doUnbindService();
    }

    protected void init() {
        this.setupMapView(new GeoPoint(48.12,-1.67), 12);
        this.actions=(LinearLayout)findViewById(R.id.actions);
        this.scroller=(ScrollView)findViewById(R.id.scroller);
    }

    /**
     * This will set up a basic MapQuest map with zoom controls
     */
    protected void setupMapView(GeoPoint pt, int zoom) {
        this.map = (MapView) findViewById(R.id.map);

        // set the zoom level
        map.getController().setZoom(zoom);

        // set the center point
        map.getController().setCenter(pt);

        // enable the zoom controls
        map.setBuiltInZoomControls(true);

    }

    /**
     * handle click on map
     * put marker where it is clicked
     * draw polyline when more than one marker
     */
    protected void handleClickOnMap(){

        final DefaultItemizedOverlay markers = initMarkers();

        final Overlay overlay = new Overlay() {

            int countMarker = 0;
            PolygonOverlay polygonOverlay = new PolygonOverlay(initLinePaint());

            /**
             * handle click on map
             */
            @Override
            public boolean onTap(GeoPoint p, MapView mapView) {
                OverlayItem marker = new OverlayItem(p,"","");
                markers.addItem(marker);
                polyData.add(p);
                countMarker++;
                if (polyData.size()==1) {
                    polygonOverlay.setData(polyData);
                    map.getOverlays().add(polygonOverlay);
                }
                return super.onTap(p, mapView);
            }
        };

        map.getOverlays().add(markers);
        map.getOverlays().add(overlay);
    }

    protected Paint initLinePaint(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        return paint;
    }

    protected DefaultItemizedOverlay initMarkers(){
        Drawable icon = getResources().getDrawable(R.drawable.location_marker);
        final DefaultItemizedOverlay markers = new DefaultItemizedOverlay(icon);
        return markers;
    }

    public void validateCoords(View view){
        List<Pair<Double,Double>> coords = new ArrayList();
        polyData.add(polyData.get(0));


        for (Overlay overlay : map.getOverlays()){
            System.out.println(overlay);
        }

        for (GeoPoint point : polyData) {
            coords.add(new Pair<Double, Double>(point.getLatitude(), point.getLongitude()));
        }

        for (Pair<Double, Double> coord : coords){
            addEventText("Point : "+coord.first+","+coord.second);
        }


        Coordinates coordinates = new Coordinates(coords);

        try {
            Message msg = Message.obtain(null,
                    RequesterService.MSG_ZONE);
            msg.replyTo = mMessenger;
            Bundle bundle = new Bundle();
            bundle.putParcelable("coord",coordinates);
            msg.setData(bundle);
            mService.send(msg);
        } catch (RemoteException e) {
            // In this case the service has crashed before we could even
            // do anything with it; we can count on soon being
            // disconnected (and then reconnected if it can be restarted)
            // so there is no need to do anything here.
        }
    }

    private void addEventText(String text){
        TextView tv = new TextView(MainActivity.this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        actions.addView(tv);
        scroller.fullScroll(ScrollView.FOCUS_DOWN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroller.fullScroll(ScrollView.FOCUS_DOWN);
            }
        },50);

    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**
     * Utility method for getting the text of an EditText, if no text was entered the hint is returned
     * @param editText
     * @return
     */
    public String getText(EditText editText){
        String s = editText.getText().toString();
        if("".equals(s)) s=editText.getHint().toString();
        return s;
    }

    /**
     * Hides the softkeyboard
     * @param v
     */
    public void hideSoftKeyboard(View v){
        //hides soft keyboard
        final InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }



    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequesterService.MSG_ZONE:
                    addEventText("Received from service: " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {


            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = new Messenger(service);
            addEventText("Attached.");

            // As part of the sample, tell the user what happened.
            Toast.makeText(MainActivity.this, "Service connected",
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            addEventText("Disconnected.");

            // As part of the sample, tell the user what happened.
            Toast.makeText(MainActivity.this, "Service disconnected",
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        bindService(new Intent(MainActivity.this,
                RequesterService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
        addEventText("Binding.");
    }

    void doUnbindService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            RequesterService.MSG_ZONE);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            addEventText("Unbinding.");
        }
    }

}
