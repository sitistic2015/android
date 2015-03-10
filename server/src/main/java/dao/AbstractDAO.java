package dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import entity.Entity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.Constant;
import util.Tools;

import java.util.List;

/**
 * Abstract class for DAO
 * AbstractDAO provides methods for DAO
 * Also use for connect and disconnect
 */
public abstract class AbstractDAO<T extends Entity> {
    protected static Cluster currentCluster;
    private static AbstractDAO instance;

    private AbstractDAO() {}

    public static AbstractDAO getInstance() {
        return instance;
    }


    protected static Bucket connect() {
        // Connect to a cluster
        currentCluster = CouchbaseCluster.create(Constant.COUCHBASE_HOSTNAME);

        // Open the default bucket
        Bucket bucket = currentCluster.openBucket(Constant.BUCKET_NAME);
        return bucket;
    }

    protected static void disconnect() {
        // Disconnect from the cluster
        currentCluster.disconnect();
    }

    public void create(T e) {
        Bucket bucket = connect();
        bucket.insert(Tools.entityToJsonDocument(e));
        disconnect();
    }

    public void remove(T e) {
        Bucket bucket = connect();
        bucket.remove(e.getId());
        disconnect();
    }

    public void update(T e) {
        Bucket bucket = connect();
        bucket.upsert(Tools.entityToJsonDocument(e));
        disconnect();
    }

    public List<T> getAll() throws NotImplementedException {
        throw new NotImplementedException();
    }
}
