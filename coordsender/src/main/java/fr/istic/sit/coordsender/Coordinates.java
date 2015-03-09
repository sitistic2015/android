package fr.istic.sit.coordsender;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jules on 12/02/15.
 */
public class Coordinates implements Parcelable {

    private List<Pair<Double, Double>> coords;

    public Coordinates(List<Pair<Double, Double>> coords) {
        this.coords = coords;
    }

    private Coordinates(Parcel in) {
        coords = new ArrayList<Pair<Double, Double>>();
        double _coords [][] = (double[][])in.readArray(ClassLoader.getSystemClassLoader());
        for(int i = 0; i < _coords.length; i++) {
            coords.add(new Pair<Double, Double>(_coords[i][0], _coords[i][1]));
        }
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
        dest.writeArray(_coords);
    }

    public static final Parcelable.Creator<Coordinates> CREATOR
            = new Parcelable.Creator<Coordinates>() {
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    public List<Pair<Double, Double>> getCoords() {
        return coords;
    }
}
