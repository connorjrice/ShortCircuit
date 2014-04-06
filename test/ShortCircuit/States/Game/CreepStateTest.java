/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.Game;

import ShortCircuit.Objects.CreepTraits;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
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
public class CreepStateTest {
    
    public CreepStateTest() {
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
     * Test of initialize method, of class CreepState.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        CreepState instance = new CreepState();
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attachCreepNode method, of class CreepState.
     */
    @Test
    public void testAttachCreepNode() {
        System.out.println("attachCreepNode");
        CreepState instance = new CreepState();
        instance.attachCreepNode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCreepSpawner method, of class CreepState.
     */
    @Test
    public void testCreateCreepSpawner() {
        System.out.println("createCreepSpawner");
        int index = 0;
        Vector3f spawnervec = null;
        CreepState instance = new CreepState();
        instance.createCreepSpawner(index, spawnervec);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildCreepSpawners method, of class CreepState.
     */
    @Test
    public void testBuildCreepSpawners() {
        System.out.println("buildCreepSpawners");
        ArrayList<Vector3f> _creepSpawnerVecs = null;
        ArrayList<String> _creepSpawnerDirs = null;
        CreepState instance = new CreepState();
        instance.buildCreepSpawners(_creepSpawnerVecs, _creepSpawnerDirs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCreep method, of class CreepState.
     */
    @Test
    public void testCreateCreep() {
        System.out.println("createCreep");
        CreepTraits ct = null;
        CreepState instance = new CreepState();
        instance.createCreep(ct);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of creepBuilder method, of class CreepState.
     */
    @Test
    public void testCreepBuilder() {
        System.out.println("creepBuilder");
        Vector3f spawnerVec = null;
        int spawnIndex = 0;
        CreepState instance = new CreepState();
        instance.creepBuilder(spawnerVec, spawnIndex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnerDir method, of class CreepState.
     */
    @Test
    public void testGetCreepSpawnerDir() {
        System.out.println("getCreepSpawnerDir");
        int index = 0;
        CreepState instance = new CreepState();
        String expResult = "";
        String result = instance.getCreepSpawnerDir(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumCreepsByLevel method, of class CreepState.
     */
    @Test
    public void testGetNumCreepsByLevel() {
        System.out.println("getNumCreepsByLevel");
        CreepState instance = new CreepState();
        int expResult = 0;
        int result = instance.getNumCreepsByLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class CreepState.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        CreepState instance = new CreepState();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnerList method, of class CreepState.
     */
    @Test
    public void testGetCreepSpawnerList() {
        System.out.println("getCreepSpawnerList");
        CreepState instance = new CreepState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnerList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseBounds method, of class CreepState.
     */
    @Test
    public void testGetBaseBounds() {
        System.out.println("getBaseBounds");
        CreepState instance = new CreepState();
        BoundingVolume expResult = null;
        BoundingVolume result = instance.getBaseBounds();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepNode method, of class CreepState.
     */
    @Test
    public void testGetCreepNode() {
        System.out.println("getCreepNode");
        CreepState instance = new CreepState();
        Node expResult = null;
        Node result = instance.getCreepNode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepList method, of class CreepState.
     */
    @Test
    public void testGetCreepList() {
        System.out.println("getCreepList");
        CreepState instance = new CreepState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepListSize method, of class CreepState.
     */
    @Test
    public void testGetCreepListSize() {
        System.out.println("getCreepListSize");
        CreepState instance = new CreepState();
        int expResult = 0;
        int result = instance.getCreepListSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAssetManager method, of class CreepState.
     */
    @Test
    public void testGetAssetManager() {
        System.out.println("getAssetManager");
        CreepState instance = new CreepState();
        AssetManager expResult = null;
        AssetManager result = instance.getAssetManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnivBox method, of class CreepState.
     */
    @Test
    public void testGetUnivBox() {
        System.out.println("getUnivBox");
        CreepState instance = new CreepState();
        Box expResult = null;
        Box result = instance.getUnivBox();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decPlrHealth method, of class CreepState.
     */
    @Test
    public void testDecPlrHealth() {
        System.out.println("decPlrHealth");
        int dam = 0;
        CreepState instance = new CreepState();
        instance.decPlrHealth(dam);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrBudget method, of class CreepState.
     */
    @Test
    public void testIncPlrBudget() {
        System.out.println("incPlrBudget");
        int value = 0;
        CreepState instance = new CreepState();
        instance.incPlrBudget(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrScore method, of class CreepState.
     */
    @Test
    public void testIncPlrScore() {
        System.out.println("incPlrScore");
        int inc = 0;
        CreepState instance = new CreepState();
        instance.incPlrScore(inc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of goToNextSpawner method, of class CreepState.
     */
    @Test
    public void testGoToNextSpawner() {
        System.out.println("goToNextSpawner");
        CreepState instance = new CreepState();
        instance.goToNextSpawner();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNextSpawner method, of class CreepState.
     */
    @Test
    public void testGetNextSpawner() {
        System.out.println("getNextSpawner");
        CreepState instance = new CreepState();
        int expResult = 0;
        int result = instance.getNextSpawner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEx method, of class CreepState.
     */
    @Test
    public void testGetEx() {
        System.out.println("getEx");
        CreepState instance = new CreepState();
        ScheduledThreadPoolExecutor expResult = null;
        ScheduledThreadPoolExecutor result = instance.getEx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class CreepState.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        CreepState instance = new CreepState();
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}