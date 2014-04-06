/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Factories;

import com.jme3.scene.Geometry;
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
public class BaseFactoryTest {
    
    public BaseFactoryTest() {
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
     * Test of getBase method, of class BaseFactory.
     */
    @Test
    public void testGetBase() {
        System.out.println("getBase");
        BaseFactory instance = null;
        Geometry expResult = null;
        Geometry result = instance.getBase();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}