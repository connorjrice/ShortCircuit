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
public class STCTest {
    
    public STCTest() {
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
     * Test of offer method, of class STC.
     */
    @Test
    public void testOffer_Spatial() {
        System.out.println("offer");
        Spatial element = null;
        STC instance = null;
        instance.offer(element);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of offer method, of class STC.
     */
    @Test
    public void testOffer_STCNode() {
        System.out.println("offer");
        STC instance = null;
        //instance.offer(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class STC.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        STC instance = null;
        instance.remove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of peek method, of class STC.
     */
    @Test
    public void testPeek() {
        System.out.println("peek");
        STC instance = null;
        Spatial expResult = null;
        Spatial result = instance.peek();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class STC.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        STC instance = null;
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class STC.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        STC instance = null;
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class STC.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        STC instance = null;
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}