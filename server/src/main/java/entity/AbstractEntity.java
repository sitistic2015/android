package entity;

import java.util.Random;

/**
 * Created by corentin on 10/03/15.
 */
public abstract class AbstractEntity {
    /**
     * Unique id of entity
     */
    protected long id;

    /**
     * Type of entity
     */
    protected String type;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(long id)
    {
        this.id=id;
    }

    /**
     * Basic contruct, assign a random id
     */
    public AbstractEntity()
    {
        Random random = new Random();
        id = random.nextLong();
    }
}
