/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Controls;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
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
public class TowerControlTest {
    
    public TowerControlTest() {
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
     * Test of controlUpdate method, of class TowerControl.
     */
    @Test
    public void testControlUpdate() {
        System.out.println("controlUpdate");
        float tpf = 0.0F;
        TowerControl instance = null;
        instance.controlUpdate(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllowedSpawners method, of class TowerControl.
     */
    @Test
    public void testGetAllowedSpawners() {
        System.out.println("getAllowedSpawners");
        TowerControl instance = null;
        int[] expResult = null;
        int[] result = instance.getAllowedSpawners();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decideShoot method, of class TowerControl.
     */
    @Test
    public void testDecideShoot() {
        System.out.println("decideShoot");
        TowerControl instance = null;
        instance.decideShoot();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of emptyTower method, of class TowerControl.
     */
    @Test
    public void testEmptyTower() {
        System.out.println("emptyTower");
        TowerControl instance = null;
        instance.emptyTower();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shootCreep method, of class TowerControl.
     */
    @Test
    public void testShootCreep() {
        System.out.println("shootCreep");
        TowerControl instance = null;
        instance.shootCreep();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addInitialCharges method, of class TowerControl.
     */
    @Test
    public void testAddInitialCharges() {
        System.out.println("addInitialCharges");
        TowerControl instance = null;
        instance.addInitialCharges();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeight method, of class TowerControl.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        TowerControl instance = null;
        int expResult = 0;
        int result = instance.getHeight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class TowerControl.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        TowerControl instance = null;
        int expResult = 0;
        int result = instance.getIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class TowerControl.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        TowerControl instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setType method, of class TowerControl.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String newtype = "";
        TowerControl instance = null;
        instance.setType(newtype);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSize method, of class TowerControl.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        Vector3f size = null;
        TowerControl instance = null;
        instance.setSize(size);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of controlRender method, of class TowerControl.
     */
    @Test
    public void testControlRender() {
        System.out.println("controlRender");
        RenderManager rm = null;
        ViewPort vp = null;
        TowerControl instance = null;
        instance.controlRender(rm, vp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cloneForSpatial method, of class TowerControl.
     */
    @Test
    public void testCloneForSpatial() {
        System.out.println("cloneForSpatial");
        Spatial spatial = null;
        TowerControl instance = null;
        Control expResult = null;
        Control result = instance.cloneForSpatial(spatial);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class TowerControl.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        JmeImporter im = null;
        TowerControl instance = null;
        instance.read(im);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class TowerControl.
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        JmeExporter ex = null;
        TowerControl instance = null;
        instance.write(ex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}