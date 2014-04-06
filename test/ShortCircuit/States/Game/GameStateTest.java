/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.Game;

import ShortCircuit.Objects.LevelParams;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
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
public class GameStateTest {
    
    public GameStateTest() {
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
     * Test of initialize method, of class GameState.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        GameState instance = new GameState();
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class GameState.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float tpf = 0.0F;
        GameState instance = new GameState();
        instance.update(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attachWorldNode method, of class GameState.
     */
    @Test
    public void testAttachWorldNode() {
        System.out.println("attachWorldNode");
        GameState instance = new GameState();
        instance.attachWorldNode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLevelParams method, of class GameState.
     */
    @Test
    public void testSetLevelParams() {
        System.out.println("setLevelParams");
        LevelParams lp = null;
        GameState instance = new GameState();
        instance.setLevelParams(lp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of baseShoot method, of class GameState.
     */
    @Test
    public void testBaseShoot() {
        System.out.println("baseShoot");
        GameState instance = new GameState();
        instance.baseShoot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class GameState.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Object type = null;
        GameState instance = new GameState();
        String expResult = "";
        String result = instance.getCost(type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of touchHandle method, of class GameState.
     */
    @Test
    public void testTouchHandle() {
        System.out.println("touchHandle");
        Vector3f trans = null;
        Spatial target = null;
        GameState instance = new GameState();
        instance.touchHandle(trans, target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dropBomb method, of class GameState.
     */
    @Test
    public void testDropBomb() {
        System.out.println("dropBomb");
        Vector3f trans = null;
        GameState instance = new GameState();
        instance.dropBomb(trans);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createLight method, of class GameState.
     */
    @Test
    public void testCreateLight() {
        System.out.println("createLight");
        GameState instance = new GameState();
        instance.createLight();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createFloor method, of class GameState.
     */
    @Test
    public void testCreateFloor() {
        System.out.println("createFloor");
        Vector3f floorscale = null;
        String floortexloc = "";
        GameState instance = new GameState();
        instance.createFloor(floorscale, floortexloc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createBase method, of class GameState.
     */
    @Test
    public void testCreateBase() {
        System.out.println("createBase");
        String texloc = "";
        Vector3f basevec = null;
        Vector3f basescale = null;
        GameState instance = new GameState();
        instance.createBase(texloc, basevec, basescale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBeamState method, of class GameState.
     */
    @Test
    public void testGetBeamState() {
        System.out.println("getBeamState");
        GameState instance = new GameState();
        BeamState expResult = null;
        BeamState result = instance.getBeamState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTowerState method, of class GameState.
     */
    @Test
    public void testGetTowerState() {
        System.out.println("getTowerState");
        GameState instance = new GameState();
        TowerState expResult = null;
        TowerState result = instance.getTowerState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepState method, of class GameState.
     */
    @Test
    public void testGetCreepState() {
        System.out.println("getCreepState");
        GameState instance = new GameState();
        CreepState expResult = null;
        CreepState result = instance.getCreepState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWorldNode method, of class GameState.
     */
    @Test
    public void testGetWorldNode() {
        System.out.println("getWorldNode");
        GameState instance = new GameState();
        Node expResult = null;
        Node result = instance.getWorldNode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepNode method, of class GameState.
     */
    @Test
    public void testGetCreepNode() {
        System.out.println("getCreepNode");
        GameState instance = new GameState();
        Node expResult = null;
        Node result = instance.getCreepNode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepList method, of class GameState.
     */
    @Test
    public void testGetCreepList() {
        System.out.println("getCreepList");
        GameState instance = new GameState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepSpawnerList method, of class GameState.
     */
    @Test
    public void testGetCreepSpawnerList() {
        System.out.println("getCreepSpawnerList");
        GameState instance = new GameState();
        ArrayList expResult = null;
        ArrayList result = instance.getCreepSpawnerList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTowerList method, of class GameState.
     */
    @Test
    public void testGetTowerList() {
        System.out.println("getTowerList");
        GameState instance = new GameState();
        ArrayList expResult = null;
        ArrayList result = instance.getTowerList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBombMesh method, of class GameState.
     */
    @Test
    public void testGetBombMesh() {
        System.out.println("getBombMesh");
        GameState instance = new GameState();
        Sphere expResult = null;
        Sphere result = instance.getBombMesh();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumCreeps method, of class GameState.
     */
    @Test
    public void testSetNumCreeps() {
        System.out.println("setNumCreeps");
        int nc = 0;
        GameState instance = new GameState();
        instance.setNumCreeps(nc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCreepMod method, of class GameState.
     */
    @Test
    public void testSetCreepMod() {
        System.out.println("setCreepMod");
        int cm = 0;
        GameState instance = new GameState();
        instance.setCreepMod(cm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumCreeps method, of class GameState.
     */
    @Test
    public void testGetNumCreeps() {
        System.out.println("getNumCreeps");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getNumCreeps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepMod method, of class GameState.
     */
    @Test
    public void testGetCreepMod() {
        System.out.println("getCreepMod");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getCreepMod();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDebug method, of class GameState.
     */
    @Test
    public void testSetDebug() {
        System.out.println("setDebug");
        boolean isDebug = false;
        GameState instance = new GameState();
        instance.setDebug(isDebug);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelected method, of class GameState.
     */
    @Test
    public void testGetSelected() {
        System.out.println("getSelected");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getSelected();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlrBombs method, of class GameState.
     */
    @Test
    public void testSetPlrBombs() {
        System.out.println("setPlrBombs");
        int bombs = 0;
        GameState instance = new GameState();
        instance.setPlrBombs(bombs);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlrBombs method, of class GameState.
     */
    @Test
    public void testGetPlrBombs() {
        System.out.println("getPlrBombs");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getPlrBombs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrBombs method, of class GameState.
     */
    @Test
    public void testIncPlrBombs() {
        System.out.println("incPlrBombs");
        int inc = 0;
        GameState instance = new GameState();
        instance.incPlrBombs(inc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decPlrBombs method, of class GameState.
     */
    @Test
    public void testDecPlrBombs() {
        System.out.println("decPlrBombs");
        GameState instance = new GameState();
        instance.decPlrBombs();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlrLvl method, of class GameState.
     */
    @Test
    public void testSetPlrLvl() {
        System.out.println("setPlrLvl");
        int level = 0;
        GameState instance = new GameState();
        instance.setPlrLvl(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlrLvl method, of class GameState.
     */
    @Test
    public void testGetPlrLvl() {
        System.out.println("getPlrLvl");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getPlrLvl();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrLvl method, of class GameState.
     */
    @Test
    public void testIncPlrLvl() {
        System.out.println("incPlrLvl");
        GameState instance = new GameState();
        instance.incPlrLvl();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlrScore method, of class GameState.
     */
    @Test
    public void testSetPlrScore() {
        System.out.println("setPlrScore");
        int score = 0;
        GameState instance = new GameState();
        instance.setPlrScore(score);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrScore method, of class GameState.
     */
    @Test
    public void testIncPlrScore() {
        System.out.println("incPlrScore");
        int inc = 0;
        GameState instance = new GameState();
        instance.incPlrScore(inc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlrScore method, of class GameState.
     */
    @Test
    public void testGetPlrScore() {
        System.out.println("getPlrScore");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getPlrScore();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlrHealth method, of class GameState.
     */
    @Test
    public void testSetPlrHealth() {
        System.out.println("setPlrHealth");
        int health = 0;
        GameState instance = new GameState();
        instance.setPlrHealth(health);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlrHealth method, of class GameState.
     */
    @Test
    public void testGetPlrHealth() {
        System.out.println("getPlrHealth");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getPlrHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrHealth method, of class GameState.
     */
    @Test
    public void testIncPlrHealth() {
        System.out.println("incPlrHealth");
        GameState instance = new GameState();
        instance.incPlrHealth();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decPlrHealth method, of class GameState.
     */
    @Test
    public void testDecPlrHealth() {
        System.out.println("decPlrHealth");
        int dam = 0;
        GameState instance = new GameState();
        instance.decPlrHealth(dam);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPlrBudget method, of class GameState.
     */
    @Test
    public void testSetPlrBudget() {
        System.out.println("setPlrBudget");
        int budget = 0;
        GameState instance = new GameState();
        instance.setPlrBudget(budget);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlrBudget method, of class GameState.
     */
    @Test
    public void testGetPlrBudget() {
        System.out.println("getPlrBudget");
        GameState instance = new GameState();
        int expResult = 0;
        int result = instance.getPlrBudget();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incPlrBudget method, of class GameState.
     */
    @Test
    public void testIncPlrBudget() {
        System.out.println("incPlrBudget");
        int value = 0;
        GameState instance = new GameState();
        instance.incPlrBudget(value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decPlrBudget method, of class GameState.
     */
    @Test
    public void testDecPlrBudget() {
        System.out.println("decPlrBudget");
        int cost = 0;
        GameState instance = new GameState();
        instance.decPlrBudget(cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAssetManager method, of class GameState.
     */
    @Test
    public void testGetAssetManager() {
        System.out.println("getAssetManager");
        GameState instance = new GameState();
        AssetManager expResult = null;
        AssetManager result = instance.getAssetManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDebugMode method, of class GameState.
     */
    @Test
    public void testGetDebugMode() {
        System.out.println("getDebugMode");
        GameState instance = new GameState();
        boolean expResult = false;
        boolean result = instance.getDebugMode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class GameState.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        GameState instance = new GameState();
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEx method, of class GameState.
     */
    @Test
    public void testGetEx() {
        System.out.println("getEx");
        GameState instance = new GameState();
        ScheduledThreadPoolExecutor expResult = null;
        ScheduledThreadPoolExecutor result = instance.getEx();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getApp method, of class GameState.
     */
    @Test
    public void testGetApp() {
        System.out.println("getApp");
        GameState instance = new GameState();
        SimpleApplication expResult = null;
        SimpleApplication result = instance.getApp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}