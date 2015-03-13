
package dao;
import entity.Position;
import entity.Unity;
import org.junit.*;
import util.Configuration;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
/**
* Created by alban on 11/03/15.
*/
public class AbstractDAOUnityTest {
    private static UnityDAO dao;
    @BeforeClass
    public static void beforeAllTests()
    {
        HashMap<String,String> configs= new HashMap<String,String>();
        configs.put("COUCHBASE_HOSTNAME","37.59.58.42");
        configs.put("BUCKET_NAME","test");
        Configuration.loadConfigurations(configs);
        dao = new UnityDAO();
        dao.connect();
    }

    @AfterClass
    public static void afterAllTests()
    {
        dao.disconnect();
    }

    @Before
    public void beforeTest() throws Exception {
    }

    @After
    public void afterTest() {
    }

    @Test
    public void testInsert()
    {
        Unity unity = new Unity();
        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
        unity.setName("Françis");
        Unity res = dao.create(unity);
        assertEquals(unity, res);
        assertEquals(unity.getId(), res.getId());
    }
    @Test
    public void testUpdate()
    {
        //insertion
        Unity unity = new Unity();
        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
        unity.setName("André-Jacques");
        Unity res = dao.create(unity);
        long idInbase = res.getId();
        assertEquals(unity, res);
        assertEquals(unity.getId(), res.getId());
        // update
        unity = dao.getById(idInbase);
        unity.setName("Gilbert");
        unity.setUnitPosition(new Position(3.0,2.0,1.2));
        res = dao.update(unity);
        assertEquals(unity, res);
        assertEquals(unity.getId(), res.getId());
    }

    @Test
    public void testDelete()
    {
        //insertion
        Unity unity = new Unity();
        unity.setUnitPosition(new Position(4.0, 9.0, 19.0));
        unity.setName("Vivien Lelouette de Saint-Coulomb");
        Unity res = dao.create(unity);
        long idInbase = res.getId();
        assertEquals(unity, res);
        assertEquals(unity.getId(), res.getId());
        // suppression
        dao.delete(unity);
        dao.getById(idInbase);
    }
}