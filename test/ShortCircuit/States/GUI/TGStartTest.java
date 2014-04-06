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
public class TGStartTest {
    
    public TGStartTest() {
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
     * Test of initialize method, of class TGStart.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        AppStateManager stateManager = null;
        Application app = null;
        TGStart instance = null;
        instance.initialize(stateManager, app);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of onStart method, of class TGStart.
     */
    @Test
    public void testOnStart() {
        System.out.println("onStart");
        String level = "";
        TGStart instance = null;
        instance.onStart(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toggle method, of class TGStart.
     */
    @Test
    public void testToggle() {
        System.out.println("toggle");
        TGStart instance = null;
        instance.toggle();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mainWindowShown method, of class TGStart.
     */
    @Test
    public void testMainWindowShown() {
        System.out.println("mainWindowShown");
        TGStart instance = null;
        boolean expResult = false;
        boolean result = instance.mainWindowShown();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class TGStart.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        float tpf = 0.0F;
        TGStart instance = null;
        instance.update(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanup method, of class TGStart.
     */
    @Test
    public void testCleanup() {
        System.out.println("cleanup");
        TGStart instance = null;
        instance.cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}