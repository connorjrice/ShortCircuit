/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.MapXML;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Connor
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ShortCircuit.MapXML.MapGeneratorTest.class, ShortCircuit.MapXML.LevelParseExceptionTest.class, ShortCircuit.MapXML.XMLLoaderTest.class})
public class MapXMLSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}