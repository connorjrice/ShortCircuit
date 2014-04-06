/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit.Controls;

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
@Suite.SuiteClasses({ShortCircuit.Controls.CreepSpawnerControlTest.class, ShortCircuit.Controls.BombControlTest.class, ShortCircuit.Controls.TowerControlTest.class, ShortCircuit.Controls.BaseControlTest.class, ShortCircuit.Controls.CreepControlTest.class})
public class ControlsSuite {

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