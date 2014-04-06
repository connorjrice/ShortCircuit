/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit;

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
@Suite.SuiteClasses({ShortCircuit.Threading.ThreadingSuite.class, ShortCircuit.TowerDefenseMainTest.class, ShortCircuit.States.StatesSuite.class, ShortCircuit.Objects.ObjectsSuite.class, ShortCircuit.MapXML.MapXMLSuite.class, ShortCircuit.DataStructures.DataStructuresSuite.class, ShortCircuit.Cheats.CheatsSuite.class, ShortCircuit.Controls.ControlsSuite.class, ShortCircuit.Factories.FactoriesSuite.class})
public class ShortCircuitSuite {

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