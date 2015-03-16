package dao;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import entity.GeoJsonDTO;
import util.Constant;
import util.Tools;

/**
 * Created by corentin on 13/03/15.
 */
public class GeoJsonDTODAO extends AbstractDAO<GeoJsonDTO>{

    /**
     * Contructeur UnityDAO
     */
    public GeoJsonDTODAO()
    {
        this.type = Constant.TYPE_GEOJSONDTO;
    }

    @Override
    public GeoJsonDTO jsonDocumentToEntity(JsonDocument jsonDocument) {
        GeoJsonDTO geo = new GeoJsonDTO();

        try {
            JsonObject content = jsonDocument.content();
            if (Constant.TYPE_GEOJSONDTO.equals(content.get("type"))) {
                geo.setId(Long.parseLong(jsonDocument.id()));
                geo.addZone(Tools.jsonArrayToZone(content.getArray("zone")));
            } else {
                throw new IllegalArgumentException();
            }
        }
        catch(Throwable t)
        {
            geo = null;
        }
        return geo;
    }

    @Override
    public JsonDocument entityToJsonDocument(GeoJsonDTO entity) {
        JsonObject jsonUser = JsonObject.empty()
                .put("type", entity.getType())
                .put("zone", Tools.zoneToJsonArray(entity.getCoordinates().get(0)));
        JsonDocument doc = JsonDocument.create(""+entity.getId(), jsonUser);
        return doc;
    }
}
