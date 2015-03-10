package entity;

/**
 * Coordinate geographic class
 * Created by mds on 10/03/15.
 * Class ${CLASS}
 */
public class Position {

    public Position(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * Abscissa

     */

    private double posX;
    /**
     * Ordinate
     */
    private double posY;
}
