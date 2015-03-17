package fr.istic.sit.selectionzone;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;

import java.util.ArrayList;
import java.util.List;

import fr.istic.sit.coordsender.Coordinates;
import fr.istic.sit.coordsender.RequesterService;

public class HomeFragment extends Fragment {
    protected MapView map;
    protected LinearLayout actions;
    protected ScrollView scroller;
    protected List<GeoPoint> polyData = new ArrayList();


    public HomeFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        handleClickOnMap();
        ((Button)rootView.findViewById(R.id.buttonValidate2) ).setOnClickListener(new View.OnClickListener() {
                                                                                      @Override
                                                                                      public void onClick(View v) {
                                                                                          validateCoords(v);
                                                                                      }
                                                                                  }
       );

        return rootView;
    }


    protected void init(View root) {
        this.setupMapView(root, new GeoPoint(48.12, -1.67), 12);
        this.actions=(LinearLayout)root.findViewById(R.id.actions2);
        this.scroller=(ScrollView)root.findViewById(R.id.scroller2);
    }



    protected void setupMapView(View root, GeoPoint pt, int zoom) {
        this.map = (MapView) root.findViewById(R.id.map2);

        // set the zoom level
        map.getController().setZoom(zoom);

        // set the center point
        map.getController().setCenter(pt);

        // enable the zoom controls
        map.setBuiltInZoomControls(true);

    }
    protected void handleClickOnMap(){

        final DefaultItemizedOverlay markers = initMarkers();

        final Overlay overlay = new Overlay() {
            PolygonOverlay polygonOverlay = new PolygonOverlay(initLinePaint());
            /**
             * handle click on map
             */
            @Override
            public boolean onTap(GeoPoint p, MapView mapView) {
                OverlayItem marker = new OverlayItem(p,"","");
                markers.addItem(marker);
                polyData.add(p);
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

    public void addEventText(String text){
        TextView tv = new TextView(getActivity());
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
    public void validateCoords(View view){
        List<Pair<Double,Double>> coords = new ArrayList();
        polyData.add(polyData.get(0));

        for (GeoPoint point : polyData) {
            coords.add(new Pair<Double, Double>(point.getLatitude(), point.getLongitude()));
        }

        for (Pair<Double, Double> coord : coords){


               addEventText("Point : " + coord.first + "," + coord.second);

        }


        Coordinates coordinates = new Coordinates(coords);

        MainActivity2 act = (MainActivity2) getActivity();


        try {
            Message msg = Message.obtain(null,
                    RequesterService.MSG_ZONE);
            msg.replyTo = act.mMessenger;
            Bundle bundle = new Bundle();
            bundle.putParcelable("coord",coordinates);
            msg.setData(bundle);
            act.mService.send(msg);
        } catch (RemoteException e) {
            // In this case the service has crashed before we could even
            // do anything with it; we can count on soon being
            // disconnected (and then reconnected if it can be restarted)
            // so there is no need to do anything here.
        }
    }


    private void displayDrone(double latitude, double longitude, String info) {
        final DefaultItemizedOverlay drones = new DefaultItemizedOverlay(getResources().getDrawable(R.drawable.ic_drone));
        OverlayItem drone = new OverlayItem(new GeoPoint(latitude, longitude), "Drone", info);
        drones.addItem(drone);
        map.getOverlays().add(drones);
    }

}
