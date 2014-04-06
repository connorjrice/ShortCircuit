/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Threading;

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
@Suite.SuiteClasses({ShortCircuit.Threading.MoveCreepTest.class, ShortCircuit.Threading.FindReachableSpawnersTest.class, ShortCircuit.Threading.FindCreepsTest.class, ShortCircuit.Threading.SpawnCreepTest.class, ShortCircuit.Threading.UpgradeTowerTest.class, ShortCircuit.Threading.FindBombVictimsTest.class})
public class ThreadingSuite {

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