/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ShortCircuit;

import com.jme3.renderer.ViewPort;
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
public class TowerDefenseMainTest {
    
    public TowerDefenseMainTest() {
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
     * Test of main method, of class TowerDefenseMain.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        TowerDefenseMain.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of simpleInitApp method, of class TowerDefenseMain.
     */
    @Test
    public void testSimpleInitApp() {
        System.out.println("simpleInitApp");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.simpleInitApp();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of showTGMenu method, of class TowerDefenseMain.
     */
    @Test
    public void testShowTGMenu() {
        System.out.println("showTGMenu");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.showTGStart();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startGame method, of class TowerDefenseMain.
     */
    @Test
    public void testStartGame() {
        System.out.println("startGame");
        boolean debug = false;
        String levelName = "";
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.startGame(debug, levelName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detachGameStates method, of class TowerDefenseMain.
     */
    @Test
    public void testDetachGameStates() {
        System.out.println("detachGameStates");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.detachGameStates();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of pause method, of class TowerDefenseMain.
     */
    @Test
    public void testPause() {
        System.out.println("pause");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.pause();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of gameover method, of class TowerDefenseMain.
     */
    @Test
    public void testGameover() {
        System.out.println("gameover");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.gameover();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of disableStates method, of class TowerDefenseMain.
     */
    @Test
    public void testDisableStates() {
        System.out.println("disableStates");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.disableStates();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableStates method, of class TowerDefenseMain.
     */
    @Test
    public void testEnableStates() {
        System.out.println("enableStates");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.enableStates();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of goToMainMenu method, of class TowerDefenseMain.
     */
    @Test
    public void testGoToMainMenu() {
        System.out.println("goToMainMenu");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.goToMainMenu();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class TowerDefenseMain.
     */
    @Test
    public void testDestroy() {
        System.out.println("destroy");
        TowerDefenseMain instance = new TowerDefenseMain();
        instance.destroy();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}