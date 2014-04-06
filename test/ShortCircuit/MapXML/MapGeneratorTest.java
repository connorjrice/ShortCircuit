/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.MapXML;

import ShortCircuit.Objects.LevelParams;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Connor
 */
public class MapGeneratorTest {
    
    public MapGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of parseXML method, of class MapGenerator.
     */
    @Test
    public void testParseXML() {
        System.out.println("parseXML");
        MapGenerator instance = null;
        instance.parseXML();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnbuiltTowerVecs method, of class MapGenerator.
     */
    @Test
    public void testGetUnbuiltTowerVecs() {
        System.out.println("getUnbuiltTowerVecs");
        MapGenerator instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getUnbuiltTowerVecs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnVecs method, of class MapGenerator.
     */
    @Test
    public void testGetCreepSpawnVecs() {
        System.out.println("getCreepSpawnVecs");
        MapGenerator instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnVecs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnDirs method, of class MapGenerator.
     */
    @Test
    public void testGetCreepSpawnDirs() {
        System.out.println("getCreepSpawnDirs");
        MapGenerator instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnDirs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseVec method, of class MapGenerator.
     */
    @Test
    public void testGetBaseVec() {
        System.out.println("getBaseVec");
        MapGenerator instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getBaseVec();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseScale method, of class MapGenerator.
     */
    @Test
    public void testGetBaseScale() {
        System.out.println("getBaseScale");
        MapGenerator instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getBaseScale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFloorScale method, of class MapGenerator.
     */
    @Test
    public void testGetFloorScale() {
        System.out.println("getFloorScale");
        MapGenerator instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getFloorScale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStarterTowers method, of class MapGenerator.
     */
    @Test
    public void testGetStarterTowers() {
        System.out.println("getStarterTowers");
        MapGenerator instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getStarterTowers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevelParams method, of class MapGenerator.
     */
    @Test
    public void testGetLevelParams() {
        System.out.println("getLevelParams");
        MapGenerator instance = null;
        LevelParams expResult = null;
        LevelParams result = instance.getLevelParams();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}