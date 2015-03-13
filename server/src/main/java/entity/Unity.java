package entity;

import util.Constant;

/**
 * The interventions units.
 * Created by corentin on 10/03/15.
 */
public class Unity extends AbstractEntity{
    private Position unitPosition;
    private String name;

    public Unity()
    {
        super();
        this.type = Constant.TYPE_UNITY;
    }
    /**
     * Unity position getter     *
     * @return Position
     */
    public Position getUnitPosition() {
        return unitPosition;
    }

    /**
     * Unity position setter     *
     * @param unitPosition Position
     */
    public void setUnitPosition(Position unitPosition) {
        this.unitPosition = unitPosition;
    }

    public void setName(String newName)
    {
        this.name = newName;
    }

    @Override
    public String toString() {
        return "Unity{"  +
                "type='" + type + "\'" +
                ", unitPosition=" + unitPosition +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unity unity = (Unity) o;

        if (name != null ? !name.equals(unity.name) : unity.name != null) return false;
        if (unitPosition != null ? !unitPosition.equals(unity.unitPosition) : unity.unitPosition != null) return false;
        if (type != null ? !type.equals(unity.type) : unity.type != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = unitPosition != null ? unitPosition.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public String getName()
    {
        return name;
    }
}
