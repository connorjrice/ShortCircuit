/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.MapXML;

import com.jme3.asset.AssetInfo;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author Connor
 */
public class XMLLoaderTest {
    
    public XMLLoaderTest() {
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
     * Test of load method, of class XMLLoader.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        AssetInfo assetInfo = null;
        XMLLoader instance = new XMLLoader();
        Document expResult = null;
        Document result = instance.load(assetInfo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createDocFromStream method, of class XMLLoader.
     */
    @Test
    public void testCreateDocFromStream() {
        System.out.println("createDocFromStream");
        InputStream inputStream = null;
        Document expResult = null;
        Document result = XMLLoader.createDocFromStream(inputStream);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}