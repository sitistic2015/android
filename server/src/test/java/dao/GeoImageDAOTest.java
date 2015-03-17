package dao;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import entity.GeoImage;
import entity.Position;
import org.junit.*;
import util.Configuration;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by alban on 16/03/15.
 */
public class GeoImageDAOTest {

    private static GeoImageDAO dao = new GeoImageDAO();
    private static byte[] imagesBytes = new byte[16];
    @BeforeClass
    public static void beforeAllTests() {
        HashMap<String, String> configs = new HashMap<String, String>();
        configs.put("COUCHBASE_HOSTNAME","37.59.58.42");
        configs.put("BUCKET_NAME","test");
        Configuration.loadConfigurations(configs);
        dao.connect();
        for(int i = 0 ; i<imagesBytes.length; i = i +1)
        {
            imagesBytes[i] = 1;
        }
    }

    @AfterClass
    public static void afterAllTests() {
        //CouchbaseCluster.create(Configuration.COUCHBASE_HOSTNAME).openBucket("e").;
        dao.disconnect();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {


    }

    @Test
    public void test()
    {
        GeoImage geoImage = new GeoImage();
        geoImage.setCoordinates(new Position(4.0, 9.0, 19.0));
        geoImage.setImageIn64(Base64.encode(imagesBytes));
        dao.entityToJsonDocument(geoImage);
    }

    @Test
    public void testInsert() {
        GeoImage geoImage = new GeoImage();
        geoImage.setCoordinates(new Position(4.0, 9.0, 19.0));
        geoImage.setImageIn64(Base64.encode(imagesBytes));
        dao.entityToJsonDocument(geoImage);

        GeoImage res = dao.create(geoImage);
        assertEquals(geoImage, res);
        assertEquals(geoImage.getId(), res.getId());
    }

    @Test
    public void testUpdate() {

        //insertion
        GeoImage geoImage = new GeoImage();
        geoImage.setCoordinates(new Position(4.0, 9.0, 19.0));
        geoImage.setImageIn64(Base64.encode(imagesBytes));
        dao.entityToJsonDocument(geoImage);

        GeoImage res = dao.create(geoImage);
        long idInbase = res.getId();
        assertEquals(geoImage, res);
        assertEquals(geoImage.getId(), res.getId());

        // update
        geoImage = dao.getById(idInbase);
        geoImage.setCoordinates(new Position(3.0, 2.0, 1.2));
        res = dao.update(geoImage);
        assertEquals(geoImage, res);
        assertEquals(geoImage.getId(), res.getId());
    }

    @Test
    public void testDelete() {
        //insertion
        GeoImage geoImage = new GeoImage();
        geoImage.setCoordinates(new Position(4.0, 9.0, 19.0));
        geoImage.setImageIn64(Base64.encode(imagesBytes));

        GeoImage res = dao.create(geoImage);
        long idInbase = res.getId();
        assertEquals(geoImage, res);
        assertEquals(geoImage.getId(), res.getId());

        // suppression
        dao.delete(geoImage);
        assertNull(dao.getById(idInbase));
    }
}
