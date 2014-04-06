/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Objects;

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
public class CreepTraitsTest {
    
    public CreepTraitsTest() {
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
     * Test of getDirection method, of class CreepTraits.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        CreepTraits instance = null;
        String expResult = "";
        String result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class CreepTraits.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        CreepTraits instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHealth method, of class CreepTraits.
     */
    @Test
    public void testGetHealth() {
        System.out.println("getHealth");
        CreepTraits instance = null;
        int expResult = 0;
        int result = instance.getHealth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpawnIndex method, of class CreepTraits.
     */
    @Test
    public void testGetSpawnIndex() {
        System.out.println("getSpawnIndex");
        CreepTraits instance = null;
        int expResult = 0;
        int result = instance.getSpawnIndex();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpawnPoint method, of class CreepTraits.
     */
    @Test
    public void testGetSpawnPoint() {
        System.out.println("getSpawnPoint");
        CreepTraits instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getSpawnPoint();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSize method, of class CreepTraits.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        CreepTraits instance = null;
        Vector3f expResult = null;
        Vector3f result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpeed method, of class CreepTraits.
     */
    @Test
    public void testGetSpeed() {
        System.out.println("getSpeed");
        CreepTraits instance = null;
        float expResult = 0.0F;
        float result = instance.getSpeed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getType method, of class CreepTraits.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        CreepTraits instance = null;
        String expResult = "";
        String result = instance.getType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTextureLocation method, of class CreepTraits.
     */
    @Test
    public void testGetTextureLocation() {
        System.out.println("getTextureLocation");
        CreepTraits instance = null;
        String expResult = "";
        String result = instance.getTextureLocation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}