package fr.istic.sit.coordsender;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.List;

/**
 * Created by jules on 12/02/15.
 */
public class Coords implements Parcelable {

    private List<Pair<Double, Double>> coords;

    public Coords(List<Pair<Double, Double>> coords) {
        this.coords = coords;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        double _coords [][]= new double[coords.size()][2];
        for(int i = 0; i < coords.size(); i++) {
            _coords[i][0] = coords.get(i).first;
            _coords[i][1] = coords.get(i).second;
        }
        
    }
}
