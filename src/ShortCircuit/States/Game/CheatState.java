package ShortCircuit.States.Game;

import ShortCircuit.Controls.TowerControl;
import ShortCircuit.Objects.Charges;
import ShortCircuit.MapXML.TowerParams;
import ShortCircuit.States.Game.GameState;
import ShortCircuit.States.Game.FriendlyState;
import ShortCircuit.States.Game.GraphicsState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;

/**
 * This state contains the code for l33t h4x.
 * @author Connor Rice
 */
public class CheatState extends AbstractAppState {
    private GameState GameState;
    private FriendlyState FriendlyState;
    
    private boolean dollaBillz;
    private boolean superMedic;
    private boolean levelOverload;
    
    private float slowItDownThere = 0f;
    private GraphicsState GraphicsState;
    
    public CheatState() {}
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.GameState = stateManager.getState(GameState.class);
        this.FriendlyState = stateManager.getState(FriendlyState.class);
        this.GraphicsState = stateManager.getState(GraphicsState.class);
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
        ArrayList<TowerParams> towerList = FriendlyState.getTowerList();
        for (int i = 0; i < towerList.size(); i++) {
            TowerControl control = towerList.get(i).getControl();
            for (int j = 0; i < 10; i++) {
                control.charges.add(new Charges("Towerc"));
                control.setBeamType("beam4");
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
        ArrayList<TowerParams> towerList = FriendlyState.getTowerList();
        for (int i = 0; i < towerList.size(); i++) {
            towerList.get(i).setScale(new Vector3f(30f,30f,10f));
        }
    }
    
    /**
     * Upgrades all of the towers to purple and adds a bunch of charges.
     */
    public void makeTowersPurp() {
        ArrayList<TowerParams> towerList = FriendlyState.getTowerList();
        for (int i = 0; i < towerList.size(); i++) {
            TowerParams tp = towerList.get(i);
            addPurptoPurp(tp.getControl());
            tp.setType("Tower4");
            GraphicsState.towerTextureCharged(tp);
        }
    }
    /**
     * Private class for adding purple charges
     * @param control the tower to be chargified
     */
    private void addPurptoPurp(TowerControl control) {
        for (int i = 0; i < 100; i++) {
            control.charges.add(new Charges("Tower4"));
        }
    }
    
    /**
     * Makes the towers all have the floor's material, and gives them super
     * base lasers that I forgot to take out. Also gives them 100 charges of
     * those super lasers.
     */
    public void makeTowersBadAss() {
        ArrayList<TowerParams> towerList = FriendlyState.getTowerList();
        for (int i = 0; i < towerList.size(); i++) {
            TowerParams tower = towerList.get(i);
            addBadAsstoBadAss(tower.getControl());
            tower.getControl().setBuilt();                    
            tower.setType("Tower4");
            GraphicsState.towerTextureCharged(tower);
        }
    }
    
    /**
     * Gives the wonky towers some super badass charges.
     * @param control 
     */
    private void addBadAsstoBadAss(TowerControl control) {
        for (int i = 0; i < 100; i++) {
            control.charges.add(new Charges("Towerc"));
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
            GameState.incPlrHealth(10);
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
