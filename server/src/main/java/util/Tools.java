package util;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import entity.AbstractEntity;
import entity.Position;
import entity.Unity;

/**
 * Created by corentin on 10/03/15.
 */
public class Tools {
    public static JsonDocument entityToJsonDocument(AbstractEntity e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    public static JsonDocument entityToJsonDocument(Unity u)  {
        JsonObject jsonUser = JsonObject.empty()
                .put("type", u.getType())
                .put("position",Tools.entityToJsonDocument(u.getUnitPosition()))
                .put("name", u.getName());

        JsonDocument doc = JsonDocument.create(""+u.getId(), jsonUser);
        return doc;
    }

    public static JsonObject entityToJsonDocument(Position p)
    {
        JsonObject jsonUser = JsonObject.empty()
                .put("altitude",p.getAltitude())
                .put("latitude",p.getLatitude())
                .put("longitude",p.getLongitude());
        return jsonUser;
    }

    public static Position jsonObjectToPosition(JsonObject jsonObject)
    {
        Position p = new Position();
        p.setAltitude((Double)jsonObject.get("altitude"));
        p.setLatitude((Double)jsonObject.get("latitude"));
        p.setLongitude((Double)jsonObject.get("longitude"));
        return p;
    }
}