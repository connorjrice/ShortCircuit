/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.DataStructures;

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
public class STCCreepCompareTest {
    
    public STCCreepCompareTest() {
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
     * Test of compare method, of class STCCreepCompare.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Spatial lhs = null;
        Spatial rhs = null;
        STCCreepCompare instance = null;
        int expResult = 0;
        int result = instance.compare(lhs, rhs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}