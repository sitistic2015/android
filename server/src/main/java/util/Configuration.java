package util;

import java.util.Map;

/**
 * Created by alban on 12/03/15.
 */
public class Configuration {
    public static String BUCKET_NAME;
    public static String COUCHBASE_HOSTNAME;

    /**
     * Load configurations
     * @param configs
     */
    public static void loadConfigurations(Map<String,String> configs)
    {
        BUCKET_NAME = configs.get("BUCKET_NAME");
        COUCHBASE_HOSTNAME = configs.get("COUCHBASE_HOSTNAME");
    }
}
