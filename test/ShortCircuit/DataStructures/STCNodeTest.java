/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.DataStructures;

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
public class STCNodeTest {
    
    public STCNodeTest() {
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
     * Test of getElement method, of class STCNode.
     */
    @Test
    public void testGetElement() {
        System.out.println("getElement");
        STCNode instance = null;
        Object expResult = null;
        Object result = instance.getElement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBottom method, of class STCNode.
     */
    @Test
    public void testSetBottom() {
        System.out.println("setBottom");
        STCNode instance = null;
        instance.setBottom(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBottom method, of class STCNode.
     */
    @Test
    public void testGetBottom() {
        System.out.println("getBottom");
        STCNode instance = null;
        STCNode expResult = null;
        STCNode result = instance.getBottom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nullify method, of class STCNode.
     */
    @Test
    public void testNullify() {
        System.out.println("nullify");
        STCNode instance = null;
        instance.nullify();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}