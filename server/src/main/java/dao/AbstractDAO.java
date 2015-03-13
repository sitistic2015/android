package dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import entity.AbstractEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Configuration;
import util.Constant;

import java.util.List;

/**
 * Abstract class for DAO
 * AbstractDAO provides methods for DAO
 * Also use for connect and disconnect
 */
public abstract class AbstractDAO<T extends AbstractEntity> {
    /**
     * CurrentConnection
     */
    protected static Cluster currentCluster;

    protected static String type;
    /**
     * public constructor for singleton
     */
    public AbstractDAO() {

    }

    /**
     * Connect to BDD and
     * @return Bucket to communicate with couchbase
     */
    protected final static Bucket connect() {
        // Connect to a cluster
        currentCluster = CouchbaseCluster.create(Configuration.COUCHBASE_HOSTNAME);

        // Open the default bucket
        Bucket bucket = currentCluster.openBucket(Configuration.BUCKET_NAME);
        return bucket;
    }

    /**
     * Disconnect BDD
     */
    protected final static void disconnect() {
        // Disconnect from the cluster
        currentCluster.disconnect();
    }

    /**
     * Create an entity
     * @param e entity to create
     */
    public final T create(T e) {
        Bucket bucket = connect();
        JsonDocument res = bucket.insert(entityToJsonDocument(e));
        disconnect();
        return jsonDocumentToEntity(res);
    }

    /**
     * Delete an entity
     * @param e
     */
    public final T delete(T e) {
        Bucket bucket = connect();
        JsonDocument res = bucket.remove(""+e.getId());
        disconnect();
        return jsonDocumentToEntity(res);
    }

    /**
     * Update entity
     * @param e
     */
    public final T update(T e) {
        Bucket bucket = connect();
        JsonDocument res = bucket.upsert(entityToJsonDocument(e));
        disconnect();
        return jsonDocumentToEntity(res);
    }

    /**
     * GetAll
     * @return
     */
    public final List<T> getAll()
    {
        throw new NotImplementedException();
    }

    /**
     * GetById
     * @return
     */
    public final T getById(long id)
    {
        Bucket bucket = connect();
        JsonDocument res = bucket.get(""+id);
        disconnect();
        return jsonDocumentToEntity(res);
    }

    /**
     * Transform a jsonDocument
     * @param jsonDocument document to transform
     * @return
     */
    public abstract T jsonDocumentToEntity(JsonDocument jsonDocument);

    public abstract JsonDocument entityToJsonDocument(T entity);

}
