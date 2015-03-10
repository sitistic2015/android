package dao;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import util.Constant;

/**
 * Abstract class for DAO
 * AbstractDAO provides methods for DAO
 * Also use for connect and disconnect
 */
public abstract class AbstractDAO<Entity> {
    protected static Cluster currentCluster;

    private AbstractDAO() {}

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

    protected static void create(Entity e) {

    }
}
