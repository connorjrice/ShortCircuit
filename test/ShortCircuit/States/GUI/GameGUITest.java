/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.States.GUI;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
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
public class GameGUITest {
    
    public GameGUITest() {
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
     * Test of initialize method, of class GameGUI.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        GameGUI instance = null;
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class GameGUI.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float tpf = 0.0F;
        GameGUI instance = null;
        instance.update(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stateAttached method, of class GameGUI.
     */
    @Test
    public void testStateAttached() {
        System.out.println("stateAttached");
        AppStateManager stateManager = null;
        GameGUI instance = null;
        instance.stateAttached(stateManager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stateDetached method, of class GameGUI.
     */
    @Test
    public void testStateDetached() {
        System.out.println("stateDetached");
        AppStateManager stateManager = null;
        GameGUI instance = null;
        instance.stateDetached(stateManager);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class GameGUI.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        GameGUI instance = null;
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}