package entity;

/**
 * The interventions units.
 * Created by corentin on 10/03/15.
 */
public class Unity {
    private long id;
    private String unitType;
    private Position unitPosition;

    /**
     * Unity position getter
     *
     * @return Position
     */
    public Position getUnitPosition() {
        return unitPosition;
    }

    /**
     * Unity position setter
     *
     * @param unitPosition Position
     */
    public void setUnitPosition(Position unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * Unity type getter
     *
     * @return String
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * Unity type setter
     *
     * @param unitType String
     */
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     * Unity ID getter
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Unity ID setter
     *
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
    }
}
