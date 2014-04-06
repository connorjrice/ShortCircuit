/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.Game;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
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
public class BeamStateTest {
    
    public BeamStateTest() {
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
     * Test of initialize method, of class BeamState.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        BeamState instance = new BeamState();
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class BeamState.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float tpf = 0.0F;
        BeamState instance = new BeamState();
        instance.update(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initAssets method, of class BeamState.
     */
    @Test
    public void testInitAssets() {
        System.out.println("initAssets");
        BeamState instance = new BeamState();
        instance.initAssets();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of attachBeamNode method, of class BeamState.
     */
    @Test
    public void testAttachBeamNode() {
        System.out.println("attachBeamNode");
        BeamState instance = new BeamState();
        instance.attachBeamNode();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reset method, of class BeamState.
     */
    @Test
    public void testReset() {
        System.out.println("reset");
        BeamState instance = new BeamState();
        instance.reset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeLaserBeam method, of class BeamState.
     */
    @Test
    public void testMakeLaserBeam() {
        System.out.println("makeLaserBeam");
        Vector3f origin = null;
        Vector3f target = null;
        String type = "";
        BeamState instance = new BeamState();
        instance.makeLaserBeam(origin, target, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class BeamState.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        BeamState instance = new BeamState();
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}