package dao;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import entity.Unity;
import util.Constant;
import util.Tools;

/**
 * Created by alban on 12/03/15.
 */
public class UnityDAO extends AbstractDAO<Unity> {

    public UnityDAO()
    {
        this.type = Constant.TYPE_UNITY;
    }

    @Override
    public Unity jsonDocumentToEntity(JsonDocument jsonDocument) {
        Unity u = new Unity();

        try {
            JsonObject content = jsonDocument.content();
            if (Constant.TYPE_UNITY.equals(content.get("type"))) {
                u.setId(Long.parseLong(jsonDocument.id()));
                u.setName((String)content.get("name"));
                u.setUnitPosition(Tools.jsonObjectToPosition((JsonObject)content.get("position")));
            } else {
                throw new IllegalArgumentException();
            }
        }
        catch(Throwable t)
        {
            u = null;
        }
        return u;
    }

    @Override
    public JsonDocument entityToJsonDocument(Unity u) {
        JsonObject jsonUser = JsonObject.empty()
                .put("type", u.getType())
                .put("position", Tools.entityToJsonDocument(u.getUnitPosition()))
                .put("name", u.getName());
        JsonDocument doc = JsonDocument.create(""+u.getId(), jsonUser);
        return doc;
    }

}
