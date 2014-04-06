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
public class CreepControlTest {
    
    public CreepControlTest() {
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
     * Test of controlUpdate method, of class CreepControl.
     */
    @Test
    public void testControlUpdate() {
        System.out.println("controlUpdate");
        float tpf = 0.0F;
        CreepControl instance = null;
        instance.controlUpdate(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class CreepControl.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        CreepControl instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepHealth method, of class CreepControl.
     */
    @Test
    public void testGetCreepHealth() {
        System.out.println("getCreepHealth");
        CreepControl instance = null;
        int expResult = 0;
        int result = instance.getCreepHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepType method, of class CreepControl.
     */
    @Test
    public void testGetCreepType() {
        System.out.println("getCreepType");
        CreepControl instance = null;
        String expResult = "";
        String result = instance.getCreepType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decCreepHealth method, of class CreepControl.
     */
    @Test
    public void testDecCreepHealth() {
        System.out.println("decCreepHealth");
        int damage = 0;
        CreepControl instance = null;
        int expResult = 0;
        int result = instance.decCreepHealth(damage);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class CreepControl.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        CreepControl instance = null;
        int expResult = 0;
        int result = instance.getValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCreepLocation method, of class CreepControl.
     */
    @Test
    public void testGetCreepLocation() {
        System.out.println("getCreepLocation");
        CreepControl instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getCreepLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCreep method, of class CreepControl.
     */
    @Test
    public void testRemoveCreep() {
        System.out.println("removeCreep");
        CreepControl instance = null;
        instance.removeCreep();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of controlRender method, of class CreepControl.
     */
    @Test
    public void testControlRender() {
        System.out.println("controlRender");
        RenderManager rm = null;
        ViewPort vp = null;
        CreepControl instance = null;
        instance.controlRender(rm, vp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cloneForSpatial method, of class CreepControl.
     */
    @Test
    public void testCloneForSpatial() {
        System.out.println("cloneForSpatial");
        Spatial spatial = null;
        CreepControl instance = null;
        CreepControl expResult = null;
        CreepControl result = instance.cloneForSpatial(spatial);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class CreepControl.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        JmeImporter im = null;
        CreepControl instance = null;
        instance.read(im);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class CreepControl.
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        JmeExporter ex = null;
        CreepControl instance = null;
        instance.write(ex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}