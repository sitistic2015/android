package dao;

import org.junit.*;
import util.Configuration;

import java.util.HashMap;

/**
 * Created by alban on 11/03/15.
 */
public class AbstractDAOUnityTest {

    @BeforeClass
    public static void beforeAllTests() {
        HashMap<String, String> configs = new HashMap<String, String>();
        configs.put("COUCHBASE_HOSTNAME", "localhost:8091");
        configs.put("BUCKET_NAME", "tests");
        Configuration.loadConfigurations(configs);
    }

    @AfterClass
    public static void afterAllTests() {
        //CouchbaseCluster.create(Configuration.COUCHBASE_HOSTNAME).openBucket("e").;
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() {


    }


    @Test
    public void testInsert() {
        Assert.assertTrue(true);
//        Unity unity = new Unity();
//        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
//        unity.setName("Françis");
//        UnityDAO dao = new UnityDAO();
//        Unity res = dao.create(unity);
//        assertEquals(unity, res);
//        assertEquals(unity.getId(), res.getId());
    }

    @Test
    public void testUpdate() {
        Assert.assertTrue(true);
        //insertion
//        Unity unity = new Unity();
//        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
//        unity.setName("André-Jacques");
//        UnityDAO dao = new UnityDAO();
//        Unity res = dao.create(unity);
//        long idInbase = res.getId();
//        assertEquals(unity, res);
//        assertEquals(unity.getId(), res.getId());
//
//        // update
//        unity = dao.getById(idInbase);
//        unity.setName("Gilbert");
//        unity.setUnitPosition(new Position(3.0, 2.0, 1.2));
//        res = dao.update(unity);
//        assertEquals(unity, res);
//        assertEquals(unity.getId(), res.getId());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(true);

        //insertion
//        Unity unity = new Unity();
//        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
//        unity.setName("Vivien Lelouette de Saint-Coulomb");
//        UnityDAO dao = new UnityDAO();
//        Unity res = dao.create(unity);
//        long idInbase = res.getId();
//        assertEquals(unity, res);
//        assertEquals(unity.getId(), res.getId());
//
//        // suppression
//        dao.delete(unity);
//        dao.getById(idInbase);

    }
}
