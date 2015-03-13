package entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mds on 11/03/15.
 * Class ${CLASS}
 */
public class Zone {


    public Zone getSurvolZone() {
        return survolZone;
    }

    public void setSurvolZone(Zone survolZone) {
        this.survolZone = survolZone;
    }

    public Iterator<Zone> exclusionZoneIterator() {
        Iterator it = zoneArray.iterator();

        return it;
    }

    public void addExclusionZone(Zone zone) {
        this.zoneArray.add(zone);
    }

    private Zone survolZone;

    private List<Zone> zoneArray = new ArrayList<Zone>();
    List<Position> zone = new ArrayList<Position>();

    public Iterator<Position> positionIterator() {
        Iterator it = zone.iterator();

        return it;
    }
}
