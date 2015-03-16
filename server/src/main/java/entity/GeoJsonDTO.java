package entity;


import util.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arno on 09/03/15.
 */
public class GeoJsonDTO extends AbstractEntity implements Serializable {
    //    private Zone zone;
    List<Zone> coordinates = new ArrayList<Zone>();
//    private List<List<List<Double>>> coordinates = new ArrayList<List<List<Double>>>();

    public GeoJsonDTO() {
        super();
        this.type = Constant.TYPE_GEOJSONDTO;
    }

    public List<Zone> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Zone> coordinates) {
        this.coordinates = coordinates;
    }

    public void addZone(Zone zone) {
        this.coordinates.add(zone);
    }

    //
//    public List<List<List<Double>>> getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(List<List<List<Double>>> coordinates) {
//        this.coordinates = coordinates;
//    }
}


