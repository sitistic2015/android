package entity;

/**
 * Created by corentin on 10/03/15.
 */
public abstract class Entity {
    protected String id;
    protected String type;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
