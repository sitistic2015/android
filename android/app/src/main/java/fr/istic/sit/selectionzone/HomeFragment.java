package fr.istic.sit.selectionzone;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.mapquest.android.maps.DefaultItemizedOverlay;
import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.Overlay;
import com.mapquest.android.maps.OverlayItem;
import com.mapquest.android.maps.PolygonOverlay;

import java.util.ArrayList;
import java.util.List;

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
        return rootView;
    }


    protected void init(View root) {
        this.setupMapView(root, new GeoPoint(48.12, -1.67), 12);
        this.actions=(LinearLayout)getActivity().findViewById(R.id.actions);
        this.scroller=(ScrollView)getActivity().findViewById(R.id.scroller);
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


}
