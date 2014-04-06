/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Controls;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
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
public class CreepSpawnerControlTest {
    
    public CreepSpawnerControlTest() {
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
     * Test of controlUpdate method, of class CreepSpawnerControl.
     */
    @Test
    public void testControlUpdate() {
        System.out.println("controlUpdate");
        float tpf = 0.0F;
        CreepSpawnerControl instance = null;
        instance.controlUpdate(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIndex method, of class CreepSpawnerControl.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        CreepSpawnerControl instance = null;
        int expResult = 0;
        int result = instance.getIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of controlRender method, of class CreepSpawnerControl.
     */
    @Test
    public void testControlRender() {
        System.out.println("controlRender");
        RenderManager rm = null;
        ViewPort vp = null;
        CreepSpawnerControl instance = null;
        instance.controlRender(rm, vp);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cloneForSpatial method, of class CreepSpawnerControl.
     */
    @Test
    public void testCloneForSpatial() {
        System.out.println("cloneForSpatial");
        Spatial spatial = null;
        CreepSpawnerControl instance = null;
        Control expResult = null;
        Control result = instance.cloneForSpatial(spatial);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class CreepSpawnerControl.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        JmeImporter im = null;
        CreepSpawnerControl instance = null;
        instance.read(im);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of write method, of class CreepSpawnerControl.
     */
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        JmeExporter ex = null;
        CreepSpawnerControl instance = null;
        instance.write(ex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}