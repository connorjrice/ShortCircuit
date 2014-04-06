/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.Game;

import ShortCircuit.Controls.TowerControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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
public class TowerStateTest {
    
    public TowerStateTest() {
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
     * Test of initialize method, of class TowerState.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        TowerState instance = new TowerState();
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attachTowerNode method, of class TowerState.
     */
    @Test
    public void testAttachTowerNode() {
        System.out.println("attachTowerNode");
        TowerState instance = new TowerState();
        instance.attachTowerNode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of towerSelected method, of class TowerState.
     */
    @Test
    public void testTowerSelected() {
        System.out.println("towerSelected");
        int tindex = 0;
        TowerState instance = new TowerState();
        instance.towerSelected(tindex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shortenTowers method, of class TowerState.
     */
    @Test
    public void testShortenTowers() {
        System.out.println("shortenTowers");
        TowerState instance = new TowerState();
        instance.shortenTowers();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of chargeTower method, of class TowerState.
     */
    @Test
    public void testChargeTower() {
        System.out.println("chargeTower");
        TowerState instance = new TowerState();
        instance.chargeTower();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upgradeTower method, of class TowerState.
     */
    @Test
    public void testUpgradeTower() {
        System.out.println("upgradeTower");
        TowerState instance = new TowerState();
        instance.upgradeTower();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createTower method, of class TowerState.
     */
    @Test
    public void testCreateTower() {
        System.out.println("createTower");
        int index = 0;
        Vector3f towervec = null;
        String type = "";
        TowerState instance = new TowerState();
        instance.createTower(index, towervec, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildUnbuiltTowers method, of class TowerState.
     */
    @Test
    public void testBuildUnbuiltTowers() {
        System.out.println("buildUnbuiltTowers");
        ArrayList<Vector3f> unbuiltTowerIn = null;
        TowerState instance = new TowerState();
        instance.buildUnbuiltTowers(unbuiltTowerIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildStarterTowers method, of class TowerState.
     */
    @Test
    public void testBuildStarterTowers() {
        System.out.println("buildStarterTowers");
        ArrayList<Integer> starterTowerIn = null;
        TowerState instance = new TowerState();
        instance.buildStarterTowers(starterTowerIn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeTowerTexture method, of class TowerState.
     */
    @Test
    public void testChangeTowerTexture() {
        System.out.println("changeTowerTexture");
        String matLoc = "";
        TowerControl control = null;
        TowerState instance = new TowerState();
        instance.changeTowerTexture(matLoc, control);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class TowerState.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        TowerState instance = new TowerState();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelectedTower method, of class TowerState.
     */
    @Test
    public void testGetSelectedTower() {
        System.out.println("getSelectedTower");
        TowerState instance = new TowerState();
        Spatial expResult = null;
        Spatial result = instance.getSelectedTower();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelectedNum method, of class TowerState.
     */
    @Test
    public void testGetSelectedNum() {
        System.out.println("getSelectedNum");
        TowerState instance = new TowerState();
        int expResult = 0;
        int result = instance.getSelectedNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepList method, of class TowerState.
     */
    @Test
    public void testGetCreepList() {
        System.out.println("getCreepList");
        TowerState instance = new TowerState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnerList method, of class TowerState.
     */
    @Test
    public void testGetCreepSpawnerList() {
        System.out.println("getCreepSpawnerList");
        TowerState instance = new TowerState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnerList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEx method, of class TowerState.
     */
    @Test
    public void testGetEx() {
        System.out.println("getEx");
        TowerState instance = new TowerState();
        ScheduledThreadPoolExecutor expResult = null;
        ScheduledThreadPoolExecutor result = instance.getEx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getApp method, of class TowerState.
     */
    @Test
    public void testGetApp() {
        System.out.println("getApp");
        TowerState instance = new TowerState();
        SimpleApplication expResult = null;
        SimpleApplication result = instance.getApp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBuiltTowerSize method, of class TowerState.
     */
    @Test
    public void testGetBuiltTowerSize() {
        System.out.println("getBuiltTowerSize");
        TowerState instance = new TowerState();
        Vector3f expResult = null;
        Vector3f result = instance.getBuiltTowerSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class TowerState.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        TowerState instance = new TowerState();
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stateAttached method, of class TowerState.
     */
    @Test
    public void testStateAttached() {
        System.out.println("stateAttached");
        AppStateManager stateManager = null;
        TowerState instance = new TowerState();
        instance.stateAttached(stateManager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stateDetached method, of class TowerState.
     */
    @Test
    public void testStateDetached() {
        System.out.println("stateDetached");
        AppStateManager stateManager = null;
        TowerState instance = new TowerState();
        instance.stateDetached(stateManager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}