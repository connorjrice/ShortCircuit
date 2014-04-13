package ShortCircuit.Tower.Cheats;

import ShortCircuit.Tower.Objects.Charges;
import ShortCircuit.Tower.Controls.TowerControl;
import ShortCircuit.Tower.States.Game.GameState;
import ShortCircuit.Tower.States.Game.LevelState;
import ShortCircuit.Tower.States.Game.TowerState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;

/**
 * This state will contain a bunch of l33t h4x.
 * Mostly for debugging purposes. And because it's fun.
 * RULES: Don't look at any code. Only use what the IDE lets you, and
 * see why documentation is pretty important. +5 l33t programmer points if
 * you can get it to compile the first time. GO!
 * *and no runtime errors
 * *you can do the GUI later
 * @author Connor Rice
 */
public class CheatState extends AbstractAppState {
    private SimpleApplication app;
    private GameState GameState;
    private LevelState LevelState;
    private TowerState TowerState;
    
    private boolean dollaBillz;
    private boolean superMedic;
    private boolean levelOverload;
    
    private float slowItDownThere = 0f;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.GameState = this.app.getStateManager().getState(GameState.class);
        this.LevelState = this.app.getStateManager().getState(LevelState.class);
        this.TowerState = this.app.getStateManager().getState(TowerState.class);

    }
    
    /**
     * Gives the player a million dollars.
     */
    public void giveMeAMillionDollars() {
        GameState.setPlrBudget(1000000);
    }
    
    /**
     * Gives the player a billion dollars.
     */
    public void giveMeABillionDollars() {
        GameState.setPlrBudget(1000000000);
    }
    
    /**
     * Adds 10 charges of type base to all towers fo FREE.
     */
    public void badassAmmoH4KX() {
        ArrayList<Spatial> towerList = TowerState.towerList;
        for (int i = 0; i < towerList.size(); i++) {
            TowerControl control = towerList.get(i).getControl(TowerControl.class);
            for (int j = 0; i < 10; i++) {
                control.charges.add(new Charges("baseLaser"));
                control.setBeamType("beam4");
                control.setBuilt();
            }
        }
    }
    
    /**
     * Creates a ridiculous amount of bombs.
     */
    public void megaBombEveryWhere() {
        Vector3f potato = Vector3f.ZERO;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                //dropbomb
                potato.add(0f,j*.001f,0f);
            }
            potato.add(i*.001f, 0f,0f);
        }
    }
    
    /**
     * Makes towers quite large.
     */
    public void makeTowersHUGE() {
        ArrayList<Spatial> towerList = TowerState.towerList;
        for (int i = 0; i < towerList.size(); i++) {
            Spatial tower = towerList.get(i);
            tower.setLocalScale(new Vector3f(10f,10f,10f));
        }
    }
    
    /**
     * Upgrades all of the towers to purple and adds a bunch of charges.
     */
    public void makeTowersPurp() {
        ArrayList<Spatial> towerList = TowerState.towerList;
        for (int i = 0; i < towerList.size(); i++) {
            TowerControl control = towerList.get(i).getControl(TowerControl.class);
            addPurptoPurp(control);
            control.setTowerType("tower4");
            TowerState.changeTowerTexture(TowerState.tow4MatLoc, control);
        }
    }
    /**
     * Private class for adding purple charges
     * @param control the tower to be chargified
     */
    private void addPurptoPurp(TowerControl control) {
        for (int i = 0; i < 100; i++) {
            control.charges.add(new Charges("tower4"));
        }
    }
    
    /**
     * Makes the towers all have the floor's material, and gives them super
     * base lasers that I forgot to take out. Also gives them 100 charges of
     * those super lasers.
     */
    public void makeTowersBadAss() {
        ArrayList<Spatial> towerList = TowerState.towerList;
        for (int i = 0; i < towerList.size(); i++) {
            TowerControl tower = towerList.get(i).getControl(TowerControl.class);
            addBadAsstoBadAss(tower);
            tower.setBuilt();                    
            tower.setTowerType("globpop");
            tower.setBeamType("Bomb");
            TowerState.changeTowerTexture("Materials/Bomb.j3m", tower);
        }
    }
    /**
     * Gives the wonky towers some super badass charges.
     * @param control 
     */
    private void addBadAsstoBadAss(TowerControl control) {
        for (int i = 0; i < 100; i++) {
            control.charges.add(new Charges("towerc"));
        }
    }
    
    /**
     * Toggles free money.
     */
    public void dollaDollaBillsYall() {
        dollaBillz = !dollaBillz;
    }
    
    /**
     * Toggles super healing.
     */
    public void iNeedASuperMedic() {
        superMedic = !superMedic;
    }
    
    /**
     * Toggles upping the level every 5 seconds.
     */
    public void bringMeThePainIWantToFeelThePain() {
        levelOverload = !levelOverload;
    }
    
    @Override
    public void update(float tpf) {
        if (dollaBillz) {
            GameState.incPlrBudget(20);
        }
        if (superMedic) {
            GameState.incPlrHealth();
        }
        if (levelOverload && slowItDownThere > 1f) {
            GameState.nextLevel();
            slowItDownThere = 0;
        }
        else {
            slowItDownThere += tpf;
        }
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}
