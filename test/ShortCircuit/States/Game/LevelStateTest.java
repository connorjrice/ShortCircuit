/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.Game;

import ShortCircuit.MapXML.MapGenerator;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
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
public class LevelStateTest {
    
    public LevelStateTest() {
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
     * Test of initialize method, of class LevelState.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        LevelState instance = null;
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of begin method, of class LevelState.
     */
    @Test
    public void testBegin() {
        System.out.println("begin");
        LevelState instance = null;
        instance.begin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class LevelState.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float tpf = 0.0F;
        LevelState instance = null;
        instance.update(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newGame method, of class LevelState.
     */
    @Test
    public void testNewGame() {
        System.out.println("newGame");
        String levelname = "";
        LevelState instance = null;
        instance.newGame(levelname);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of debugGame method, of class LevelState.
     */
    @Test
    public void testDebugGame() {
        System.out.println("debugGame");
        LevelState instance = null;
        instance.debugGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class LevelState.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        LevelState instance = null;
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFloorMatLoc method, of class LevelState.
     */
    @Test
    public void testGetFloorMatLoc() {
        System.out.println("getFloorMatLoc");
        LevelState instance = null;
        String expResult = "";
        String result = instance.getFloorMatLoc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMG method, of class LevelState.
     */
    @Test
    public void testGetMG() {
        System.out.println("getMG");
        LevelState instance = null;
        MapGenerator expResult = null;
        MapGenerator result = instance.getMG();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseVec method, of class LevelState.
     */
    @Test
    public void testGetBaseVec() {
        System.out.println("getBaseVec");
        LevelState instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getBaseVec();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseScale method, of class LevelState.
     */
    @Test
    public void testGetBaseScale() {
        System.out.println("getBaseScale");
        LevelState instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getBaseScale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFloorScale method, of class LevelState.
     */
    @Test
    public void testGetFloorScale() {
        System.out.println("getFloorScale");
        LevelState instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getFloorScale();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnerVecs method, of class LevelState.
     */
    @Test
    public void testGetCreepSpawnerVecs() {
        System.out.println("getCreepSpawnerVecs");
        LevelState instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnerVecs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnbuiltTowerVecs method, of class LevelState.
     */
    @Test
    public void testGetUnbuiltTowerVecs() {
        System.out.println("getUnbuiltTowerVecs");
        LevelState instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getUnbuiltTowerVecs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class LevelState.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        LevelState instance = null;
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}